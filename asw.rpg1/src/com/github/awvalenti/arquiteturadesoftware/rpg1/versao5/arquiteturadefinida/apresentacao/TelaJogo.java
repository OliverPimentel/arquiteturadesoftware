package com.github.awvalenti.arquiteturadesoftware.rpg1.versao5.arquiteturadefinida.apresentacao;

import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import com.github.awvalenti.arquiteturadesoftware.rpg1.versao5.arquiteturadefinida.logicajogo.Direcao;
import com.github.awvalenti.arquiteturadesoftware.rpg1.versao5.arquiteturadefinida.logicajogo.Elemento;
import com.github.awvalenti.arquiteturadesoftware.rpg1.versao5.arquiteturadefinida.logicajogo.Fase;
import com.github.awvalenti.arquiteturadesoftware.rpg1.versao5.arquiteturadefinida.logicajogo.Posicao;
import com.github.awvalenti.arquiteturadesoftware.rpg1.versao5.arquiteturadefinida.logicajogo.SaidaJogo;
import com.github.awvalenti.arquiteturadesoftware.rpg1.versao5.arquiteturadefinida.logicajogo.TabuleiroFase;

public class TelaJogo implements SaidaJogo {

	private FabricaIcones fabricaIcones;
	private JFrame frame;
	private List<Fase> fases;
	private Fase faseAtual;
	private List<JLabel> labels;

	public TelaJogo(FabricaIcones fabricaIcones) {
		this.fabricaIcones = fabricaIcones;
		
		fases = new ArrayList<Fase>(TabuleiroFase.values().length);
		for (TabuleiroFase t : TabuleiroFase.values()) {
			fases.add(new Fase(t.getTabuleiro()));
		}

		defineFaseAtual();
		
		frame = new JFrame();
		frame.addKeyListener(new TecladoListener());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		labels = new ArrayList<JLabel>(faseAtual.getNumeroColunas() * faseAtual.getNumeroLinhas());
		desenhaFrame();
	}
	
	public Fase getFaseAtual() {
		return faseAtual;
	}

	private void preencherTela() {
		for (int i = 0; i < faseAtual.getNumeroLinhas(); i++) {
			for (int j = 0; j < faseAtual.getNumeroColunas(); j++) {
				JLabel label = new JLabel(fabricaIcones.obterIcone(faseAtual.elementoEm(new Posicao(i, j)))); 
				labels.add(label);
				frame.add(label);
			}
		}
	}
	
	private void defineFaseAtual() {
		faseAtual = fases.remove(0);
		faseAtual.setSaida(this);
	}

	private void desenhaFrame() {
		while (!labels.isEmpty()) frame.remove(labels.remove(0));
		
		frame.setLayout(new GridLayout(faseAtual.getNumeroLinhas(), faseAtual.getNumeroColunas()));
		preencherTela();
		frame.pack();
		frame.setLocationRelativeTo(null);
	}
	
	@Override
	public void iniciarJogo() {
		frame.setVisible(true);
	}

	@Override
	public void alterarElemento(Posicao posicao, Elemento elemento) {
		int indice = faseAtual.getNumeroColunas() * posicao.getLinha() + posicao.getColuna();
		((JLabel) frame.getContentPane().getComponent(indice)).setIcon(fabricaIcones.obterIcone(elemento));
	}

	@Override
	public void passarDeFase() {
		if (fases.isEmpty()) {
			JOptionPane.showMessageDialog(frame, "Ganhou!", "Ganhou!", JOptionPane.INFORMATION_MESSAGE);
			System.exit(0);
		} else {
			defineFaseAtual();
			desenhaFrame();
			faseAtual.iniciar();
		}
	}

	@Override
	public void perderJogo() {
		JOptionPane.showMessageDialog(frame, "Perdeu!", "Perdeu!", JOptionPane.ERROR_MESSAGE);
		System.exit(0);
	}
	
	private class TecladoListener implements KeyListener {

		@Override
		public void keyTyped(KeyEvent e) {
		}

		@Override
		public void keyPressed(KeyEvent e) {
			Direcao direcao = Direcao.comCodigo(e.getKeyCode());
			if (direcao != null) faseAtual.fazerMovimento(direcao);
		}

		@Override
		public void keyReleased(KeyEvent e) {
		}
	}
}
