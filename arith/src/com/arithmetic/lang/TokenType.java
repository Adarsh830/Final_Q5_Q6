package com.arithmetic.lang;

import java.util.regex.Pattern;

public enum TokenType {
	MULTI_COMMENT("/\\*([^*]|(\\*+([^*/])))*\\*+/"), //
	COMMENT("//[^\\r\\n]+"), //
	NUMBER("[\\d]+"), //
	
	FOR("for"), //
	IF("if"), //
	ELSE("else"), //
	WHILE("while"), //
	DO("do"),//
	INT("int"),//
	FLOAT("float"),//
	SWITCH("switch"),//
	CLASS("class"),//
	VOID("void"),//
	BOOL("void"),//
	XOR("xor|XOR"), //
	NOT("not|NOT"), //
	OR("or|OR"), //
	AND("and|AND"),//
	PRINT("print"), //
	SEMICOLON(";"), //
	COMMA(","), //
	ID("[a-zA-Z][\\w]*"), //
	LAND("&&"), //
	LOR("\\|\\|"), //
	GREATER(">"), //
	LESS("<"), //
	GREATER_EQUAL(">="), //
	LESS_EQUAL("<="), //
	NEQUAL("!="), //
	EQUAL("=="), //
	ASSIGNMENT_ADD("\\+="), //
	ASSIGNMENT_SUB("-="), //
	ASSIGNMENT_MUL("\\*="), //
	ASSIGNMENT_DIV("/="), //
	ASSIGNMENT_AND("&="), //
	ASSIGNMENT_XOR("\\^="), //
	ASSIGNMENT_OR("\\|="), //
	INC("\\+\\+"), //
	ADD("\\+"), //
	DEC("--"), //
	SUB("-"), //
	MUL("\\*"), //
	DIV("/"), //
	LPAR("\\("), //
	RPAR("\\)"), //
	LBRACE("\\{"), //
	RBRACE("\\}"), //
	LINE("[\\r]*[\\n]"), //
	SPACE("[ \\s\\t\\v]+"), //
	ASSIGNMENT("="); //

	Pattern pattern;

	TokenType(String regEx) { this.pattern = Pattern.compile(regEx); }
}
