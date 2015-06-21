package br.edu.ifsp.cmp.asw_ed2.tinkerscript.semantico;

import br.edu.ifsp.cmp.asw_ed2.tinkerscript.lexico.AnalisadorLexico;
import br.edu.ifsp.cmp.asw_ed2.tinkerscript.lexico.SimboloLexico;
import br.edu.ifsp.cmp.asw_ed2.tinkerscript.semantico.ast.ArvoreSintaticaAbstrata;

public class Parser {
	private AnalisadorLexico lex;
	private Grammar grammar;
	ArvoreSintaticaAbstrata ast;
	
	public Parser(AnalisadorLexico lex) {
		this.lex = lex;
		grammar = Grammar.tinkerscriptGrammar();
	}
	
	public ArvoreSintaticaAbstrata compile() {
		RulesStateMachine rules = grammar.getRulesStageMachine();
		ast = new ArvoreSintaticaAbstrata();
		
		for (SimboloLexico token : lex) {
			if (rules.change(token.getCategoria().name())) {
				ast.readToken(token);
			}
		}
		
		return ast;
	}
}
