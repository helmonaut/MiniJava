package edu.kit.pp.minijava.ast;

public class Type extends Node {

	private BasicType _basicType;
	private int _dimension;

	public Type(BasicType basicType, int dimension) {
		_basicType = basicType;
		_dimension = dimension;
	}

}
