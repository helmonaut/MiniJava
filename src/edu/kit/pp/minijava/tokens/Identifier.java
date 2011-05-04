package edu.kit.pp.minijava.tokens;

public class Identifier extends Token {

	public Identifier(String value) {
		super(value);
	}

	public String toString() {
		return "identifier " + _value;
	}

	public boolean isIdentifier() {
		return true;
	}
}
