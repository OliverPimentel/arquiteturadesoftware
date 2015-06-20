package br.edu.ifsp.cmp.asw_ed2.tinkerscript.lexer;

import java.util.regex.Pattern;

public enum TokenCategory {
	PROGRAM_BEGIN("inicio"),
	PROGRAM_END(null),

	WHITESPACE(" +"),
	NEW_LINE("\\r\\n|\\n|\\r"),
	
	NUMBER("[0-9]+"),
	STAR_SYM("\\*"),
	COLON_SYM(":"),
	
	LESS_THAN("<"),
	MORE_THAN(">"),
	EQUALS("="),
	
	AND("&"),
	OR("\\|"),

	TRUE_SYM("sim"),
	FALSE_SYM("nao|não"),
	
	IF_BEGIN("se"),
	IF_CONDITION_END("entao"),
	
	STRING_NOTATION("\""),
	
	IDENTIFIER(".+")
	;
	
	public static final String SEPARATORS = "\\\"'!@#$%&^~,.:;+-*/=|<>()[]{} \r\n\t";
	
	private Pattern pattern;
	
	TokenCategory(String pattern) {
		this.pattern = Pattern.compile("^" + pattern + "$");
	}
	
	public static boolean isSeparator(char smallLexeme) {
		return SEPARATORS.indexOf(smallLexeme) > -1;
	}
	
	public static TokenCategory findMatching(String input) {
		for (TokenCategory cat : values())
			if (cat.matches(input))
				return cat;
		return null;
	}
	
	public Pattern getPattern() {
		return pattern;
	}
	
	public boolean matches(Character input) {
		return matches(Character.toString(input));
	}
	
	public boolean matches(String input) {
		if (pattern == null) return false;
		return pattern.matcher(input).matches();
	}
}
