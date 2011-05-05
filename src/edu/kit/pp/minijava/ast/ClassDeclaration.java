package edu.kit.pp.minijava.ast;

import edu.kit.pp.minijava.tokens.Identifier;
import java.util.LinkedList;
import java.util.List;

public class ClassDeclaration extends Node {

	private Identifier _name;
	private List<MainMethod> _mainMethods;
	private List<Method> _methods;
	private List<Field> _fields;

	public ClassDeclaration(Identifier name) {
		_name = name;
		_mainMethods = new LinkedList<MainMethod>();
		_methods = new LinkedList<Method>();
		_fields = new LinkedList<Field>();
	}

	public void add(ClassMember member) {
		if (member instanceof Field) {
			add((Field) member);
		}
		else if (member instanceof Method) {
			add((Method) member);
		}
		else if (member instanceof MainMethod) {
			add((MainMethod) member);
		}
	}

	public void add(Field field) {
		_fields.add(field);
	}

	public void add(Method method) {
		_methods.add(method);
	}

	public void add(MainMethod mainMethod) {
		_mainMethods.add(mainMethod);
	}
}