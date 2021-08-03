package com.arithmetic.lang;

public class Token {

	public final TokenType type;
	public final String    text;
	public final int       colon;
	public final int       line;

	public Token(TokenType type, String text, int colon, int line) {
		this.type = type;
		this.text = text;
		this.colon = colon;
		this.line = line;
	}

	@Override
	public String toString() {
		return "{\ntokenType: " + type + ",\nline: " + line + ",\ncolon: " + colon + ",\ntext: " + text + "\n}";
	}
}