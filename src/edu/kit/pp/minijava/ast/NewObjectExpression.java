package edu.kit.pp.minijava.ast;

import edu.kit.pp.minijava.tokens.Identifier;

public class NewObjectExpression extends PrimaryExpression {

	public NewObjectExpression(Identifier className) {
		super(className);
	}
}
