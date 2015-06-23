package com.github.awvalenti.arquiteturadesoftware.rpg1.versao5.arquiteturadefinida.logicajogo;

public class Fase {
	protected Tabuleiro tabuleiro;
	protected ComportamentoProgramavel comportamento;
	
	public Fase(Tabuleiro tabuleiro) {
		this.tabuleiro = tabuleiro;
	}
	
	public Fase(Tabuleiro tabuleiro, ComportamentoProgramavel comportamento) {
		this(tabuleiro);
		this.comportamento = comportamento;
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
	
	public void ativarUrtiga() {
		tabuleiro.ativarUrtiga();
	}
	
	public void desativarUrtiga() {
		tabuleiro.desativarUrtiga();
	}
	
	public void abrirPortal() {
		tabuleiro.reexibirPortal();
	}
	
	public void fecharPortal() {
		tabuleiro.ocultarPortal();
	}
}
