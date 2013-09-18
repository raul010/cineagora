package br.com.cineagora.util;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;

public class CinemaApp extends Application {

	private static Context context;
	/**
	 * TODO: Bolar um tempo de vida para a lista na app
	 */
	ListaCinemaObservable listaCinemaObserver;
	
	public static Context getAppContext() {
		return CinemaApp.context;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		CinemaApp.context = getApplicationContext();
		listaCinemaObserver = new ListaCinemaObservable();
	}
	
	public ListaCinemaObservable getListaCinemaObserver() {
		return listaCinemaObserver;
	}

	@Override
	public void onLowMemory() {
		super.onLowMemory();
	}

	@Override
	public void onTerminate() {
		super.onTerminate();
	}
	
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
	}
}
