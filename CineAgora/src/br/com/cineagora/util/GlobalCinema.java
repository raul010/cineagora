package br.com.cineagora.util;

import java.util.List;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import br.com.cinemodel.model.Cinema;
import br.com.cinemodel.view.CinemaElementView;

public class GlobalCinema extends Application {

	private static Context context;
	/**
	 * TODO: Bolar um tempo de vida para a lista na app
	 */
	private List<CinemaElementView> listaCinema;

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
	}

	@Override
	public void onCreate() {
		super.onCreate();
		GlobalCinema.context = getApplicationContext();
	}

	@Override
	public void onLowMemory() {
		super.onLowMemory();
	}

	@Override
	public void onTerminate() {
		super.onTerminate();
	}

	public List<CinemaElementView> getListaCinema() {
		return listaCinema;
	}

	public void setListaCinema(List<CinemaElementView> listaCinema) {
		this.listaCinema = listaCinema;
	}

	public static Context getAppContext() {
		return GlobalCinema.context;
	}

}
