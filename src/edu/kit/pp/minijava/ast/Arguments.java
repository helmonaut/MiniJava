package edu.kit.pp.minijava.ast;

import java.util.LinkedList;
import java.util.List;

public class Arguments extends Node {

	private List<Expression> _arguments;

	public Arguments() {
		_arguments = new LinkedList<Expression>();
	}

	public void add(Expression argument) {
		_arguments.add(argument);
	}
}
