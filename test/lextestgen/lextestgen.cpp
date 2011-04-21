#include <string>
#include <cstdlib>
#include <iostream>
#include <vector>
#include <set>

const int KeywordCount = 53;

std::string Keywords[] = {"abstract", "assert", "boolean", "break", "byte", "case", "catch", "char", "class", "const", "continue", "default", "double", "do", "else", "enum", "extends", "false", "finally", "final", "float", "for", "goto", "if", "implements", "import", "instanceof", "interface", "int", "long", "native", "new", "null", "package", "private", "protected", "public", "return", "short", "static", "strictfp", "super", "switch", "synchronized", "this", "throws", "throw", "transient", "true", "try", "void", "volatile", "while"};

std::set<std::string> KeywordSet;

const int OperatorCount = 46;

std::string Operators[] = {"!=", "!", "(", ")", "*=", "*", "++", "+=", "+", ",", "-=", "--", "-", ".", "/=", "/", ":", ";", "«=", "«", "<=", "<", "==", "=", ">=", "»=", "»>=", "»>", "»", ">", "?", "%=", "%", "&=", "&&", "&", "[", "]", "^=", "^", "{", "}", "~", "|=", "||", "|"};

char IdentStartChars[] = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ_";
char IdentChars[] = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ_0123456789";

std::ostream& SourceOutput = std::cout;

std::ostream& TokenOutput = std::cerr;

void emitWhitespace()
{
  switch (rand()%4) {
    case 0:
      SourceOutput << " ";
      break;
    case 1:
      SourceOutput << "\n";
      break;
    case 2:
      SourceOutput << "\r";
      break;
    case 3:
      SourceOutput << "\t";
      break;
    }  

  while(rand()%5) switch (rand()%4) {
    case 0:
      SourceOutput << " ";
      break;
    case 1:
      SourceOutput << "\n";
      break;
    case 2:
      SourceOutput << "\r";
      break;
    case 3:
      SourceOutput << "\t";
      break;
    }  
}

template<class A> void emit(A token, A source){
  TokenOutput << token << std::endl;
  SourceOutput << source;
  emitWhitespace();
}

template<class A> void emit(A token){
  emit(token,token);
}

char genIdentStartChar()
{
  int i=rand()%(sizeof(IdentStartChars)-1);

  return IdentStartChars[i];
}

char genIdentChar()
{
  int i=rand()%(sizeof(IdentChars)-1);

  return IdentChars[i];
}


void genIdent(std::string& name)
{
  do
  {
    name = genIdentStartChar();
    while(rand()%10) name+= genIdentChar();
  } while(KeywordSet.find(name)!=KeywordSet.end());
}


// Statement → Block | EmptyStatement | IfStatement | ExpressionStatement | WhileStatement | ReturnStatement
// Block → { BlockStatement* }
// BlockStatement → Statement | LocalVariableDeclarationStatement
// LocalVariableDeclarationStatement → Type IDENT (= Expression)? ;
// EmptyStatement → ;
// WhileStatement → while ( Expression ) Statement
// IfStatement → if ( Expression ) Statement (else Statement)?
// ExpressionStatement → Expression ;
// ReturnStatement → return Expression? ;
// Expression → AssignmentExpression
// AssignmentExpression → LogicalOrExpression (= AssignmentExpression)?
// LogicalOrExpression → (LogicalOrExpression ||)? LogicalAndExpression
// LogicalAndExpression → (LogicalAndExpression &&)? EqualityExpression
// EqualityExpression → (EqualityExpression (== | !=))? RelationalExpression
// RelationalExpression → (RelationalExpression (< | <= | > | >=))? AdditiveExpression
// AdditiveExpression → (AdditiveExpression (+ | -))? MultiplicativeExpression
// MultiplicativeExpression → (MultiplicativeExpression (* | / | %))? UnaryExpression
// Una1ryExpression → PostfixExpression | (! | -) UnaryExpression
// PostfixExpression → PrimaryExpression (PostfixOp)*
// PostfixOp → MethodInvocation | FieldAccess | ArrayAccess
// MethodInvocation → . IDENT ( Arguments )
// FieldAccess → . IDENT
// ArrayAccess → [ Expression ]
// Arguments → (Expression (, Expression)*)?
// PrimaryExpression → null | false | true | INTEGER_LITERAL | IDENT | IDENT ( Arguments ) | this | ( Expression ) | NewObjectExpression | NewArrayExpression
// NewObjectExpression → new IDENT ( )
// NewArrayExpression → new BasicType [ Expression ] ([ ])* */

// BasicType → int | boolean | void | IDENT

void genBasicType() {
  switch(rand()%4) {
  case 0:
    emit("int");
    break;

  case 1:
    emit("boolean");
    break;

  case 2:
    emit("void");
    break;

  case 3:
    std::string name;
    genIdent(name);
    emit(name);
    break;
  }
}

// Type → Type [ ] | BasicType

void genType() {
  if((rand()%3)!=2) genBasicType();
  else{
    genType();
    emit("[");
    emit("]");
  }
}

// Parameter → Type IDENT

// Parameters → Parameter | Parameter , Parameters

// MainMethod → public static void IDENT ( String [ ] IDENT ) Block

void genMainMethod(){
}

// Method → public Type IDENT ( Parameters? ) Block

void genMethod(){
}

// Field → public Type IDENT ;

void genField() {
  std::string name;

  genIdent(name);

  emit("public");

  genType();

  emit(name);

  emit(";");
}

// ClassMember → Field | Method | MainMethod

void genClassMember(){
  switch(rand()%3) {
  case 0:
    genField();
    break;
  case 1:
    genMethod();
    break;
  case 2:
    genMainMethod();
    break;      
    }
}

// ClassDeclaration → class IDENT { ClassMember* }

void genClassDeclaration(){
  std::string name;
  genIdent(name);

  emit("class");
  emit(name);
  emit("{");

  while(rand()%10) genClassMember();

  emit("}");  
}

// Program → ClassDeclaration*

void genProgram(){
  while ((rand() % 5) != 0) genClassDeclaration();
}

int main(int argc, char** argv)
{
  if(argc==2)
    srand(atoi(argv[1]));
  else srand(0);

  for(int i=0;i<KeywordCount;i++) KeywordSet.insert(Keywords[i]);

  genProgram();

  TokenOutput << "EOF" << std::endl;

  return 0;
}


