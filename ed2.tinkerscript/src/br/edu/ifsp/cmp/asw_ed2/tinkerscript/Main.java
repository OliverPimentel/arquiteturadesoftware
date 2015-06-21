package br.edu.ifsp.cmp.asw_ed2.tinkerscript;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;

import br.edu.ifsp.cmp.asw_ed2.tinkerscript.lexico.AnalisadorLexico;
import br.edu.ifsp.cmp.asw_ed2.tinkerscript.lexico.AnalisadorLexicoException;
import br.edu.ifsp.cmp.asw_ed2.tinkerscript.parser.Parser;
import br.edu.ifsp.cmp.asw_ed2.tinkerscript.util.Inspector;

public class Main {
	public static void main(String[] args) {
		try {
			AnalisadorLexico lexer = new AnalisadorLexico("exemplo.tinkerscript");
			lexer.compilar();
			Inspector.inspect(lexer);
			
//			Parser parser = new Parser(lexer);
//			parser.compile();
//			Inspector.inspect(parser);
			
		} catch (FileNotFoundException | UnsupportedEncodingException | AnalisadorLexicoException e) {
			e.printStackTrace();
		}
	}
}
