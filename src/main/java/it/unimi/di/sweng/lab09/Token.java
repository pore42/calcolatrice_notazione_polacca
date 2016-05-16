package it.unimi.di.sweng.lab09;

import java.util.HashMap;
import java.util.Map;

/**
 * Un <strong>token</strong>, ossia una parte di una espressione.
 * 
 * Un token ha un {@link Token.Kind tipo} che permette di sapere se esso
 * corrisponde ad un numero (nel qual caso il suo valore è contenuto nel campo
 * {@link Token#value value}), oppure ad una delle quattro operazioni
 * aritmetiche.
 * 
 */
public final class Token {

	/**
	 * Il tipo di {@link Token} che può essere trovato in una espressione.
	 */
	public enum Kind {
		/** Il tipo corrispondente ad un valore numerico. */
		VALUE,
		/** Il tipo corrispondente all'opreazione somma <code>+</code>. */
		SUM,
		/** Il tipo corrispondente all'opreazione sottrazione <code>-</code>. */
		SUBRACTION,
		/** Il tipo corrispondente all'opreazione prodotto <code>*</code>. */
		PRODUCT,
		/** Il tipo corrispondente all'opreazione divisione <code>/</code>. */
		DIVISION;
	}

	/** Il {@link Kind} del {@link Token} letto. */
	public final Kind kind;

	/**
	 * Se il {@link Kind} del {@link Token} letto è {@link Kind#VALUE}, contiene
	 * il valore del numero letto.
	 */
	public final double value;

	private Token(final Token.Kind kind, final double value) {
		this.kind = kind;
		this.value = value;
	}

	private static final Map<String, Token> STRING_TO_TOKEN;
	static {
		STRING_TO_TOKEN = new HashMap<String, Token>();
		STRING_TO_TOKEN.put("+", new Token(Kind.SUM, -1));
		STRING_TO_TOKEN.put("-", new Token(Kind.SUBRACTION, -1));
		STRING_TO_TOKEN.put("*", new Token(Kind.PRODUCT, -1));
		STRING_TO_TOKEN.put("/", new Token(Kind.DIVISION, -1));
	}

	/**
	 * Crea un {@link Token} data una stringa contenente un simbolo di
	 * operazione aritmetica, o un valore numerico.
	 * 
	 * @param token
	 *            la stringa.
	 * @return il {@link Token}.
	 * @throws IllegalArgumentException
	 *             se la stringa non contiene un valore, o un simbolo di
	 *             operazione aritmetica.
	 */
	public static Token valueOf(final String token) {
		Token result = STRING_TO_TOKEN.get(token);
		if (result != null) return result;
		try {
			return new Token(Token.Kind.VALUE, Double.parseDouble(token));
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException("Can't parse '" + token + "'");
		}
	}

}
