package br.edu.ifsp.cmp.asw_ed2.tinkerscript.util;

import static java.util.stream.Collectors.maxBy;

import java.util.List;
import java.util.Map.Entry;
import java.util.stream.Stream;

import br.edu.ifsp.cmp.asw_ed2.tinkerscript.lexico.AnalisadorLexico;
import br.edu.ifsp.cmp.asw_ed2.tinkerscript.lexico.SimboloLexico;
import br.edu.ifsp.cmp.asw_ed2.tinkerscript.lexico.SimboloLexicoCategoria;
import br.edu.ifsp.cmp.asw_ed2.tinkerscript.semantico.AmbienteDeExecucao;
import br.edu.ifsp.cmp.asw_ed2.tinkerscript.sintatico.arvore.ArvoreSintaticaAbstrata;
import br.edu.ifsp.cmp.asw_ed2.tinkerscript.sintatico.arvore.nos.NóAbstrato;
import br.edu.ifsp.cmp.asw_ed2.tinkerscript.sintatico.arvore.nos.NóExpressoes;

public class InspetorDepuracao {
	public static final int LARGURA_TABULACAO = 8;
	public static final int CATEGORIA_DE_MAIOR_NOME =
			Stream.of(SimboloLexicoCategoria.values())
			.map(Enum::name)
			.collect(maxBy((a, b) -> a.length() - b.length()))
			.orElse("").length();
	
	private StringBuilder linha;
	private static InspetorDepuracao inspetorPadrao = null;
	
	private InspetorDepuracao(StringBuilder linha) {
		this.linha = linha;
	}
	
	public static InspetorDepuracao padrao() {
		if (inspetorPadrao == null) inspetorPadrao = new InspetorDepuracao(new StringBuilder());
		return inspetorPadrao;
	}
	
	public void visualizar(AnalisadorLexico lexico) {
		for (SimboloLexico simbolo : lexico) {
			adicionarTabulacao(simbolo.getCategoria().name(), CATEGORIA_DE_MAIOR_NOME);
			envolver(simbolo.getPosicao().toString(), '\t');
			envolver(simbolo.getLexema().replace("\n", "\\n"), '"');
			
			imprimirLinha();
		}
	}
	
	public void visualizar(AmbienteDeExecucao ambiente) {
		for (Entry<String,Object> entrada : ambiente.getTabelaDeSimbolos().entrySet()) {
			System.out.println("-> " + entrada.getKey() + "\n" + entrada.getValue() + "\n");
		}
		System.out.println("Comportamentos:");
		for (Entry<String,List<NóExpressoes>> entrada : ambiente.getComportamentos().entrySet()) {
			System.out.println("--" + entrada.getKey() + "\t|\t" + entrada.getValue().size());
		}
	}
	
	public void visualizar(ArvoreSintaticaAbstrata ast) {
		imprimirPreOrdem(ast.getRaiz(), "");
	}
	
	private void imprimirPreOrdem(NóAbstrato nó, String padding) {
		if (nó != null) {
			System.out.println(padding + nó.toString());
			for (NóAbstrato filho : nó.getFilhos())
				imprimirPreOrdem(filho, padding + "  ");
		}
	}
	
	private void adicionarTabulacao(String texto, int limite) {
		int tabs = limite / LARGURA_TABULACAO - texto.length() / LARGURA_TABULACAO;
		
		linha.append(texto);
		for (int i = 0; i < tabs; i++) linha.append('\t');
	}
	
	private void envolver(String texto, char caractere) {
		linha.append(caractere).append(texto).append(caractere);
	}
	
	private void imprimirLinha() {
		System.out.println(linha.toString());
		linha.delete(0, linha.length());
	}
}
