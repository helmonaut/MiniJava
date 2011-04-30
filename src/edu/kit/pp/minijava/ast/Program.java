package edu.kit.pp.minijava.ast;

import java.util.LinkedList;
import java.util.List;

public class Program extends Node {

	private List<ClassDeclaration> _classes;

	public Program() {
		_classes = new LinkedList<ClassDeclaration>();
	}

	public void add(ClassDeclaration c) {
		_classes.add(c);
	}
}
