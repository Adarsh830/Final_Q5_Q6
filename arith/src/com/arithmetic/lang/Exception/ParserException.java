package com.arithmetic.lang.Exception;

import com.arithmetic.lang.Token;

public class ParserException extends RuntimeException {
	public ParserException(String message) { super(message); }

	public ParserException(String message, Token token) { super(message + "\n line: " + token.line); }
}
