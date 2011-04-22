package edu.kit.pp.minijava.tokens;

public class Keyword extends Token {

    public Keyword(String value) {
	super(value);
    }

    public String toString() {
	return _value;
    }
}
