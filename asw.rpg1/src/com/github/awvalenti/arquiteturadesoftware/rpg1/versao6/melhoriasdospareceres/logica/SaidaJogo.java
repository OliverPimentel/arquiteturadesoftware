package com.github.awvalenti.arquiteturadesoftware.rpg1.versao6.melhoriasdospareceres.logica;

import com.github.awvalenti.arquiteturadesoftware.rpg1.versao6.melhoriasdospareceres.apresentacao.ControleJogoListener;
import com.github.awvalenti.arquiteturadesoftware.rpg1.versao6.melhoriasdospareceres.logica.fases.Fase;

public interface SaidaJogo {
	void setControleJogoListener(ControleJogoListener listener);
	
	void iniciarJogo(Fase faseAtual);

	void alterarElemento(Posicao posicao, Elemento novo);

	void passarDeFase(Fase proximaFase);

	void perderJogo();
}
