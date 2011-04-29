package edu.kit.pp.minijava;

import edu.kit.pp.minijava.ast.Node;
import edu.kit.pp.minijava.tokens.Token;
import edu.kit.pp.minijava.tokens.Eof;
import java.io.IOException;
import java.util.HashMap;

public class Parser {
	public static class UnexpectedTokenException extends Exception {
		private Token _token;

		public UnexpectedTokenException(Token token) {
			_token = token;
		}

		public Token getToken() {
			return _token;
		}
	}

	private static class ParserFunction {
		public static abstract class ParseExpressionPrefixFunction{
			Node parse() throws UnexpectedTokenException{return new Node();};
		}

		public static abstract class ParseExpressionInfixFunction{
			Node parse(Node left) throws UnexpectedTokenException{return left;};
		}

		public ParseExpressionPrefixFunction _parseExpressionPrefixFunction;
		public int _precedence;
		public ParseExpressionInfixFunction _parseExpressionInfixFunction;

		ParserFunction(int p, ParseExpressionPrefixFunction f, ParseExpressionInfixFunction inf){
			_parseExpressionPrefixFunction=f;
			_precedence=p;
			_parseExpressionInfixFunction=inf;
		}
	}

	private PeekingLexer _lexer;
	private static final int LOOK_AHEAD_SIZE=4;
	private HashMap<String, ParserFunction> _expressionParsers;

	private void initalizeExpressionParserMap(){
		_expressionParsers=new HashMap<String, ParserFunction>();

		_expressionParsers.put("+", new ParserFunction(0,
													   null,
													   new ParserFunction.ParseExpressionInfixFunction(){
														   Node parse(Node left) throws UnexpectedTokenException{
															   expectToken("+");
															   parseExpression(1);
															   return new Node();
														   }
													   }
													   ));
	}

	public Parser(Lexer lexer) throws IOException {
		_lexer = new PeekingLexer(lexer, LOOK_AHEAD_SIZE);

		initalizeExpressionParserMap();
	}

	private void consumeToken() {
		try {
			_lexer.next();
		} catch (IOException e) {
			System.out.println("Wir müssen was gegen diese IOException-Flut tun :/");
		}
	}

	private Token getCurrentToken() {
		return _lexer.peek(0);
	}

	private boolean acceptToken(String s, int pos){
		return _lexer.peek(pos).toString().equals(s);
	}

	private boolean acceptToken(String s){
		return acceptToken(s,0);
	}

	private boolean acceptIdentifier() {
		return getCurrentToken().isIdentifier();
	}

	private void expectToken(String s) throws UnexpectedTokenException {
		if(!acceptToken(s)) throw new UnexpectedTokenException(getCurrentToken());
		consumeToken();
	}

	private void expectIdentifier() throws UnexpectedTokenException {
		if(!getCurrentToken().isIdentifier()) throw new UnexpectedTokenException(getCurrentToken());
		consumeToken();
	}

	private Node parsePrimaryExpression() throws UnexpectedTokenException{
		expectToken("integer literal 23");
		return new Node();
	}

	private Node parseExpression(int precedence) throws UnexpectedTokenException {
		ParserFunction pf=_expressionParsers.get(getCurrentToken().toString());
		Node left;

		if(pf!=null && pf._parseExpressionPrefixFunction!=null) left=pf._parseExpressionPrefixFunction.parse();
		else left=parsePrimaryExpression();

		while(true){
			pf=_expressionParsers.get(getCurrentToken().toString());

			if(pf==null) break;// throw new UnexpectedTokenException(getCurrentToken());

			if(pf._parseExpressionInfixFunction == null  || pf._precedence < precedence) break;

			left=pf._parseExpressionInfixFunction.parse(left);
		}

		return new Node();
	}

	private Node parseExpressionStatement() throws UnexpectedTokenException {
		parseExpression(0);
		expectToken(";");
		return new Node();
	}

	private Node parseBlockStatement() throws UnexpectedTokenException {
		parseExpressionStatement();
		return new Node();
	}

	private Node parseBlock() throws UnexpectedTokenException {
		expectToken("{");
		while(!acceptToken("}")){
			parseBlockStatement();
		}
		expectToken("}");
		return new Node();
	}

	private Node parseParameters() throws UnexpectedTokenException {
		return new Node();
	}

	private Node parseType() throws UnexpectedTokenException {
		if(	!acceptToken("void")	&&
			!acceptToken("int")		&&
			!acceptToken("boolean") &&
			!acceptIdentifier()		) {
			throw new UnexpectedTokenException(getCurrentToken());
		}
		consumeToken();
		while(acceptToken("[") && acceptToken("]", 1)) {
			consumeToken();
			consumeToken();
		}
		if(acceptToken("[")) {
			throw new UnexpectedTokenException(getCurrentToken());
		}
		return new Node();
	}

	private Node parseMainMethod() throws UnexpectedTokenException {
		expectToken("static");
		expectToken("void");
		expectIdentifier();
		expectToken("(");
		expectToken("String");
		expectToken("[");
		expectToken("]");
		expectIdentifier();
		expectToken(")");
		return parseBlock();
		// return new ASTNode();
	}

	private Node parseMethod() throws UnexpectedTokenException {
		expectToken("(");
		parseParameters();
		expectToken(")");
		parseBlock();

		return new Node();
	}


	private Node parseField() throws UnexpectedTokenException {
		expectToken("public");
		parseType();
		expectIdentifier();
		expectToken(";");

		return new Node();
	}

	private Node parseClassMember() throws UnexpectedTokenException {
		expectToken("public");
		if( acceptToken("static", 1) /* &&
			acceptToken("void", 2)	 &&
				acceptToken("main", 3) */) {
			parseMainMethod();
		} else {
			parseType();
			expectIdentifier();
			// Field
			if(acceptToken(";")) {
				consumeToken();
			// Method
			} else {
				return parseMethod();
			}
		}

		return new Node();
	}

	private Node parseClass() throws UnexpectedTokenException {
		expectIdentifier();

		expectToken("{");

		while(!acceptToken("}")){
			parseClassMember();
		}

		expectToken("}");

		return new Node();
	}

	public Node parseProgram() throws UnexpectedTokenException {
		//		consumeToken();

		while(!acceptToken("EOF")) {
			// consumeToken();
			expectToken("class");
			parseClass();
		}

		expectToken("EOF");

		return new Node();
	}
}