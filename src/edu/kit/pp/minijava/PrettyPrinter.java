package edu.kit.pp.minijava;

import edu.kit.pp.minijava.ast.Arguments;
import edu.kit.pp.minijava.ast.ArrayAccess;
import edu.kit.pp.minijava.ast.BasicType;
import edu.kit.pp.minijava.ast.BinaryExpression;
import edu.kit.pp.minijava.ast.Block;
import edu.kit.pp.minijava.ast.BlockStatement;
import edu.kit.pp.minijava.ast.ClassDeclaration;
import edu.kit.pp.minijava.ast.ClassMember;
import edu.kit.pp.minijava.ast.Expression;
import edu.kit.pp.minijava.ast.Field;
import edu.kit.pp.minijava.ast.FieldAccess;
import edu.kit.pp.minijava.ast.LocalMethodInvocationExpression;
import edu.kit.pp.minijava.ast.LocalVariableDeclarationStatement;
import edu.kit.pp.minijava.ast.MainMethod;
import edu.kit.pp.minijava.ast.Method;
import edu.kit.pp.minijava.ast.MethodInvocation;
import edu.kit.pp.minijava.ast.NewArrayExpression;
import edu.kit.pp.minijava.ast.NewObjectExpression;
import edu.kit.pp.minijava.ast.Node;
import edu.kit.pp.minijava.ast.Parameter;
import edu.kit.pp.minijava.ast.Parameters;
import edu.kit.pp.minijava.ast.PostfixExpression;
import edu.kit.pp.minijava.ast.PostfixOp;
import edu.kit.pp.minijava.ast.PrimaryExpression;
import edu.kit.pp.minijava.ast.Program;
import edu.kit.pp.minijava.ast.Type;
import edu.kit.pp.minijava.ast.UnaryExpression;

public class PrettyPrinter implements NodeVisitor {

	@Override
	public String process(Program prog) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String process(ClassDeclaration classDecl) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String process(ClassMember classMember) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String process(MainMethod mainMethod) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String process(Method method) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String process(Field field) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String process(Parameters params) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String process(Parameter param) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String process(Type type) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String process(BasicType basType) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String process(Block block) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String process(BlockStatement blockStmt) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String process(LocalVariableDeclarationStatement locVarDeclStmt) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String process(Expression expr) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String process(BinaryExpression binExpr) {
		StringBuilder s= new StringBuilder();
		s.append("(" + binExpr.getLeft() + " " + binExpr.getToken() + " " + binExpr.getRight() + ")");
		return s.toString();
	}

	@Override
	public String process(PostfixExpression postfixExpr) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String process(PrimaryExpression primExp) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String process(UnaryExpression unaryExp) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String process(PostfixOp postfixOp) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String process(LocalMethodInvocationExpression locMethInvocExpr) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String process(NewObjectExpression newObjExpr) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String process(NewArrayExpression newArrayExpr) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String process(MethodInvocation methodInvoc) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String process(FieldAccess fieldAccess) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String process(ArrayAccess arrayAccess) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String process(Arguments arguments) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String process(Node node) {
		// TODO Auto-generated method stub
		return null;
	}

}
