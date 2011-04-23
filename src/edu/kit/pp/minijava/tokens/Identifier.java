// vi:ai:noet sta sw=4 ts=4 sts=0
package edu.kit.pp.minijava.tokens;

public class Identifier extends Token {

    public Identifier(String value) {
	super(value);
    }

    public String toString() {
	return "identifier " + _value;
    }
}
