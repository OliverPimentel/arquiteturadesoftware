package br.edu.ifsp.cmp.asw_ed2.tinkerscript.sintatico.arvore.nos;

public class NóCondicao extends NóAbstrato {
	public void setCondicao(NóAbstrato expr) {
		filhos.add(0, expr);
	}
	
	public void setCorpo(NóAbstrato expressao) {
		filhos.add(1, expressao);
	}
}
