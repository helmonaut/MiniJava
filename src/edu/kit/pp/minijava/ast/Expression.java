package edu.kit.pp.minijava.ast;

import edu.kit.pp.minijava.tokens.Token;

public class Expression extends Node {

	private Token _token;

	public Expression() {
	}

	public Expression(Token token) {
		_token = token;
	}

	public Token getToken() {
		return _token;
	}
}
