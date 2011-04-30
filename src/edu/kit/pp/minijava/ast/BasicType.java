package edu.kit.pp.minijava.ast;

import edu.kit.pp.minijava.tokens.Token;

public class BasicType extends Node {

	private Token _token;

	public BasicType(Token token) {
		_token = token;
	}

}
