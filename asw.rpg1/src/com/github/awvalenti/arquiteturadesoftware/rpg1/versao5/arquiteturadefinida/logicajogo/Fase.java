package com.github.awvalenti.arquiteturadesoftware.rpg1.versao5.arquiteturadefinida.logicajogo;

public class Fase {
	protected Tabuleiro tabuleiro;
	
	public Fase(Tabuleiro tabuleiro) {
		this.tabuleiro = tabuleiro;
	}

	public void setSaida(SaidaJogo saida) {
		tabuleiro.setSaida(saida);
	}
	
	public void iniciar() {
		tabuleiro.iniciarJogo();
	}
	
	public int getNumeroLinhas() {
		return tabuleiro.getNumeroLinhas();
	}

	public int getNumeroColunas() {
		return tabuleiro.getNumeroColunas();
	}

	public Elemento elementoEm(Posicao posicao) {
		return tabuleiro.elementoEm(posicao);
	}
	
	public void fazerMovimento(Direcao d) {
		tabuleiro.fazerMovimento(d);
	}
}
