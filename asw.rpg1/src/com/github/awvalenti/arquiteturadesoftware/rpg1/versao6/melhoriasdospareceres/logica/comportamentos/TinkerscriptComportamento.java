package com.github.awvalenti.arquiteturadesoftware.rpg1.versao6.melhoriasdospareceres.logica.comportamentos;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;

import br.edu.ifsp.cmp.asw_ed2.tinkerscript.lexico.AnalisadorLexico;
import br.edu.ifsp.cmp.asw_ed2.tinkerscript.lexico.AnalisadorLexicoException;
import br.edu.ifsp.cmp.asw_ed2.tinkerscript.semantico.AmbienteDeExecucao;
import br.edu.ifsp.cmp.asw_ed2.tinkerscript.sintatico.AnalisadorSintatico;
import br.edu.ifsp.cmp.asw_ed2.tinkerscript.sintatico.ErroDeSintaxeException;
import br.edu.ifsp.cmp.asw_ed2.tinkerscript.sintatico.arvore.ArvoreSintaticaAbstrata;
import br.edu.ifsp.cmp.asw_ed2.tinkerscript.util.InspetorDepuracao;

import com.github.awvalenti.arquiteturadesoftware.rpg1.versao6.melhoriasdospareceres.logica.Elemento;

public class TinkerscriptComportamento implements ComportamentoProgramavel {
	private AmbienteDeExecucao ambiente;
	
	public TinkerscriptComportamento(String caminhoArquivoFonte) {
		try {
			AnalisadorLexico lexico = new AnalisadorLexico(caminhoArquivoFonte);
			lexico.analisar();
			
			System.out.println("\n\nLéxico");
			InspetorDepuracao.padrao().visualizar(lexico);
			
			AnalisadorSintatico sintaxe = new AnalisadorSintatico(lexico);
			ArvoreSintaticaAbstrata ast = sintaxe.analisar();
			
			System.out.println("\n\nÁrvore sintática abstrata (pre-ordem)");
			InspetorDepuracao.padrao().visualizar(ast);
			
			System.out.println("\n\nRuntime");
			this.ambiente = new AmbienteDeExecucao();
			ambiente.executar(ast);
			InspetorDepuracao.padrao().visualizar(ambiente);

			System.out.println("Pronto!");
		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (AnalisadorLexicoException | ErroDeSintaxeException e) {
			System.err.println(e.getMessage());
		}
	}
	
	@Override
	public void executar(int linha, int coluna, Elemento elemento) {
		ambiente.executar(linha, coluna, elemento.name());
	}
}
