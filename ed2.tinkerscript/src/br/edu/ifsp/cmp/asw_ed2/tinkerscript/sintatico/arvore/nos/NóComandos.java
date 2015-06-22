package br.edu.ifsp.cmp.asw_ed2.tinkerscript.sintatico.arvore.nos;

public class NóComandos extends NóAbstrato {
	public void adicionar(NóComando comando) {
		filhos.add(comando);
	}
}
