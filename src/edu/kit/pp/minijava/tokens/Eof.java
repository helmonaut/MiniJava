package edu.kit.pp.minijava.tokens;

public class Eof extends Token {

	public Eof() {
		super("");
	}

	public String toString() {
		return "EOF";
	}

	public boolean isEof() {
		return true;
	}
}
