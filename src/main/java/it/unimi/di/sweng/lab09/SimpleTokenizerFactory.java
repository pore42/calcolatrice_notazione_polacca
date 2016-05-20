package it.unimi.di.sweng.lab09;

public class SimpleTokenizerFactory implements TokenizerFactory {

	@Override
	public Tokenizer tokenizer(String expression) 
	{
		return new SimpleTokenizer(expression);
	}

}
