package com.github.awvalenti.arquiteturadesoftware.rpg1.versao6.melhoriasdospareceres.logica;

public enum Elemento {

	AGUA("/agua.png"),
	MACA("/maca.png"),
	PERSONAGEM("/personagem.png"),
	GRAMA("/grama.png"),
	PORTAL("/passagem.png"),
	URTIGA("/urtiga.png"),
	;

	private final String caminhoImagem;

	Elemento(String caminhoImagem) {
		this.caminhoImagem = caminhoImagem;
	}

	public String getCaminhoImagem() {
		return caminhoImagem;
	}

}
