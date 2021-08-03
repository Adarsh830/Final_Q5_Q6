package com.arithmetic.lang.Exception;

import com.arithmetic.lang.Token;

public class LexerException extends RuntimeException {
	public LexerException(String message) { super(message); }

	public LexerException(String message, Token token) { super(message + "\n line: " + token.line); }
}
