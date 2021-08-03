package com.arithmetic.lang;

import com.arithmetic.lang.Exception.LexerException;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

public class Lexer {

	private String           src;
	private int              pos;
	private int              colon;
	private int              line;
	private ArrayList<Token> tokens;

	public Lexer(String src) { this.src = src; pos = 0; colon = 1; line = 1; tokens = new ArrayList<>(); }

	private boolean nextToken() {
		if (pos >= src.length())
			return false;
		else {
			for (TokenType tt : TokenType.values()) {
				Matcher m = tt.pattern.matcher(src);
				m.region(pos, src.length());
				if (m.lookingAt()) {
					String tokenText = m.group();
					tokens.add(new Token(tt, tokenText, colon, line));
					if (tt == TokenType.LINE) {
						line++;
						colon = 1;
					} else
						colon += tokenText.length();
					pos = m.end();
					return true;
				}
			}
			throw new LexerException("Could not match any token at line: " + line + "\n: " + pos + "\n");
		}
	}

	public List<Token> lex() {
		while (nextToken())
			;
		return tokens;
	}

}