package edu.kit.pp.minijava;

import edu.kit.pp.minijava.tokens.Eof;
import edu.kit.pp.minijava.tokens.Token;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class PeekingLexerTest {

	private PeekingLexer _lexer;

	public PeekingLexerTest() {
	}

	@BeforeClass
	public static void setUpClass() throws Exception {
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		Lexer lexer = mock(Lexer.class);
		when(lexer.next()).thenReturn(
			new Token("a"), new Token("b"), new Token("c"),
			new Token("d"), new Eof(), new Eof(), new Eof(), new Eof());
		_lexer = new PeekingLexer(lexer, 3);
	}

	@After
	public void tearDown() {
	}

	@Test
	public void nextShouldReturnCorrectTokens() throws Exception {
		assertEquals("a", _lexer.next().getValue());
		assertEquals("b", _lexer.next().getValue());
		assertEquals("c", _lexer.next().getValue());
		assertEquals("d", _lexer.next().getValue());
	}

	@Test
	public void peekShouldWork() {
		assertEquals("a", _lexer.peek(0).getValue());
		assertEquals("b", _lexer.peek(1).getValue());
		assertEquals("c", _lexer.peek(2).getValue());
	}
}
