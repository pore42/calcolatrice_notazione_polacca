package it.unimi.di.sweng.lab09;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CalculatorTest {
	
	/*@Rule
	public Timeout globalTimeout = Timeout.seconds(10); // 10 seconds max*/

	private static final double DELTA = .1E-10;

	@Mock Tokenizer tokenizer;
	
	@Mock Stack stack;
	
	@Mock TokenizerFactory tF;
	@Mock StackFactory sF;
	
	@Before
	public void setUp(){
		when(tF.tokenizer(anyString())).thenReturn(tokenizer);
		when(sF.stack()).thenReturn(stack);
	}
	
	@Test
	public void testEmptyString() throws Exception {
				
		Calculator c = new SimpleCalculator(tF,sF);
		when(stack.isEmpty()).thenReturn(true);
		
		assertEquals(0.0,c.eval(""),DELTA);
		
	}
	
	@Test
	public void testSingleNumber() throws Exception {
		Calculator c = new SimpleCalculator(tF,sF);
		
		when(tokenizer.hasNextToken()).thenReturn(true,false);
  		when(tokenizer.nextToken()).thenReturn(Token.valueOf("1"));
  		when(stack.isEmpty()).thenReturn(false,true);
  		when(stack.pop()).thenReturn(1.0);
		
		assertEquals(1.0,c.eval("1"),DELTA);
	}
	
	@Test
	public void testSomma() throws Exception {
		Calculator c = new SimpleCalculator(tF,sF);
  		when(tokenizer.hasNextToken()).thenReturn(true,true,true,false);
  		when(tokenizer.nextToken()).thenReturn(Token.valueOf("2"),Token.valueOf("1"),Token.valueOf("+"));
  		when(stack.isEmpty()).thenReturn(false,true);
  		when(stack.pop()).thenReturn(1.0,2.0,3.0);
		assertEquals(3.0,c.eval("2 1 +"),DELTA);
	}
	
	@Test
	public void testDiffernza() throws Exception {
		Calculator c = new SimpleCalculator(tF,sF);
  		when(tokenizer.hasNextToken()).thenReturn(true,true,true,false);
  		when(tokenizer.nextToken()).thenReturn(Token.valueOf("2"),Token.valueOf("1"),Token.valueOf("-"));
  		when(stack.isEmpty()).thenReturn(false,true);
  		when(stack.pop()).thenReturn(1.0,2.0,1.0);
		assertEquals(1.0,c.eval("2 1 -"),DELTA);
	}

	@Test
	public void testProdotto() throws Exception {
		Calculator c = new SimpleCalculator(tF,sF);
  		when(tokenizer.hasNextToken()).thenReturn(true,true,true,false);
  		when(tokenizer.nextToken()).thenReturn(Token.valueOf("5"),Token.valueOf("2"),Token.valueOf("*"));
  		when(stack.isEmpty()).thenReturn(false,true);
  		when(stack.pop()).thenReturn(2.0,5.0,10.0);
		assertEquals(10.0,c.eval("5 2 *"),DELTA);
	}
	
	@Test
	public void testDivisione() throws Exception {
		Calculator c = new SimpleCalculator(tF,sF);
  		when(tokenizer.hasNextToken()).thenReturn(true,true,true,false);
  		when(tokenizer.nextToken()).thenReturn(Token.valueOf("10"),Token.valueOf("2"),Token.valueOf("/"));
  		when(stack.isEmpty()).thenReturn(false,true);
  		when(stack.pop()).thenReturn(2.0,10.0,5.0);
		assertEquals(5.0,c.eval("10 2 /"),DELTA);
		
		InOrder io = inOrder(stack);		
		io.verify(stack).push(10);
		io.verify(stack).push(2);
		io.verify(stack, times(2)).pop();
		io.verify(stack).push(5);
		io.verify(stack).isEmpty();
		io.verify(stack).pop();
		io.verify(stack).isEmpty();
		io.verifyNoMoreInteractions();
		
	}
	
	@Test(expected=IllegalStateException.class)
	public void testTooNumbers() {
		Calculator c = new SimpleCalculator(tF,sF);
  		when(tokenizer.hasNextToken()).thenReturn(true,true,false);
  		when(tokenizer.nextToken()).thenReturn(Token.valueOf("10"),Token.valueOf("2"));
  		when(stack.isEmpty()).thenReturn(false,false);
  		when(stack.pop()).thenReturn(2.0, 10.0);
		c.eval("10 2");
		
	}
	
	@Test(expected=IllegalStateException.class)
	public void testNotEnoughNumbers() {
		Calculator c = new SimpleCalculator(tF,sF);
  		when(tokenizer.hasNextToken()).thenReturn(true,true,false);
  		when(tokenizer.nextToken()).thenReturn(Token.valueOf("10"),Token.valueOf("+"));
  		when(stack.pop()).thenReturn(10.0).thenThrow(new IllegalStateException());
		c.eval("10 +");
		
	}
	
	@Test
	public void testConcatenation() throws Exception {
		Calculator c = new SimpleCalculator(tF,sF);
		when(tokenizer.hasNextToken()).thenReturn(true,true,true,true,true,false);
		when(tokenizer.nextToken()).thenReturn(Token.valueOf("10"),Token.valueOf("10"),Token.valueOf("+"),Token.valueOf("10"),Token.valueOf("+"));
  		when(stack.pop()).thenReturn(10.0, 10.0, 20.0, 10.0, 30.0);
  		when(stack.isEmpty()).thenReturn(false,true);
		assertEquals(30.0, c.eval("10 10 + 10 +"), DELTA);
	}
}
