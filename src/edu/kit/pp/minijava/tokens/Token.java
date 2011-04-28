// vi:ai:noet sta sw=4 ts=4 sts=0
package edu.kit.pp.minijava.tokens;

import java.util.HashMap;
import java.util.Map;
import java.util.Collections;

import java.util.zip.CRC32;

public class Token {

    protected HashValue _hashval;
    protected String _value;
    protected static boolean _hashCollision = true;

    public Token(long crcval, String value) {
        _hashval = new HashValue(crcval);
        _value = value;
    }
    
    protected static Map<HashValue, Token> createMap(String []words) {
        Map<HashValue, Token> aMap = new HashMap<HashValue, Token>();
        CRC32 crc = new CRC32();

        for (String keyword : words) {
            crc.reset();
            for (int i = 0; i < keyword.length(); i++) {
                crc.update((short) keyword.charAt(i));
            }

            HashValue hv = new HashValue(crc.getValue());
            Token op;

            if (null != (op = aMap.get(hv))) {
                // _hashCollision = true;
                System.err.printf("Token '%s' and '%s' have the same hashvalue.\n",
                        op._value, keyword);
                System.exit(1);
            }
            aMap.put(hv, new Token(crc.getValue(), keyword));
        }

        return aMap;
        // return Collections.unmodifiableMap(aMap);
    }
    
    
    public String toString() {
        return "Token(" + _value + ")";
    }

    public boolean isEof() {
        return false;
    }
    
     @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Token other = (Token) obj;
        if (!this._hashval.equals(other._hashval)) {
            return false;
        }
        
        // if(! _hashCollision) return true;
        
        if(!this._value.equals(other._value)) {
            return false;            
        }
        return true;
    }
    
    public boolean isIdentifier() {
        return false;

    }

    public String getValue() {
        return _value;
    }

    public int hashCode() {
        return _hashval.hashCode();
    }
}
