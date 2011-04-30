package edu.kit.pp.minijava.ast;

import edu.kit.pp.minijava.tokens.Identifier;

public class MainMethod extends ClassMember {

	private Block _block;
	private Identifier _variableName;

	public MainMethod(Identifier name, Identifier variableName, Block block) {
		super(name);
		_block = block;
		_variableName = variableName;
	}

}
