package br.com.cineagora;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost;
import android.widget.TabWidget;
import br.com.cineagora.util.Cronometro;
import br.com.cineagora.util.GlobalCinema;
import br.com.cineagora.util.Util;
import br.com.cinemodel.model.Cinema;
import br.com.cinemodel.view.CinemaElementView;

public class MainActivity extends FragmentActivity {
	List<CinemaElementView> cinemas = new ArrayList<CinemaElementView>();
	TabHost mTabHost;
    ViewPager  mViewPager;
    TabsAdapter mTabsAdapter;

	@Override
	public View onCreateView(String name, Context context, AttributeSet attrs) {
		return super.onCreateView(name, context, attrs);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		

		Cronometro __cron = new Cronometro("Recupera fazendo Rest, ou obtendo dados salvos no device");
		__cron.inicia();

		if (savedInstanceState == null || !savedInstanceState.containsKey(Util.LABEL_CINEMAS)) {
			Util.inicializaStrictMode();
			Location location = getLocalizacao();
			// Abre nova Task(Thread) conforme classe privada abaixo
			new GetAddressTask(this).execute(location);

			__cron.finaliza("Recuparacao COM processo Rest");
		} else { // Fim audit
			cinemas = savedInstanceState.getParcelableArrayList(Util.LABEL_CINEMAS);
			__cron.finaliza("Recuparacao SEM processo Rest");
		}
		
		mTabHost = (TabHost)findViewById(android.R.id.tabhost);
        mTabHost.setup();

        mViewPager = (ViewPager)findViewById(R.id.pager);

        mTabsAdapter = new TabsAdapter(this, mTabHost, mViewPager);
        
        mTabsAdapter.addTab(mTabHost.newTabSpec("inicio").setIndicator("Inicio"),
                FragmentStackSupport.CountingFragment.class, null);
        mTabsAdapter.addTab(mTabHost.newTabSpec("cinema").setIndicator("Cinemas"),
        		CinemaFragment.class, null);
        mTabsAdapter.addTab(mTabHost.newTabSpec("filme").setIndicator("Filmes"),
                LoaderCursorSupport.CursorLoaderListFragment.class, null);
        mTabsAdapter.addTab(mTabHost.newTabSpec("extras").setIndicator("Extras"),
                LoaderCustomSupport.AppListFragment.class, null);
        mTabsAdapter.addTab(mTabHost.newTabSpec("raul").setIndicator("Re-Gay"),
                LoaderThrottleSupport.ThrottledLoaderListFragment.class, null);

        if (savedInstanceState != null) {
            mTabHost.setCurrentTabByTag(savedInstanceState.getString("tab"));
        }
	}
	
	private class GetAddressTask extends AsyncTask<Location, Void, String> {
		Context mContext;
		private String pais;
		private String estado;
		private String cidade;

		public GetAddressTask(Context context) {
			super();
			mContext = context;
		}

		@Override
		protected String doInBackground(Location... params) {
			// ## Pega endereco ###
			Geocoder geocoder = new Geocoder(mContext, Locale.getDefault());
			Location loc = params[0];
			List<Address> addresses = null;

			// Tenta ate 3 vezes. Geralmente vai na primeira. Quando muito na
			// segunda.
			for (int i = 0; 2 > i; i++) {
				try {
					addresses = geocoder.getFromLocation(loc.getLatitude(), loc.getLongitude(), 1);

				} catch (IOException e) {
					continue;
				}
				if (i >= 1)
					Log.w("Loop de tentativas pra pega enderecos", "" + i);
				break;
			}
			if (addresses != null && addresses.size() > 0) {
				Address address = addresses.get(0);

				cidade = address.getAdminArea();
				estado = address.getLocality();
				pais = address.getCountryName();
				// String sub = address.getSubLocality();

				if (exiteCidadeEEstado()) {
					
					cinemas = Util.fazRequestParaCache((cidade + Util.URL_DELIMITADOR + estado).trim());
					//Objeto global da aplicacao, ou seja, compartilhado entre activities, fragmentos etc.
					((GlobalCinema)getApplication()).setListaCinema(cinemas);

					return (cidade + Util.URL_DELIMITADOR + estado);
				} else {
					throw new RuntimeException("Não existe cidade e estado");
				}
			}
			return null;
		}

		private boolean exiteCidadeEEstado() {
			return (estado != null && !estado.equals("")) || cidade != null && !cidade.equals("");
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	protected void onSaveInstanceState(Bundle budleState) {
		Cronometro __cron = new Cronometro("ON_SAVE_INST_STATE");
		__cron.inicia();
		budleState.putParcelableArrayList(Util.LABEL_CINEMAS, (ArrayList<CinemaElementView>)cinemas);
		super.onSaveInstanceState(budleState);
		__cron.finaliza();

	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
	}
	/**
     * This is a helper class that implements the management of tabs and all
     * details of connecting a ViewPager with associated TabHost.  It relies on a
     * trick.  Normally a tab host has a simple API for supplying a View or
     * Intent that each tab will show.  This is not sufficient for switching
     * between pages.  So instead we make the content part of the tab host
     * 0dp high (it is not shown) and the TabsAdapter supplies its own dummy
     * view to show as the tab content.  It listens to changes in tabs, and takes
     * care of switch to the correct paged in the ViewPager whenever the selected
     * tab changes.
     */
    public static class TabsAdapter extends FragmentPagerAdapter
            implements TabHost.OnTabChangeListener, ViewPager.OnPageChangeListener {
        private final Context mContext;
        private final TabHost mTabHost;
        private final ViewPager mViewPager;
        private final ArrayList<TabInfo> mTabs = new ArrayList<TabInfo>();

        static final class TabInfo {
            private final String tag;
            private final Class<?> clss;
            private final Bundle args;

            TabInfo(String _tag, Class<?> _class, Bundle _args) {
                tag = _tag;
                clss = _class;
                args = _args;
            }
        }

        static class DummyTabFactory implements TabHost.TabContentFactory {
            private final Context mContext;

            public DummyTabFactory(Context context) {
                mContext = context;
            }

            @Override
            public View createTabContent(String tag) {
                View v = new View(mContext);
                v.setMinimumWidth(0);
                v.setMinimumHeight(0);
                return v;
            }
        }

        public TabsAdapter(FragmentActivity activity, TabHost tabHost, ViewPager pager) {
            super(activity.getSupportFragmentManager());
            mContext = activity;
            mTabHost = tabHost;
            mViewPager = pager;
            mTabHost.setOnTabChangedListener(this);
            mViewPager.setAdapter(this);
            mViewPager.setOnPageChangeListener(this);
        }

        public void addTab(TabHost.TabSpec tabSpec, Class<?> clss, Bundle args) {
            tabSpec.setContent(new DummyTabFactory(mContext));
            String tag = tabSpec.getTag();

            TabInfo info = new TabInfo(tag, clss, args);
            mTabs.add(info);
            mTabHost.addTab(tabSpec);
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return mTabs.size();
        }

        @Override
        public Fragment getItem(int position) {
            TabInfo info = mTabs.get(position);
            return Fragment.instantiate(mContext, info.clss.getName(), info.args);
        }

        @Override
        public void onTabChanged(String tabId) {
            int position = mTabHost.getCurrentTab();
            mViewPager.setCurrentItem(position);
        }

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }

        @Override
        public void onPageSelected(int position) {
            // Unfortunately when TabHost changes the current tab, it kindly
            // also takes care of putting focus on it when not in touch mode.
            // The jerk.
            // This hack tries to prevent this from pulling focus out of our
            // ViewPager.
            TabWidget widget = mTabHost.getTabWidget();
            int oldFocusability = widget.getDescendantFocusability();
            widget.setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);
            mTabHost.setCurrentTab(position);
            widget.setDescendantFocusability(oldFocusability);
        }

        @Override
        public void onPageScrollStateChanged(int state) {
        }
    }

	@TargetApi(Build.VERSION_CODES.FROYO)
	private Location getLocalizacao() {
		// Teste Locale
		Context c = getBaseContext();
		Resources r = c.getResources();
		Configuration config = r.getConfiguration();
		Locale currentLocale = config.locale;

		LocationManager locationManager = (LocationManager) getApplicationContext().getSystemService(LOCATION_SERVICE);

		List<String> providers = locationManager.getProviders(true);
		Location location = null;

		for (String p : providers) {
			// melhor opcao: GPS
			if (p.equals(LocationManager.GPS_PROVIDER)) {
				return locationManager.getLastKnownLocation(p);
			}// segunda melhor: Endereco Antena Wireless
			else if (p.equals(LocationManager.NETWORK_PROVIDER)) {
				location = locationManager.getLastKnownLocation(p);
				continue;
			}// ultimo caso: Ultima Ocorrencia
			else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.FROYO && location == null) {
				location = locationManager.getLastKnownLocation(p);
			}
		}
		
		if (location == null)
			throw new RuntimeException("Nao veio Location");
		
		Log.d("Location", "Selecionado via " + location.getProvider());

		return location;
	}


	@Override
	protected void onStart() {
		super.onStart();
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	protected void onRestart() {
		super.onRestart();
	}

	@Override
	protected void onPause() {
		super.onPause();
	}

	@Override
	protected void onStop() {
		super.onStop();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

}