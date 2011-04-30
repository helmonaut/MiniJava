package edu.kit.pp.minijava.ast;

public class IfStatement extends Statement {

	private Expression _condition;
	private Statement _statement1; // TODO better names
	private Statement _statement2;

	public IfStatement(Expression condition, Statement s1, Statement s2) {
		_condition = condition;
		_statement1 = s1;
		_statement2 = s2;
	}
}
