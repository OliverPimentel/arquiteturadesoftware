package br.edu.ifsp.cmp.asw_ed2.tinkerscript.sintatico.arvore.nos;

import br.edu.ifsp.cmp.asw_ed2.tinkerscript.lexico.SimboloLexico;

public class NóBooleano extends NóAbstrato {
	public NóBooleano(SimboloLexico simbolo) {
		super(simbolo);
	}

	@Override
	public String toString() {
		return super.toString() + " " + simbolo.getLexema(); 
	}
}
