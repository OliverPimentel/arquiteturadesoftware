package com.github.awvalenti.arquiteturadesoftware.rpg1.versao6.melhoriasdospareceres.logica.fases;

import com.github.awvalenti.arquiteturadesoftware.rpg1.versao6.melhoriasdospareceres.logica.ControleJogo;
import com.github.awvalenti.arquiteturadesoftware.rpg1.versao6.melhoriasdospareceres.logica.Direcao;
import com.github.awvalenti.arquiteturadesoftware.rpg1.versao6.melhoriasdospareceres.logica.Elemento;
import com.github.awvalenti.arquiteturadesoftware.rpg1.versao6.melhoriasdospareceres.logica.FabricaFases;
import com.github.awvalenti.arquiteturadesoftware.rpg1.versao6.melhoriasdospareceres.logica.Posicao;
import com.github.awvalenti.arquiteturadesoftware.rpg1.versao6.melhoriasdospareceres.logica.SaidaJogo;
import com.github.awvalenti.arquiteturadesoftware.rpg1.versao6.melhoriasdospareceres.logica.Tabuleiro;
import com.github.awvalenti.arquiteturadesoftware.rpg1.versao6.melhoriasdospareceres.logica.comportamentos.ComportamentoProgramavel;

public abstract class FaseAbstrata implements Fase, ControleJogo {
	protected Tabuleiro tabuleiro;
	protected ComportamentoProgramavel comportamento;
	protected SaidaJogo saida;
	protected FabricaFases fabrica;
	
	public FaseAbstrata(Tabuleiro tabuleiro) {
		this.tabuleiro = tabuleiro;
	}
	
	public FaseAbstrata(Tabuleiro tabuleiro, ComportamentoProgramavel comportamento) {
		this(tabuleiro);
		this.comportamento = comportamento;
	}

	@Override
	public void setSaida(SaidaJogo saida) {
		this.saida = saida;
	}
	
	@Override
	public void setFabricaFases(FabricaFases fabrica) {
		this.fabrica = fabrica;
	}
	
	@Override
	public void iniciar() {
		saida.iniciarJogo(this);
	}
	
	@Override
	public int getTabuleiroLinhas() {
		return tabuleiro.getNumeroLinhas();
	}
	
	@Override
	public int getTabuleiroColunas() {
		return tabuleiro.getNumeroColunas();
	}
	
	@Override
	public Elemento getElementoEm(Posicao posicao) {
		return tabuleiro.elementoEm(posicao);
	}

	@Override
	public void moverParaCima() {
		Posicao personagem = getPosicaoPersonagem();
		mover(personagem, personagem.somar(Direcao.CIMA), Direcao.CIMA);
	}

	@Override
	public void moverParaBaixo() {
		Posicao personagem = getPosicaoPersonagem();
		mover(personagem, personagem.somar(Direcao.BAIXO), Direcao.BAIXO);
	}

	@Override
	public void moverParaEsquerda() {
		Posicao personagem = getPosicaoPersonagem();
		mover(personagem, personagem.somar(Direcao.ESQUERDA), Direcao.ESQUERDA);
	}

	@Override
	public void moverParaDireita() {
		Posicao personagem = getPosicaoPersonagem();
		mover(personagem, personagem.somar(Direcao.DIREITA), Direcao.DIREITA);
	}
	
	protected abstract void mover(Posicao posicaoAntiga, Posicao posicaoNova, Direcao direcao);
	
	protected void alterarElemento(Posicao posicao, Elemento elemento) {
		tabuleiro.alterarElemento(posicao, elemento);
		saida.alterarElemento(posicao, elemento);
	}
	
	protected Posicao getPosicaoPersonagem() {
		return tabuleiro.acharPosicaoDe(Elemento.PERSONAGEM);
	}
}
