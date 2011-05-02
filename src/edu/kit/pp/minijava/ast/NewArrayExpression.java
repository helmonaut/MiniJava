package edu.kit.pp.minijava.ast;

public class NewArrayExpression extends PrimaryExpression {

	private BasicType _basicType;
	private Expression _expression;
	private int _fieldCount;

	public NewArrayExpression(BasicType basicType, Expression expression, int fieldCount) {
		_basicType = basicType;
		_expression = expression;
		_fieldCount = fieldCount;
	}
}
