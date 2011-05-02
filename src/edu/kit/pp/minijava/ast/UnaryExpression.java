package edu.kit.pp.minijava.ast;

import edu.kit.pp.minijava.tokens.Token;

public class UnaryExpression extends Expression {
	private Expression _operand;
	
	public UnaryExpression(Token token, Expression operand) {
		super(token);
		_operand = operand;
	}
	
	public Expression getOperand() {
		return _operand;
	}
}
