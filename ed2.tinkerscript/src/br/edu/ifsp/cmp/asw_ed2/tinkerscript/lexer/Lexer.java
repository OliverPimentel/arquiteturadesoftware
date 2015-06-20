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
	
	public Lexer execute(String source) throws FileNotFoundException, UnsupportedEncodingException {
		return new Lexer(new InputStreamReader(new ByteArrayInputStream(source.getBytes())));
	}
	
	public Lexer(Reader input) {
		this.input = input;
		tokens = new ArrayList<Token>();
		tokenBuilder = new StringBuilder();
		
		line = 1;
		column = 1;
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
	
	private void _compile(char character) throws LexerException {
		if (character == (char) -1) return; // EOF reached?
		
		if (TokenCategory.isSeparator(character)) {
			if (tokenBuilder.length() > 0)
				tokens.add(tokenize(tokenBuilder.toString()));
			tokenBuilder.delete(0, tokenBuilder.length());
			
			// avoid creating multiple tokens for consecutive white-spaces
			if (TokenCategory.WHITESPACE.matches(String.valueOf(character))) {
				StringBuilder blankBuilder = new StringBuilder();
				do { blankBuilder.append(character); }
				while (TokenCategory.WHITESPACE.matches(character = next()));
				tokens.add(tokenize(blankBuilder.toString()));
			} else {
				tokens.add(tokenize(character));
				character = next();
			}
		} else {
			tokenBuilder.append(character);
			character = next();
		}
		
		_compile(character);
	}
	
	private char next() throws LexerException {
		try {
			char character = (char) input.read();
			
			if (character == '\n') { line += 1; column = 0; }
			column += 1;
			
			return character;
		} catch (IOException e) {
			throw new LexerException(line, column, e);
		}
	}
	
	private Token tokenize(String lexeme) throws LexerException {
		TokenCategory category = TokenCategory.findMatching(lexeme);
		
		if (category == null)
			throw new LexerException(line, column, "Unexpected token: " + lexeme);
		
		return new Token(lexeme, category, new FilePosition(line, column));
	}
	
	private Token tokenize(char smallLexeme) throws LexerException {
		return tokenize(Character.toString((smallLexeme)));
	}
}
