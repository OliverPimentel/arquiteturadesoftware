package br.edu.ifsp.cmp.asw_ed2.tinkerscript;

public class SyntaxErrorException extends Exception {
	private static final long serialVersionUID = 2871102525129311223L;

	public SyntaxErrorException(int line, int column, String message, Throwable cause) {
		super("Syntax error at (" + line + ":" + column +"): " + message, cause);
	}
}
