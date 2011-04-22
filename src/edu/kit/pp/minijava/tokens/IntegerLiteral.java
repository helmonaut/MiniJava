package edu.kit.pp.minijava.tokens;

public class IntegerLiteral extends Token {

    public IntegerLiteral(String value) {
	super(value);
    }

    public String toString() {
	return "integer literal " + _value;
    }
}
