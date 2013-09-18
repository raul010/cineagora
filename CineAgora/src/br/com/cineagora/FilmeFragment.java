package br.com.cineagora;

import java.util.ArrayList;
import java.util.List;

import android.app.ListActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import br.com.cineagora.util.Util;
import br.com.cinemodel.view.CinemaElementView;
import br.com.model.Filme;

public class FilmeFragment extends ListActivity {

	private View listaCinemaView;
	private ListView listaFilmeView;
	private List<Filme> filmes = new ArrayList<Filme>();

	private View v;
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.lista_cinema);
		
		//v = inflater.inflate(R.layout.lista_cinema, container, false);
		
		Bundle args = getIntent().getExtras();
		Log.d("1", "passou");
		CinemaElementView cinema = ((CinemaElementView) args.getParcelable(Util.LABEL_CINEMA)).cinema;
		filmes.addAll(cinema.getFilmes());
		Log.d("onCreate()", "cinema = " + cinema.toString());

		Log.d("2", "passou");
		ArrayAdapter<Filme> filmeAdapter = new ArrayAdapter<Filme>(this, android.R.layout.simple_list_item_1,
				filmes);
		setListAdapter(filmeAdapter);
		
		Log.d("onCreate()", "filmes = " + filmes.toString());

		Log.d("3", "passou");
//		listaCinemaView = findViewById(android.R.id.content);
//		listaFilmeView = (ListView) findViewById(R.id.filme_view);

		Log.d("4", "passou");
		// listaCinemaView.setVisibility(View.INVISIBLE);
		//listaFilmeView.setVisibility(View.VISIBLE);

		Log.d("5", "passou");

		// listaFilmeView.setOnItemClickListener(new OnItemClickListener() {
		// @Override
		// public void onItemClick(AdapterView<?> av, View v, int position, long
		// id) {
		// FilmeCartazView cinema = (FilmeCartazView) filmes.get(position);
		//
		// DetalhesFilmeFragment listaFilmeFragment = new
		// DetalhesFilmeFragment();
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
//	@Override
//	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//		v = inflater.inflate(R.layout.lista_cinema, container, false);
//		return v;
//
//	}
//
//	@Override
//	public void onActivityCreated(Bundle savedInstanceState) {
//		super.onActivityCreated(savedInstanceState);
//
//		Bundle args = getArguments();
//		Log.d("1", "passou");
//		Cinema cinema = (Cinema) args.get(Util.LABEL_CINEMA);
//		filmes.addAll(cinema.getFilmes());
//
//		Log.d("2", "passou");
//		ArrayAdapter<Filme> filmeAdapter = new ArrayAdapter<Filme>(getActivity(), android.R.layout.simple_list_item_1,
//				filmes);
//		setListAdapter(filmeAdapter);
//
//		Log.d("3", "passou");
//		listaCinemaView = getActivity().findViewById(android.R.id.content);
//		listaFilmeView = (ListView) v.findViewById(R.id.filme_view);
//
//		Log.d("4", "passou");
//		// listaCinemaView.setVisibility(View.INVISIBLE);
//		//listaFilmeView.setVisibility(View.VISIBLE);
//
//		Log.d("5", "passou");
//
//		// listaFilmeView.setOnItemClickListener(new OnItemClickListener() {
//		// @Override
//		// public void onItemClick(AdapterView<?> av, View v, int position, long
//		// id) {
//		// FilmeCartazView cinema = (FilmeCartazView) filmes.get(position);
//		//
//		// DetalhesFilmeFragment listaFilmeFragment = new
//		// DetalhesFilmeFragment();
//		// Bundle bundleCinema = new Bundle();
//		// bundleCinema.putParcelable(CINEMA, cinema);
//		// listaFilmeFragment.setArguments(bundleCinema);
//		//
//		// FragmentTransaction transaction =
//		// getSupportFragmentManager().beginTransaction();
//		//
//		// transaction.replace(R.id.fragment_container, listaFilmeFragment);
//		// transaction.addToBackStack(null);
//		// transaction.commit();
//		// }
//		//
//		// });
//	}

//	@Override
//	public void onDestroyView() {
//		super.onDestroyView();
//		// listaCinemaView.setVisibility(View.VISIBLE);
//		//listaFilmeView.setVisibility(View.INVISIBLE);
//	}

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