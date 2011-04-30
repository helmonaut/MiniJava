package edu.kit.pp.minijava.ast;

import edu.kit.pp.minijava.tokens.Identifier;

public class FieldAccess extends PostfixOp {

	private Identifier _name;

	public FieldAccess(Identifier name) {
		_name = name;
	}

}
