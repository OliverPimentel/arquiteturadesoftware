package br.edu.ifsp.cmp.asw_ed2.tinkerscript.lexer;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Lexer implements Iterable<Token> {
	private Reader input;
	List<Token> tokens;
	StringBuilder tokenBuilder;
	private int line, column;
	private boolean lineFeedRead;
	
	public Lexer execute(String source) throws FileNotFoundException, UnsupportedEncodingException {
		return new Lexer(new InputStreamReader(new ByteArrayInputStream(source.getBytes())));
	}
	
	public Lexer(Reader input) {
		this.input = input;
		tokens = new ArrayList<Token>();
		tokenBuilder = new StringBuilder();
		
		line = 1;
		column = 0;
		lineFeedRead = false;
	}
	
	public Lexer(File sourceFile, String charset) throws FileNotFoundException, UnsupportedEncodingException {
		this(new InputStreamReader(new FileInputStream(sourceFile), charset));
	}
	
	public Lexer(String sourceFilePath, String charset) throws FileNotFoundException, UnsupportedEncodingException {
		this(new InputStreamReader(new FileInputStream(new File(sourceFilePath)), charset));
	}
	
	public Lexer(String sourceFilePath) throws FileNotFoundException, UnsupportedEncodingException {
		this(sourceFilePath, "utf-8");
	}
	
	@Override
	public Iterator<Token> iterator() {
		if (tokens == null) throw new RuntimeException("You must compile first");
		return tokens.iterator(); 
	}
	
	public void compile() throws LexerException {
		_compile(next());
	}
	
	public void inspect() {
		int tabSize = 8;
		int longest = 0;
		for (TokenCategory category : TokenCategory.values())
			if (longest < category.name().length())
				longest = category.name().length();
		
		int tabsToInsert = longest / tabSize;
		StringBuilder lineBuilder = new StringBuilder();
		
		for (Token token : this) {
			String categoryName = token.getCategory().name();
			lineBuilder.append(categoryName);
			
			for (int i = 0; i < tabsToInsert - (categoryName.length() / tabSize); i++)
				lineBuilder.append('\t');
			
			lineBuilder.append('\t').append(token.getPosition().toString()).append('\t');
			lineBuilder.append('"').append(token.getLexeme().replace("\n", "\\n")).append('"');
			
			System.out.println(lineBuilder.toString());
			
			lineBuilder.delete(0, lineBuilder.length());
		}
	}
	
	private void _compile(char character) throws LexerException {
		// Reached EOF
		if (character == (char) -1) { addTokenFromBuilder(); return; }
		
		if (TokenCategory.isSeparator(character)) {
			addTokenFromBuilder();
			
			// avoid creating multiple tokens for consecutive white-spaces
			if (TokenCategory.WHITESPACE.matches(String.valueOf(character))) {
				StringBuilder blankBuilder = new StringBuilder();
				do { blankBuilder.append(character); }
				while (TokenCategory.WHITESPACE.matches(character = next()));
				tokens.add(tokenize(blankBuilder.toString()));
			} else {
				column++;
				tokens.add(tokenize(character));
				character = next();
			}
		} else {
			tokenBuilder.append(character);
			character = next();
		}
		
		_compile(character);
	}
	
	private void addTokenFromBuilder() throws LexerException {
		if (tokenBuilder.length() > 0)
			tokens.add(tokenize(tokenBuilder.toString()));
		tokenBuilder.delete(0, tokenBuilder.length());
	}
	
	private char next() throws LexerException {
		if (lineFeedRead) { line += 1; column = 0; lineFeedRead = false; }
		
		try {
			char character = (char) input.read();
			column++;
			if (character == '\n') lineFeedRead = true;
			return character;
		} catch (IOException e) {
			throw new LexerException(line, column, e);
		}
	}
	
	private Token tokenize(String lexeme) throws LexerException {
		TokenCategory category = TokenCategory.findMatching(lexeme);
		int lexemeStart = column - lexeme.length();
		
		if (category == null)
			throw new LexerException(line, lexemeStart, "Unexpected token: \"" + lexeme + "\"");
		
		return new Token(lexeme, category, new FilePosition(line, lexemeStart));
	}
	
	private Token tokenize(char smallLexeme) throws LexerException {
		return tokenize(Character.toString((smallLexeme)));
	}
}
