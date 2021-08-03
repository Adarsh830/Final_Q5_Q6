package com.arithmetic.lang.Exception;

import com.arithmetic.lang.Token;

public class SemanticsException extends RuntimeException {
	public SemanticsException(String message) { super(message); }

	public SemanticsException(String message, Token token) { super(message + "\nline: " + token.line); }
}
