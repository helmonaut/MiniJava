package edu.kit.pp.minijava.ast;

import edu.kit.pp.minijava.tokens.Token;

public class BinaryExpression extends Expression {

	private Expression _left, _right;

	public BinaryExpression(Token operator, Expression left, Expression right) {
		super(operator);
		_left = left;
		_right = right;
	}

	public Expression getLeft() {
		return _left;
	}

	public Expression getRight() {
		return _right;
	}
}
