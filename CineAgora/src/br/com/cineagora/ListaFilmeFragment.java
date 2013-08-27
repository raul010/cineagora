package br.com.cineagora;

import java.util.List;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import br.com.cinemodel.model.Cinema;
import br.com.cinemodel.model.Filme;

public class ListaFilmeFragment extends ListFragment {

	private View cinemaView;
	private View filmeView;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		
		Bundle args = getArguments();
		
		Cinema cinema = (Cinema) args.get(MainActivity.CINEMA);
		List<Filme> filmes = cinema.getFilmes();
		
		ArrayAdapter<Filme> filmeAdapter = new ArrayAdapter<Filme>(getActivity(),
				android.R.layout.simple_list_item_1, filmes);
		setListAdapter(filmeAdapter);
		
		cinemaView = getActivity().findViewById(R.id.lista_cinema);
		filmeView = getActivity().findViewById(R.id.lista_filmes);
		
		cinemaView.setVisibility(View.INVISIBLE);
		filmeView.setVisibility(View.VISIBLE);
		
		return super.onCreateView(inflater, container, savedInstanceState);
	}
	
	@Override
	public void onDestroyView() {
		super.onDestroyView();
		cinemaView.setVisibility(View.VISIBLE);
		filmeView.setVisibility(View.INVISIBLE);
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
