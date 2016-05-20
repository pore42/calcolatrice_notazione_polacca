package it.unimi.di.sweng.lab09;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CalculatorTestIntegrated {
	
	@Rule
	public Timeout globalTimeout = Timeout.seconds(10); // 10 seconds max

	private static final double DELTA = .1E-10;

	private TokenizerFactory tF = new SimpleTokenizerFactory();
	private StackFactory sF = new SimpleStackFactory();
	
	@Test
	public void testEmptyString() throws Exception {
				
		Calculator c = new SimpleCalculator(tF,sF);
		
		assertEquals(0.0,c.eval(""),DELTA);
		
	}
	
	@Test
	public void testSingleNumber() throws Exception {
		Calculator c = new SimpleCalculator(tF,sF);
		
		assertEquals(1.0,c.eval("1"),DELTA);
	}
	
	@Test
	public void testSomma() throws Exception {
		Calculator c = new SimpleCalculator(tF,sF);
		assertEquals(3.0,c.eval("2 1 +"),DELTA);
	}
	
	@Test
	public void testDiffernza() throws Exception {
		Calculator c = new SimpleCalculator(tF,sF);
		assertEquals(1.0,c.eval("2 1 -"),DELTA);
	}

	@Test
	public void testProdotto() throws Exception {
		Calculator c = new SimpleCalculator(tF,sF);
		assertEquals(10.0,c.eval("5 2 *"),DELTA);
	}
	
	@Test
	public void testDivisione() throws Exception {
		Calculator c = new SimpleCalculator(tF,sF);
		assertEquals(5.0,c.eval("10 2 /"),DELTA);
		
	}
	
	@Test(expected=IllegalStateException.class)
	public void testTooNumbers() {
		Calculator c = new SimpleCalculator(tF,sF);
		c.eval("10 2");
		
	}
	
	@Test(expected=IllegalStateException.class)
	public void testNotEnoughNumbers() {
		Calculator c = new SimpleCalculator(tF,sF);
		c.eval("10 /");
		
	}
	
	@Test
	public void testConcatenation() throws Exception {
		Calculator c = new SimpleCalculator(tF,sF);
		assertEquals(30.0, c.eval("10 10 + 10 +"), DELTA);
	}
}
