package br.edu.ifsp.cmp.asw_ed2.tinkerscript.semantico;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Grammar {
	public static final String MAIN_RULE_IDENTIFIER = "x:begin";
	
	public static final String TINKERSCRIPT = ""
			+ MAIN_RULE_IDENTIFIER
			+ "-> PROGRAMA_INICIO declaration PULO_DE_LINHA commands PROGRAMA_FIM"
			+ "-> PROGRAMA_INICIO commands PROGRAMA_FIM"
			+ ""
			+ "declaration"
			+ "-> IDENTIFICADOR IGUAL NUMERO"
			+ "-> IDENTIFICADOR IGUAL IDENTIFICADOR"
			+ ""
			+ "commands"
			+ "-> positions DOIS_PONTOS expressions"
			+ ""
			+ "positions"
			+ "-> IDENTIFICADOR"
			+ "-> position_value position_value"
			+ ""
			+ "position_value"
			+ "-> ASTERISCO"
			+ "-> NUMERO"
			+ ""
			+ "expressions"
			+ "-> NUMERO";

	private Map<String,Rule> rules;
	private Rule currentRule;
	
	public static Grammar tinkerscriptGrammar() {
		return new Grammar(TINKERSCRIPT);
	}
	
	private Grammar(String definition) {
		rules = new HashMap<>();
		currentRule = null;
		
		for (String line : definition.split("\n"))
			readDefinitionLine(line);
	}
	
	public RulesStateMachine getRulesStageMachine() {
		RulesStateMachine machine = new RulesStateMachine(mainRule().getIdentifer());
		
		
		return machine;
	}
	
	private void _addTransition(RulesStateMachine machine, Rule from) {
		for (List<Rule> definition : from) {
			for (Rule rule : definition) {
				
			}
		}
	}
	
	private void readDefinitionLine(String line) {
		try (Scanner scan = new Scanner(line)) {
			if (!scan.hasNext()) return;
			
			String word = scan.next();
			
			if (word.startsWith("->")) {
				while (scan.hasNext()) { currentRule.addDefinition(fetchRule(scan.next())); } 
			} else {
				currentRule = fetchRule(word);
			}
		}
	}
	
	private Rule fetchRule(String identifier) {
		if (rules.containsKey(identifier))
			return rules.get(identifier);
		
		Rule newRule = new Rule(identifier);
		rules.put(identifier, newRule);
		
		return newRule;
	}
	
	private Rule mainRule() {
		return rules.get(MAIN_RULE_IDENTIFIER);
	}
}
