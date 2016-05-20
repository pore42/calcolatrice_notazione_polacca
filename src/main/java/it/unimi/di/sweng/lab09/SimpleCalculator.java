package it.unimi.di.sweng.lab09;


public class SimpleCalculator implements Calculator {
	
	private TokenizerFactory tokenizerF;
	private Stack stack;
	
	public SimpleCalculator(TokenizerFactory tokenizerF, Stack stack) {
		this.tokenizerF = tokenizerF;
		this.stack = stack;
	}

	@Override
	public double eval(String expression) {
		Tokenizer tokenizer = tokenizerF.tokenizer(expression);
		if ( !tokenizer.hasNextToken() ) return 0.0;
		return 1;
	}

}
