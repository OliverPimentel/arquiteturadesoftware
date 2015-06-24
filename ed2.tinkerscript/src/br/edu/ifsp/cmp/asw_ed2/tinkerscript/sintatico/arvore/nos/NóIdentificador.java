package br.edu.ifsp.cmp.asw_ed2.tinkerscript.sintatico.arvore.nos;

import br.edu.ifsp.cmp.asw_ed2.tinkerscript.lexico.SimboloLexico;

public class NóIdentificador extends NóAbstrato {
	public NóIdentificador(SimboloLexico simbolo) {
		super(simbolo);
	}

	public String identificador() {
		return simbolo.getLexema();
	}
	
	@Override
	public String toString() {
		return super.toString() + " " + simbolo.getLexema(); 
	}
}
