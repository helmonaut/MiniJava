// vi:ai:noet sta sw=4 ts=4 sts=0
package edu.kit.pp.minijava.tokens;

import java.util.HashMap;
import java.util.Map;
import java.util.Collections;

public class Keyword extends Token {

    public static final Map<String, Boolean> _KEYWORDS = createMap();

    private static Map<String, Boolean> createMap() {
	String[] keywords = {"abstract", "assert", "boolean", "break", "byte",
	    "case", "catch", "char", "class", "const", "continue", "default",
	    "double", "do", "else", "enum", "extends", "false", "finally",
	    "final", "float", "for", "goto", "if", "implements", "import",
	    "instanceof", "interface", "int", "long", "native", "new", "null",
	    "package", "private", "protected", "public", "return", "short",
	    "static", "strictfp", "super", "switch", "synchronized", "this",
	    "throws", "throw", "transient", "true", "try", "void", "volatile",
	    "while"};

	Map<String, Boolean> aMap = new HashMap<String, Boolean>();
	for (String keyword : keywords) {
	    aMap.put(keyword, true);
	}

	return Collections.unmodifiableMap(aMap);
    }

    public Keyword(String value) {
	super(value);
    }

    public String toString() {
	return _value;
    }
}
