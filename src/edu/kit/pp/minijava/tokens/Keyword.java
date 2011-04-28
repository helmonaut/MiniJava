// vi:ai:noet sta sw=4 ts=4 sts=0
package edu.kit.pp.minijava.tokens;

import java.util.HashMap;
import java.util.Map;
import java.util.Collections;

import java.util.zip.CRC32;

public class Keyword extends Token {

    private static final String[] _keywords = {
        "abstract", "assert", "boolean", "break", "byte",
        "case", "catch", "char", "class", "const", "continue", "default",
        "double", "do", "else", "enum", "extends", "false", "finally",
        "final", "float", "for", "goto", "if", "implements", "import",
        "instanceof", "interface", "int", "long", "native", "new", "null",
        "package", "private", "protected", "public", "return", "short",
        "static", "strictfp", "super", "switch", "synchronized", "this",
        "throws", "throw", "transient", "true", "try", "void", "volatile",
        "while"};
    
    // public static final Map<String, Boolean> _KEYWORDS = createMap();
    private static final Map<HashValue, Keyword> _Keywords = createMap2();
    
    private static Map<String, Boolean> createMap() {
        _hashCollision = false;
        Map<String, Boolean> aMap = new HashMap<String, Boolean>();
        for (String keyword : _keywords) {
            aMap.put(keyword, true);
        }

        return Collections.unmodifiableMap(aMap);
    }
    
    public static boolean isKeyword(long crcval, String value) {
        HashValue hv = new HashValue(crcval);
        return _Keywords.containsKey(hv);
    }
    
     private static Map<HashValue, Keyword> createMap2() {
        Map<HashValue, Keyword> kMap = new HashMap<HashValue, Keyword>();
        
        for(Token t : Token.createMap(_keywords).values()) {
            kMap.put(t._hashval, new Keyword(t.hashCode(), t._value));
        }
        return Collections.unmodifiableMap(kMap);
    }

    public Keyword(long crcval, String value) {
        super(crcval, value);
    }

    public static Keyword newKeyword(long crcval, String value) {
        Keyword kw;
        if (null == (kw = _Keywords.get(new HashValue(crcval)))) {
            System.err.println("Unknown Keyword.");
            System.exit(8);
        }
        return kw;
    }

    public String toString() {
        return _value;
    }
}
