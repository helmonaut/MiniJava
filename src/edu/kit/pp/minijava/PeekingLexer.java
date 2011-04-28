package edu.kit.pp.minijava;

import edu.kit.pp.minijava.tokens.Token;
import java.io.IOException;

class PeekingLexer {

	private Lexer _lexer;
	private int _size;
	private Token[] _lookAhead;
	private int _pos;

	public PeekingLexer(Lexer lexer, int size) throws IOException {
		_lexer = lexer;
		_size = size;
		_pos = 0;
		_lookAhead = new Token[size];
		for (int i = 0; i < size; i++) {
			_lookAhead[i] = _lexer.next();
		}
	}

	public Token next() throws IOException {
		Token result = _lookAhead[_pos % _size];
		_lookAhead[_pos % _size] = _lexer.next();
		_pos += 1;
		return result;
	}

	Token peek(int i) {
		return _lookAhead[(_pos + i) % _size];
	}

}
