package br.com.cineagora;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import br.com.cineagora.view.CinemaElementView;
import br.com.cineagora.view.EnderecoResumoView;
import br.com.cineagora.view.FilmeCartazView;
import br.com.cinemodel.Cinema;
import br.com.cinemodel.Endereco;
import br.com.cinemodel.Filme;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;

public class MainActivity extends Activity {
	List<CinemaElementView> cinemas = null;

	@Override
	protected void onResume() {
		super.onResume();
		System.out.println(cinemas);
		System.out.println("RESUME");
	}

	@Override
	protected void onRestart() {
		super.onRestart();
		System.out.println(cinemas);
		System.out.println("RESTART");
	}

	@Override
	protected void onPause() {
		super.onPause();
		System.out.println(cinemas);
		System.out.println("PAUSE");
	}

	@Override
	protected void onStop() {
		super.onStop();
		System.out.println(cinemas);
		System.out.println("STOP");
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		System.out.println(cinemas);
		System.out.println("DESTROY");
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		if (cinemas == null) {
			inicializaStrictMode();
			cinemas = recuperaLista();
		}

		ArrayAdapter<CinemaElementView> cinemaAdapter = new ArrayAdapter<CinemaElementView>(
				this, android.R.layout.simple_list_item_1, cinemas);

		ListView listaCinema = (ListView) findViewById(R.id.lista_cinema);
		listaCinema.setAdapter(cinemaAdapter);

		listaCinema.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> av, View v, int position,
					long id) {
				CinemaElementView cinema = cinemas.get(position);

				// ArrayAdapter<Filme> filmeAdapter = new
				// ArrayAdapter<Filme>(Activity.this,
				// android.R.layout.simple_list_item_1, cinema.getFilmes());
				//
				// ListView listaFilme = (ListView)
				// findViewById(R.id.lista_filme);
				// listaFilme.setAdapter(filmeAdapter);
			}

		});

		// for (CinemaElement cinemaElement : cinemas) {
		// System.out.println("CINEMA: " + cinemaElement.getNome());
		// System.out.println("ENDERECO: "
		// + ((EnderecoResumo) cinemaElement.getEndereco())
		// .getDadosRecebidos());
		// System.out.println("************************************");
		//
		// for (Filme filme : cinemaElement.getFilmes()) {
		// System.out.println("---------------------------------");
		// System.out.println("FILME: " + filme.getNome());
		// System.out.println("---------------------------------");
		//
		// for (String hr : ((FilmeCartaz) filme).getHorarios()) {
		// System.out.println("HORARIO: " + hr);
		// }
		// }
		// }
	}

	@SuppressLint("NewApi")
	private void inicializaStrictMode() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
					.permitAll().build();
			StrictMode.setThreadPolicy(policy);

		}
	}

	// @Override
	// protected void onListItemClick(ListView l, View v, int position, long id)
	// {
	// Cinema cinema = cinemas.get(position);
	// ArrayAdapter<Filme> filmesAdapter = new ArrayAdapter<Filme>(
	// this, android.R.layout.simple_list_item_1, cinema.getFilmes());
	// setListAdapter(filmesAdapter);
	//
	// super.onListItemClick(l, v, position, id);
	// }

	private List<CinemaElementView> recuperaLista() {
		String response = fazRequest("http://192.168.1.9:8080/cineserver/cinemas/");

		Gson gson = new GsonBuilder()
				.registerTypeAdapter(Cinema.class, new CinemaElementAdapter())
				.registerTypeAdapter(Endereco.class,
						new EnderecoResumoAdapter())
				.registerTypeAdapter(Filme.class, new FilmeCartazAdapter())
				.create();

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
			InputStream is = con.getInputStream();
			response = leituraDoStream(is);

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

	// Adpters necessários para inicialização correta
	private static class CinemaElementAdapter implements
			JsonDeserializer<CinemaElementView> {
		@Override
		public CinemaElementView deserialize(JsonElement json, Type t,
				JsonDeserializationContext context) throws JsonParseException {
			return context.deserialize(json.getAsJsonObject(),
					CinemaElementView.class);
		}
	}

	private static class EnderecoResumoAdapter implements
			JsonDeserializer<EnderecoResumoView> {
		public EnderecoResumoView deserialize(JsonElement json, Type t,
				JsonDeserializationContext context) throws JsonParseException {
			return context.deserialize(json.getAsJsonObject(),
					EnderecoResumoView.class);
		}
	}

	private static class FilmeCartazAdapter implements
			JsonDeserializer<FilmeCartazView> {
		public FilmeCartazView deserialize(JsonElement json, Type t,
				JsonDeserializationContext context) throws JsonParseException {
			return context.deserialize(json.getAsJsonObject(),
					FilmeCartazView.class);
		}

	}

}
