package br.edu.ifsp.cmp.asw_ed2.tinkerscript.semantico;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import br.edu.ifsp.cmp.asw_ed2.tinkerscript.sintatico.arvore.ArvoreSintaticaAbstrata;
import br.edu.ifsp.cmp.asw_ed2.tinkerscript.sintatico.arvore.nos.NóAbstrato;
import br.edu.ifsp.cmp.asw_ed2.tinkerscript.sintatico.arvore.nos.NóAtribuicao;
import br.edu.ifsp.cmp.asw_ed2.tinkerscript.sintatico.arvore.nos.NóBooleano;
import br.edu.ifsp.cmp.asw_ed2.tinkerscript.sintatico.arvore.nos.NóChamadaDeFuncao;
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

public class AmbienteDeExecucao {
	private FuncoesPadrao builtin;
	private Map<String, Object> tabelaDeSimbolos;
	private Map<String, List<NóExpressoes>> comportamentos;
	private Stack<Object> pilha;
	
	public AmbienteDeExecucao(FuncoesPadrao builtin) {
		this.builtin = builtin;
		
		tabelaDeSimbolos = new HashMap<>();
		comportamentos = new HashMap<>();
		pilha = new Stack<>();
	}
	
	public void executar(ArvoreSintaticaAbstrata arvore) {
		visitar(arvore.getRaiz());
	}
	
	public void executar(int linha, int coluna, String elemento) {
		for (NóExpressoes expressoes : expressoes(linha, coluna, elemento))
			visitar(expressoes);
	}

	public Map<String, Object> getTabelaDeSimbolos() {
		return tabelaDeSimbolos;
	}
	
	public Map<String, List<NóExpressoes>> getComportamentos() {
		return comportamentos;
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
			visitar((NóNumero) no.filho(1));
			tabelaDeSimbolos.put(((NóIdentificador) no.filho(0)).identificador(), (Integer) pilha.pop());
		} else if (valor instanceof NóBooleano) {
			visitar((NóBooleano) no.filho(1));
			tabelaDeSimbolos.put(((NóIdentificador) no.filho(0)).identificador(), (Boolean) pilha.pop());
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
		for (NóAbstrato cmd : expressoes.getFilhos()) {
			visitar((NóExpressao) cmd);
		}
	}
	
	private void visitar(NóExpressao expressao) {
		for (NóAbstrato expr : expressao.getFilhos()) {
			if (expr instanceof NóCondicao) {
				//visitar((NóExprAtomo) expr.filho(0));
			} else {
				for (NóAbstrato terminal : expr.getFilhos()) {
					if (terminal instanceof NóAtribuicao) visitar((NóAtribuicao) terminal);
					else if (terminal instanceof NóChamadaDeFuncao) visitar((NóChamadaDeFuncao) terminal);
				}
			}
		}
	}

	private void visitar(NóChamadaDeFuncao terminal) {
		String nomeFuncao = terminal.filho(0).getSimbolo().getLexema();
		builtin.executar(nomeFuncao);
	}

	private void visitar(NóAtribuicao terminal) {
	}

	private void visitar(NóNumero numero) {
		pilha.push(numero.valor());
	}

	private void visitar(NóBooleano filho) {
		pilha.push(filho.valor());
	}

	private List<NóExpressoes> expressoes(int linha, int coluna, String elemento) {
		List<NóExpressoes> vazia = Arrays.asList(new NóExpressoes[]{});
		List<NóExpressoes> cmds = new ArrayList<>();
		cmds.addAll(comportamentos.getOrDefault("* *", vazia));
		cmds.addAll(comportamentos.getOrDefault("* " + coluna, vazia));
		cmds.addAll(comportamentos.getOrDefault(linha + " *" + coluna, vazia));
		cmds.addAll(comportamentos.getOrDefault(elemento, vazia));
		cmds.addAll(comportamentos.getOrDefault(linha + " " + coluna, vazia));
		
		return cmds;
		
	}
	
	private String notacaoPosicao(NóPosicao posicao) {
		String notacao = "";
		Iterator<NóAbstrato> iterador = posicao.getFilhos().iterator();

		NóAbstrato posicaoValor = iterador.next();
		if (posicaoValor instanceof NóIdentificador || posicaoValor instanceof NóPosicaoQualquer) {
			notacao = posicaoValor.getSimbolo().getLexema();
		} else if (posicaoValor instanceof NóNumero) {
			notacao = ((NóNumero) posicaoValor).valor().toString();
		}

		if (iterador.hasNext()) {
			posicaoValor = iterador.next();
			if (posicaoValor instanceof NóIdentificador || posicaoValor instanceof NóPosicaoQualquer)
				notacao += " " + posicaoValor.getSimbolo().getLexema();
			else if (posicaoValor instanceof NóNumero) 
				notacao += " " + ((NóNumero) posicaoValor).valor().toString();
		}

		return notacao;
	}
}
