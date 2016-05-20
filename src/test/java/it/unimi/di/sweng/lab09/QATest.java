package it.unimi.di.sweng.lab09;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;

public class QATest {

	@Rule
	public Timeout globalTimeout = Timeout.seconds(2); // 2 seconds max

	private static final double DELTA = .1E-10;

	private Calculator calculator;

	@Before
	public void setUp() {
		calculator = new SimpleCalculator(new SimpleTokenizerFactory(), new SimpleStackFactory());
	}
	
	@Test
	public void testEmpty() {
		assertEquals(0, calculator.eval(""), DELTA);
	}

	@Test
	public void testNoOperation() {
		assertEquals(1, calculator.eval("1"), DELTA);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testNotParseable() {
		calculator.eval("a");
	}

	@Test(expected = IllegalStateException.class)
	public void testExtraArguments() {
		calculator.eval("1 2");
	}

	@Test
	public void testSum() {
		assertEquals(3, calculator.eval("1 2 +"), DELTA);
	}

	@Test(expected = IllegalStateException.class)
	public void testNotEnoughArguments() {
		calculator.eval("1 +");
	}

	@Test
	public void testSubtraction() {
		assertEquals(3, calculator.eval("5 2 -"), DELTA);
	}

	@Test
	public void testProduct() {
		assertEquals(10, calculator.eval("5 2 *"), DELTA);
	}

	@Test
	public void testDivision() {
		assertEquals(2.5, calculator.eval("5 2 /"), DELTA);
	}

	@Test(expected = ArithmeticException.class)
	public void testDivisionByZero() {
		calculator.eval("5 0 /");
	}

}
