package br.edu.ifsp.cmp.asw_ed2.tinkerscript.semantico;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import br.edu.ifsp.cmp.asw_ed2.tinkerscript.sintatico.arvore.ArvoreSintaticaAbstrata;
import br.edu.ifsp.cmp.asw_ed2.tinkerscript.sintatico.arvore.nos.NóAbstrato;
import br.edu.ifsp.cmp.asw_ed2.tinkerscript.sintatico.arvore.nos.NóBooleano;
import br.edu.ifsp.cmp.asw_ed2.tinkerscript.sintatico.arvore.nos.NóComando;
import br.edu.ifsp.cmp.asw_ed2.tinkerscript.sintatico.arvore.nos.NóComandos;
import br.edu.ifsp.cmp.asw_ed2.tinkerscript.sintatico.arvore.nos.NóCondicao;
import br.edu.ifsp.cmp.asw_ed2.tinkerscript.sintatico.arvore.nos.NóDeclaracao;
import br.edu.ifsp.cmp.asw_ed2.tinkerscript.sintatico.arvore.nos.NóDeclaracoes;
import br.edu.ifsp.cmp.asw_ed2.tinkerscript.sintatico.arvore.nos.NóExpressao;
import br.edu.ifsp.cmp.asw_ed2.tinkerscript.sintatico.arvore.nos.NóExpressoes;
import br.edu.ifsp.cmp.asw_ed2.tinkerscript.sintatico.arvore.nos.NóIdentificador;
import br.edu.ifsp.cmp.asw_ed2.tinkerscript.sintatico.arvore.nos.NóNumero;
import br.edu.ifsp.cmp.asw_ed2.tinkerscript.sintatico.arvore.nos.NóPosicao;
import br.edu.ifsp.cmp.asw_ed2.tinkerscript.sintatico.arvore.nos.NóPosicaoQualquer;
import br.edu.ifsp.cmp.asw_ed2.tinkerscript.sintatico.arvore.nos.NóPrograma;

import com.github.awvalenti.arquiteturadesoftware.rpg1.versao5.arquiteturadefinida.logicajogo.Direcao;
import com.github.awvalenti.arquiteturadesoftware.rpg1.versao5.arquiteturadefinida.logicajogo.Elemento;
import com.github.awvalenti.arquiteturadesoftware.rpg1.versao5.arquiteturadefinida.logicajogo.Fase;

public class AmbienteDeExecucao {
	private Map<String, Object> tabelaDeSimbolos;
	private Map<String, List<NóExpressoes>> comportamentos;
	
	public AmbienteDeExecucao(Fase faseAtual) {
		tabelaDeSimbolos = new HashMap<>();
		comportamentos = new HashMap<>();
	}
	
	public void executar(ArvoreSintaticaAbstrata arvore) {
		visitar(arvore.getRaiz());
	}
	
	public void executar(int linha, int coluna, Elemento elemento) {
		for (NóExpressoes expressoes : expressoes(linha, coluna, elemento))
			visitar(expressoes);
	}

	public Map<String, Object> getTabelaDeSimbolos() {
		return tabelaDeSimbolos;
	}
	
	private void visitar(NóPrograma programa) {
		visitar((NóDeclaracoes) programa.filho(0));
		visitar((NóComandos) programa.filho(1));
	}
	
	private void visitar(NóDeclaracoes declaracoes) {
		for (NóAbstrato declaracao : declaracoes.getFilhos())
			visitar((NóDeclaracao) declaracao);
	}

	private void visitar(NóDeclaracao no) {
		NóAbstrato valor = no.filho(1);
		
		if (valor instanceof NóNumero) {
			tabelaDeSimbolos.put(((NóIdentificador) no.filho(0)).identificador(), ((NóNumero) no.filho(1)).valor());
		} else if (valor instanceof NóBooleano) {
			tabelaDeSimbolos.put(((NóIdentificador) no.filho(0)).identificador(), ((NóBooleano) no.filho(1)).valor());
		} else {
			throw new RuntimeException("Não implementado.");
		}
	}
	
	private void visitar(NóComandos comandos) {
		for (NóAbstrato cmd : comandos.getFilhos()) {
			visitar((NóComando) cmd);
		}
	}
	
	private void visitar(NóComando cmd) {
		NóPosicao posicao = (NóPosicao) cmd.filho(0);
		NóExpressoes expressoes = (NóExpressoes) cmd.filho(1);

		String chave = notacaoPosicao(posicao);
		
		if (comportamentos.containsKey(chave)) { comportamentos.get(chave).add(expressoes); }
		else {
			List<NóExpressoes> exprs = new ArrayList<>();
			exprs.add(expressoes);
			comportamentos.put(chave, exprs);
		}
	}
	
	private void visitar(NóExpressoes expressoes) {
		
	}
	
	private void visitar(NóExpressao expressao) {
		for (NóAbstrato sub : expressao.getFilhos()) {
			if (sub instanceof NóCondicao) {}
		}
	}

	private List<NóExpressoes> expressoes(int linha, int coluna, Elemento elemento) {
		List<NóExpressoes> cmds = new ArrayList<>();
		cmds.addAll(comportamentos.get("* *"));
		cmds.addAll(comportamentos.get("* " + coluna));
		cmds.addAll(comportamentos.get(linha + " *" + coluna));
		cmds.addAll(comportamentos.get(elemento.name()));
		cmds.addAll(comportamentos.get(linha + " " + coluna));
		
		return cmds;
		
	}
	
	private String notacaoPosicao(NóPosicao posicao) {
		String notacao = "";
		Iterator<NóAbstrato> iterador = posicao.getFilhos().iterator();

		NóAbstrato posicaoValor = iterador.next();
		if (posicaoValor instanceof NóIdentificador || posicaoValor instanceof NóPosicaoQualquer)
			notacao = posicaoValor.getSimbolo().getLexema();
		else if (posicaoValor instanceof NóNumero) 
			notacao = ((NóNumero) posicaoValor).valor().toString();

		if (iterador.hasNext()) {
			posicaoValor = iterador.next();
			if (posicaoValor instanceof NóIdentificador || posicaoValor instanceof NóPosicaoQualquer)
				notacao = posicaoValor.getSimbolo().getLexema();
			else if (posicaoValor instanceof NóNumero) 
				notacao = ((NóNumero) posicaoValor).valor().toString();
		}

		return notacao;
	}

	public static AmbienteDeExecucao teste() {
		return new AmbienteDeExecucao(new Fase(null) {
			public int getNumeroColunas() { return 10; }
			public int getNumeroLinhas() { return 10; }
			public void ativarUrtiga() { System.out.println("urtiga ativada!"); }
			public void desativarUrtiga() { System.out.println("urtiga desativada!"); }
			public void fazerMovimento(Direcao d) { }
		});
	}
}
