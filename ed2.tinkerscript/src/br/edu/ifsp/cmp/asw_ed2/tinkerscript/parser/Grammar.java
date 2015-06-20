package br.edu.ifsp.cmp.asw_ed2.tinkerscript.parser;

import java.util.Scanner;

public class Grammar {
	public static final String TINKERSCRIPT = ""
			+ "begin"
			+ "-> PROGRAM_BEGIN declaration NEW_LINE commands"
			+ "-> PROGRAM_BEGIN commands"
			+ ""
			+ "declaration"
			+ "-> IDENTIFIER EQUALS NUMBER"
			+ "-> IDENTIFIER EQUALS IDENTIFIER"
			+ ""
			+ "commands"
			+ "-> positions COLON_SYM expressions"
			+ ""
			+ "positions"
			+ "-> IDENTIFIER"
			+ "-> position_value position_value"
			+ ""
			+ "position_value"
			+ "-> STAR_SYM"
			+ "-> NUMBER"
			+ ""
			+ "expressions"
			+ "-> NUMBER";

	private RulesStateMachine rulesMachine;
	
	public static Grammar tinkerscriptGrammar() {
		return new Grammar(TINKERSCRIPT);
	}
	
	private Grammar(String definition) {
		for (String line : definition.split("\n"))
			readDefinitionLine(line);
	}
	
	private void readDefinitionLine(String line) {
		try (Scanner scan = new Scanner(line)) {
			while (scan.hasNext()) {
				String word = scan.next();
				if (word.startsWith("->"));
			}
		}
	}
}
