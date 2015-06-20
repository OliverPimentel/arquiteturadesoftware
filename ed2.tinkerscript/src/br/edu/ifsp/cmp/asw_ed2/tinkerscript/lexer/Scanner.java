package br.edu.ifsp.cmp.asw_ed2.tinkerscript.lexer;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class Scanner {
	private int line;
	private int column;
	private List<Token> tokens;
	
	private Scanner() {
		line = 1;
		column = 1;
		tokens = new ArrayList<>();
	}
	
	public static List<Token> read(File file) throws LexerException {
		StringBuilder sb = new StringBuilder();
		
		try (java.util.Scanner scan = new java.util.Scanner(file)) {
			while (scan.hasNext()) {
				sb.append(scan.nextLine());
				sb.append("\n");
			}
		} catch (FileNotFoundException e) { throw new LexerException(0, 0, e); }
		
		return new Scanner().readSource(sb.toString().trim());
	}
	
	public static List<Token> read(String path) throws LexerException {
		return read(new File(path));
	}
	
	private List<Token> readSource(String sourceCode) throws LexerException {
		try (java.util.Scanner scanner = new java.util.Scanner(sourceCode)) {
			analize(scanner);
			return tokens;
		}
	}
	
	private void analize(java.util.Scanner scanner) throws LexerException {
		if (!scanner.hasNext()) return;
		
		StringBuilder builder = new StringBuilder(scanner.next());
		if (builder.length() == 0) { line++; column = 0; };
		
		Token token = extractToken(builder.toString());
		while (token == null && scanner.hasNext()) {
			String newFragment = scanner.next();
			if (newFragment.isEmpty()) { line++; column = 0; }
			builder.append(newFragment);
			token = extractToken(builder.toString());
		}
		
		if (token == null) {
			int end = builder.indexOf("\n");
			String msg = "Unexpected token: " + builder.substring(0, end == -1 ? 0 : end) + "...";
			
			throw new LexerException(line, column, new Throwable(msg));
		}
		
		if (token.getCategory() == TokenCategory.NEW_LINE) {
			line++;
			column = 1;
		} else {
			column += token.getLexeme().length();
		}
		
		tokens.add(token);
		
		analize(scanner);
	}
	
	private Token extractToken(String source) {
		for (TokenCategory category : TokenCategory.values()) {
			if (category.matches(source)) {
				return new Token(source, category);
			}
		}
		return null;
	}
}