package com.github.awvalenti.arquiteturadesoftware.rpg1.versao6.melhoriasdospareceres.logica.comportamentos;

import com.github.awvalenti.arquiteturadesoftware.rpg1.versao6.melhoriasdospareceres.logica.Elemento;

public interface ComportamentoProgramavel {
	void executar(int linha, int coluna, Elemento elemento);
}
