package br.edu.ifsp.cmp.asw_ed2.tinkerscript.sintatico.arvore.nos;

import br.edu.ifsp.cmp.asw_ed2.tinkerscript.lexico.SimboloLexico;


public class NóIdentificador extends NóAbstrato {
	private SimboloLexico simbolo;

	public NóIdentificador(SimboloLexico simbolo) {
		this.simbolo = simbolo;
	}
	
	@Override
	public String toString() {
		return super.toString() + " " + simbolo.getLexema(); 
	}
}
