package br.com.cineagora;

import java.util.List;
import java.util.Locale;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import br.com.cineagora.util.Util;
import br.com.cinemodel.model.Cinema;
import br.com.cinemodel.model.Filme;
import br.com.cinemodel.view.FilmeCartazView;

public class FilmeFragment extends ListFragment {

	private ListView listaCinemaView;
	private ListView listaFilmeView;
	private List<Filme> filmes;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		Bundle args = getArguments();

		Cinema cinema = (Cinema) args.get(Util.LABEL_CINEMA);
		filmes = cinema.getFilmes();

		ArrayAdapter<Filme> filmeAdapter = new ArrayAdapter<Filme>(getActivity(),
				android.R.layout.simple_list_item_1, filmes);
		setListAdapter(filmeAdapter);

		listaCinemaView = (ListView) getActivity().findViewById(R.id.cinema_view);
		listaFilmeView = (ListView) getActivity().findViewById(R.id.filme_view);

		listaCinemaView.setVisibility(View.INVISIBLE);
		listaFilmeView.setVisibility(View.VISIBLE);

//		listaFilmeView.setOnItemClickListener(new OnItemClickListener() {
//			@Override
//			public void onItemClick(AdapterView<?> av, View v, int position, long id) {
//				FilmeCartazView cinema = (FilmeCartazView) filmes.get(position);
//
//				DetalhesFilmeFragment listaFilmeFragment = new DetalhesFilmeFragment();
//				Bundle bundleCinema = new Bundle();
//				bundleCinema.putParcelable(CINEMA, cinema);
//				listaFilmeFragment.setArguments(bundleCinema);
//
//				FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//
//				transaction.replace(R.id.fragment_container, listaFilmeFragment);
//				transaction.addToBackStack(null);
//				transaction.commit();
//			}
//
//		});

		return super.onCreateView(inflater, container, savedInstanceState);
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		listaCinemaView.setVisibility(View.VISIBLE);
		listaFilmeView.setVisibility(View.INVISIBLE);
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