package it.unimi.di.sweng.lab09;

public class SimpleStackFactory implements StackFactory {

	@Override
	public Stack stack() 
	{	
		return new SimpleStack();
	}
	

}
