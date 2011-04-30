package edu.kit.pp.minijava.ast;

import edu.kit.pp.minijava.tokens.Token;

public class LocalMethodInvocationExpression extends PrimaryExpression {

	private Arguments _arguments;

	public LocalMethodInvocationExpression(Token token, Arguments arguments) {
		super(token);
		_arguments = arguments;
	}

}
