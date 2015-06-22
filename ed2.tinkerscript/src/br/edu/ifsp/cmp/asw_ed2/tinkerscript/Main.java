package br.edu.ifsp.cmp.asw_ed2.tinkerscript;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;

import br.edu.ifsp.cmp.asw_ed2.tinkerscript.lexico.AnalisadorLexico;
import br.edu.ifsp.cmp.asw_ed2.tinkerscript.lexico.AnalisadorLexicoException;
import br.edu.ifsp.cmp.asw_ed2.tinkerscript.sintatico.AnalisadorSintatico;
import br.edu.ifsp.cmp.asw_ed2.tinkerscript.sintatico.ErroDeSintaxeException;
import br.edu.ifsp.cmp.asw_ed2.tinkerscript.util.InspetorDepuracao;

public class Main {
	public static void main(String[] args) {
		try {
			AnalisadorLexico lexico = new AnalisadorLexico("exemplo2.tinkerscript");
			lexico.analisar();
			InspetorDepuracao.padrao().visualizar(lexico);
			
			AnalisadorSintatico sintaxe = new AnalisadorSintatico(lexico);
			sintaxe.analisar();
			InspetorDepuracao.padrao().visualizar(sintaxe);
			
		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (AnalisadorLexicoException | ErroDeSintaxeException e) {
			System.err.println(e.getMessage());
		}
	}
}
