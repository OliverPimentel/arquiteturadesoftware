package br.edu.ifsp.cmp.asw_ed2.tinkerscript.lexico;

public class Posicao {
	private int linha;
	private int coluna;
	
	public Posicao(int linha, int coluna) {
		this.linha = linha;
		this.coluna = coluna;
	}
	public int getLinha() {
		return linha;
	}
	public int getColuna() {
		return coluna;
	}
	public String toString() {
		return linha + ":" + coluna;
	}
}
