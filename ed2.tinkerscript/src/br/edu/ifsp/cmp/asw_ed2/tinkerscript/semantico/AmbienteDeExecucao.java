package br.edu.ifsp.cmp.asw_ed2.tinkerscript.semantico;

import java.util.HashMap;
import java.util.Map;

import com.github.awvalenti.arquiteturadesoftware.rpg1.versao5.arquiteturadefinida.logicajogo.Direcao;
import com.github.awvalenti.arquiteturadesoftware.rpg1.versao5.arquiteturadefinida.logicajogo.Fase;

import br.edu.ifsp.cmp.asw_ed2.tinkerscript.sintatico.arvore.ArvoreSintaticaAbstrata;
import br.edu.ifsp.cmp.asw_ed2.tinkerscript.sintatico.arvore.nos.NóAbstrato;
import br.edu.ifsp.cmp.asw_ed2.tinkerscript.sintatico.arvore.nos.NóBooleano;
import br.edu.ifsp.cmp.asw_ed2.tinkerscript.sintatico.arvore.nos.NóComando;
import br.edu.ifsp.cmp.asw_ed2.tinkerscript.sintatico.arvore.nos.NóComandos;
import br.edu.ifsp.cmp.asw_ed2.tinkerscript.sintatico.arvore.nos.NóDeclaracao;
import br.edu.ifsp.cmp.asw_ed2.tinkerscript.sintatico.arvore.nos.NóDeclaracoes;
import br.edu.ifsp.cmp.asw_ed2.tinkerscript.sintatico.arvore.nos.NóExpressoes;
import br.edu.ifsp.cmp.asw_ed2.tinkerscript.sintatico.arvore.nos.NóIdentificador;
import br.edu.ifsp.cmp.asw_ed2.tinkerscript.sintatico.arvore.nos.NóNumero;
import br.edu.ifsp.cmp.asw_ed2.tinkerscript.sintatico.arvore.nos.NóPosicao;
import br.edu.ifsp.cmp.asw_ed2.tinkerscript.sintatico.arvore.nos.NóPrograma;

public class AmbienteDeExecucao {
	private Map<String, Object> tabelaDeSimbolos;
	
	public AmbienteDeExecucao(Fase faseAtual) {
		tabelaDeSimbolos = new HashMap<>();
	}
	
	public void executar(ArvoreSintaticaAbstrata arvore) {
		visitar(arvore.getRaiz());
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
		
		System.out.println(expressoes);
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
