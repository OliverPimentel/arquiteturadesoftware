package br.edu.ifsp.cmp.asw_ed2.tinkerscript;

public class ScannerException extends Exception {
	private static final long serialVersionUID = -1027886543236653627L;

	public ScannerException(FilePosition position, Throwable cause) {
		this(position.getLine(), position.getColumn(), cause);
	}
	
	public ScannerException(int line, int column, Throwable cause) {
		super("Error while reading source code (at " + line + ":" + column + ") " + cause.getMessage(), cause);
	}
}
