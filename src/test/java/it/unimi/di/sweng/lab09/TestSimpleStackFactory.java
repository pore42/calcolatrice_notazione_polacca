
package it.unimi.di.sweng.lab09;

import static org.junit.Assert.*;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;

public class TestSimpleStackFactory {

	@Rule
	public Timeout globalTimeout = Timeout.seconds(10); // 10 seconds max

	@Test
	public void testStack() {
		StackFactory st = new SimpleStackFactory();
		assertEquals(true, st.stack() instanceof Stack);
	}

}
