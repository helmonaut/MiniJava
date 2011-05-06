package edu.kit.pp.minijava;

import edu.kit.pp.minijava.ast.Program;
import edu.kit.pp.minijava.tokens.Token;
import java.io.FileReader;
import java.io.IOException;

import java.io.File;
import java.io.FilenameFilter;

public class MiniJava {

	private static void syntaxCheckFiles(File[] files, String fail, String success) throws IOException {
		for(int i = 0; i < files.length; i++) {
			Lexer lexer = new Lexer(new FileReader(files[i]));
			Parser parser = new Parser(lexer);
			try {
				parser.parseProgram();
				if(null != success) {
					System.err.println(files[i].getCanonicalFile() + ": " + success);
				}
			} catch(Parser.UnexpectedTokenException e) {
				if(null != fail) {
					System.err.println(files[i].getCanonicalFile() + ": " + fail);
					//System.err.println("Unexpected Token: " + e.getToken());
					System.err.println(e.toString());
				}
			}
		}
	}

	private static void runTestFiles(String dirName) throws IOException {
		File failDir = new File(dirName, "fail");
		File successDir = new File(dirName, "success");

		FilenameFilter mjFilter = new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name) {
				return !name.startsWith(".") && name.endsWith(".mj");
			}
		};

		syntaxCheckFiles(failDir.listFiles(mjFilter), "ok, expected to fail", "not ok");
		syntaxCheckFiles(successDir.listFiles(mjFilter), "not ok", "ok");

	}

	private static void runLexTest(String fileName) throws IOException {
		runLexTest(new File(fileName));
	}

	private static void runLexTest(File file) throws IOException {
		Lexer lexer = new Lexer(new FileReader(file));

		Token nextToken = lexer.next();

		while (!nextToken.isEof()) {
			System.out.println(nextToken);
			nextToken = lexer.next();
		}
		System.out.println(nextToken);

	}

	private static void runSyntaxCheck(String fileName) throws IOException{
		runSyntaxCheck(new File(fileName));
	}

	private static void runSyntaxCheck(File file) throws IOException {
		Lexer lexer = new Lexer(new FileReader(file));
		Parser parser = new Parser(lexer);
		Program program;
		try{
			program = parser.parseProgram();
		}
		catch(Parser.UnexpectedTokenException e){
			System.out.println(e);
			return;
		}
		System.out.println(program);
		System.out.println("");
		System.out.println("Correct Syntax");
	}

	public static void printAST(String fileName) throws IOException {
		Lexer lexer = new Lexer(new FileReader(fileName));
		Parser parser = new Parser(lexer);
		Program program = parser.parseProgram();
		System.out.print(program.print());
	}


	public static void main(String[] args) throws IOException {
		if (args.length == 0) {
			System.out.println("Usage: MiniJava [options] <file>");
			return;
		}

		if (args[0].equals("--lextest"))
			runLexTest(args[args.length -1]);
		else if(args[0].equals("--syntaxcheck"))
			runSyntaxCheck(args[args.length -1]);
		else if(args[0].equals("--testdir"))
			runTestFiles(args[args.length - 1]);
		else if(args[0].equals("--print-ast"))
			printAST(args[args.length - 1]);
	}
}
