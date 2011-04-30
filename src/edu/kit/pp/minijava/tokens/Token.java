// vi:ai:noet sta sw=4 ts=4 sts=0
package edu.kit.pp.minijava.tokens;

public class Token {
	protected String _value;

	public Token(String value) {
		_value = value;
	}

	public String toString() {
		return "Token(" + _value + ")";
	}

	public boolean isEof() {
		return false;
	}

	public boolean isIdentifier() {
		return false;
	}

	public String getValue() {
		return _value;
	}
}