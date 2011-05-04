package edu.kit.pp.minijava.tokens;

class HashValue {

    protected long _value = 0;

    public HashValue(long value) {
        _value = value;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final HashValue other = (HashValue) obj;
        return this._value == other._value;
    }

    public int hashCode() {
        return (int) _value;
    }

    public String toString() {
        return "[" + Integer.toHexString(hashCode()) + "] ";
    }
}
