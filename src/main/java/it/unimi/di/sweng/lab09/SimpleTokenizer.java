package it.unimi.di.sweng.lab09;

import java.util.StringTokenizer;

public class SimpleTokenizer implements Tokenizer {

	private StringTokenizer stk;
	
	public SimpleTokenizer(String str) 
	{
		stk= new StringTokenizer(str, " ");
	}
	
	
	@Override
	public boolean hasNextToken() {
		return stk.hasMoreTokens();
	}

	@Override
	public Token nextToken() 
	{
		String token= stk.nextToken();
		Token t=Token.valueOf(token);
		return t;
	}

}
