package edu.kit.pp.minijava;

import edu.kit.pp.minijava.ast.*;

public interface NodeVisitor {
	String process(Node node);
	String process(Program prog);
	String process(ClassDeclaration classDecl);
	String process(ClassMember classMember);
	String process(MainMethod mainMethod);
	String process(Method method);
	String process(Field field);
	String process(Parameters params);
	String process(Parameter param);
	String process(Type type);
	String process(BasicType basType);
	String process(Block block);
	String process(BlockStatement blockStmt);
	String process(LocalVariableDeclarationStatement locVarDeclStmt);
	String process(Expression expr);
	String process(BinaryExpression binExpr);
	String process(PostfixExpression postfixExpr);
	String process(PrimaryExpression primExp);
	String process(UnaryExpression unaryExp);
	String process(PostfixOp postfixOp);
	String process(LocalMethodInvocationExpression locMethInvocExpr);
	String process(NewObjectExpression newObjExpr);
	String process(NewArrayExpression newArrayExpr);
	String process(MethodInvocation methodInvoc);
	String process(FieldAccess fieldAccess);
	String process(ArrayAccess arrayAccess);
	String process(Arguments arguments);
}
