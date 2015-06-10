package br.edu.ifsp.cmp.asw_ed2.tinkerscript.lexer;

import java.util.regex.Pattern;

public enum TokenCategory {
	PROGRAM_BEGIN("^inicio:"),
	
	WHITESPACE("\\s+"),
	NEW_LINE("\n*"),
	
	NUMBER("[0-9]+"),
	STAR_SYM("\\*"),
	COLON_SYM(":"),
	
	LESS_THAN("<"),
	MORE_THAN(">"),
	EQUALS("="),
	INC_SYM("\\+="),
	DEC_SYM("-="),
	PROD_SYM("\\*="),
	
	AND("&&"),
	OR("||"),

	TRUE_SYM("sim"),
	FALSE_SYM("nao|não"),
	
	IF_BEGIN("se"),
	IF_CONDITION_END("entao"),
	
	STRING_NOTATION("\"[^\"]*\""),
	
	ELEMENT_NAME("AGUA|GRAMA|PERSONAGEM|MACA|PORTAL|URTIGA"),
	
	IDENTIFIER("[a-zA-Z0-9_]+\\??")
	;
	
	private Pattern pattern;
	
	TokenCategory(String pattern) {
		this.pattern = Pattern.compile("^" + pattern + "$");
	}
	
	public Pattern getPattern() {
		return pattern;
	}
	
	public boolean matches(String input) {
		return pattern.matcher(input).matches();
	}
}
