package br.com.cineagora.util;

import java.util.List;
import java.util.Observable;

import android.util.Log;
import br.com.cinemodel.view.CinemaElementView;

public class ListaCinemaObservable extends Observable {
	
	private List<CinemaElementView> listaCinema;

	public List<CinemaElementView> getListaCinema() {
		return listaCinema;
	}

	public void setListaCinema(List<CinemaElementView> listaCinema) {
		Log.d("setListaCinema()", "passou");
		this.listaCinema = listaCinema;
		setChanged();
		notifyObservers(listaCinema);
	}
}
