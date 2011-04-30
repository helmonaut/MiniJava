package edu.kit.pp.minijava.ast;

import edu.kit.pp.minijava.tokens.Identifier;

public class NewObjectExpression extends Expression {

	public NewObjectExpression(Identifier className) {
		super(className);
	}
}
