package com.github.awvalenti.arquiteturadesoftware.rpg1.versao5.arquiteturadefinida.logicajogo;

public enum Elemento {

	AGUA("/agua.gif"),
	MACA("/maca.png"),
	PERSONAGEM("/personagem.png"),
	GRAMA("/grama.gif"),
	PORTAL("/passagem.png"),
	URTIGA("/urtiga.gif"),
	;

	private final String caminhoImagem;

	Elemento(String caminhoImagem) {
		this.caminhoImagem = caminhoImagem;
	}

	public String getCaminhoImagem() {
		return caminhoImagem;
	}

}
