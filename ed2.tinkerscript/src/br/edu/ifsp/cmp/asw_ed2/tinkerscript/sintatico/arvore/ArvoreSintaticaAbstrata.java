package br.edu.ifsp.cmp.asw_ed2.tinkerscript.sintatico.arvore;

import br.edu.ifsp.cmp.asw_ed2.tinkerscript.sintatico.arvore.nos.NóAbstrato;

public class ArvoreSintaticaAbstrata {
	private NóAbstrato raiz;
	
	public ArvoreSintaticaAbstrata(NóAbstrato raiz) {
		this.raiz = raiz;
	}
	
	public NóAbstrato getRaiz() {
		return raiz;
	}
}
