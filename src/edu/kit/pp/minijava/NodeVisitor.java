package edu.kit.pp.minijava;

import edu.kit.pp.minijava.ast.*;

public interface NodeVisitor {
	void processProgram(Program prog);
	void processClassDeclaration(ClassDeclaration classDecl);
	void processClassMember(ClassMember classMember);
	void processMainMethod(MainMethod mainMethod);
	void processMethod(Method method);
	void processField(Field field);
	void processParameters(Parameters params);
	void processParameter(Parameter param);
	void processType(Type type);
	void processBasicType(BasicType basType);
	void processBlock(Block block);
	void processBlockStatement(BlockStatement blockStmt);
	void processLocalVariableDeclarationStatement(LocalVariableDeclarationStatement locVarDeclStmt);
	void processExpression(Expression expr);
	void processBinaryExpression(BinaryExpression binExpr);
	void processPostfixExpression(PostfixExpression postfixExpr);
	void processPrimaryExpression(PrimaryExpression primExp);
	void processUnaryExpression(UnaryExpression unaryExp);
	void processPostfixOp(PostfixOp postfixOp);
	void processLocalMethodInvocExpr(LocalMethodInvocationExpression locMethInvocExpr);
	void processNewObjectExpression(NewObjectExpression newObjExpr);
	void processNewArrayExpression(NewArrayExpression newArrayExpr);
	void processMethodInvocation(MethodInvocation methodInvoc);
	void processFieldAccess(Field field);
	void processArrayAccess(ArrayAccess arrayAccess);
	void processArguments(Arguments arguments);
}
