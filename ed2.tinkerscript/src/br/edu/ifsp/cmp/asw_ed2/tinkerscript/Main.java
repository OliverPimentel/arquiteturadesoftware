package br.edu.ifsp.cmp.asw_ed2.tinkerscript;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;

import br.edu.ifsp.cmp.asw_ed2.tinkerscript.lexico.AnalisadorLexico;
import br.edu.ifsp.cmp.asw_ed2.tinkerscript.lexico.AnalisadorLexicoException;
import br.edu.ifsp.cmp.asw_ed2.tinkerscript.parser.Parser;
import br.edu.ifsp.cmp.asw_ed2.tinkerscript.util.InspetorDepuracao;

public class Main {
	public static void main(String[] args) {
		try {
			AnalisadorLexico lexico = new AnalisadorLexico("exemplo.tinkerscript");
			lexico.analisar();
			InspetorDepuracao.padrao().visualizar(lexico);
			
//			Parser parser = new Parser(lexico);
//			parser.compile();
//			InspetorDepuracao.inspect(parser);
			
		} catch (FileNotFoundException | UnsupportedEncodingException | AnalisadorLexicoException e) {
			e.printStackTrace();
		}
	}
}
