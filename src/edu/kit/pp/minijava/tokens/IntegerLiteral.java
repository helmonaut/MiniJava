// vi:ai:noet sta sw=4 ts=4 sts=0
package edu.kit.pp.minijava.tokens;

public class IntegerLiteral extends Token {

    public IntegerLiteral(String value) {
	super(value);
    }

    public String toString() {
	return "integer literal " + _value;
    }
}
