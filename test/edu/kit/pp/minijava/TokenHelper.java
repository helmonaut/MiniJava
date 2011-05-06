package edu.kit.pp.minijava;

import edu.kit.pp.minijava.tokens.*;

public abstract class TokenHelper {
	public static Token I() {
		return I("generated_identifier");
	}

	public static Token I(String s) {
		return new Identifier(s);
	}

	public static Token O(String s) {
		return new Operator(s);
	}

	public static Token K(String s) {
		return new Keyword(s);
	}

	public static Token IL(String s) {
		return new IntegerLiteral(s);
	}

	public static Token EOF() {
		return new Eof();
	}
}
