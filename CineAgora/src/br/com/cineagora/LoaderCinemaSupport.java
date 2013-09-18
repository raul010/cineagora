package br.com.cineagora;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import br.com.cineagora.util.CinemaApp;
import br.com.cineagora.util.Util;
import br.com.cinemodel.view.CinemaElementView;

/**
 * Demonstration of the use of a CursorLoader to load and display contacts data
 * in a fragment.
 */
@SuppressWarnings("all")
public class LoaderCinemaSupport extends FragmentActivity {

	private static CinemaApp cinemaApp;
	private static List<CinemaElementView> cinemas;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.d("LoaderCinemaSupport", "onCreate() - entrou");

		FragmentManager fm = getSupportFragmentManager();

		// Create the list fragment and add it as our sole content.
		if (fm.findFragmentById(android.R.id.content) == null) {
			DataCinemaListFragment list = new DataCinemaListFragment();
			fm.beginTransaction().add(android.R.id.content, list).commit();
		}
	}

	public static class DataCinemaListFragment extends ListFragment implements
			LoaderManager.LoaderCallbacks<List<CinemaElementView>>, Observer {

		// This is the Adapter being used to display the list's data.
		ArrayAdapter<CinemaElementView> cinemaAdapter;

		// If non-null, this is the current filter the user has provided.
		String mCurFilter;

		@Override
		public void onActivityCreated(Bundle savedInstanceState) {
			super.onActivityCreated(savedInstanceState);
			Log.d("onActivityCreated", "entrou");

			// Registra Observer
			cinemaApp = (CinemaApp) getActivity().getApplication();
			cinemaApp.getListaCinemaObserver().addObserver(this);


			// We have a menu item to show in action bar.
			setHasOptionsMenu(true);

			if (savedInstanceState != null && savedInstanceState.containsKey(Util.LABEL_CINEMAS)) {
				Log.d("savedInstanceState", "existe");
				cinemas = savedInstanceState.getParcelableArrayList(Util.LABEL_CINEMAS);
			}

			// Cria um adapter vazio que sera usado para mostrar os dados do
			// (cinemas) quando forem carregados ou ja carrega caso existir.
			if (cinemas == null) {
				cinemaAdapter = new ArrayAdapter<CinemaElementView>(getActivity(), android.R.layout.simple_list_item_1,
						new ArrayList<CinemaElementView>());
				setEmptyText("Estamos obtendo os cinemas para sua localidade");
				setListShown(false);
			} else {
				cinemaAdapter = new ArrayAdapter<CinemaElementView>(getActivity(), android.R.layout.simple_list_item_1,
						cinemas);
			}
			setListAdapter(cinemaAdapter);

			// Prepare the loader. Either re-connect with an existing one,
			// or start a new one.
			getLoaderManager().initLoader(0, null, this);
		}

		@Override
		public void update(Observable obs, Object obj) {
			Log.d("update", "entrou");

			if (obj != null) {
				Log.d("updade()", "obj != null");
				cinemas = (List<CinemaElementView>) obj;

				Bundle bundle = new Bundle();
				bundle.putParcelableArrayList(Util.LABEL_CINEMAS, (ArrayList<CinemaElementView>) cinemas);
				
				Log.d("update()", "deu restartLoader(...)");
				Log.d("update()", "cinemas = "+ cinemas.toString());

				getLoaderManager().restartLoader(0, bundle, DataCinemaListFragment.this);
				cinemaApp.getListaCinemaObserver().deleteObserver(this);
			}

		}

		@Override
		public void onListItemClick(ListView l, View v, int position, long id) {
			// Insert desired behavior here.
			Log.i("FragmentComplexList", "Item clicked: " + id);
			
			CinemaElementView cinema = (CinemaElementView) cinemas.get(position);
			
			
//			Intent i = new Intent(getActivity().getApplicationContext(), FilmeFragment.class);
			Intent i = new Intent(getActivity(), FilmeFragment.class);
			i.putExtra(Util.LABEL_CINEMA, (Parcelable)cinema);
			startActivity(i);
			
			
//			FilmeFragment listaFilmeFragment = new FilmeFragment();
//			Bundle bundleCinema = new Bundle();
//			bundleCinema.putParcelable(Util.LABEL_CINEMA, cinema);
//			listaFilmeFragment.setArguments(bundleCinema);
//			
//			
//			
//			FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
////			transaction.replace(R.id.filme_fragment, listaFilmeFragment);
//			
//			transaction.replace(android.R.id.content, listaFilmeFragment);
//			transaction.addToBackStack(null);
//			transaction.commit();
			
		}

		@Override
		public Loader<List<CinemaElementView>> onCreateLoader(int id, Bundle bundle) {
			Log.d("onCreateLoader()", "entrou");

			if ((bundle == null || !bundle.containsKey(Util.LABEL_CINEMAS))) {
				Log.d("onCreateLoader()", "nao recuperou bundle");

				Toast.makeText(cinemaApp.getApplicationContext(),
						"Pode utilizar os outros serviços, enquanto atualizamos as programações.", Toast.LENGTH_LONG)
						.show();
			} else {
				Log.d("onCreateLoader()", "recuperou bundle");
				cinemas = bundle.getParcelableArrayList(Util.LABEL_CINEMAS);
			}

			// This is called when a new Loader needs to be created. This
			// sample only has one Loader, so we don't care about the ID.

			// Now create and return a Loader that will take care of
			// creating the data displayed.
			return new CinemasListLoader(getActivity());
		}

		@Override
		public void onDestroyView() {
			super.onDestroyView();
		}

		@Override
		public void onLoadFinished(Loader<List<CinemaElementView>> arg0, List<CinemaElementView> data) {
			// Swap the new List
			if (data != null) {
			cinemaAdapter = new ArrayAdapter<CinemaElementView>(getActivity(), android.R.layout.simple_list_item_1,
					data);
			Log.d("onLoadFinished()", "data = " + data.toString());
			setListAdapter(cinemaAdapter);
			} else {
				Log.d("onLoadFinished()", "data == null");
			}
			// The list should now be shown.
			if (isResumed()) {
				setListShown(true);
			} else {
				setListShownNoAnimation(true);
			}

		}

		@Override
		public void onLoaderReset(Loader<List<CinemaElementView>> arg0) {
			//cinemaAdapter.clear();
			Log.d("onLoaderReset", "chamou");

		}

	}

	static class CinemasListLoader extends AsyncTaskLoader<List<CinemaElementView>> {

		private List<CinemaElementView> listaDeCinemas;

		public CinemasListLoader(Context context) {
			super(context);
		}

		@Override
		public List<CinemaElementView> loadInBackground() {
			Log.d("DataListLoader.loadInBackground", "entrou");

			// You should perform the heavy task of getting data from
			// Internet or database or other source
			// Here, we are generating some Sample data

			return cinemas;
		}

		/**
		 * Called when there is new data to deliver to the client. The super
		 * class will take care of delivering it; the implementation here just
		 * adds a little more logic.
		 */
		@Override
		public void deliverResult(List<CinemaElementView> listOfData) {
			if (isReset()) {
				// An async query came in while the loader is stopped. We
				// don't need the result.
				if (listOfData != null) {
					onReleaseResources(listOfData);
				}
			}
			List<CinemaElementView> oldApps = listOfData;
			listaDeCinemas = listOfData;

			if (isStarted()) {
				// If the Loader is currently started, we can immediately
				// deliver its results.
				super.deliverResult(listOfData);
			}

			// At this point we can release the resources associated with
			// 'oldApps' if needed; now that the new result is delivered we
			// know that it is no longer in use.
			if (oldApps != null) {
				onReleaseResources(oldApps);
			}
		}

		/**
		 * Handles a request to start the Loader.
		 */
		@Override
		protected void onStartLoading() {
			if (listaDeCinemas != null) {
				// If we currently have a result available, deliver it
				// immediately.
				deliverResult(listaDeCinemas);
			}

			if (takeContentChanged() || listaDeCinemas == null) {
				// If the data has changed since the last time it was loaded
				// or is not currently available, start a load.
				forceLoad();
			}
		}

		/**
		 * Handles a request to stop the Loader.
		 */
		@Override
		protected void onStopLoading() {
			// Attempt to cancel the current load task if possible.
			cancelLoad();
		}

		/**
		 * Handles a request to cancel a load.
		 */
		@Override
		public void onCanceled(List<CinemaElementView> apps) {
			super.onCanceled(apps);

			// At this point we can release the resources associated with 'apps'
			// if needed.
			onReleaseResources(apps);
		}

		/**
		 * Handles a request to completely reset the Loader.
		 */
		@Override
		protected void onReset() {
			super.onReset();

			// Ensure the loader is stopped
			onStopLoading();

			// At this point we can release the resources associated with 'apps'
			// if needed.
			if (listaDeCinemas != null) {
				onReleaseResources(listaDeCinemas);
				listaDeCinemas = null;
			}
		}

		/**
		 * Helper function to take care of releasing resources associated with
		 * an actively loaded data set.
		 */
		protected void onReleaseResources(List<CinemaElementView> apps) {
		}

	}

	@Override
	public void onSaveInstanceState(Bundle budleState) {
		budleState.putParcelableArrayList(Util.LABEL_CINEMAS, (ArrayList<CinemaElementView>) cinemas);

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