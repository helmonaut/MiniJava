package edu.kit.pp.minijava.ast;

import edu.kit.pp.minijava.tokens.Token;

public class PrimaryExpression extends Expression {
	public PrimaryExpression() {
	}
	
	public PrimaryExpression(Token token) {
		super(token);
	}
}
