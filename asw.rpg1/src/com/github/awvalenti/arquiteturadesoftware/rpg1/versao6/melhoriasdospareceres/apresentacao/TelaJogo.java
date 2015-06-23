package com.github.awvalenti.arquiteturadesoftware.rpg1.versao6.melhoriasdospareceres.apresentacao;

import java.awt.Component;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import com.github.awvalenti.arquiteturadesoftware.rpg1.versao6.melhoriasdospareceres.logica.ControleJogo;
import com.github.awvalenti.arquiteturadesoftware.rpg1.versao6.melhoriasdospareceres.logica.Elemento;
import com.github.awvalenti.arquiteturadesoftware.rpg1.versao6.melhoriasdospareceres.logica.Posicao;
import com.github.awvalenti.arquiteturadesoftware.rpg1.versao6.melhoriasdospareceres.logica.SaidaJogo;
import com.github.awvalenti.arquiteturadesoftware.rpg1.versao6.melhoriasdospareceres.logica.fases.Fase;

public class TelaJogo implements SaidaJogo {
	private ControleJogoListener controlador;
	private FabricaIcones fabricaIcones;
	private JFrame frame;
	private JLabelPool pool;
	private Fase faseAtual;

	public TelaJogo(FabricaIcones fabricaIcones) {
		this.fabricaIcones = fabricaIcones;
		
		frame = new JFrame();
		frame.setContentPane(new Container());
		frame.setTitle("asw.rpg1");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		pool = new JLabelPool(frame);
	}
	
	private void preencherTela() {
		pool.limparFrame();
		
		for (int i = 0; i < faseAtual.getTabuleiroLinhas(); i++) {
			for (int j = 0; j < faseAtual.getTabuleiroColunas(); j++) {
				pool.obterJLabelParaFrame().setIcon(fabricaIcones.obterIcone(faseAtual.getElementoEm(new Posicao(i, j)))); 
			}
		}
	}
	
	private void desenhaFrame() {
		frame.setLayout(new GridLayout(faseAtual.getTabuleiroLinhas(), faseAtual.getTabuleiroColunas()));
		preencherTela();
		frame.pack();
		frame.setLocationRelativeTo(null);
	}
	
	private void definirControle() {
		controlador.setControleJogo((ControleJogo) faseAtual);
	}
	
	@Override
	public void iniciarJogo(Fase faseAtual) {
		this.faseAtual = faseAtual;
		desenhaFrame();
		definirControle();
		
		if (!frame.isVisible()) frame.setVisible(true);
	}

	@Override
	public void alterarElemento(Posicao posicao, Elemento elemento) {
		int indice = faseAtual.getTabuleiroColunas() * posicao.getLinha() + posicao.getColuna();
		((JLabel) frame.getContentPane().getComponent(indice)).setIcon(fabricaIcones.obterIcone(elemento));
	}

	@Override
	public void passarDeFase(Fase proximaFase) {
		if (proximaFase == null) {
			JOptionPane.showMessageDialog(frame, "Ganhou!", "Ganhou!", JOptionPane.INFORMATION_MESSAGE);
			System.exit(0);
		} else {
			proximaFase.setSaida(this);
			proximaFase.iniciar();
		}
	}

	@Override
	public void perderJogo() {
		JOptionPane.showMessageDialog(frame, "Perdeu!", "Perdeu!", JOptionPane.ERROR_MESSAGE);
		System.exit(0);
	}

	@Override
	public void setControleJogoListener(ControleJogoListener listener) {
		controlador = listener;
		frame.addKeyListener((KeyListener) controlador);
	}
	
	private class JLabelPool {
		private JFrame frame;
		private Set<JLabel> labels;
		private List<JLabel> labelsDisponiveis;
		
		public JLabelPool(JFrame frame) {
			this.frame = frame;
			labels = new HashSet<JLabel>();
			labelsDisponiveis = new ArrayList<JLabel>();
		}
		
		public JLabel obterJLabelParaFrame() {
			JLabel label;
			
			if (labelsDisponiveis.size() < 1) {
				label = new JLabel();
				labels.add(label);
			} else {
				label = labelsDisponiveis.remove(0);
			}
			frame.getContentPane().add(label);
			return label;
		}
		
		public void limparFrame() {
			Container contentPane = frame.getContentPane();
			for (Component c : contentPane.getComponents()) {
				if (c instanceof JLabel) {
					contentPane.remove(c);
					labelsDisponiveis.add((JLabel) c);
				}
			}
		}
	}
}
