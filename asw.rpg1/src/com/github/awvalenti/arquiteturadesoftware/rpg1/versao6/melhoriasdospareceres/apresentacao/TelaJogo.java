package com.github.awvalenti.arquiteturadesoftware.rpg1.versao6.melhoriasdospareceres.apresentacao;

import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import com.github.awvalenti.arquiteturadesoftware.rpg1.versao6.melhoriasdospareceres.logica.Elemento;
import com.github.awvalenti.arquiteturadesoftware.rpg1.versao6.melhoriasdospareceres.logica.Posicao;
import com.github.awvalenti.arquiteturadesoftware.rpg1.versao6.melhoriasdospareceres.logica.SaidaJogo;
import com.github.awvalenti.arquiteturadesoftware.rpg1.versao6.melhoriasdospareceres.logica.fases.Fase;

public class TelaJogo implements SaidaJogo {

	private FabricaIcones fabricaIcones;
	private JFrame frame;
	private Fase faseAtual;

	public TelaJogo(FabricaIcones fabricaIcones) {
		this.fabricaIcones = fabricaIcones;
		
		frame = new JFrame();
		frame.setTitle("asw.rpg1");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	private void preencherTela() {
		List<Component> labels = new ArrayList<Component>(frame.getContentPane().getComponentCount());
		for (Component c : frame.getContentPane().getComponents()) labels.add((JLabel) c);
		frame.getContentPane().removeAll();
		
		for (int i = 0; i < faseAtual.getTabuleiroLinhas(); i++) {
			for (int j = 0; j < faseAtual.getTabuleiroColunas(); j++) {
				JLabel label;
				if (labels.size() > 0) label = (JLabel) labels.remove(0);
				else label = new JLabel();
				
				label.setIcon(fabricaIcones.obterIcone(faseAtual.getElementoEm(new Posicao(i, j)))); 
				frame.getContentPane().add(label);
			}
		}
	}
	
	private void desenhaFrame() {
		frame.setLayout(new GridLayout(faseAtual.getTabuleiroLinhas(), faseAtual.getTabuleiroColunas()));
		preencherTela();
		frame.pack();
		frame.setLocationRelativeTo(null);
	}
	
	@Override
	public void iniciarJogo(Fase faseAtual) {
		this.faseAtual = faseAtual;
		desenhaFrame();
		frame.setVisible(true);
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
			faseAtual = proximaFase;
			desenhaFrame();
		}
	}

	@Override
	public void perderJogo() {
		JOptionPane.showMessageDialog(frame, "Perdeu!", "Perdeu!", JOptionPane.ERROR_MESSAGE);
		System.exit(0);
	}

	@Override
	public void setControleJogoListener(ControleJogoListener listener) {
		frame.addKeyListener((KeyListener) listener);
	}
}
