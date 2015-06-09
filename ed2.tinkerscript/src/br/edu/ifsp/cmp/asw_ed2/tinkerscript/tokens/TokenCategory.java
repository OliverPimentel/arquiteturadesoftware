package br.edu.ifsp.cmp.asw_ed2.tinkerscript.tokens;

import java.util.regex.Pattern;

public enum TokenCategory {
	PROGRAM_BEGIN("inicio:"),
	
	WHITESPACE("[ ]+"),
	NEW_LINE("\n"),
	
	NUMBER("[0-9]+"),
	STAR_SYM("\\*"),
	COLON_SYM(":"),
	
	LESS_THAN("<"),
	MORE_THAN(">"),
	EQUALS("="),
	INC_SYM("+="),
	DEC_SYM("-="),
	PROD_SYM("*="),
	
	TRUE_SYM("sim"),
	FALSE_SYM("nao|não"),
	
	IF_BEGIN("se"),
	IF_CONDITION_END("entao"),
	
	ELEMENT_NAME("AGUA|GRAMA|PERSONAGEM|MACA|PORTAL|URTIGA"),
	;
	
	private Pattern pattern;
	
	TokenCategory(String pattern) {
		this.pattern = Pattern.compile(pattern);
	}
	
	public boolean matches(String input) {
		return pattern.matcher(input).matches();
	}
}
