package edu.kit.pp.minijava.ast;

import java.util.LinkedList;
import java.util.List;

public class PostfixExpression extends Expression {
	private Expression _expression;
	private List<PostfixOp> _ops;
	
	public PostfixExpression(Expression expression) {
		super(expression.getToken());
		_expression = expression;
		_ops = new LinkedList<PostfixOp>();
	}
	
	public void add(PostfixOp op) {
		_ops.add(op);
	}
}