package com.github.awvalenti.arquiteturadesoftware.rpg1.versao6.melhoriasdospareceres.logica;

import static com.github.awvalenti.arquiteturadesoftware.rpg1.versao6.melhoriasdospareceres.logica.Elemento.AGUA;
import static com.github.awvalenti.arquiteturadesoftware.rpg1.versao6.melhoriasdospareceres.logica.Elemento.GRAMA;
import static com.github.awvalenti.arquiteturadesoftware.rpg1.versao6.melhoriasdospareceres.logica.Elemento.MACA;
import static com.github.awvalenti.arquiteturadesoftware.rpg1.versao6.melhoriasdospareceres.logica.Elemento.PERSONAGEM;
import static com.github.awvalenti.arquiteturadesoftware.rpg1.versao6.melhoriasdospareceres.logica.Elemento.PORTAL;
import static com.github.awvalenti.arquiteturadesoftware.rpg1.versao6.melhoriasdospareceres.logica.Elemento.URTIGA;

public class FabricaElementosTabuleiros {
	private static final Elemento[][][] ELEMENTOS_TABULEIROS = {
		new Elemento[][] {
				{GRAMA,		GRAMA,		GRAMA,	GRAMA,	GRAMA,	GRAMA,	GRAMA,		GRAMA,	GRAMA,	GRAMA,	GRAMA,	AGUA,		AGUA,	AGUA,	AGUA},
				{GRAMA,		GRAMA,		GRAMA,	GRAMA,	GRAMA,	GRAMA,	GRAMA,		GRAMA,	GRAMA,	GRAMA,	AGUA,	AGUA,		AGUA,	AGUA,	AGUA},
				{GRAMA,		GRAMA,		GRAMA,	MACA,	GRAMA,	GRAMA,	GRAMA,		GRAMA,	AGUA,	AGUA,	AGUA,	AGUA,		GRAMA,	GRAMA,	GRAMA},
				{PORTAL,	GRAMA,		GRAMA,	GRAMA,	GRAMA,	GRAMA,	GRAMA,		GRAMA,	AGUA,	AGUA,	AGUA,	AGUA,		GRAMA,	GRAMA,	GRAMA},
				{GRAMA,		GRAMA,		GRAMA,	GRAMA,	GRAMA,	GRAMA,	AGUA,		AGUA,	AGUA,	AGUA,	AGUA,	AGUA,		GRAMA,	GRAMA,	GRAMA},
				{GRAMA,		GRAMA,		GRAMA,	GRAMA,	GRAMA,	GRAMA,	GRAMA,		GRAMA,	GRAMA,	GRAMA,	GRAMA,	GRAMA,		GRAMA,	GRAMA,	MACA},
				{GRAMA,		GRAMA,		GRAMA,	GRAMA,	AGUA,	AGUA,	AGUA,		AGUA,	AGUA,	AGUA,	AGUA,	AGUA,		AGUA,	AGUA,	GRAMA},
				{GRAMA,		GRAMA,		GRAMA,	GRAMA,	AGUA,	AGUA,	GRAMA,		GRAMA,	AGUA,	AGUA,	AGUA,	AGUA,		AGUA,	AGUA,	GRAMA},
				{GRAMA,		GRAMA,		GRAMA,	GRAMA,	GRAMA,	MACA,	GRAMA,		GRAMA,	GRAMA,	GRAMA,	GRAMA,	GRAMA,		GRAMA,	GRAMA,	GRAMA},
				{GRAMA,		PERSONAGEM,	GRAMA,	GRAMA,	URTIGA,	GRAMA,	GRAMA,		GRAMA,	GRAMA,	GRAMA,	GRAMA,	GRAMA,		GRAMA,	GRAMA,	GRAMA}
		},
		new Elemento[][] {
				{AGUA,		AGUA,		AGUA,	GRAMA,	GRAMA,	GRAMA,	GRAMA,		GRAMA,	GRAMA,	GRAMA,	GRAMA,	AGUA,		AGUA,	AGUA,	AGUA},
				{AGUA,		AGUA,		AGUA,	GRAMA,	GRAMA,	GRAMA,	PERSONAGEM,	GRAMA,	GRAMA,	GRAMA,	AGUA,	AGUA,		AGUA,	AGUA,	AGUA},
				{GRAMA,		AGUA,		AGUA,	MACA,	GRAMA,	GRAMA,	GRAMA,		GRAMA,	AGUA,	AGUA,	AGUA,	AGUA,		GRAMA,	GRAMA,	GRAMA},
				{PORTAL,	GRAMA,		AGUA,	GRAMA,	GRAMA,	GRAMA,	GRAMA,		GRAMA,	AGUA,	AGUA,	AGUA,	AGUA,		GRAMA,	GRAMA,	GRAMA},
				{GRAMA,		GRAMA,		AGUA,	GRAMA,	GRAMA,	GRAMA,	AGUA,		AGUA,	AGUA,	AGUA,	AGUA,	AGUA,		GRAMA,	GRAMA,	GRAMA},
				{AGUA,		GRAMA,		AGUA,	GRAMA,	GRAMA,	GRAMA,	GRAMA,		URTIGA,	GRAMA,	GRAMA,	GRAMA,	GRAMA,		GRAMA,	GRAMA,	MACA},
				{AGUA,		GRAMA,		AGUA,	GRAMA,	AGUA,	AGUA,	AGUA,		AGUA,	AGUA,	AGUA,	AGUA,	AGUA,		AGUA,	AGUA,	GRAMA},
				{AGUA,		GRAMA,		AGUA,	GRAMA,	AGUA,	AGUA,	GRAMA,		GRAMA,	AGUA,	AGUA,	AGUA,	AGUA,		AGUA,	AGUA,	GRAMA},
				{AGUA,		GRAMA,		AGUA,	GRAMA,	GRAMA,	MACA,	GRAMA,		GRAMA,	GRAMA,	GRAMA,	GRAMA,	GRAMA,		GRAMA,	GRAMA,	GRAMA},
				{AGUA,		GRAMA,		GRAMA,	GRAMA,	GRAMA,	GRAMA,	GRAMA,		GRAMA,	GRAMA,	GRAMA,	GRAMA,	GRAMA,		GRAMA,	GRAMA,	GRAMA},
		},
		new Elemento[][] {
				{AGUA,		AGUA,		AGUA,	MACA,	GRAMA,	GRAMA,	GRAMA,		AGUA,	AGUA,	AGUA,	AGUA,	AGUA,		AGUA,	GRAMA,	GRAMA},
				{GRAMA,		GRAMA,		GRAMA,	GRAMA,	AGUA,	AGUA,	GRAMA,		GRAMA,	GRAMA,	GRAMA,	GRAMA,	GRAMA,		GRAMA,	GRAMA,	MACA},
				{GRAMA,		AGUA,		AGUA,	AGUA,	AGUA,	AGUA,	AGUA,		AGUA,	AGUA,	AGUA,	AGUA,	GRAMA,		GRAMA,	GRAMA,	AGUA},
				{GRAMA,		AGUA,		GRAMA,	GRAMA,	GRAMA,	AGUA,	GRAMA,		GRAMA,	GRAMA,	AGUA,	GRAMA,	GRAMA,		GRAMA,	AGUA,	AGUA},
				{GRAMA,		AGUA,		GRAMA,	AGUA,	GRAMA,	AGUA,	GRAMA,		AGUA,	GRAMA,	AGUA,	AGUA,	URTIGA,		GRAMA,	AGUA,	AGUA},
				{GRAMA,		GRAMA,		GRAMA,	AGUA,	GRAMA,	GRAMA,	GRAMA,		AGUA,	GRAMA,	AGUA,	AGUA,	GRAMA,		AGUA,	AGUA,	AGUA},
				{AGUA,		AGUA,		AGUA,	AGUA,	AGUA,	AGUA,	AGUA,		AGUA,	MACA,	AGUA,	AGUA,	GRAMA,		AGUA,	AGUA,	AGUA},
				{PORTAL,	AGUA,		GRAMA,	GRAMA,	GRAMA,	GRAMA,	GRAMA,		GRAMA,	GRAMA,	AGUA,	AGUA,	GRAMA,		AGUA,	AGUA,	AGUA},
				{GRAMA,		AGUA,		GRAMA,	AGUA,	AGUA,	AGUA,	AGUA,		AGUA,	AGUA,	AGUA,	AGUA,	GRAMA,		AGUA,	AGUA,	AGUA},
				{PERSONAGEM,GRAMA,		GRAMA,	AGUA,	AGUA,	MACA,	GRAMA,		GRAMA,	GRAMA,	GRAMA,	GRAMA,	GRAMA,		AGUA,	AGUA,	AGUA},
		},
		new Elemento[][] {
				{AGUA,		AGUA,		AGUA,	AGUA,	AGUA,	AGUA,	AGUA,		AGUA,	AGUA,	AGUA,	AGUA,	PERSONAGEM,	GRAMA,	GRAMA,	GRAMA},
				{AGUA,		AGUA,		GRAMA,	GRAMA,	GRAMA,	AGUA,	AGUA,		AGUA,	AGUA,	AGUA,	GRAMA,	GRAMA,		GRAMA,	GRAMA,	GRAMA},
				{AGUA,		AGUA,		GRAMA,	MACA,	AGUA,	GRAMA,	AGUA,		AGUA,	GRAMA,	GRAMA,	GRAMA,	GRAMA,		AGUA,	AGUA,	AGUA},
				{AGUA,		GRAMA,		GRAMA,	AGUA,	GRAMA,	AGUA,	AGUA,		AGUA,	GRAMA,	GRAMA,	GRAMA,	GRAMA,		AGUA,	AGUA,	AGUA},
				{AGUA,		AGUA,		GRAMA,	AGUA,	GRAMA,	AGUA,	GRAMA,		GRAMA,	GRAMA,	GRAMA,	GRAMA,	GRAMA,		AGUA,	AGUA,	AGUA},
				{AGUA,		AGUA,		GRAMA,	GRAMA,	GRAMA,	AGUA,	AGUA,		AGUA,	GRAMA,	AGUA,	AGUA,	AGUA,		AGUA,	AGUA,	MACA},
				{AGUA,		AGUA,		GRAMA,	AGUA,	GRAMA,	GRAMA,	GRAMA,		URTIGA,	GRAMA,	GRAMA,	GRAMA,	GRAMA,		GRAMA,	GRAMA,	GRAMA},
				{AGUA,		AGUA,		GRAMA,	AGUA,	GRAMA,	GRAMA,	AGUA,		AGUA,	GRAMA,	GRAMA,	GRAMA,	GRAMA,		GRAMA,	GRAMA,	AGUA},
				{AGUA,		AGUA,		PORTAL,	GRAMA,	AGUA,	MACA,	AGUA,		AGUA,	AGUA,	GRAMA,	AGUA,	AGUA,		AGUA,	AGUA,	AGUA},
				{AGUA,		AGUA,		AGUA,	AGUA,	GRAMA,	GRAMA,	GRAMA,		GRAMA,	GRAMA,	AGUA,	AGUA,	AGUA,		AGUA,	AGUA,	AGUA},
		},
	};
	
	private int indiceTabuleiroAtual = 0;
	
	public Elemento[][] obterNovosElementos() {
		if (indiceTabuleiroAtual < ELEMENTOS_TABULEIROS.length)
			return clonar(ELEMENTOS_TABULEIROS[indiceTabuleiroAtual++]);
		return null;
	}
	
	private Elemento[][] clonar(Elemento[][] elementos) {
		Elemento[][] clone = new Elemento[elementos.length][elementos[0].length];
		for (int i = 0; i < elementos.length; i++)
			for (int j = 0; j < elementos[i].length; j++)
				clone[i][j] = elementos[i][j];
		
		return clone;
	}
}
