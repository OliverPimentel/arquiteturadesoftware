package br.edu.ifsp.cmp.asw_ed2.tinkerscript.sintatico;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import br.edu.ifsp.cmp.asw_ed2.tinkerscript.lexico.SimboloLexicoCategoria;

public class Regra implements Iterable<List<Regra>> {
	private String identifier;
	private List<List<Regra>> definitions;
	private boolean terminal;
	
	public Regra(String identifier) {
		this.identifier = identifier;
		this.definitions = new ArrayList<List<Regra>>();
		this.terminal = SimboloLexicoCategoria.valueOf(identifier) != null;
	}
	
	public String getIdentifer() {
		return identifier;
	}
	
	public void adicionarDefinicao(Regra... definition) {
		List<Regra> definitionRules = new ArrayList<>();
		
		for (Regra rule : definition)
			definitionRules.add(rule);
		
		definitions.add(definitionRules);
	}
	
	public boolean ehTerminal() {
		return terminal;
	}

	@Override
	public Iterator<List<Regra>> iterator() {
		return definitions.iterator();
	}
}
