package br.edu.ifsp.cmp.asw_ed2.tinkerscript;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;

import br.edu.ifsp.cmp.asw_ed2.tinkerscript.lexer.Lexer;
import br.edu.ifsp.cmp.asw_ed2.tinkerscript.lexer.LexerException;

public class Main {
	public static void main(String[] args) {
		try {
			Lexer lex = new Lexer("exemplo.tinkerscript");
			lex.compile();
			lex.inspect();
			System.out.println("fim");
		} catch (FileNotFoundException | UnsupportedEncodingException | LexerException e) {
			e.printStackTrace();
		}
	}
}
