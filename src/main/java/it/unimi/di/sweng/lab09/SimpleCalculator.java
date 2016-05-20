package it.unimi.di.sweng.lab09;

public class SimpleCalculator implements Calculator {

	private TokenizerFactory tokenizerF;
	private StackFactory stackF;

	public SimpleCalculator(TokenizerFactory tokenizerF, StackFactory stackF) {
		this.tokenizerF = tokenizerF;
		this.stackF = stackF;
	}

	@Override
	public double eval(String expression) {
		Stack stack = stackF.stack();
		Tokenizer tokenizer = tokenizerF.tokenizer(expression);
		while (tokenizer.hasNextToken()) {
			Token t = tokenizer.nextToken();

			switch (t.kind) {
			case VALUE:
				stack.push(t.value);
				break;
			case SUM:
				stack.push(stack.pop() + stack.pop());
				break;
			case SUBRACTION: 
			{
				double a = stack.pop(), b = stack.pop();
				stack.push(b - a);
			}
				break;
			case PRODUCT:
				stack.push(stack.pop() * stack.pop());
				break;
			case DIVISION: 
			{
				double a = stack.pop();
				double b = stack.pop();
				stack.push(b / a);
			}
				break;
			}

		}
		return stack.isEmpty() ? 0 : stack.pop();
	}

}
