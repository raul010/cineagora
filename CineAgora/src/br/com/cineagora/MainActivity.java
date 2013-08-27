package br.com.cineagora;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import org.apache.commons.io.IOUtils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.AttributeSet;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import br.com.cinemodel.model.Cinema;
import br.com.cinemodel.model.Endereco;
import br.com.cinemodel.model.Filme;
import br.com.cinemodel.view.CinemaElementView;
import br.com.cinemodel.view.EnderecoResumoView;
import br.com.cinemodel.view.FilmeCartazView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;

public class MainActivity extends FragmentActivity {
	private static final String SITE = "http://192.168.1.15:8080/cineserver/cinemas/";
	private static final String CINEMAS = "cinemas";
	protected static final String CINEMA = "cinema";
	ArrayList<CinemaElementView> cinemas = null;
	private long inicioAuditoria;
	private boolean auditoriaAtivada = false;

	@Override
	public View onCreateView(String name, Context context, AttributeSet attrs) {
		return super.onCreateView(name, context, attrs);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		________________________________________iniciaContagemAuditoria();
		if (savedInstanceState == null || !savedInstanceState.containsKey(CINEMAS)) {
			inicializaStrictMode();
			cinemas = recuperaLista();
			________________________________________finalizaContagemAuditoria("Recuparacao COM Rest");
		} else { // Fim audit
			cinemas = savedInstanceState.getParcelableArrayList(CINEMAS);
			________________________________________finalizaContagemAuditoria("Recuparacao SEM Rest");
		}

		ArrayAdapter<CinemaElementView> cinemaAdapter = new ArrayAdapter<CinemaElementView>(this,
				android.R.layout.simple_list_item_1, cinemas);

		ListView listaCinema = (ListView) findViewById(R.id.lista_cinema);
		listaCinema.setAdapter(cinemaAdapter);

		listaCinema.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> av, View v, int position, long id) {
				CinemaElementView cinema = cinemas.get(position);
				ListaFilmeFragment listaFilmeFragment = new ListaFilmeFragment();
				Bundle bundleCinema = new Bundle();
				bundleCinema.putParcelable(CINEMA, cinema);
				listaFilmeFragment.setArguments(bundleCinema);

				FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

				//av.setVisibility(View.INVISIBLE);

				transaction.replace(R.id.fragment_container, listaFilmeFragment);
				transaction.addToBackStack(null);
				transaction.commit();
				//av.setVisibility(View.VISIBLE);
			}

		});
	}

	@SuppressLint("NewApi")
	private void inicializaStrictMode() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
			________________________________________iniciaContagemAuditoria();
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
			StrictMode.setThreadPolicy(policy);
			________________________________________finalizaContagemAuditoria("Start StrictMode");
		}
	}

	@Override
	protected void onSaveInstanceState(Bundle budleState) {
		________________________________________iniciaContagemAuditoria();
		System.out.println("ON_SAVE_INST_STATE");
		budleState.putParcelableArrayList(CINEMAS, cinemas);
		super.onSaveInstanceState(budleState);
		________________________________________finalizaContagemAuditoria("ON_SAVE_INST_STATE");

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

	private ArrayList<CinemaElementView> recuperaLista() {
		String response = fazRequest(SITE);

		Gson gson = new GsonBuilder().registerTypeAdapter(Cinema.class, new CinemaElementAdapter())
				.registerTypeAdapter(Endereco.class, new EnderecoResumoAdapter())
				.registerTypeAdapter(Filme.class, new FilmeCartazAdapter()).create();

		Type collectionType = new TypeToken<ArrayList<CinemaElementView>>() {
		}.getType();
		return gson.fromJson(response, collectionType);
	}

	private String fazRequest(String enderecoUrl) {
		HttpURLConnection con;
		URL url;
		String response = null;

		try {
			url = new URL(enderecoUrl);
			con = (HttpURLConnection) url.openConnection();
			response = leituraDoStream(con.getInputStream());

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return response;

	}

	private String leituraDoStream(InputStream in) {
		String linha = null;

		try {
			StringWriter writer = new StringWriter();
			IOUtils.copy(in, writer);
			linha = writer.toString();

		} catch (IOException e) {
			e.printStackTrace();
		}

		return linha;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	// Adpters necessarios para inicializacao correta
	private static class CinemaElementAdapter implements JsonDeserializer<CinemaElementView> {
		@Override
		public CinemaElementView deserialize(JsonElement json, Type t, JsonDeserializationContext context)
				throws JsonParseException {
			return context.deserialize(json.getAsJsonObject(), CinemaElementView.class);
		}
	}

	private static class EnderecoResumoAdapter implements JsonDeserializer<EnderecoResumoView> {
		public EnderecoResumoView deserialize(JsonElement json, Type t, JsonDeserializationContext context)
				throws JsonParseException {
			return context.deserialize(json.getAsJsonObject(), EnderecoResumoView.class);
		}
	}

	private static class FilmeCartazAdapter implements JsonDeserializer<FilmeCartazView> {
		public FilmeCartazView deserialize(JsonElement json, Type t, JsonDeserializationContext context)
				throws JsonParseException {
			return context.deserialize(json.getAsJsonObject(), FilmeCartazView.class);
		}

	}

	private void ________________________________________iniciaContagemAuditoria() {
		// considerando show sql=false, e sem estar em modo debug.
		inicioAuditoria = System.nanoTime();
	}

	private void ________________________________________finalizaContagemAuditoria(String mensagem) {
		if (!auditoriaAtivada ) return;
		long fim = System.nanoTime() - inicioAuditoria;
		double segundos = (double) fim / 1000000000.0;
		if (mensagem != null) {
			System.out.println("[Auditoria] " + mensagem + ": " + segundos);
		}
		inicioAuditoria = 0;
	}
}
