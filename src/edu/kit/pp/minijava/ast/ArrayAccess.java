package edu.kit.pp.minijava.ast;

public class ArrayAccess extends PostfixOp {

	private Expression _expression;

	public ArrayAccess(Expression expression) {
		_expression = expression;
	}

}
