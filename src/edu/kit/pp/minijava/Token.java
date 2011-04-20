package edu.kit.pp.minijava;

public class Token {
	public String _type;
	
	public Token(String type) {
		_type = type;
	}
	
	public String toString() {
		return "Token(" + _type + ")";
	}
}
