// vi:ai:noet sta sw=4 ts=4 sts=0
package edu.kit.pp.minijava.tokens;

import java.util.HashMap;
import java.util.Map;
import java.util.LinkedList;

public class Identifier extends Token {

    private static HashMap<HashValue, LinkedList<Identifier>> _Identifiers = new HashMap<HashValue, LinkedList<Identifier>>();
    private int _id;
    private static boolean _hashCollision = false;

    public Identifier(long crcval, String value) {
        super(crcval, value);
    }

    public static Identifier newIdentifier(long crcval, String value) {
        HashValue hv = new HashValue(crcval);
        LinkedList<Identifier> idList;
        Identifier id;

        // new hash
        if (null == (idList = _Identifiers.get(hv))) {
            idList = new LinkedList<Identifier>();
            id = new Identifier(crcval, value);
            idList.add(id);
            _Identifiers.put(hv, idList);
            return id;
        }

        // check if identifier is listed
        for (Identifier idTmp : idList) {
            if (value.equals(idTmp._value)) {
                return idTmp;
            }
        }
        
        _hashCollision = true;
        
        // add new identifier
        id = new Identifier(crcval, value);
        idList.add(id);
        return id;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Identifier other = (Identifier) obj;
        if(!_hashval.equals(other._hashval)) return false;
        if(! _hashCollision) return true;
        return _value.equals(other._value);
    }

    @Override
    public int hashCode() {
        return _hashval.hashCode();
    }


    public String toString() {
        return "identifier " + _value;
    }

    public boolean isIdentifier() {
        return true;
    }
}
