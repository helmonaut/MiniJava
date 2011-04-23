// vi:ai:noet sta sw=4 ts=4 sts=0
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
