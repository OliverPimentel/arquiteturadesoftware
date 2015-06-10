package br.edu.ifsp.cmp.asw_ed2.tinkerscript.lexer;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import br.edu.ifsp.cmp.asw_ed2.tinkerscript.FilePosition;

public class Scanner {
	private Scanner() {	}

	public static List<Token> read(File file) throws ScannerException {
	}
	
	public static List<Token> read(String path) throws ScannerException {
	}
	
	private List<Token> readSource(String sourceCode) throws ScannerException {
		int currentLine = 0, currentColumn = 0;
		List<Token> tokens = new ArrayList<Token>();
		
		for (String line : sourceCode.split("\n")) {
			try (java.util.Scanner scanner = new java.util.Scanner(line)) {
				currentLine++;
				currentColumn = 0;
				
				while (scanner.hasNext()) {
					Token token = analize(scanner.next(), currentLine, currentColumn);
					
					if (token == null) throw new ScannerException(currentLine, currentColumn, null);
					
					tokens.add(token);
					currentColumn += token.getLexeme().length();
				}
				
				tokens.add(new Token("\n", TokenCategory.NEW_LINE, new FilePosition(currentLine, currentColumn)));
			}
		}
		
		return tokens;
	}
	
	private Token analize(String lexeme, int line, int column) {
		FilePosition position = new FilePosition(line, column);
		for (TokenCategory category : TokenCategory.values()) {
			if (category.matches(lexeme)) {
				return new Token(lexeme, category, position);
			}
		}
		return null;
	}
}