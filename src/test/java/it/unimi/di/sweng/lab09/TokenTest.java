package it.unimi.di.sweng.lab09;

import static org.junit.Assert.assertEquals;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;

public class TokenTest {

	@Rule
	public Timeout globalTimeout = Timeout.seconds(10); // 10 seconds max

	private static final double DELTA = .1E-10;

	@Test
	public void testValue() {
		assertEquals(3, Token.valueOf("3").value, DELTA);
	}

	@Test
	public void testSum() {
		assertEquals(Token.Kind.SUM, Token.valueOf("+").kind);
	}

	@Test
	public void testSubtraction() {
		assertEquals(Token.Kind.SUBRACTION, Token.valueOf("-").kind);
	}

	@Test
	public void testProduct() {
		assertEquals(Token.Kind.PRODUCT, Token.valueOf("*").kind);
	}

	@Test
	public void testDivision() {
		assertEquals(Token.Kind.DIVISION, Token.valueOf("/").kind);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testUnparseable() {
		Token.valueOf("a");
	}

}
