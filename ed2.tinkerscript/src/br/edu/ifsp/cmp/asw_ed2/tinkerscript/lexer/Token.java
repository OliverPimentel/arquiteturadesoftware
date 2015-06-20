package br.edu.ifsp.cmp.asw_ed2.tinkerscript.lexer;


public class Token {
	protected String lexeme;
	protected TokenCategory category;
	protected FilePosition position;
	
	public Token(String lexeme, TokenCategory category, FilePosition position) {
		this(lexeme, category);
		this.position = position;
	}
	
	public Token(String lexeme, TokenCategory category) {
		this.lexeme = lexeme;
		this.category = category;
	}
	
	public String getLexeme() {
		return lexeme;
	}
	
	public TokenCategory getCategory() {
		return category;
	}
	
	public void setFilePosition(FilePosition position) {
		this.position = position;
	}
}
