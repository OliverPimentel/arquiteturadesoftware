package br.edu.ifsp.cmp.asw_ed2.tinkerscript.util;

import static java.util.stream.Collectors.maxBy;

import java.util.stream.Stream;

import br.edu.ifsp.cmp.asw_ed2.tinkerscript.lexico.AnalisadorLexico;
import br.edu.ifsp.cmp.asw_ed2.tinkerscript.lexico.SimboloLexico;
import br.edu.ifsp.cmp.asw_ed2.tinkerscript.lexico.SimboloLexicoCategoria;
import br.edu.ifsp.cmp.asw_ed2.tinkerscript.parser.Parser;

public class Inspector {
	public static final int TAB_SIZE = 8;
	public static final int LONGEST_TOKEN_NAME =
			Stream.of(SimboloLexicoCategoria.values())
			.map(Enum::name)
			.collect(maxBy((a, b) -> a.length() - b.length()))
			.orElse("").length();
	
	public static void tabbedValue(StringBuilder builder, String value, int longest) {
		int tabsToInsert = longest / TAB_SIZE - value.length() / TAB_SIZE;
		builder.append(value);
		for (int i = 0; i < tabsToInsert; i++) builder.append('\t');
	}
	
	public static void wrap(StringBuilder builder, String value, char wrapper) {
		builder.append(wrapper).append(value).append(wrapper);
	}

	public static void inspect(AnalisadorLexico lexer) {
		for (SimboloLexico token : lexer) {
			StringBuilder lineBuilder = new StringBuilder();
			
			tabbedValue(lineBuilder, token.getCategoria().name(), LONGEST_TOKEN_NAME);
			wrap(lineBuilder, token.getPosicao().toString(), '\t');
			wrap(lineBuilder, token.getLexema().replace("\n", "\\n"), '"');
			
			System.out.println(lineBuilder.toString());
		}
	}
	
	public static void inspect(Parser parser) {
		System.out.println(parser);
	}

	private Inspector() { }
}
