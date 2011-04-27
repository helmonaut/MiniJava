package edu.kit.pp.minijava;

import edu.kit.pp.minijava.ast.ASTNode;
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
			ASTNode parse() throws UnexpectedTokenException{return new ASTNode();};
		}
		
		public static abstract class ParseExpressionInfixFunction{
			ASTNode parse(ASTNode left) throws UnexpectedTokenException{return left;};
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

	private Lexer _lexer;
	private static final int LOOK_AHEAD_SIZE=4;
	private Token[] _lookAheadBuffer;
	private int _lookAheadBufferIndex;
	private HashMap<String, ParserFunction> _expressionParsers;
	
	private void initalizeExpressionParserMap(){
		_expressionParsers=new HashMap();

		_expressionParsers.put("+", new ParserFunction(0,
													   null,
													   new ParserFunction.ParseExpressionInfixFunction(){
														   ASTNode parse(ASTNode left) throws UnexpectedTokenException{
															   expectToken("+");
															   parseExpression(1);
															   return new ASTNode();
														   }
													   }
													   ));
	}

	public Parser(Lexer lexer) {
		_lexer=lexer;		
		_lookAheadBuffer=new Token[LOOK_AHEAD_SIZE];
		for(int i=0;i<LOOK_AHEAD_SIZE;i++) consumeToken();

		initalizeExpressionParserMap();
	}

	private void consumeToken() {
		try{
			_lookAheadBuffer[(_lookAheadBufferIndex)%LOOK_AHEAD_SIZE]=_lexer.next();
		} catch (IOException e) {
			_lookAheadBuffer[(_lookAheadBufferIndex)%LOOK_AHEAD_SIZE]=new Eof();	
		}			

		_lookAheadBufferIndex++;
		
		if(_lookAheadBufferIndex>=LOOK_AHEAD_SIZE) _lookAheadBufferIndex=0;
	}

	private Token lookAhead(int k) {
		return _lookAheadBuffer[(k+_lookAheadBufferIndex)%LOOK_AHEAD_SIZE];
	}
	
	private Token getCurrentToken() {
		return lookAhead(0);
	}

	private boolean acceptToken(String s, int pos){
		return lookAhead(pos).toString().equals(s);
	}

	private boolean acceptToken(String s){
		return acceptToken(s,0);
	}
	
	private void expectToken(String s) throws UnexpectedTokenException {
		if(!acceptToken(s)) throw new UnexpectedTokenException(getCurrentToken());		
		consumeToken();
	}

	private void expectIdentifier() throws UnexpectedTokenException {
		if(!getCurrentToken().isIdentifier()) throw new UnexpectedTokenException(getCurrentToken());
		consumeToken();
	}

	private ASTNode parsePrimaryExpression() throws UnexpectedTokenException{
		expectToken("integer literal 23");
		return new ASTNode();
	}

	private ASTNode parseExpression(int precedence) throws UnexpectedTokenException {
		ParserFunction pf=_expressionParsers.get(getCurrentToken().toString());
		ASTNode left;

		if(pf!=null && pf._parseExpressionPrefixFunction!=null) left=pf._parseExpressionPrefixFunction.parse();
		else left=parsePrimaryExpression();

		while(true){
			pf=_expressionParsers.get(getCurrentToken().toString());

			if(pf==null) break;// throw new UnexpectedTokenException(getCurrentToken());

			if(pf._parseExpressionInfixFunction == null  || pf._precedence < precedence) break;
			
			left=pf._parseExpressionInfixFunction.parse(left);
		}

		return new ASTNode();
	}

	private ASTNode parseExpressionStatement() throws UnexpectedTokenException {
		parseExpression(0);
		expectToken(";");
		return new ASTNode();
	}
	
	private ASTNode parseBlockStatement() throws UnexpectedTokenException {
		parseExpressionStatement();
		return new ASTNode();
	}

	private ASTNode parseBlock() throws UnexpectedTokenException {
		expectToken("{");
		while(!acceptToken("}")){
			parseBlockStatement();
		}
		expectToken("}");
		return new ASTNode();
	}

	private ASTNode parseParameters() throws UnexpectedTokenException {
		return new ASTNode();
	}

	private ASTNode parseType() throws UnexpectedTokenException {
		expectToken("void");

		return new ASTNode();
	}
	
	private ASTNode parseMainMethod() throws UnexpectedTokenException {
		return new ASTNode();
	}

	private ASTNode parseMethod() throws UnexpectedTokenException {
		expectToken("public");
		parseType();
		expectIdentifier();
		expectToken("(");
		parseParameters();
		expectToken(")");
		parseBlock();
		
		return new ASTNode();
	}


	private ASTNode parseField() throws UnexpectedTokenException {
		expectToken("public");
		parseType();
		expectIdentifier();
		expectToken(";");

		return new ASTNode();
	}

	private ASTNode parseClassMember() throws UnexpectedTokenException {
		if(acceptToken(";",3)) parseField();
		else if(acceptToken("(",3)) parseMethod();
		else parseMainMethod();
		
		return new ASTNode();		
	}

	private ASTNode parseClass() throws UnexpectedTokenException {
		expectIdentifier();

		expectToken("{");

		while(!acceptToken("}")){
			parseClassMember();
		}

		expectToken("}");

		return new ASTNode();
	}	

	public ASTNode parseProgram() throws UnexpectedTokenException {
		//		consumeToken();

		while(!acceptToken("EOF")) {
			consumeToken();
			parseClass();			
		}

		expectToken("EOF");
		
		return new ASTNode();
	}
}