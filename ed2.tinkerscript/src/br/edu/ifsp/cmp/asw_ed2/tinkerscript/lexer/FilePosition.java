package br.edu.ifsp.cmp.asw_ed2.tinkerscript.lexer;

public class FilePosition {
	private int line;
	private int column;
	
	public FilePosition(int line, int column) {
		this.line = line;
		this.column = column;
	}
	public int getLine() {
		return line;
	}
	public int getColumn() {
		return column;
	}
	public String toString() {
		return line + ":" + column;
	}
}
