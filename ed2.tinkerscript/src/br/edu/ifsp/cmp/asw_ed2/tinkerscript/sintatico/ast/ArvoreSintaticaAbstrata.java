package br.edu.ifsp.cmp.asw_ed2.tinkerscript.sintatico.ast;

import br.edu.ifsp.cmp.asw_ed2.tinkerscript.estruturas.Arvore;
import br.edu.ifsp.cmp.asw_ed2.tinkerscript.lexico.SimboloLexico;

public class ArvoreSintaticaAbstrata extends Arvore<AbstractNode> {
	public ArvoreSintaticaAbstrata(AbstractNode raizValor) {
		super(raizValor);
	}

	public void readToken(SimboloLexico token) {
		
	}
}
