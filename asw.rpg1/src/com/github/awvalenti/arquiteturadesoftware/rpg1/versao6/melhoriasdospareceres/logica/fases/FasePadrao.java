package com.github.awvalenti.arquiteturadesoftware.rpg1.versao6.melhoriasdospareceres.logica.fases;

import com.github.awvalenti.arquiteturadesoftware.rpg1.versao6.melhoriasdospareceres.logica.Direcao;
import com.github.awvalenti.arquiteturadesoftware.rpg1.versao6.melhoriasdospareceres.logica.Elemento;
import com.github.awvalenti.arquiteturadesoftware.rpg1.versao6.melhoriasdospareceres.logica.Posicao;
import com.github.awvalenti.arquiteturadesoftware.rpg1.versao6.melhoriasdospareceres.logica.Tabuleiro;

public class FasePadrao extends FaseAbstrata {
	private Posicao posicaoDoPortalOculto;
	private boolean urtigaAtivado;
	private Direcao ultimaDirecao;
	
	public FasePadrao(Tabuleiro tabuleiro) {
		super(tabuleiro);
	}
	
	@Override
	public void iniciar() {
		super.iniciar();
		ocultarPortal();
		ultimaDirecao = null;
	}
	
	public void ocultarPortal() {
		posicaoDoPortalOculto = tabuleiro.acharPosicaoDe(Elemento.PORTAL);
		alterarElemento(posicaoDoPortalOculto, Elemento.GRAMA);
	}

	public void reexibirPortal() {
		alterarElemento(posicaoDoPortalOculto, Elemento.PORTAL);
	}

	public void ativarUrtiga() {
		urtigaAtivado = true;
	}
	
	public void desativarUrtiga() {
		urtigaAtivado = false;
	}
	
	private int quantidadeMacasRestantes() {
		return tabuleiro.contarElementos(Elemento.MACA);
	}

	@Override
	protected void mover(Posicao posicaoAntiga, Posicao posicaoNova, Direcao direcao) {
		if (tabuleiro.posicaoEhInvalida(posicaoNova)) return;
		
		if (podeMover(direcao)) {
			Elemento alcancado = tabuleiro.elementoEm(posicaoNova);
			alterarElemento(posicaoAntiga, Elemento.GRAMA);
			alterarElemento(posicaoNova, Elemento.PERSONAGEM);
			
			switch (alcancado) {
			case AGUA:
				saida.perderJogo();
				break;
			case MACA:
				if (quantidadeMacasRestantes() == 0) reexibirPortal();
				break;
			case PORTAL:
				desativarUrtiga();
				ultimaDirecao = null;
				saida.passarDeFase(fabrica.proximaFase());
				break;
			case URTIGA:
				ativarUrtiga();
				break;
			default:
				break;
			}
		}
	}
	
	private boolean podeMover(Direcao direcao) {
		if (urtigaAtivado) {
			if (ultimaDirecao == direcao) {
				ultimaDirecao = null;
				return true;
			} else {
				ultimaDirecao = direcao;
				return false;
			}
		} else return true;
	}
}
