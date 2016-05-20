package it.unimi.di.sweng.lab09;

import static org.junit.Assert.*;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;

public class TestSimpleTokenizerFactory {
	
	
	
	@Rule
	public Timeout globalTimeout = Timeout.seconds(10); // 10 seconds max

	@Test
	public void testTokenizer()
	{
		TokenizerFactory tf= new SimpleTokenizerFactory();
		assertEquals(true , tf.tokenizer("") instanceof Tokenizer);
	}

}
