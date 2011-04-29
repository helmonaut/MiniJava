grammar MiniJava;

Program: ClassDeclaration*;

ClassDeclaration: 'class' IDENT '{' ClassMember* '}';

ClassMember: Field | Method | MainMethod;

Field: 'public' Type IDENT ';';

MainMethod: 'public static void' IDENT '(' 'String' '[' ']' IDENT ')' Block;

Method: 'public' Type IDENT '(' Parameters? ')' Block;

Parameters: Parameter | Parameter ',' Parameters;

Parameter: Type IDENT;

Type: BasicType ('[' ']')* ;

BasicType: 'int' | 'boolean' | 'void' | IDENT;

//rule Statement has non-LL(*) decision due to recursive rule invocations reachable from alts 3,4.  
//Resolve by left-factoring or using syntactic predicates or using backtrack=true option.
Statement: 	  Block
	| EmptyStatement
	| IfStatement
	| ExpressionStatement
	| WhileStatement
	| ReturnStatement
	;

Block: '{' BlockStatement* '}';

//rule BlockStatement has non-LL(*) decision due to recursive rule invocations reachable from alts 1,2.  
//Resolve by left-factoring or using syntactic predicates or using backtrack=true option.
BlockStatement: Statement 
	| LocalVariableDeclarationStatement;

LocalVariableDeclarationStatement: Type IDENT ('=' AssignmentExpression)? ';';

EmptyStatement: ';';

WhileStatement: 'while' '(' AssignmentExpression ')' Statement ;

IfStatement: 'if' '(' AssignmentExpression ')' Statement ('else' Statement)? ;

ExpressionStatement: AssignmentExpression ';' ;

ReturnStatement: 'return' AssignmentExpression? ';' ;

//Expression: AssignmentExpression ;

AssignmentExpression: 	LogicalOrExpression ('=' AssignmentExpression)? 	;

LogicalOrExpression: LogicalAndExpression ('||' LogicalOrExpression)? ;

LogicalAndExpression: EqualityExpression ('&&' LogicalAndExpression )? ;

EqualityExpression: RelationalExpression (( '==' | '!=' ) EqualityExpression )? ;

RelationalExpression: AdditiveExpression (('<' | '<=' | '>' | '>=') RelationalExpression )? ;

AdditiveExpression: MultiplicativeExpression (('+'|'-') AdditiveExpression)? ;

MultiplicativeExpression: UnaryExpression (('*' |  '/' | '%') MultiplicativeExpression)? ;

UnaryExpression: PostfixExpression | ('!'|'-') UnaryExpression ;

PostfixExpression: PrimaryExpression (PostfixOp)* ;

PostfixOp: MethodInvocation
	| FieldAccess
	| ArrayAccess
	;

MethodInvocation: '.' IDENT '(' Arguments ')';

FieldAccess: '.' IDENT ;
ArrayAccess: '[' AssignmentExpression ']' ;

Arguments: (AssignmentExpression (',' AssignmentExpression)*)? ;

PrimaryExpression: 'null'
	| 'false'
	| 'true'
	| INTEGER_LITERAL
	| IDENT
	| IDENT '(' Arguments ')'
	| 'this'
	| '(' AssignmentExpression ')'
	| NewObjectExpression
	| NewArrayExpression
	;

NewObjectExpression: 'new' IDENT '(' ')' ;

NewArrayExpression: 'new' BasicType '[' AssignmentExpression ']' ('[' ']')* ;

IDENT: ('_' |'a'..'z' | 'A'..'Z')+ ;
INTEGER_LITERAL: ('0'..'9')+ ;

