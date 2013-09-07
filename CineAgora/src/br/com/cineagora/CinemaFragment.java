package br.com.cineagora;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import br.com.cineagora.util.Cronometro;
import br.com.cineagora.util.GlobalCinema;
import br.com.cineagora.util.Util;
import br.com.cinemodel.view.CinemaElementView;



public class CinemaFragment extends ListFragment {

	private ListView listaCinemaView;
	private List<CinemaElementView> cinemas = new ArrayList<CinemaElementView>();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		Log.d("Criou ", "CinemaFrag");
		// so cria nova task se nao houver lsita de cinemas
		if (savedInstanceState == null || !savedInstanceState.containsKey(Util.LABEL_CINEMAS)) {
			new AguardaCinemaTask(GlobalCinema.getAppContext()).execute("");
			View v = inflater.inflate(R.layout.lista_cinema, container, false);
			
			listaCinemaView = (ListView) v.findViewById(R.id.cinema_view);

			listaCinemaView.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> av, View v, int position, long id) {
					CinemaElementView cinema = cinemas.get(position);
					FilmeFragment listaFilmeFragment = new FilmeFragment();
					Bundle bundleCinema = new Bundle();
					bundleCinema.putParcelable(Util.LABEL_CINEMA, cinema);
					listaFilmeFragment.setArguments(bundleCinema);
					FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();

					transaction.replace(R.id.fragment_container, listaFilmeFragment);
					transaction.addToBackStack(null);
					transaction.commit();
				}

			});
			
		} else {
			cinemas = savedInstanceState.getParcelableArrayList(Util.LABEL_CINEMAS);
			ArrayAdapter<CinemaElementView> cinemaAdapter = new ArrayAdapter<CinemaElementView>(getActivity(),
					android.R.layout.simple_list_item_1, cinemas);
			setListAdapter(cinemaAdapter);
		}

		return super.onCreateView(inflater, container, savedInstanceState);
	}

	@Override
	public void onSaveInstanceState(Bundle budleState) {
		Cronometro __cron = new Cronometro("ON_SAVE_INST_STATE");
		__cron.inicia();
		budleState.putParcelableArrayList(Util.LABEL_CINEMAS, (ArrayList<CinemaElementView>) cinemas);
		super.onSaveInstanceState(budleState);
		__cron.finaliza();

	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
	}

	private class AguardaCinemaTask extends AsyncTask<String, Void, List<CinemaElementView>> {
		Context mContext;

		public AguardaCinemaTask(Context context) {
			super();
			mContext = context;
			Log.d("Criou ", "CinemaTask");
		}

		@Override
		protected List<CinemaElementView> doInBackground(String... params) {
			Log.d("Rodou", "doInBackground()");
			// Aguarda retorno do AppGlobal
			// Create an empty adapter we will use to display the loaded data.
			GlobalCinema globalCinema = (GlobalCinema) getActivity().getApplication();

			if (globalCinema != null && globalCinema.getListaCinema() != null) {
				Log.d("ListaCinemas", " ja estava preenchida");
				cinemas = globalCinema.getListaCinema();
			} else {

				// fica no laco, ate a lista de cinema do GlobalCinema for
				// preenchida
				Log.d("Entrou", " no loop()");
				while (true) {
					globalCinema = (GlobalCinema) getActivity().getApplication();
					if (globalCinema != null && globalCinema.getListaCinema() != null) {
						cinemas = globalCinema.getListaCinema();
						break;
					} else {
						try {
							Thread.sleep(500);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
				Log.d("Saiu", "do loop() ... cinemas = " + cinemas);
			}
			if (cinemas == null)
				throw new RuntimeException("Deveria vir um GlobalCinema");
			return cinemas;
		}

		@Override
		protected void onPostExecute(List<CinemaElementView> cinemas) {
			super.onPostExecute(cinemas);
			Log.d("Rodou ", "onPostExecute() com cinemas = " + cinemas);

			ArrayAdapter<CinemaElementView> cinemaAdapter = new ArrayAdapter<CinemaElementView>(getActivity(),
					android.R.layout.simple_list_item_1, cinemas);
			setListAdapter(cinemaAdapter);
		}
	}

	@Override
	public void onStart() {
		super.onStart();
	}

	@Override
	public void onResume() {
		super.onResume();
	}

	@Override
	public void onPause() {
		super.onPause();
	}

	@Override
	public void onStop() {
		super.onStop();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}

}
