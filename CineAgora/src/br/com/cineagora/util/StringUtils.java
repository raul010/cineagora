package br.com.cineagora.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Classe criada para suprir o fato da API do Java, Normalizer, exigir Level de
 * API do Android incompativel.
 * 
 * @author Raul
 */

public class StringUtils {

	private static String[] REPLACES = { "a", "e", "i", "o", "u", "c", "A", "E", "I", "O", "U", "C" };
	private static Pattern[] PATTERNS = null;
	
	private static void inicializaPatterns() {
		PATTERNS = new Pattern[REPLACES.length];
		PATTERNS[0] = Pattern.compile("[�����]");
		PATTERNS[1] = Pattern.compile("[����]");
		PATTERNS[2] = Pattern.compile("[����]");
		PATTERNS[3] = Pattern.compile("[�����]");
		PATTERNS[4] = Pattern.compile("[����]");
		PATTERNS[5] = Pattern.compile("[�]");
		PATTERNS[6] = Pattern.compile("[�����]");
		PATTERNS[7] = Pattern.compile("[����]");
		PATTERNS[8] = Pattern.compile("[����]");
		PATTERNS[9] = Pattern.compile("[�����]");
		PATTERNS[10] = Pattern.compile("[����]");
		PATTERNS[11] = Pattern.compile("[�]");
	}

	private StringUtils() {
	}

	/**
	 * Substitui os caracteres acentuados por nao acentuados.
	 * 
	 * @param texto
	 * @return
	 */
	public static String removeAcentos(String texto) {
		if (PATTERNS == null) {
			inicializaPatterns();
		}

		String resultado = texto;
		for (int i = 0; i < PATTERNS.length; i++) {
			Matcher matcher = PATTERNS[i].matcher(resultado);
			resultado = matcher.replaceAll(REPLACES[i]);
		}
		return resultado;
	}
}