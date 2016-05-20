package it.unimi.di.sweng.lab09;

import java.util.NoSuchElementException;

public class SimpleCalculator implements Calculator {

	private TokenizerFactory tokenizerF;
	private StackFactory stackF;

	public SimpleCalculator(TokenizerFactory tokenizerF, StackFactory stackF) {
		this.tokenizerF = tokenizerF;
		this.stackF = stackF;
	}

	@Override
	public double eval(String expression) throws IllegalArgumentException, IllegalStateException, ArithmeticException {
		Stack stack = stackF.stack();
		Tokenizer tokenizer = tokenizerF.tokenizer(expression);
		while (tokenizer.hasNextToken()) {
			Token t = tokenizer.nextToken();
			try {

				switch (t.kind) {
				case VALUE:
					stack.push(t.value);
					break;
				case SUM:
					stack.push(stack.pop() + stack.pop());
					break;
				case SUBRACTION: {
					double a = stack.pop(), b = stack.pop();
					stack.push(b - a);
				}
					break;
				case PRODUCT:
					stack.push(stack.pop() * stack.pop());
					break;
				case DIVISION: {
					double a = stack.pop();
					double b = stack.pop();
					stack.push(b / a);
				}
					break;
				}
			} catch (NoSuchElementException e) {
				throw new IllegalStateException();
			}

		}

		if (stack.isEmpty())
			return 0;
		else {
			double out = stack.pop();
			if (stack.isEmpty())
				return out;
			else
				throw new IllegalStateException();
		}
	}

}
