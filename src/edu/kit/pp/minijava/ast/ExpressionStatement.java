package edu.kit.pp.minijava.ast;

public class ExpressionStatement extends Statement {

	private Expression _expression;

	public ExpressionStatement(Expression expression) {
		_expression = expression;
	}

}
