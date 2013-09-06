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
import android.support.v4.app.FragmentActivity;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import br.com.cineagora.util.Cronometro;
import br.com.cineagora.util.Util;
import br.com.cinemodel.view.CinemaElementView;

public class MainActivity extends FragmentActivity {
	private static final String CINEMAS = "cinemas";
	protected static final String CINEMA = "cinema";
	String s = null;

	ArrayList<CinemaElementView> cinemas = null;

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

		if (savedInstanceState == null || !savedInstanceState.containsKey(CINEMAS)) {
			Util.inicializaStrictMode();
			// Abre nova Task(Thread) conforme classe privada abaixo
			// AsyncTask<Location, Void, String> enderecosTask =
			Location location = getLocalizacao();
			new GetAddressTask(this).execute(location);

			// cinemas = Util.recuperaObjetoListaDeCinemasDoSite();
			__cron.finaliza("Recuparacao COM processo Rest");
		} else { // Fim audit
			cinemas = savedInstanceState.getParcelableArrayList(CINEMAS);
			__cron.finaliza("Recuparacao SEM processo Rest");
		}

		// ArrayAdapter<CinemaElementView> cinemaAdapter = new
		// ArrayAdapter<CinemaElementView>(this,
		// android.R.layout.simple_list_item_1, cinemas);
		//
		//
		//
		// ListView listaCinemaView = (ListView)
		// findViewById(R.id.lista_cinema);
		// listaCinemaView.setAdapter(cinemaAdapter);
		//
		// listaCinemaView.setOnItemClickListener(new OnItemClickListener() {
		// @Override
		// public void onItemClick(AdapterView<?> av, View v, int position, long
		// id) {
		// CinemaElementView cinema = cinemas.get(position);
		// FilmeFragment listaFilmeFragment = new FilmeFragment();
		// Bundle bundleCinema = new Bundle();
		// bundleCinema.putParcelable(CINEMA, cinema);
		// listaFilmeFragment.setArguments(bundleCinema);
		//
		// FragmentTransaction transaction =
		// getSupportFragmentManager().beginTransaction();
		//
		// transaction.replace(R.id.fragment_container, listaFilmeFragment);
		// transaction.addToBackStack(null);
		// transaction.commit();
		// }
		//
		// });
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
		Location locationTemp = null;

		for (String p : providers) {
			locationTemp = locationManager.getLastKnownLocation(p);
			// melhor opcao: GPS
			if (p.equals(LocationManager.GPS_PROVIDER) && locationTemp != null) {
				return locationTemp;
			}// segunda melhor: Endereco Antena Wireless
			else if (p.equals(LocationManager.NETWORK_PROVIDER) && locationTemp != null) {
				location = locationTemp;
				continue;
			}// ultimo caso: Ultima Ocorrencia
			else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.FROYO && location == null) {
				location = locationTemp;
			}
		}

		if (location == null)
			throw new RuntimeException("Não veio Location");

		return location;
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
			
			//Tenta ate 3 vezes. Geralmente vai na primeira. Quando muito na segunda.
			for (int i = 0; 2 > i; i++) {
				try {
					addresses = geocoder.getFromLocation(loc.getLatitude(), loc.getLongitude(), 1);
					
				} catch (IOException e) {
					continue;
				}
				if(i >= 1)
					Log.w("Loop de tentativas pra pega enderecos", ""+i);
				break;
			}
			if (addresses != null && addresses.size() > 0) {
				Address address = addresses.get(0);

				cidade = address.getAdminArea();
				estado = address.getLocality();
				pais = address.getCountryName();
				// String sub = address.getSubLocality();

				if (exiteCidadeEEstado()) {

					Util.fazRequestParaCache((cidade + Util.URL_DELIMITADOR + estado).trim());

					return (cidade + Util.URL_DELIMITADOR + estado);
				}
				return "Endereco nao encontrado";
			} else {
				return "Endereco nao encontrado";
			}
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
		budleState.putParcelableArrayList(CINEMAS, cinemas);
		super.onSaveInstanceState(budleState);
		__cron.finaliza();

	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
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
