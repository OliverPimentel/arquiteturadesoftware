package br.edu.ifsp.cmp.asw_ed2.tinkerscript.sintatico.arvore.nos;


public class NóComando extends NóAbstrato {
	public NóComando(NóPosicao posicao, NóExpressoes expressao) {
		filhos.add(0, posicao);
		filhos.add(1, expressao);
	}
}
