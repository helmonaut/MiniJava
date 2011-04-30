package edu.kit.pp.minijava;

import edu.kit.pp.minijava.ast.*;
import edu.kit.pp.minijava.tokens.*;
import java.io.IOException;
import java.util.HashMap;
// TODO  delete throws UnexpectedToken...

public class Parser {

	public static class UnexpectedTokenException extends RuntimeException {
		private Token _token;

		public UnexpectedTokenException(Token token) {
			_token = token;
		}

		public Token getToken() {
			return _token;
		}
	}

	private static class ParserFunction {
		public static interface ParseExpressionPrefixFunction {
			Expression parse();
		}
		public static interface ParseExpressionInfixFunction {
			Expression parse(Expression left);
		}
		public static class ParseBinaryExpressionInfixFunction implements ParseExpressionInfixFunction {
			private Parser _parser;
			private String[] _operators;
			private int _precedence;

			public ParseBinaryExpressionInfixFunction(Parser parser, String[] operators, int precedence) {
				_parser = parser;
				_operators = operators;
				_precedence = precedence;
			}

			@Override
			public Expression parse(Expression left) {
				String o = null;
				for (String operator : _operators) {
					if (_parser.acceptToken(operator)) {
						o = operator;
						break;
					}
				}
				if (o == null)
					throw new UnexpectedTokenException(_parser.getCurrentToken());
				Token t = _parser.expectToken(o);
				Expression right = _parser.parseExpression(_precedence + 1);
				return new BinaryExpression(t, left, right);
			}
		}

		public ParseExpressionPrefixFunction _parseExpressionPrefixFunction;
		public int _precedence;
		public ParseExpressionInfixFunction _parseExpressionInfixFunction;

		ParserFunction(int p, ParseExpressionPrefixFunction f, ParseExpressionInfixFunction inf) {
			_precedence=p;
			_parseExpressionPrefixFunction=f;
			_parseExpressionInfixFunction=inf;
		}
	}

	private PeekingLexer _lexer;
	private static final int LOOK_AHEAD_SIZE=4;
	private HashMap<String, ParserFunction> _expressionParsers;

	private void initalizeExpressionParserMap(){
		_expressionParsers=new HashMap<String, ParserFunction>();

		_expressionParsers.put("=", new ParserFunction(1, null, new ParserFunction.ParseBinaryExpressionInfixFunction(this, new String[] {"="}, 1)));
		_expressionParsers.put("||", new ParserFunction(2, null, new ParserFunction.ParseBinaryExpressionInfixFunction(this, new String[] {"||"}, 2)));
		_expressionParsers.put("&&", new ParserFunction(3, null, new ParserFunction.ParseBinaryExpressionInfixFunction(this, new String[] {"&&"}, 3)));
		ParserFunction ef = new ParserFunction(4, null, new ParserFunction.ParseBinaryExpressionInfixFunction(this, new String[] {"==", "!="}, 4));
		_expressionParsers.put("==", ef);
		_expressionParsers.put("!=", ef);
		ParserFunction cf = new ParserFunction(5, null, new ParserFunction.ParseBinaryExpressionInfixFunction(this, new String[] {"<", "<=", ">", ">="}, 5));
		_expressionParsers.put("<", cf);
		_expressionParsers.put("<=", cf);
		_expressionParsers.put(">", cf);
		_expressionParsers.put(">=", cf);
		ParserFunction af = new ParserFunction(6, null, new ParserFunction.ParseBinaryExpressionInfixFunction(this, new String[] {"+", "-"}, 6));
		_expressionParsers.put("+", af);
		_expressionParsers.put("-", af);
		ParserFunction mf = new ParserFunction(7, null, new ParserFunction.ParseBinaryExpressionInfixFunction(this, new String[] {"*", "/", "%"}, 7));
		_expressionParsers.put("*", mf);
		_expressionParsers.put("/", mf);
		_expressionParsers.put("%", mf);
	}

	public Parser(Lexer lexer) throws IOException {
		_lexer = new PeekingLexer(lexer, LOOK_AHEAD_SIZE);

		initalizeExpressionParserMap();
	}

	private Token consumeToken() {
		try {
			return _lexer.next();
		} catch (IOException e) {
			return null;
		}
	}

	private Token getCurrentToken() {
		return _lexer.peek(0);
	}

	private boolean acceptToken(String s, int pos){
		return _lexer.peek(pos).getValue().equals(s);
	}

	private boolean acceptToken(String s){
		return acceptToken(s, 0);
	}

	private boolean acceptIntegerLiteral() {
		return getCurrentToken() instanceof IntegerLiteral;
	}

	private boolean acceptIdentifier() {
		return getCurrentToken() instanceof Identifier;
	}

	private Token expectToken(String s) throws UnexpectedTokenException {
		if (!acceptToken(s))
			throw new UnexpectedTokenException(getCurrentToken());
		return consumeToken();
	}

	private Token expectIntegerLiteral() throws UnexpectedTokenException {
		if (!(getCurrentToken() instanceof IntegerLiteral))
			throw new UnexpectedTokenException(getCurrentToken());
		return consumeToken();
	}

	private Token expectIdentifier() throws UnexpectedTokenException {
		if (!(getCurrentToken() instanceof Identifier))
			throw new UnexpectedTokenException(getCurrentToken());
		return consumeToken();
	}

	private Expression parsePrimaryExpression() throws UnexpectedTokenException {
		if (acceptToken("null"))
			return new PrimaryExpression(expectToken("null"));
		else if (acceptToken("false"))
			return new PrimaryExpression(expectToken("false"));
		else if (acceptToken("true"))
			return new PrimaryExpression(expectToken("true"));
		else if (acceptIntegerLiteral())
			return new PrimaryExpression(expectIntegerLiteral());
		else if (acceptIdentifier()) {
			if (acceptToken("(", 1)) {
				return parseLocalMethodInvocation();
			}
			return new PrimaryExpression(expectIdentifier());
		}
		else if (acceptToken("this"))
			return new PrimaryExpression(expectToken("this"));
		else if (acceptToken("(")) {
			expectToken("(");
			Expression e = parseExpression(0);
			expectToken(")");
			return e;
		}
		else if (acceptToken("new")) {
			if (acceptToken("(", 2)) {
				expectToken("new");
				Token t = expectIdentifier();
				expectToken("(");
				expectToken(")");
				return new NewObjectExpression(t);
			}
			else if (acceptToken("[", 2)) {
				return parseNewArrayExpression();
			}
		}

		throw new UnexpectedTokenException(getCurrentToken());
	}

	private NewArrayExpression parseNewArrayExpression() {
		expectToken("new");
		BasicType bt = parseBasicType();
		expectToken("[");
		Expression e = parseExpression();
		expectToken("]");

		int fieldCount = 1;
		while (acceptToken("[")) {
			expectToken("[");
			expectToken("]");
			fieldCount += 1;
		}

		return new NewArrayExpression(bt, e, fieldCount);
	}

	private BasicType parseBasicType() {
		if (acceptToken("int"))
			return new BasicType(expectToken("int"));
		else if (acceptToken("boolean"))
			return new BasicType(expectToken("boolean"));
		else if (acceptToken("void"))
			return new BasicType(expectToken("void"));
		else if (acceptIdentifier())
			return new BasicType(expectIdentifier());
		throw new UnexpectedTokenException(getCurrentToken());
	}

	private LocalMethodInvocationExpression parseLocalMethodInvocation() throws UnexpectedTokenException {
		Token t = expectIdentifier();
		expectToken("(");
		Arguments a = parseArguments();
		expectToken(")");
		return new LocalMethodInvocationExpression(t, a);
	}

	private Arguments parseArguments() throws UnexpectedTokenException {
		Arguments a = new Arguments();
		while (!acceptToken(")")) {
			a.add(parseExpression());
			if (acceptToken(")"))
				break;
			else
				expectToken(",");
		}
		return a;
	}

	public Expression parseExpression() throws UnexpectedTokenException {
		return parseExpression(0);
	}

	public Expression parseExpression(int precedence) throws UnexpectedTokenException {
		ParserFunction pf = _expressionParsers.get(getCurrentToken().toString());
		Expression left;

		if (pf != null && pf._parseExpressionPrefixFunction != null)
			left = pf._parseExpressionPrefixFunction.parse();
		else
			left = parsePrimaryExpression();

		while (true) {
			pf = _expressionParsers.get(getCurrentToken().toString());

			if (pf == null)
				break;// throw new UnexpectedTokenException(getCurrentToken());

			if (pf._parseExpressionInfixFunction == null || pf._precedence < precedence)
				break;

			left = pf._parseExpressionInfixFunction.parse(left);
		}

		return left;
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
		if (	!acceptToken("void")	&&
			!acceptToken("int")		&&
			!acceptToken("boolean") &&
			!acceptIdentifier()		) {
			throw new UnexpectedTokenException(getCurrentToken());
		}
		consumeToken();
		while (acceptToken("[") && acceptToken("]", 1)) {
			consumeToken();
			consumeToken();
		}
		if (acceptToken("[")) {
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