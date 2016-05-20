package it.unimi.di.sweng.lab09;

import static org.junit.Assert.assertEquals;

import java.util.NoSuchElementException;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;

public class TestSimpleStack
{
	SimpleStack s;
	@Rule
	public Timeout globalTimeout = Timeout.seconds(10); // 10 seconds max

	private static final double DELTA = .1E-10;

	@Before
	public void setUp()
	{
		s= new SimpleStack();
	}
	
	@Test
	public void testpush()
	{
		
		assertEquals(0, s.length());
		s.push(5);
		s.push(7);
		assertEquals(2, s.length());	
	}
	
	
	
	
	@Test (expected=NoSuchElementException.class)
	public void testemptyStack()
	{
		s.pop();
	
	}
	
	@Test
	public void testpop()
	{
		
		assertEquals(0, s.length());
		s.push(5);
		s.push(7);
		assertEquals(2, s.length());
		
		s.pop();
		assertEquals(1, s.length());
		s.pop();
		assertEquals(0, s.length());
	}
	

	

}
