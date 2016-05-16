package it.unimi.di.sweng.lab09;

import java.util.NoSuchElementException;

/**
 * Le classi che implementano questa interfaccia sono in grado di suddividere
 * una espressione in {@link Token}.
 */
public interface Tokenizer {

	/**
	 * Restituisce <code>true</code> nel caso l'espressione contenga un altro
	 * {@link Token}, <code>false</code> altrimenti.
	 * 
	 * @throws IllegalArgumentException
	 *             se l'espressione contiene del testo non corrispondente ad un
	 *             numero, o ad uno delle operazioni aritmentiche.
	 * @return se l'espressione contiene un altro {@link Token}, o meno.
	 */
	public boolean hasNextToken();

	/**
	 * Restituisce il prossimo {@link Token} contenuto nell'espressione.
	 * 
	 * @throws IllegalArgumentException
	 *             se l'espressione contiene del testo non corrispondente ad un
	 *             numero, o ad uno delle operazioni aritmentiche.
	 * @throws NoSuchElementException
	 *             se l'espressione non contiene altri {@link Token}.
	 * @return il {@link Token}.
	 */
	public Token nextToken();

}
