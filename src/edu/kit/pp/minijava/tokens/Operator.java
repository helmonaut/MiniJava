// vi:ai:noet sta sw=4 ts=4 sts=0
package edu.kit.pp.minijava.tokens;

public class Operator extends Token {

    public Operator(String value) {
	super(value);
    }

    public String toString() {
	return _value;
    }
}
