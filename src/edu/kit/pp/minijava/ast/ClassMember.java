package edu.kit.pp.minijava.ast;

import edu.kit.pp.minijava.tokens.Identifier;

public class ClassMember extends Node {

	private Identifier _name;

	public ClassMember(Identifier name) {
		_name = name;
	}

}
