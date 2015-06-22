package br.edu.ifsp.cmp.asw_ed2.tinkerscript.sintatico.arvore;

import br.edu.ifsp.cmp.asw_ed2.tinkerscript.sintatico.arvore.nos.NóPrograma;

public class ArvoreSintaticaAbstrata {
	private NóPrograma raiz;
	
	public ArvoreSintaticaAbstrata(NóPrograma raiz) {
		this.raiz = raiz;
	}
	
	public NóPrograma getRaiz() {
		return raiz;
	}
}
