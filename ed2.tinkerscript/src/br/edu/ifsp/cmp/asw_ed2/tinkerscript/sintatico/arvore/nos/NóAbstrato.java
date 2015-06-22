package br.edu.ifsp.cmp.asw_ed2.tinkerscript.sintatico.arvore.nos;

import java.util.ArrayList;
import java.util.List;

import br.edu.ifsp.cmp.asw_ed2.tinkerscript.lexico.SimboloLexico;


public abstract class NóAbstrato {
	protected SimboloLexico simbolo;
	protected List<NóAbstrato> filhos;
	
	public NóAbstrato() {
		filhos = new ArrayList<>(2);
	}
	
	public NóAbstrato(SimboloLexico simbolo) {
		this();
		this.simbolo = simbolo;
	}
	
	public void adicionar(NóAbstrato nó) {
		filhos.add(nó);
	}
	
	public NóAbstrato filho(int indice) {
		if (indice >= filhos.size()) return null;
		return filhos.get(indice);
	}
	
	public String toString() {
		return this.getClass().getSimpleName(); 
	}

	public List<NóAbstrato> getFilhos() {
		return filhos;
	}
}
