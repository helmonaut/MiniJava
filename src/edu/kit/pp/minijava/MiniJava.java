// vi:ai:noet sta sw=4 ts=4 sts=0
package edu.kit.pp.minijava;

import edu.kit.pp.minijava.tokens.Token;
import java.io.FileReader;
import java.io.IOException;

public class MiniJava {

	private static void runLexTest(String FileName) throws IOException {
		Lexer lexer = new Lexer(new FileReader(FileName));

		Token nextToken = lexer.next();

		while (!nextToken.isEof()) {
			System.out.println(nextToken);
			nextToken = lexer.next();
		}
		System.out.println(nextToken);

    }

	private static void runSyntaxCheck(String FileName) throws IOException {
		Lexer lexer = new Lexer(new FileReader(FileName));
		Parser parser = new Parser(lexer);
		
		try{
			parser.parseProgram();
		}
		catch(Parser.UnexpectedTokenException e){
			System.out.println("Unexpected Token: "+e.getToken());
			return;
		}

		System.out.println("Correct Syntax");
	}


	public static void main(String[] args) throws IOException {
		if (args.length == 0) {
			System.out.println("Usage: MiniJava [options] <file>");
			return;
		}		

		if (args[0].equals("--lextest")) runLexTest(args[args.length -1]);
		else if(args[0].equals("--syntaxcheck")) runSyntaxCheck(args[args.length -1]);		    
	}
}