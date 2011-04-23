#include <string>
#include <cstdlib>
#include <iostream>
#include <vector>
#include <set>

const int ExpressionSkipProbability=2;
const int ExpressionRecursionSoftLimit=150;
const int StatementRecursionSoftLimit=150;
const bool SanerWhitespaces=false;


const int KeywordCount = 53;

std::string Keywords[] = {"abstract", "assert", "boolean", "break", "byte", "case", "catch", "char", "class", "const", "continue", "default", "double", "do", "else", "enum", "extends", "false", "finally", "final", "float", "for", "goto", "if", "implements", "import", "instanceof", "interface", "int", "long", "native", "new", "null", "package", "private", "protected", "public", "return", "short", "static", "strictfp", "super", "switch", "synchronized", "this", "throws", "throw", "transient", "true", "try", "void", "volatile", "while"};

std::set<std::string> KeywordSet;

const int OperatorCount = 46;

std::string Operators[] = {"!=", "!", "(", ")", "*=", "*", "++", "+=", "+", ",", "-=", "--", "-", ".", "/=", "/", ":", ";", "<<=", "<<", "<=", "<", "==", "=", ">=", ">>=", ">>>=", ">>>", ">>", ">", "?", "%=", "%", "&=", "&&", "&", "[", "]", "^=", "^", "{", "}", "~", "|=", "||", "|"};

char IdentStartChars[] = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ_";
char IdentChars[] = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ_0123456789";

std::ostream& SourceOutput = std::cout;

std::ostream& TokenOutput = std::cerr;

void emitComment(){
  SourceOutput << "/*";

  while(rand()%50) {
    char c=rand()%256;

    SourceOutput<<c;
    
    if(c=='*'){
      do c=rand()%256; while (c=='/');
      SourceOutput<<c;
    }
  }

  SourceOutput << "*/";
}

void emitWhitespace()
{
  if(SanerWhitespaces){
    SourceOutput << " ";
    return;
  }

  if(!(rand()%10)) emitComment();

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

  while(rand()%5) switch (rand()%5) {
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
    case 4:
      if(!(rand()%10)) emitComment();
      break;
    }  
}

template<class A,class B> void emit(A token, B source){
  TokenOutput << token << "\n";
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


void genIdent()
{
  std::string name;

  do
  {
    name = genIdentStartChar();
    while(rand()%10) name+= genIdentChar();
  } while(KeywordSet.find(name)!=KeywordSet.end());

  emit("identifier "+name,name);
}

void genIntegerLiteral(){  
  if(!(rand()%5)) {
    emit("integer literal 0","0");
    return;
  }

  std::string s;

  s='1'+(rand()%9);

  while(rand()%10) s+='0'+rand()%10;

  emit("integer literal "+s,s);
}

void genBlock();
void genStatement();
void genExpresssion();
void genBasicType();
void genLocalVarDec();
void genType();

// NewArrayExpression → new BasicType [ Expression ] ([ ])* 

void genNewArrayExpression(){
  emit("new");
  genBasicType();
  emit("[");
  genExpresssion();
  emit("]");
  while(!(rand()%3)) {
    emit("[");
    emit("]");
  }
}

// NewObjectExpression → new IDENT ( )
void genNewObjectExpression(){
  emit("new");
  genIdent();
  emit("(");
  emit(")");
}

// Arguments → (Expression (, Expression)*)?

void genArguments(){
  if(rand()%2) return;
  
  genExpresssion();

  while(rand()%3){
    emit(",");
    genExpresssion();
  }
}

// ArrayAccess → [ Expression ]

void genArrayAccess(){
  emit("[");
  genExpresssion();
  emit("]");
}

// FieldAccess → . IDENT

void genFieldAccess(){
  emit(".");
  genIdent();
}

// MethodInvocation → . IDENT ( Arguments )

void genMethodInvokation(){
  emit(".");
  genIdent();
  emit("(");
  genArguments();
  emit(")");
}

// PostfixOp → MethodInvocation | FieldAccess | ArrayAccess

void genPostfixOp(){
  switch(rand()%3){
  case 0:
    genMethodInvokation();
    break;
  case 1:
    genFieldAccess();
    break;
  case 2:
    genArrayAccess();
    break;
  }
}

// PrimaryExpression → null | false | true | INTEGER_LITERAL | IDENT | IDENT ( Arguments ) | this | ( Expression ) | NewObjectExpression | NewArrayExpression

void genPrimaryExpression(){
  switch(rand()%10){
  case 0:
    emit("null");
    break;
  case 1:
    emit("false");
    break;
  case 2:
    emit("true");
    break;
  case 3:
    genIntegerLiteral();
    break;
  case 4:
    genIdent();
    break;
  case 5:
    genIdent();
    emit("(");
    genArguments();
    emit(")");
    break;
  case 6:
    emit("this");
    break;
  case 7:    
    emit("(");
    genExpresssion();
    emit(")");
    break;
  case 8:
    genNewObjectExpression();
    break;
  case 9:
    genNewArrayExpression();
    break;    
  }  
}

// PostfixExpression → PrimaryExpression (PostfixOp)*

void genPostfixExpression(){
  genPrimaryExpression();
  while(rand()%3) genPostfixOp();
}

// UnaryExpression → PostfixExpression | (! | -) UnaryExpression

void genUnaryExpression(){
  if(rand()%ExpressionSkipProbability) genPostfixExpression();
    else {
      if(rand()%2) emit ("!");
      else emit("-");
      genUnaryExpression();
    }
}

// MultiplicativeExpression → (MultiplicativeExpression (* | / | %))? UnaryExpression

void genMultiplicativeExpression(){
  if(!(rand()%ExpressionSkipProbability)){
    genMultiplicativeExpression();
    switch(rand()%3){
    case 0:
      emit("*");
      break;
    case 1:
      emit("/");
      break;
    case 2:
      emit("%");
      break;
    }
  }
  genUnaryExpression();
}

// AdditiveExpression → (AdditiveExpression (+ | -))? MultiplicativeExpression
void genAdditiveExpresssion(){
  if(!(rand()%ExpressionSkipProbability)) {
    genAdditiveExpresssion();
    if(rand()%2) emit("+");
    else emit("-");
  }

  genMultiplicativeExpression();
}

// RelationalExpression → (RelationalExpression (< | <= | > | >=))? AdditiveExpression

void genRelationalExpression(){
  if(!(rand()%ExpressionSkipProbability)){
    genRelationalExpression();
    switch(rand()%4){
    case 0:
      emit("<");
      break;
    case 1:
      emit("<=");
      break;
    case 2:
      emit(">");
      break;
    case 3:
      emit(">=");
      break;
    }
  }

  genAdditiveExpresssion();
}

// EqualityExpression → (EqualityExpression (== | !=))? RelationalExpression

void genEqualityExpression(){
  if(!(rand()%ExpressionSkipProbability)){
    genEqualityExpression();
    if(rand()%2) emit("==");
    else emit("!=");
  }
  genRelationalExpression();
}

// LogicalAndExpression → (LogicalAndExpression &&)? EqualityExpression

void genLogicalAndExpression(){
  if(!(rand()%ExpressionSkipProbability)){
    genLogicalAndExpression();
    emit("&&");
  }
  genEqualityExpression();
}

// LogicalOrExpression → (LogicalOrExpression ||)? LogicalAndExpression

void genLogicalOrExpression(){
  if(!(rand()%ExpressionSkipProbability)){
    genLogicalOrExpression();
    emit("||");
  }
  genLogicalAndExpression();
}

// AssignmentExpression → LogicalOrExpression (= AssignmentExpression)?

void genAssignmentExpression(){
  genLogicalOrExpression();
  if(!(rand()%ExpressionSkipProbability)) {
    emit("=");
    genAssignmentExpression();    
  }
}

// Expression → AssignmentExpression

void genExpresssion(){

  // prevent excessive recursions
  static int ExpressionDepth=0;
  ExpressionDepth++;
  if( (ExpressionDepth>ExpressionRecursionSoftLimit) && (rand()%(ExpressionDepth-ExpressionRecursionSoftLimit)) ){
    emit("null");
    ExpressionDepth--;
    return;
  }

  genAssignmentExpression();
  ExpressionDepth--;
}

// ReturnStatement → return Expression? ;

void genReturnStatement(){
  emit("return");
  if(rand()%2) genExpresssion();
  emit(";");
}

// ExpressionStatement → Expression ;

void genExpresssionStatement(){
  genExpresssion();
  emit(";");
}

// IfStatement → if ( Expression ) Statement (else Statement)?

void genIfStatement(){
  emit("if");
  emit("(");
  genExpresssion();
  emit(")");
  genStatement();

  if(rand()%2) {
    emit("else");
    genStatement();
  }
}

// WhileStatement → while ( Expression ) Statement

void genWhileStatement(){
  emit("while");
  emit("(");
  genExpresssion();
  emit(")");
  genStatement();
}

// EmptyStatement → ;

void genEmptyStatement(){
  emit(";");
}

// LocalVariableDeclarationStatement → Type IDENT (= Expression)? ;

void genLocalVarDec(){
  genType();
  genIdent();
  if(rand()%2){
    emit("=");
    genExpresssion();
  }
  emit(";");
}

// Statement → Block | EmptyStatement | IfStatement | ExpressionStatement | WhileStatement | ReturnStatement

void genStatement(){

  // prevent excessive recutsion
  static int StatementDepth=0;
  StatementDepth++;

  if( (StatementDepth > StatementRecursionSoftLimit) && (rand()%(StatementDepth-StatementRecursionSoftLimit)) ) {
    emit(";");
    StatementDepth--;
    return;
  }

  

  switch(rand()%6) {
  case 0:
    genBlock();
    break;
  case 1:
    genEmptyStatement();
    break;
  case 2:
    genIfStatement();
    break;
  case 3:
    genExpresssionStatement();
    break;
  case 4:
    genWhileStatement();
    break;
  case 5:
    genReturnStatement();
    break;
  }
  StatementDepth--;
}

// BlockStatement → Statement | LocalVariableDeclarationStatement

void genBlockStatement(){
  if(rand()%2) genStatement();
  else genLocalVarDec();
}

// Block → { BlockStatement* }

void genBlock(){
  emit("{");
  while(rand()%20) genBlockStatement();
  emit("}");
}

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
    genIdent();
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

void genParameter(){
  genType();
  genIdent();
}



// Parameters → Parameter | Parameter , Parameters

void genParameters(){
  genParameter();
  
  if(!(rand()%3)) {
    emit(",");
    genParameters();
    }
}

// MainMethod → public static void IDENT ( String [ ] IDENT ) Block

void genMainMethod(){
  emit("public");
  emit("static");
  emit("void");
  genIdent();
  emit("(");
  emit("identifier String", "String");
  emit("[");
  emit("]");
  genIdent();
  emit (")");
  genBlock();
}

// Method → public Type IDENT ( Parameters? ) Block

void genMethod(){
  emit("public");
  genType();
  emit("(");
  if((rand()%4)!=0) genParameters();
  emit(")");
  genBlock();
}

// Field → public Type IDENT ;

void genField() {
  emit("public");

  genType();

  genIdent();

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
  emit("class");
  genIdent();
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

  SourceOutput.flush();

  TokenOutput << "EOF" << std::endl;

  return 0;
}


