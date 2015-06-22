package br.edu.ifsp.cmp.asw_ed2.tinkerscript.sintatico.arvore.nos;

import br.edu.ifsp.cmp.asw_ed2.tinkerscript.lexico.SimboloLexico;

public class NóString extends NóAbstrato {
	private StringBuilder builder;

	public NóString() {
		builder = new StringBuilder();
	}

	public void concatenar(SimboloLexico parte) {
		builder.append(parte.getLexema());
	}
}
