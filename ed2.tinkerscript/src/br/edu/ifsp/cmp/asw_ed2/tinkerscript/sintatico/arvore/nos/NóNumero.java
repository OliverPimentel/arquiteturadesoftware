package br.edu.ifsp.cmp.asw_ed2.tinkerscript.sintatico.arvore.nos;

import br.edu.ifsp.cmp.asw_ed2.tinkerscript.lexico.SimboloLexico;

public class NóNumero extends NóAbstrato {
	SimboloLexico simbolo;
	
	public NóNumero(SimboloLexico simbolo) {
		this.simbolo = simbolo;
	}
	
	@Override
	public String toString() {
		return super.toString() + " " + simbolo.getLexema(); 
	}
}
