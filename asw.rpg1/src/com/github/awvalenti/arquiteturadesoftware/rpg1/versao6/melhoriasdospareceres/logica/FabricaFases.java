package com.github.awvalenti.arquiteturadesoftware.rpg1.versao6.melhoriasdospareceres.logica;

import com.github.awvalenti.arquiteturadesoftware.rpg1.versao6.melhoriasdospareceres.logica.fases.Fase;
import com.github.awvalenti.arquiteturadesoftware.rpg1.versao6.melhoriasdospareceres.logica.fases.FasePadrao;

public class FabricaFases {
	FabricaElementosTabuleiros fabricaElementosTabuleiros;
	
	public FabricaFases() {
		fabricaElementosTabuleiros = new FabricaElementosTabuleiros();
	}
	
	public Fase proximaFase() {
		Elemento[][] elementos = fabricaElementosTabuleiros.obterNovosElementos();
		if (elementos == null) return null;
		return new FasePadrao(new Tabuleiro(elementos));
	}
}
