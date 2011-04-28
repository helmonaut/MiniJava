package edu.kit.pp.minijava.tokens;

public class Error extends Token {

	public Error() {
		super("");
	}

	public String toString() {
		return "error";
	}

	public boolean isEof() {
		return true;
	}
}