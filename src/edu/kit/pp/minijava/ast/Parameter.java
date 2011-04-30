package edu.kit.pp.minijava.ast;

import edu.kit.pp.minijava.tokens.Identifier;

public class Parameter extends Node {

	private Type _type;
	private Identifier _name;

	public Parameter(Type type, Identifier name) {
		_type = type;
		_name = name;
	}

}
