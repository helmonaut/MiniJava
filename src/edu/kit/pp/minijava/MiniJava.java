package edu.kit.pp.minijava;

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

            while (nextToken != null) {
                System.out.println(nextToken);
                nextToken = lexer.next();
            }
        }
    }
}