package edu.kit.pp.minijava.ast;

public class ReturnStatement extends Statement {

	private Expression _expression;

	public ReturnStatement(Expression expression) {
		_expression = expression;
	}

}
