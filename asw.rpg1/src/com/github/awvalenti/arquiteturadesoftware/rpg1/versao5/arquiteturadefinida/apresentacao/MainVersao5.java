package com.github.awvalenti.arquiteturadesoftware.rpg1.versao5.arquiteturadefinida.apresentacao;


public class MainVersao5 {

	public static void main(String[] args) {
		TelaJogo telaJogo = new TelaJogo(new FabricaIcones());
		telaJogo.getFaseAtual().iniciar();
	}
}
