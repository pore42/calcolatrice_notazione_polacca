package it.unimi.di.sweng.lab09;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import static org.mockito.Mockito.*; 

@RunWith(MockitoJUnitRunner.class)
public class CalculatorTest {
	
	@Rule
	public Timeout globalTimeout = Timeout.seconds(10); // 10 seconds max

	private static final double DELTA = .1E-10;

	@Mock Tokenizer tokenizer;
	
	@Mock Stack stack;
	
	@Mock TokenizerFactory tF;
	
	@Before
	public void setUp(){
		when(tF.tokenizer(anyString())).thenReturn(tokenizer);
	}
	
	@Test
	public void testEmptyString() throws Exception {
				
		Calculator c = new SimpleCalculator(tF,stack);
		
		assertEquals(0.0,c.eval(""),DELTA);
		
	}

}
