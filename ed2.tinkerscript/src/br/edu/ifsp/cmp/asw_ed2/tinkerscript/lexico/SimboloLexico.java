package br.edu.ifsp.cmp.asw_ed2.tinkerscript.lexico;


public class SimboloLexico {
	protected String lexema;
	protected SimboloLexicoCategoria categoria;
	protected Posicao posicao;
	
	public SimboloLexico(String lexema, SimboloLexicoCategoria categoria, Posicao posicao) {
		this(lexema, categoria);
		this.posicao = posicao;
	}
	
	public SimboloLexico(String lexema, SimboloLexicoCategoria categoria) {
		this.lexema = lexema;
		this.categoria = categoria;
	}
	
	public String getLexema() {
		return lexema;
	}
	
	public SimboloLexicoCategoria getCategoria() {
		return categoria;
	}
	
	public Posicao getPosicao() {
		return posicao;
	}
	
	public void setPosicao(Posicao posicao) {
		this.posicao = posicao;
	}
	
	public String toString() {
		return new StringBuilder(categoria.name())
		.append("(")
		.append(lexema.replace("\n", "\\n"))
		.append(") [")
		.append(posicao.toString())
		.append("] ")
		.toString();
	}
}
