package com.github.awvalenti.arquiteturadesoftware.rpg1.versao6.melhoriasdospareceres.logica.fases;

import com.github.awvalenti.arquiteturadesoftware.rpg1.versao6.melhoriasdospareceres.logica.Elemento;
import com.github.awvalenti.arquiteturadesoftware.rpg1.versao6.melhoriasdospareceres.logica.FabricaFases;
import com.github.awvalenti.arquiteturadesoftware.rpg1.versao6.melhoriasdospareceres.logica.Posicao;
import com.github.awvalenti.arquiteturadesoftware.rpg1.versao6.melhoriasdospareceres.logica.SaidaJogo;

public interface Fase {
	void setSaida(SaidaJogo saida);
	void setFabricaFases(FabricaFases fabrica);
	
	void iniciar();
	
	int getTabuleiroLinhas();
	int getTabuleiroColunas();
	Elemento getElementoEm(Posicao posicao);
}
