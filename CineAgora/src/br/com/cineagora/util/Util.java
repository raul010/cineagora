package br.com.cineagora.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

import org.apache.commons.io.IOUtils;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.StrictMode;
import android.util.Log;
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

public class Util {

	public static final String UTF_8 = "UTF-8";

	public static final String HOST = "http://rachacuca.servegame.com";
	public static final String URL_PATH = "/cineserver";
	public static final String URL_CINEMAS = "/cinemas";
	public static final String URL_CIDADE_ESTADO = "/cidade-estado";

	public static final String URL_DELIMITADOR = "frango";
	public static final String URL_DELIMITADOR_PATTERN = "frango";

	public static void fazRequestParaCache(String result) {
		String cidade = null;
		String estado = null;
		result = urlEncode(result);
		String[] locais = result.split(URL_DELIMITADOR_PATTERN);

		if (locais.length == 2) {
			cidade = locais[0];
			estado = locais[1];
		} else {
			Log.w("Split Local", "Deveriam existir 2 ococrrencias, ha " + locais.length);
		}
		String requestParaCache = null;

		// e.g.
		// http://www.host.com.br/cineserver/cinemas/cidade-estado/?cidade=Sao%20Paulo&estado=Sao%20Paulo
		// (com seu devido acento)
		requestParaCache = String.format("%s%s%s%s%s%s%s%s", HOST, URL_PATH, URL_CINEMAS, URL_CIDADE_ESTADO,
				"?cidade=", cidade, "&estado=", estado);
		fazRequest(requestParaCache);
	}

	private static String fazRequest(String enderecoUrl) {
		String response = null;
		try {
			URL url = new URL(enderecoUrl);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestProperty("Content-Type", "text/plain; charset=utf-8");
			response = fazLeituraDoStream(con.getInputStream());

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
	/**
	 * Faz encode padrao de URL, para o mesmo formato que ser feito o decode
	 * @param enderecoUrl
	 * @return
	 */
	private static String urlEncode(String enderecoUrl) {
		try {
			return URLEncoder.encode(enderecoUrl, UTF_8);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static ArrayList<CinemaElementView> recuperaObjetoListaDeCinemasDoSite() {
		String response = fazRequest(HOST + URL_PATH + URL_CIDADE_ESTADO);

		// 1- Remonta Strings em objeto Json
		// 2- Para nao usar o mesmo model do server, se o que esta la eh
		// (CinemaElement instanceof Cinema) no
		// device fica (CinemaElementAdapter instanceof Cinema) -- Porem isso
		// fica transparente ao desenvolvedor.
		Gson gson = new GsonBuilder().registerTypeAdapter(Cinema.class, new CinemaElementAdapter())
				.registerTypeAdapter(Endereco.class, new EnderecoResumoAdapter())
				.registerTypeAdapter(Filme.class, new FilmeCartazAdapter()).create();

		Type collectionType = new TypeToken<ArrayList<CinemaElementView>>() {
		}.getType();
		return gson.fromJson(response, collectionType);
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
