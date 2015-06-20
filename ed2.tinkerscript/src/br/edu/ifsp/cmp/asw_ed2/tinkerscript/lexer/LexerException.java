package br.edu.ifsp.cmp.asw_ed2.tinkerscript.lexer;

public class LexerException extends Exception {
	private static final long serialVersionUID = -1027886543236653627L;

	public LexerException(int line, int column, Throwable cause) {
		super("Error while reading source code (at " + line + ":" + column + ")", cause);
	}
	
	public LexerException(int line, int column, String message) {
		this(line, column, new Exception(message));
	}
}
