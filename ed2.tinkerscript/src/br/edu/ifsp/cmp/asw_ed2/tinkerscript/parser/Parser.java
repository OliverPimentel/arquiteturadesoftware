package br.edu.ifsp.cmp.asw_ed2.tinkerscript.parser;

import br.edu.ifsp.cmp.asw_ed2.tinkerscript.lexico.AnalisadorLexico;
import br.edu.ifsp.cmp.asw_ed2.tinkerscript.lexico.SimboloLexico;
import br.edu.ifsp.cmp.asw_ed2.tinkerscript.parser.ast.AbstractSyntaxTree;

public class Parser {
	private AnalisadorLexico lex;
	private Grammar grammar;
	AbstractSyntaxTree ast;
	
	public Parser(AnalisadorLexico lex) {
		this.lex = lex;
		grammar = Grammar.tinkerscriptGrammar();
	}
	
	public AbstractSyntaxTree compile() {
		RulesStateMachine rules = grammar.getRulesStageMachine();
		ast = new AbstractSyntaxTree();
		
		for (SimboloLexico token : lex) {
			if (rules.change(token.getCategoria().name())) {
				ast.readToken(token);
			}
		}
		
		return ast;
	}
}
