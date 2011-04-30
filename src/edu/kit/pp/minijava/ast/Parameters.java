package edu.kit.pp.minijava.ast;

import java.util.LinkedList;
import java.util.List;

public class Parameters extends Node {

	private List<Parameter> _parameters;

	public Parameters() {
		_parameters = new LinkedList<Parameter>();
	}

	public void add(Parameter p) {
		_parameters.add(p);
	}
}
