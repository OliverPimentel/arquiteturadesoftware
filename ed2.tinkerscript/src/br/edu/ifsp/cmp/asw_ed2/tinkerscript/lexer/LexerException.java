package br.edu.ifsp.cmp.asw_ed2.tinkerscript.lexer;

public class LexerException extends Exception {
	private static final long serialVersionUID = -1027886543236653627L;
	
	private final static String errorMessageFormat = "Error while reading source code (at %d:%d) %s\n"; 

	public LexerException(int line, int column, String message) {
		super(String.format(errorMessageFormat, line, column, message));
	}
	
	public LexerException(int line, int column, Throwable cause) {
		this(line, column, cause.getMessage());
	}
}
