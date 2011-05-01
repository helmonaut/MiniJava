// vi:ai:noet sta sw=4 ts=4 sts=0
package edu.kit.pp.minijava;

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
					// System.err.println("Unexpected Token: " + e.getToken());
				}
			}
		}
	}

	private static void runTestFiles(String DirName) throws IOException {
		File failDir = new File(DirName, "fail");
		File successDir = new File(DirName, "success");

		FilenameFilter mjFilter = new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name) {
				return !name.startsWith(".") && name.endsWith(".mj");
			}
		};

		syntaxCheckFiles(failDir.listFiles(mjFilter), "ok", "not ok");
		syntaxCheckFiles(successDir.listFiles(mjFilter), "not ok", "ok");
		
	}
	
	private static void runLexTest(String FileName) throws IOException {
		runLexTest(new File(FileName));
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
	
	private static void runSyntaxCheck(String FileName) throws IOException{
		runSyntaxCheck(new File(FileName));
	}

	private static void runSyntaxCheck(File file) throws IOException {
		Lexer lexer = new Lexer(new FileReader(file));
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
		else if(args[0].equals("--testdir")) runTestFiles(args[args.length - 1]);
	}	
}
