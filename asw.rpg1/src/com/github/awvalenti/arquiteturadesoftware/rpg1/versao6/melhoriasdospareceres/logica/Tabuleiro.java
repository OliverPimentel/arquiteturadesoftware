package com.github.awvalenti.arquiteturadesoftware.rpg1.versao6.melhoriasdospareceres.logica;

public class Tabuleiro {

	private Elemento[][] matriz;
	
	public Tabuleiro(Elemento[][] matriz) {
		this.matriz = matriz;
	}

	public int getNumeroLinhas() {
		return matriz.length;
	}

	public int getNumeroColunas() {
		return matriz[0].length;
	}

	public Elemento elementoEm(Posicao posicao) {
		return matriz[posicao.getLinha()][posicao.getColuna()];
	}

	public void alterarElemento(Posicao posicao, Elemento e) {
		matriz[posicao.getLinha()][posicao.getColuna()] = e;
	}
	
	public Posicao acharPosicaoDe(Elemento elemento) {
		for (int i = 0; i < matriz.length; i++) {
			for (int j = 0; j < matriz[i].length; j++) {
				if (matriz[i][j] == elemento) {
					return new Posicao(i, j);
				}
			}
		}
		
		return null;
	}
	
	public int contarElementos(Elemento e) {
		int ret = 0;
		for (int i = 0; i < matriz.length; i++)
			for (int j = 0; j < matriz[i].length; j++)
				if (matriz[i][j] == e) ++ret;
		return ret;
	}
	
	public boolean posicaoEhInvalida(Posicao p) {
		return p.getLinha() < 0 || p.getLinha() >= getNumeroLinhas()
				|| p.getColuna() < 0 || p.getColuna() >= getNumeroColunas();
	}
}
