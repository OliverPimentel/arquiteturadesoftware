package br.edu.ifsp.cmp.asw_ed2.tinkerscript.sintatico.arvore.nos;

import br.edu.ifsp.cmp.asw_ed2.tinkerscript.lexico.SimboloLexico;
import br.edu.ifsp.cmp.asw_ed2.tinkerscript.lexico.SimboloLexicoCategoria;

public class NóBooleano extends NóAbstrato {
	public NóBooleano(SimboloLexico simbolo) {
		super(simbolo);
	}
	
	public boolean valor() {
		if (simbolo.getCategoria() == SimboloLexicoCategoria.VERDADEIRO)
			return true;
		return false;
	}

	@Override
	public String toString() {
		return super.toString() + " " + simbolo.getLexema(); 
	}
}
