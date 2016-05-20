package it.unimi.di.sweng.lab09;

import java.util.ArrayDeque;

public class SimpleStack implements Stack
{
	ArrayDeque<Double> pila;
	
	public SimpleStack()
	{
		pila= new ArrayDeque<Double>();
	}
	

	public int length()
	{
		return pila.size();
	}


	@Override
	public boolean isEmpty() {
		return (this.length()==0);
	}


	@Override
	public double pop() {
		
		return pila.pop();
	}


	@Override
	public void push(double value) {
		pila.push(value);
		
	}
	
	
	
	
}
