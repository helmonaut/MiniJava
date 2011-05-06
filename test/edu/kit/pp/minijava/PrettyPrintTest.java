package edu.kit.pp.minijava;

import edu.kit.pp.minijava.ast.BinaryExpression;
import edu.kit.pp.minijava.ast.PrimaryExpression;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class PrettyPrintTest extends TokenHelper {

	public PrettyPrintTest() {

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
	public void printSimpleExpression() throws Exception {
		BinaryExpression e = new BinaryExpression(O("+"), new PrimaryExpression(I("a")), new PrimaryExpression(I("b")));
		assertEquals("(a + b)", e.print());
//		PrettyPrinter pp= new PrettyPrinter();
//		assertEquals("(a + b)", pp.process(e));
	}
}
