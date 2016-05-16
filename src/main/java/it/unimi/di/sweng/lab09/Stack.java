package it.unimi.di.sweng.lab09;

import java.util.NoSuchElementException;

/**
 * Le classi che implementano questa interfaccia realizzano una pila di valori
 * numerici.
 */
public interface Stack {

	/**
	 * Restituisce se la pila è vuota, o meno.
	 * 
	 * @return se la pila è vuota, o meno.
	 */
	public boolean isEmpty();

	/**
	 * Preleva un valore dalla pila.
	 * 
	 * @throws NoSuchElementException
	 *             se la pila è vuota.
	 * @return il valore estratto dalla pila.
	 */
	public double pop();

	/**
	 * Aggiunge un valore sulla pila.
	 * 
	 * @param value
	 *            il valore da aggiungere alla pila.
	 */
	public void push(final double value);

}
