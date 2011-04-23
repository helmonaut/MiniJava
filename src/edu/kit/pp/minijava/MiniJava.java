// vi:ai:noet sta sw=4 ts=4 sts=0
package edu.kit.pp.minijava;

import edu.kit.pp.minijava.tokens.Token;
import java.io.FileReader;
import java.io.IOException;

public class MiniJava {

    public static void main(String[] args) throws IOException {
        if (args.length == 0) {
            System.out.println("Usage: MiniJava [options] <file>");
            return;
        }

        Lexer lexer = new Lexer(new FileReader(args[args.length - 1]));

        if (args[0].equals("--lextest")) {
            Token nextToken = lexer.next();

	    while (!nextToken.isEof()) {
                System.out.println(nextToken);
                nextToken = lexer.next();
            }
	    System.out.println(nextToken);
        }
    }
}
