package edu.kit.pp.minijava.ast;

import edu.kit.pp.minijava.tokens.Identifier;

public class MethodInvocation extends PostfixOp {

	private Identifier _name;
	private Arguments _arguments;

	public MethodInvocation(Identifier name, Arguments arguments) {
		_name = name;
		_arguments = arguments;
	}
}
