package br.edu.ifsp.cmp.asw_ed2.tinkerscript.sintatico.arvore.nos;

public class NóPrograma extends NóAbstrato {
	public void adicionar(NóDeclaracoes declaracoes) {
		filhos.add(0, declaracoes);
	}
	
	public void adicionar(NóComandos comandos) {
		filhos.add(1, comandos);
	}
}
