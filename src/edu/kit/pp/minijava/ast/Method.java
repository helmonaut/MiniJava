package edu.kit.pp.minijava.ast;

import edu.kit.pp.minijava.tokens.Identifier;

public class Method extends ClassMember {

	private Type _type;
	private Block _block;
	private Parameters _parameters;

	public Method(Type type, Identifier name, Block block, Parameters parameters) {
		super(name);
		_block = block;
		_parameters = parameters;
	}
}
