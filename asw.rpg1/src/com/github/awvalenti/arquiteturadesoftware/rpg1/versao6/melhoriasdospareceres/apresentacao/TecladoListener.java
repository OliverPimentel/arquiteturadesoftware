package com.github.awvalenti.arquiteturadesoftware.rpg1.versao6.melhoriasdospareceres.apresentacao;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import com.github.awvalenti.arquiteturadesoftware.rpg1.versao6.melhoriasdospareceres.logica.ControleJogo;

public class TecladoListener implements KeyListener, ControleJogoListener {
	private ControleJogo controle;
	
	public TecladoListener(ControleJogo controle) {
		this.controle = controle;
	}

	@Override
	public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_UP:	controle.moverParaCima();		break;
		case KeyEvent.VK_DOWN:	controle.moverParaBaixo();		break;
		case KeyEvent.VK_LEFT:	controle.moverParaEsquerda();	break;
		case KeyEvent.VK_RIGHT:	controle.moverParaDireita();	break;
		}
	}
	
	@Override
	public void keyTyped(KeyEvent e) {	}
	
	@Override
	public void keyReleased(KeyEvent e) { }
}