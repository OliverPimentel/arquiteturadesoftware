package br.edu.ifsp.cmp.asw_ed2.tinkerscript.parser;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Parser {
	private RulesStateMachine rules;
	
	private Parser(RulesStateMachine rules) {
		this.rules = rules;
	}
	
	public static Parser createTinkerscriptScanner() {
		RulesStateMachine tinkerscriptRules = new RulesStateMachine("program_begin");
		tinkerscriptRules.addState("line_term");
		tinkerscriptRules.addState("program_end");
		
		// Program
		tinkerscriptRules.addTransition("program_begin", "program_end");
		tinkerscriptRules.addTransition("program_begin", "decl_begin");
		tinkerscriptRules.addTransition("program_begin", "_commands");
		
		// Declaration
		tinkerscriptRules.addTransition("decl_begin",  "_decl_assign");
		tinkerscriptRules.addTransition("decl_begin",  "decl_end");
		
		tinkerscriptRules.addTransition("_decl_assign", "decl_ident");
		tinkerscriptRules.addTransition("decl_ident",   "decl_equals");
		tinkerscriptRules.addTransition("decl_equals",  "decl_value");
		tinkerscriptRules.addTransition("decl_value",   "decl_term");
		
		tinkerscriptRules.addTransition("decl_term",   "_decl_assign");
		tinkerscriptRules.addTransition("decl_term",   "decl_end");
		
		tinkerscriptRules.addTransition("decl_end",    "_commands");
		
		// Commands
		tinkerscriptRules.addTransition("_commands", "pos_x");
		tinkerscriptRules.addTransition("_commands", "element");
		
		tinkerscriptRules.addTransition("pos_x",   "pos_y");
		tinkerscriptRules.addTransition("pos_y",   "pos_sep");
		tinkerscriptRules.addTransition("element", "pos_sep");
		
		tinkerscriptRules.addTransition("pos_sep", "line_term");
		tinkerscriptRules.addTransition("pos_sep", "_reserv");
		tinkerscriptRules.addTransition("pos_sep", "_expr");
		
		tinkerscriptRules.addTransition("_reserv",      "if_stmt");
		tinkerscriptRules.addTransition("if_stmt",      "if_cond_expr");
		tinkerscriptRules.addTransition("if_cond_expr", "if_cond_end");
		tinkerscriptRules.addTransition("if_cond_end",  "if_body_expr");
		tinkerscriptRules.addTransition("if_body_expr", "line_term");
		
		return new Parser(tinkerscriptRules);
	}
}
