package it.unimi.di.sweng.lab09;

/**
 * Le classi che implementano questa interfaccia sono in grado di valutare una
 * espressione.
 */
public interface Calculator {

	/***
	 * Restituisce il valore di una espressione in
	 * <em>notazione polacca inversa</em>.
	 * 
	 * @param expression
	 *            l'espressione.
	 * @throws IllegalArgumentException
	 *             se l'espressione contiene del testo non corrispondente ad un
	 *             numero, o ad uno delle operazioni aritmentiche.
	 * @throws ArithmeticException
	 *             se l'espressione contiene una espressione che comporta
	 *             divisione per zero.
	 * @throws IllegalStateException
	 *             se al termine dell'espressione la pila contiene più di un
	 *             solo valore, o se durante la valutazione la pila non contiene
	 *             abbastanza valori per un certa operazione.
	 * @return il suo valore.
	 */
	public double eval(final String expression);

}
