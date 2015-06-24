package br.edu.ifsp.cmp.asw_ed2.tinkerscript;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;

import br.edu.ifsp.cmp.asw_ed2.tinkerscript.lexico.AnalisadorLexico;
import br.edu.ifsp.cmp.asw_ed2.tinkerscript.lexico.AnalisadorLexicoException;
import br.edu.ifsp.cmp.asw_ed2.tinkerscript.semantico.AmbienteDeExecucao;
import br.edu.ifsp.cmp.asw_ed2.tinkerscript.semantico.FuncoesPadrao;
import br.edu.ifsp.cmp.asw_ed2.tinkerscript.sintatico.AnalisadorSintatico;
import br.edu.ifsp.cmp.asw_ed2.tinkerscript.sintatico.ErroDeSintaxeException;
import br.edu.ifsp.cmp.asw_ed2.tinkerscript.sintatico.arvore.ArvoreSintaticaAbstrata;
import br.edu.ifsp.cmp.asw_ed2.tinkerscript.util.InspetorDepuracao;

public class Main {
	public static void main(String[] args) {
		try {
			AnalisadorLexico lexico = new AnalisadorLexico("exemplo.tinkerscript");
			lexico.analisar();
			
			System.out.println("\n\nLéxico");
			InspetorDepuracao.padrao().visualizar(lexico);
			
			AnalisadorSintatico sintaxe = new AnalisadorSintatico(lexico);
			ArvoreSintaticaAbstrata ast = sintaxe.analisar();
			
			System.out.println("\n\nÁrvore sintática abstrata (pre-ordem)");
			InspetorDepuracao.padrao().visualizar(ast);
			
			System.out.println("\n\nRuntime");
			AmbienteDeExecucao ambiente = new AmbienteDeExecucao(new FuncoesPadrao() {
				@Override
				public void executar(String nome, Object... argumentos) {
					// TODO Auto-generated method stub
					
				}
			});
			ambiente.executar(ast);
			InspetorDepuracao.padrao().visualizar(ambiente);
		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (AnalisadorLexicoException | ErroDeSintaxeException e) {
			System.err.println(e.getMessage());
		}
	}
}
