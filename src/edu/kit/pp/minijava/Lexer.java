// vi:ai:noet sta sw=4 ts=4 sts=0
package edu.kit.pp.minijava;

import edu.kit.pp.minijava.tokens.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PushbackReader;
import java.io.Reader;

import java.util.zip.CRC32;

public class Lexer {

    private PushbackReader _reader;
    private CRC32 _crc;

    public Lexer(Reader reader) throws IOException {
        _reader = new PushbackReader(new BufferedReader(reader));
        _crc = new CRC32();
    }

    public Token next() throws IOException {
        while (true) {
            _crc.reset();
            int c = read();
            _crc.update(c);
            switch (c) {
                case ' ':
                case '\n':
                case '\r':
                case '\t':
                    break;
                case '/':
                    c = read();
                    switch (c) {
                        case '*':
                            skipComment();
                            break;
                        case '=':
                            _crc.update(c);
                            return operator("/=");
                        default:
                            unread(c);
                            return operator("/");
                    }
                    break;
                case '!':
                    return operator(ifThenElse('=', "!=", "!"));
                case '(':
                    return operator("(");
                case ')':
                    return operator(")");
                case '*':
                    return operator(ifThenElse('=', "*=", "*"));
                case '+':
                    c = read();
                    switch (c) {
                        case '+':
                            _crc.update(c);
                            return operator("++");
                        case '=':
                            _crc.update(c);
                            return operator("+=");
                        default:
                            unread(c);
                            return operator("+");
                    }
                case ',':
                    return operator(",");
                case '-':
                    c = read();
                    switch (c) {
                        case '-':
                            _crc.update(c);
                            return operator("--");
                        case '=':
                            _crc.update(c);
                            return operator("-=");
                        default:
                            unread(c);
                            return operator("-");
                    }
                case '.':
                    return operator(".");
                case ':':
                    return operator(":");
                case ';':
                    return operator(";");
                case '<':
                    c = read();
                    switch (c) {
                        case '<':
                            return operator(ifThenElse('=', "<<=", "<<"));
                        case '=':
                            _crc.update(c);
                            return operator("<=");
                        default:
                            unread(c);
                            return operator("<");
                    }
                case '=':
                    return operator(ifThenElse('=', "==", "="));
                case '>':
                    c = read();
                    switch (c) {
                        case '>':
                            _crc.update(c);
                            c = read();
                            switch (c) {
                                case '>':
                                    return operator(ifThenElse('=', ">>>=", ">>>"));
                                case '=':
                                    _crc.update(c);
                                    return operator(">>=");
                                default:
                                    unread(c);
                                    return operator(">>");
                            }
                        case '=':
                            return operator(">=");
                        default:
                            unread(c);
                            return operator(">");
                    }
                case '?':
                    return operator("?");
                case '%':
                    return operator(ifThenElse('=', "%=", "%"));
                case '&':
                    c = read();
                    switch (c) {
                        case '&':
                            _crc.update(c);
                            return operator("&&");
                        case '=':
                            _crc.update(c);
                            return operator("&=");
                        default:
                            unread(c);
                            return operator("&");
                    }
                case '[':
                    return operator("[");
                case ']':
                    return operator("]");
                case '^':
                    return operator(ifThenElse('=', "^=", "^"));
                case '{':
                    return operator("{");
                case '}':
                    return operator("}");
                case '~':
                    return operator("~");
                case '|':
                    c = read();
                    switch (c) {
                        case '|':
                            _crc.update(c);
                            return operator("||");
                        case '=':
                            _crc.update(c);
                            return operator("|=");
                        default:
                            unread(c);
                            return operator("|");
                    }
                case -1:
                    return eof();
                default:
                    if (isLetter(c) || c == '_') {
                        return lexIdentifier(c);
                    } else if (isDigit(c)) {
                        return lexInteger(c);
                    } else {
                        return error();
                    }
            }
        }
    }

    private void skipComment() throws IOException {
        _crc.reset();
        while (true) {
            int c = read();
            switch (c) {
                case '*':
                    c = read();
                    if (c == '/') {
                        return;
                    }
                    unread(c);
                    break;
                case -1:
                    return;
                default:
                    ;
            }
        }
    }

    private Token lexIdentifier(int c) throws IOException {
        StringBuffer name = new StringBuffer();
        name.append((char) c);
        while (true) {
            c = read();
            if (isLetter(c) || c == '_' || isDigit(c)) {
                _crc.update(c);
                name.append((char) c);
            } else {
                unread(c);
                String tmpName = name.toString();
                if (Keyword.isKeyword(_crc.getValue(), tmpName)) {
                    return keyword(tmpName);
                }
                return identifier(tmpName);
            }
        }
    }

    private Token lexInteger(int c) throws IOException {
        StringBuffer name = new StringBuffer();
        name.append((char) c);
        if (c == '0') {
            return integerLiteral(name.toString());
        } else {
            while (true) {
                c = read();
                if (isDigit(c)) {
                    _crc.update(c);
                    name.append((char) c);
                } else {
                    unread(c);
                    return integerLiteral(name.toString());
                }
            }
        }
    }

    private int read() throws IOException {
        return (short) _reader.read();
    }

    private void unread(int c) throws IOException {
        _reader.unread(c);
    }

    private String ifThenElse(int c, String t1, String t2) throws IOException {
        int n = read();
        if (n == c) {
            _crc.update(c);
            return t1;
        } else {
            unread(n);
        }
        return t2;
    }

    private boolean isDigit(int c) {
        return c >= '0' && c <= '9';
    }

    private boolean isLetter(int c) {
        return (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z');
    }

    private Token keyword(String s) {
        return Keyword.newKeyword(_crc.getValue(), s);
    }

    private Token operator(String s) {
        return Operator.newOperator(_crc.getValue(), s);
    }

    private Token identifier(String s) {
        return Identifier.newIdentifier(_crc.getValue(), s);
    }

    private Token integerLiteral(String s) {
        return new IntegerLiteral(_crc.getValue(), s);
    }

    private Token eof() {
        return new Eof();
    }

    private Token error() {
        return new edu.kit.pp.minijava.tokens.Error();
    }
}
