package br.edu.ifsp.cmp.asw_ed2.tinkerscript.sintatico.arvore.nos;

public class NóPosicao extends NóAbstrato {
	public void adicionar(NóIdentificador identificador) {
		filhos.add(0, identificador);
	}
}
