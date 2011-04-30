package edu.kit.pp.minijava.ast;

import edu.kit.pp.minijava.tokens.Identifier;

public class Field extends ClassMember {

	private Type _type;

	public Field(Type type, Identifier name) {
		super(name);
		_type = type;
	}
}
