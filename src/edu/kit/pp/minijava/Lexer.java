// vi:ai:noet sta sw=4 ts=4 sts=0
package edu.kit.pp.minijava;

import edu.kit.pp.minijava.tokens.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PushbackReader;
import java.io.Reader;
import java.util.ArrayList;


public class Lexer {
	private PushbackReader _reader;
	private int _line = 1;
	private int _column = 1;
	private final int TABSIZE= 4;

	private boolean _lastCharIsCRLF= false;

	private ArrayList<Integer> _lineLengths= new ArrayList<Integer>();

	public Lexer(Reader reader) throws IOException {
		final int UNDOBUFFERSIZE= 2;
		_reader = new PushbackReader(new BufferedReader(reader), UNDOBUFFERSIZE);
	}

	public int getLine() {
		return _line;
	}

	public int getColumn() {
		return _column;
	}

	public Token next() throws IOException {
		while (true) {
			int c = read();
			switch (c) {
			case '\n':
				break;
			case ' ':
				break;
			case '\r':
				break;
			case '\t':
				break;
			case '/':
				c = read();
				switch (c) {
				case '*':
					skipComment();
					break;
				case '=':
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
				case '+': return operator("++");
				case '=': return operator("+=");
				default:
					unread(c);
					return operator("+");
				}
			case ',':
				return operator(",");
			case '-':
				c = read();
				switch (c) {
				case '-': return operator("--");
				case '=': return operator("-=");
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
					c = read();
					switch (c) {
					case '>':
						return operator(ifThenElse('=', ">>>=", ">>>"));
					case '=':
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
					return operator("&&");
				case '=':
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
					return operator("||");
				case '=':
					return operator("|=");
				default:
					unread(c);
					return operator("|");
				}
			case -1: return eof();
			default:
				if (isLetter(c) || c == '_') {
					return lexIdentifier(c);
				}
				else if (isDigit(c)) {
					return lexInteger(c);
				}
				else {
					return error();
				}
			}
		}
	}

	private void skipComment() throws IOException {
		while (true) {
			int c = read();
			switch (c) {
			case '*':
				c = read();
				if (c == '/') return;
				unread(c);
				break;
			case -1: return;
			case '\n':
			default: ;
			}
		}
	}

	private Token lexIdentifier(int c) throws IOException {
		StringBuffer name = new StringBuffer();
		name.append((char) c);
		while (true) {
			c = read();
			if (isLetter(c) || c == '_' || isDigit(c)) {
				name.append((char) c);
			}
			else {
				unread(c);
				if(Keyword._KEYWORDS.containsKey(name.toString())) {
					return keyword(name.toString());
				}
				return identifier(name.toString());
			}
		}
	}

	private Token lexInteger(int c) throws IOException {
		StringBuffer name = new StringBuffer();
		name.append((char) c);
		if (c == '0') {
			return integerLiteral(name.toString());
		}
		else {
			while (true) {
				c = read();
				if (isDigit(c)) {
					name.append((char) c);
				} else {
					unread(c);
					return integerLiteral(name.toString());
				}
			}
		}
	}

	private int _lastTab = 0;
	private int read() throws IOException {
		int c= (short)_reader.read();
		if (c == '\r') {
			_lastCharIsCRLF= false;

			int nextchar= (short)_reader.read();
			if (nextchar == '\n') {
				_lastCharIsCRLF= true;
				c= nextchar;
			}			
			else {
				_reader.unread(nextchar);
			}

			_lineLengths.add(new Integer(_column));
			_line++;
			_column = 1;
			return c;
		}
		else if (c == '\n') {
			_lineLengths.add(new Integer(_column));
			_line++;
			_column = 1;
			return c;
		}
		else if ( c== '\t') {
			_lastTab = 4 - ((_column - 1) % TABSIZE);
			_column += _lastTab;
		}
		else {
			_column++;
		}
		return c;
	}

	private void unread(int c) throws IOException {
		if (c == '\n') {
			if (_lastCharIsCRLF == true) {
				_lastCharIsCRLF= false;
				_reader.unread('\n');
				_reader.unread('\r');
				_column= _lineLengths.remove(_lineLengths.size()-1).intValue();
				_line--;
				return;
			}
			else {
				_reader.unread(c);
				_column= _lineLengths.remove(_lineLengths.size()-1).intValue();
				_line--;
				return;
			}
		}
		else if (c == '\r') {
			_reader.unread(c);
			_column= _lineLengths.remove(_lineLengths.size()-1).intValue();
			_line--;
			return;
		}
		else if (c == '\t') {
			//nothing else to do, columns are aligned to
			//tab sizes
			_column -= _lastTab;
		}
		else 
			_column--;
		
		_reader.unread(c);
	}

	private String ifThenElse(int c, String t1, String t2) throws IOException {
		int n = read();
		if (n == c) return t1;
		else unread(n);
		return t2;
	}

	private boolean isDigit(int c) {
	    return c >= '0' && c <= '9';
	}

	private boolean isLetter(int c) {
	    return (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z');
	}

	private Token keyword(String s) {
		return new Keyword(s);
	}

	private Token operator(String s) {
		return new Operator(s);
	}

	private Token identifier(String s) {
		return new Identifier(s);
	}

	private Token integerLiteral(String s) {
		return new IntegerLiteral(s);
	}

	private Token eof() {
		return new Eof();
	}

	private Token error() {
		return new edu.kit.pp.minijava.tokens.Error();
	}
}
