package edu.kit.pp.minijava.ast;

import edu.kit.pp.minijava.tokens.Identifier;

public class LocalVariableDeclarationStatement extends BlockStatement {

	private Type _type;
	private Identifier _name;
	private Expression _expression;

	public LocalVariableDeclarationStatement(Type type, Identifier name, Expression expression) {
		_type = type;
		_name = name;
		_expression = expression;
	}

}
