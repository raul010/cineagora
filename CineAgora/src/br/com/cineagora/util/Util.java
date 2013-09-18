package br.com.cineagora.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.StrictMode;
import android.util.Log;
import br.com.cinemodel.view.CinemaElementView;
import br.com.cinemodel.view.EnderecoResumoView;
import br.com.cinemodel.view.FilmeCartazView;
import br.com.cinemodel.view.NomeFilmeView;
import br.com.model.Cinema;
import br.com.model.Endereco;
import br.com.model.Filme;
import br.com.model.base.NomeFilme;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;

public class Util {
	public static final String LABEL_CINEMAS = "cinemas";
	public static final String LABEL_CINEMA = "cinema";
	public static final String LABEL_UTF_8 = "UTF-8";

	public static final String HOST = "http://rachacuca.servegame.com";
	public static final String URL_PATH = "/cineserver";
	public static final String URL_CINEMAS = "/cinemas";
	public static final String URL_CIDADE_ESTADO = "/cidade-estado";

	public static final String URL_DELIMITADOR = "frango";
	public static final String URL_DELIMITADOR_PATTERN = "frango";

	public static List<CinemaElementView> fazRequestParaCache(String cidadeEestado) {
		String cidade = null;
		String estado = null;
		String[] locais = cidadeEestado.split(URL_DELIMITADOR_PATTERN);

		if (locais.length == 2) {
			cidade = locais[0];
			estado = locais[1];
		} else {
			Log.w("Split Local", "Deveriam existir 2 ococrrencias, ha " + locais.length);
		}
		String requestParaCache = null;

		// e.g.
		// http://localhost:8080/cineserver/cinemas/cidade-estado?cidade=S%C3%A3o%20Paulo&estado=S%C3%A3o%20Paulo
		// (com seu devido acento)
		requestParaCache = String.format("%s%s%s%s%s%s%s%s", HOST, URL_PATH, URL_CINEMAS, URL_CIDADE_ESTADO,
				"?cidade=", cidade, "&estado=", estado);

		String response = fazRequest(requestParaCache);
		
		return remontaDeStringParaJson(response);
		
	}

	private static String fazRequest(String enderecoUrl) {
		String response = null;
		try {
			enderecoUrl = fazEncodeParaASCII(enderecoUrl);
			URL url = new URL(enderecoUrl);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestProperty("Content-Type", "text/plain; charset=utf-8");
			// Comentar esta linha para testes sem necessidade request
			Log.d("Solicitou ","Request");
			response = fazLeituraDoStream(con.getInputStream());
			Log.d("Retornou ","Response");

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return response;

	}

	private static String fazLeituraDoStream(InputStream in) {
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

//	public static ArrayList<CinemaElementView> recuperaObjetoListaDeCinemasDoSite() {
//		String response = fazRequest(HOST + URL_PATH + URL_CIDADE_ESTADO);
//
//		return remontaDeStringParaJson(response);
//	}

	/**
	 * 1- Remonta Strings em objeto Json; <br>2- Para nao usar o mesmo model do
	 * server, se o que esta la eh (CinemaElement instanceof Cinema) no device
	 * fica (CinemaElementAdapter instanceof Cinema) - Porem isso fica
	 * transparente ao desenvolvedor.
	 * 
	 * @param response
	 * @return lista de cinemas do response
	 */
	private static ArrayList<CinemaElementView> remontaDeStringParaJson(String response) {
		Gson gson = new GsonBuilder().registerTypeAdapter(Cinema.class, new CinemaElementAdapter())
				.registerTypeAdapter(Endereco.class, new EnderecoResumoAdapter())
				.registerTypeAdapter(Filme.class, new FilmeCartazAdapter())
				.registerTypeAdapter(NomeFilme.class, new NomeFilmeAdapter())
				.create();

		Type collectionType = new TypeToken<ArrayList<CinemaElementView>>() {
		}.getType();
		
		Log.d("Iniciado", "Conversao Json");
		ArrayList<CinemaElementView> listaCinemas = gson.fromJson(response, collectionType);
		Log.d("Finalizado", "Conversao Json");

		return listaCinemas;
	}

	/**
	 * Faz o encode correto, apenas da parte query do link
	 * 
	 * @param url
	 * @return ascII
	 */
	public static String fazEncodeParaASCII(String url) {
		URL urlApi;
		URI uri;
		try {
			urlApi = new URL(url);
			uri = new URI(urlApi.getProtocol(), urlApi.getUserInfo(), urlApi.getHost(), urlApi.getPort(),
					urlApi.getPath(), urlApi.getQuery(), urlApi.getRef());
		} catch (URISyntaxException e) {
			e.printStackTrace();
			// DEBUG
			throw new RuntimeException(e);
		} catch (MalformedURLException e) {
			e.printStackTrace();
			// DEBUG
			throw new RuntimeException(e);
		}
		return uri.toASCIIString();
	}

	// Adpters (Filtros neste caso) necessarios para inicializacao dos Elementos
	// representandes dos models.
	public static class CinemaElementAdapter implements JsonDeserializer<CinemaElementView> {
		@Override
		public CinemaElementView deserialize(JsonElement json, Type t, JsonDeserializationContext context)
				throws JsonParseException {
			return context.deserialize(json.getAsJsonObject(), CinemaElementView.class);
		}
	}

	public static class EnderecoResumoAdapter implements JsonDeserializer<EnderecoResumoView> {
		public EnderecoResumoView deserialize(JsonElement json, Type t, JsonDeserializationContext context)
				throws JsonParseException {
			return context.deserialize(json.getAsJsonObject(), EnderecoResumoView.class);
		}
	}

	public static class FilmeCartazAdapter implements JsonDeserializer<FilmeCartazView> {
		public FilmeCartazView deserialize(JsonElement json, Type t, JsonDeserializationContext context)
				throws JsonParseException {
			return context.deserialize(json.getAsJsonObject(), FilmeCartazView.class);
		}

	}
	public static class NomeFilmeAdapter implements JsonDeserializer<NomeFilmeView> {
		public NomeFilmeView deserialize(JsonElement json, Type t, JsonDeserializationContext context)
				throws JsonParseException {
			return context.deserialize(json.getAsJsonObject(), NomeFilmeView.class);
		}

	}

	/**
	 * Necessario o uso, sempre que possivel.
	 */
	@SuppressLint("NewApi")
	public static void inicializaStrictMode() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
			Cronometro __cron = new Cronometro("Start StrictMode");
			__cron.inicia();

			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
			StrictMode.setThreadPolicy(policy);

			__cron.finaliza();
		}
	}

	

}
