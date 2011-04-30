package edu.kit.pp.minijava.ast;

public class WhileStatement extends Statement {

	private Expression _condition;
	private Statement _statement;

	public WhileStatement(Expression condition, Statement statement) {
		_condition = condition;
		_statement = statement;
	}

}
