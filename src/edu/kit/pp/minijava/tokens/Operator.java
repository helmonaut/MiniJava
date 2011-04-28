// vi:ai:noet sta sw=4 ts=4 sts=0
package edu.kit.pp.minijava.tokens;

import java.util.HashMap;
import java.util.Map;
import java.util.Collections;

public class Operator extends Token {
    
    private static final String _operators[] = {
        "!=", "!", "(", ")", "*=", "*", "++", "+=", "+", ",", "-=", "--", "-", ".",
        "/=", "/", ":", ";", "<<=", "<<", "<=", "<", "==", "=", ">=", ">>=", ">>>=",
        ">>>", ">>", ">", "?", "%=", "%", "&=", "&&", "&", "[", "]", "^=", "^", "{",
        "}", "~", "|=", "||", "|"};
    
    private static final Map<HashValue, Operator> _Operators = createMap();

    private static Map<HashValue, Operator> createMap() {
        // _hashCollision = false;
        Map<HashValue, Operator> oMap = new HashMap<HashValue, Operator>();
        
        for(Token t : Token.createMap(_operators).values()) {
            oMap.put(t._hashval, new Operator(t.hashCode(), t._value));
        }
        return Collections.unmodifiableMap(oMap);
    }

    public static Operator newOperator(long crcval, String value) {
        Operator op;
        if (null == (op = _Operators.get(new HashValue(crcval)))) {
            System.err.println("Unknown Operator.");
            System.exit(9);
        }
        return op;
    }

    public Operator(long crcval, String value) {
        super(crcval, value);
    }

    public String toString() {
        return _value;
    }
}