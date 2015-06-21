package br.edu.ifsp.cmp.asw_ed2.tinkerscript.lexico;

import java.util.regex.Pattern;

public enum SimboloLexicoCategoria {
	PROGRAMA_INICIO("inicio"),
	PROGRAMA_FIM(null),

	ESPACO(" +"),
	PULO_DE_LINHA("\\r\\n|\\n|\\r"),
	
	NUMERO("[0-9]+"),
	ASTERISCO("\\*"),
	DOIS_PONTOS(":"),
	
	MENOS_QUE("<"),
	MAIOR_QUE(">"),
	IGUAL("="),
	
	E("&"),
	OU("\\|"),

	VERDADEIRO("sim"),
	FALSO("nao|não"),
	
	SE_INICIO("se"),
	SE_CONDICAO_FIM("entao"),
	
	NOTACAO_STRING("\""),
	
	IDENTIFICADOR(".+")
	;
	
	public static final String SEPARADORES = "\\\"'!@#$%&^~,.:;+-*/=|<>()[]{} \r\n\t";
	
	private Pattern padrao;
	
	SimboloLexicoCategoria(String padrao) {
		this.padrao = Pattern.compile("^" + padrao + "$");
	}
	
	public static boolean ehSeparador(char simbolo) {
		return SEPARADORES.indexOf(simbolo) > -1;
	}
	
	public static SimboloLexicoCategoria buscarCasamentoCom(String entrada) {
		for (SimboloLexicoCategoria cat : values())
			if (cat.casaCom(entrada))
				return cat;
		return null;
	}
	
	public Pattern getPadrao() {
		return padrao;
	}
	
	public boolean casaCom(Character entrada) {
		return casaCom(Character.toString(entrada));
	}
	
	public boolean casaCom(String entrada) {
		if (padrao == null) return false;
		return padrao.matcher(entrada).matches();
	}
}
