package edu.kit.pp.minijava.tokens;

public class Operator extends Token {

	public Operator(String value) {
		super(value);
	}

	public String toString() {
		return _value;
	}
}
