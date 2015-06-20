package br.edu.ifsp.cmp.asw_ed2.tinkerscript;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;

import br.edu.ifsp.cmp.asw_ed2.tinkerscript.lexer.Lexer;
import br.edu.ifsp.cmp.asw_ed2.tinkerscript.lexer.LexerException;
import br.edu.ifsp.cmp.asw_ed2.tinkerscript.lexer.Token;

public class Main {
	public static void main(String[] args) {
		try {
			Lexer lex = new Lexer("exemplo.tinkerscript");
			lex.compile();
			
			for (Token t : lex) {
				String l = t.getLexeme();
				String c = t.getCategory().toString();
				System.out.println((c.length() < 8 ? c + "\t" : c) + "\t" + "\"" + (l.equals("\n") ? "" : l)  + "\"");
			}
			
			System.out.println("fim");
		} catch (FileNotFoundException | UnsupportedEncodingException | LexerException e) {
			e.printStackTrace();
		}
	}
}
