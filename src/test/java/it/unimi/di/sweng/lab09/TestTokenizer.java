package it.unimi.di.sweng.lab09;

import static org.junit.Assert.assertEquals;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;


public class TestTokenizer 
{
	@Rule
	public Timeout globalTimeout = Timeout.seconds(10); // 10 seconds max

	private static final double DELTA = .1E-10;

	@Test
	public void testEmptyString()
	{
		SimpleTokenizer s = new SimpleTokenizer("");
		assertEquals(false, s.hasNextToken());
		
	}
	
	@Test
	public void testNum()
	{
		SimpleTokenizer s = new SimpleTokenizer("42");
		assertEquals(true, s.hasNextToken());
		assertEquals(42,s.nextToken().value, DELTA);
		assertEquals(false, s.hasNextToken());
	}
	
	@Test (expected=IllegalArgumentException.class)
	public void testUnoparsable()
	{
		SimpleTokenizer s = new SimpleTokenizer("pippo");
		s.nextToken();

	}
	
	
	
	

}
