package br.edu.ifsp.cmp.asw_ed2.tinkerscript.parser;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Rule implements Iterable<List<Rule>> {
	private String identifier;
	private List<List<Rule>> definitions;
	
	public Rule(String identifier) {
		this.identifier = identifier;
		this.definitions = new ArrayList<List<Rule>>();
	}
	
	public String getIdentifer() {
		return identifier;
	}
	
	public void addDefinition(Rule... definition) {
		List<Rule> definitionRules = new ArrayList<>();
		
		for (Rule rule : definition)
			definitionRules.add(rule);
		
		definitions.add(definitionRules);
	}

	@Override
	public Iterator<List<Rule>> iterator() {
		return definitions.iterator();
	}
}
