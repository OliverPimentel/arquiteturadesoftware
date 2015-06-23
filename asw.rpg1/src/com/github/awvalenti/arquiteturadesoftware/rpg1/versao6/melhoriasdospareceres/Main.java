package com.github.awvalenti.arquiteturadesoftware.rpg1.versao6.melhoriasdospareceres;

import com.github.awvalenti.arquiteturadesoftware.rpg1.versao6.melhoriasdospareceres.apresentacao.ControleJogoListener;
import com.github.awvalenti.arquiteturadesoftware.rpg1.versao6.melhoriasdospareceres.apresentacao.FabricaIcones;
import com.github.awvalenti.arquiteturadesoftware.rpg1.versao6.melhoriasdospareceres.apresentacao.TecladoListener;
import com.github.awvalenti.arquiteturadesoftware.rpg1.versao6.melhoriasdospareceres.apresentacao.TelaJogo;
import com.github.awvalenti.arquiteturadesoftware.rpg1.versao6.melhoriasdospareceres.logica.FabricaFases;
import com.github.awvalenti.arquiteturadesoftware.rpg1.versao6.melhoriasdospareceres.logica.fases.Fase;

public class Main {
	public static void main(String[] args) {
		
		FabricaFases fabricaFases = new FabricaFases();
		Fase fase = fabricaFases.proximaFase();
		
		ControleJogoListener teclado = new TecladoListener();
		TelaJogo telaJogo = new TelaJogo(new FabricaIcones());
		
		telaJogo.setControleJogoListener(teclado);
		
		fase.setSaida(telaJogo);
		fase.iniciar();
	}
}
