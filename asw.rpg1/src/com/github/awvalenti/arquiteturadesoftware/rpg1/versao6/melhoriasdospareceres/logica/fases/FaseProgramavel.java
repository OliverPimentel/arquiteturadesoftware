package com.github.awvalenti.arquiteturadesoftware.rpg1.versao6.melhoriasdospareceres.logica.fases;

import com.github.awvalenti.arquiteturadesoftware.rpg1.versao6.melhoriasdospareceres.logica.Direcao;
import com.github.awvalenti.arquiteturadesoftware.rpg1.versao6.melhoriasdospareceres.logica.Elemento;
import com.github.awvalenti.arquiteturadesoftware.rpg1.versao6.melhoriasdospareceres.logica.Posicao;
import com.github.awvalenti.arquiteturadesoftware.rpg1.versao6.melhoriasdospareceres.logica.Tabuleiro;
import com.github.awvalenti.arquiteturadesoftware.rpg1.versao6.melhoriasdospareceres.logica.comportamentos.ComportamentoProgramavel;

public class FaseProgramavel extends FaseAbstrata {
	private ComportamentoProgramavel comportamento;
	
	public FaseProgramavel(Tabuleiro tabuleiro) {
		super(tabuleiro);
	}
	
	public void setComportamentoProgramavel(ComportamentoProgramavel comportamento) {
		this.comportamento = comportamento;
	}

	@Override
	protected void mover(Posicao posicaoAntiga, Posicao posicaoNova, Direcao direcao) {
		if (tabuleiro.posicaoEhInvalida(posicaoNova)) return;
		
		Elemento alcancado = tabuleiro.elementoEm(posicaoNova);
		alterarElemento(posicaoAntiga, Elemento.GRAMA);
		alterarElemento(posicaoNova, Elemento.PERSONAGEM);
		
		comportamento.executar(posicaoNova.getLinha(), posicaoNova.getColuna(), alcancado);
	}

}
