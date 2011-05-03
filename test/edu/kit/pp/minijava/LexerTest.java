package edu.kit.pp.minijava;

import edu.kit.pp.minijava.tokens.*;
import java.io.IOException;
import java.io.StringReader;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class LexerTest {

	public LexerTest() {
	}

	@BeforeClass
	public static void setUpClass() throws Exception {
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
	}

	@Before
	public void setUp() {
	}

	@After
	public void tearDown() {
	}

	@Test
	public void testCRLFLineEndings() throws Exception {
		Lexer lex= createLexer("a\r\nb\r\nc\r\nd\r\ne\r\nf\r\n");
		lex.next();
		int col1= lex.getColumn();
		int line1= lex.getLine();
		lex.next();
		int col2= lex.getColumn();
		int line2= lex.getLine();
		lex.next();
		int col3= lex.getColumn();
		int line3= lex.getLine();
		lex.next();
		int col4= lex.getColumn();
		int line4= lex.getLine();
		lex.next();
		int col5= lex.getColumn();
		int line5= lex.getLine();
		lex.next();
		int col6= lex.getColumn();
		int line6= lex.getLine();
		
		assertTrue(line1 == 1 && col1 == 2);
		assertTrue(line2 == 2 && col2 == 2);
		assertTrue(line3 == 3 && col3 == 2);
		assertTrue(line4 == 4 && col4 == 2);
		assertTrue(line5 == 5 && col5 == 2);
		assertTrue(line6 == 6 && col6 == 2);
	}

	@Test
	public void testLineEndings() throws Exception {
		Lexer lex= createLexer("\n\ra\rb\nc\rd\re\r\nf\r\n");
		lex.next();
		int col1= lex.getColumn();
		int line1= lex.getLine();
		lex.next();
		int col2= lex.getColumn();
		int line2= lex.getLine();
		lex.next();
		int col3= lex.getColumn();
		int line3= lex.getLine();
		lex.next();
		int col4= lex.getColumn();
		int line4= lex.getLine();
		lex.next();
		int col5= lex.getColumn();
		int line5= lex.getLine();
		lex.next();
		int col6= lex.getColumn();
		int line6= lex.getLine();
		
		assertTrue(line1 == 3 && col1 == 2);
		assertTrue(line2 == 4 && col2 == 2);
		assertTrue(line3 == 5 && col3 == 2);
		assertTrue(line4 == 6 && col4 == 2);
		assertTrue(line5 == 7 && col5 == 2);
		assertTrue(line6 == 8 && col6 == 2);
	}

	@Test
	public void testGetTextPositionWithMultiLineComment() throws Exception {
		Lexer lex= createLexer("ab\n/*comment line 2\ncomment line 3\ncomment line 4*/\ncde\nfgh");
		lex.next();
		int col1= lex.getColumn();
		int line1= lex.getLine();
		lex.next();
		int col2= lex.getColumn();
		int line2= lex.getLine();
		lex.next();
		int col3= lex.getColumn();
		int line3= lex.getLine();
		assertTrue(col1 == 3 && line1 == 1);
		assertTrue(col2 == 4 && line2 == 5);
		assertTrue(col3 == 4 && line3 == 6);
	}
	
	@Test
	public void testGetTextPosition3Lines() throws Exception {
		Lexer lex= createLexer("ab\ncde\nfgh");
		lex.next();
		int col1= lex.getColumn();
		int line1= lex.getLine();
		lex.next();
		int col2= lex.getColumn();
		int line2= lex.getLine();
		lex.next();
		int col3= lex.getColumn();
		int line3= lex.getLine();
		assertTrue(col1 == 3 && line1 == 1);
		assertTrue(col2 == 4 && line2 == 2);
		assertTrue(col3 == 4 && line3 == 3);
	}
	

	@Test
	public void shouldHandleCommentsWithStars() throws Exception {
		assertTrue(createLexer("/**\n Comment over multiple * lines.\n**/").next().isEof());
	}

	@Test
	public void shouldNotIgnorePartsAfterCommentsWithStars() throws Exception {
		assertEquals("class", createLexer("/*\n**/class").next().getValue());
	}

	@Test
	public void shouldOutputErrorGivenNonAsciiCharacters() throws Exception {
		assertTrue(createLexer("∞").next() instanceof edu.kit.pp.minijava.tokens.Error);
	}

	@Test
	public void shouldNotAllowIntegersWithLeadingZeros() throws Exception {
		Lexer lexer = createLexer("012");
		assertEquals("0", lexer.next().getValue());
		assertEquals("12", lexer.next().getValue());
	}

	@Test
	public void shouldAccecptSingleOperator() throws Exception {
		Lexer lexer = createLexer("=");
		assertTrue(lexer.next() instanceof Operator);
		assertTrue(lexer.next() instanceof Eof);
	}

	private Lexer createLexer(String input) throws IOException {
		return new Lexer(new StringReader(input));
	}
}