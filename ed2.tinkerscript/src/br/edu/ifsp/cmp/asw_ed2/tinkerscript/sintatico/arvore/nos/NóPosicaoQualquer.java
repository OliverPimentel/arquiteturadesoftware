package br.edu.ifsp.cmp.asw_ed2.tinkerscript.sintatico.arvore.nos;

import br.edu.ifsp.cmp.asw_ed2.tinkerscript.lexico.SimboloLexico;

public class NóPosicaoQualquer extends NóAbstrato {

	public NóPosicaoQualquer(SimboloLexico atual) {
		super(atual);
	}
	
	@Override
	public String toString() {
		return super.toString() + " " + simbolo.getLexema(); 
	}
}
