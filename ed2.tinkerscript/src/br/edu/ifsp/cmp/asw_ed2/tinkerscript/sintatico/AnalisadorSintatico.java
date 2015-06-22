package br.edu.ifsp.cmp.asw_ed2.tinkerscript.sintatico;

import java.util.Iterator;

import br.edu.ifsp.cmp.asw_ed2.tinkerscript.lexico.AnalisadorLexico;
import br.edu.ifsp.cmp.asw_ed2.tinkerscript.lexico.SimboloLexico;
import br.edu.ifsp.cmp.asw_ed2.tinkerscript.lexico.SimboloLexicoCategoria;
import static br.edu.ifsp.cmp.asw_ed2.tinkerscript.lexico.SimboloLexicoCategoria.*;


public class AnalisadorSintatico {
	private AnalisadorLexico lexico;
	private Iterator<SimboloLexico> lexicoIterador;
	private SimboloLexico atual;
	
	public AnalisadorSintatico(AnalisadorLexico lexico) {
		this.lexico = lexico;
	}
	
	public void analisar() throws ErroDeSintaxeException {
		lexicoIterador = lexico.iterator();
		proximoSimbolo();
		programa();
	}
	
	private boolean aceita(SimboloLexicoCategoria categoria) {
		while (atual != null && atual.getCategoria().equals(ESPACO)) { proximoSimbolo(); }
		
		if (atual == null) { return false; }
		if (atual.getCategoria().equals(categoria)) { proximoSimbolo(); return true; }
		return false;
	}
	
	private void esperado(SimboloLexicoCategoria categoria) throws ErroDeSintaxeException {
		if (aceita(categoria)) return;
		throw new ErroDeSintaxeException(atual());
	}

	// Gramatica
	private void programa() throws ErroDeSintaxeException {
		esperado(PROGRAMA_INICIO);
		esperado(DOIS_PONTOS);
		esperado(PULO_DE_LINHA);
		declaracoes();
		comandos();
		esperado(PROGRAMA_FIM);
	}
	
	private void declaracoes() throws ErroDeSintaxeException {
		declaracao();
		if (aceita(PULO_DE_LINHA));
		else declaracoes();
	}
	
	private void declaracao() throws ErroDeSintaxeException {
		if (aceita(IDENTIFICADOR)) {
			esperado(IGUAL);
			if (!aceita(NUMERO)) esperado(IDENTIFICADOR);
			esperado(PULO_DE_LINHA);
		} else
			if (aceita(PULO_DE_LINHA));
			else throw new ErroDeSintaxeException(atual());
	}
	
	private void comandos() throws ErroDeSintaxeException {
		posicao();
		esperado(DOIS_PONTOS);
		expressoes();
		esperado(PULO_DE_LINHA);
		if (aceita(PROGRAMA_FIM)) ;
		else comandos();
	}
	
	private void posicao() throws ErroDeSintaxeException {
		if (aceita(IDENTIFICADOR)) ;
		else { posicaoValor(); posicaoValor(); }
	}
	
	private void posicaoValor() throws ErroDeSintaxeException {
		if (aceita(ASTERISCO) || aceita(NUMERO)) ;
		else throw new ErroDeSintaxeException(atual());
	}
	
	private void expressoes() throws ErroDeSintaxeException {
		esperado(NUMERO);
	}
	
	private void proximoSimbolo() {
		if (!lexicoIterador.hasNext()) atual = null;
		else                  		   atual = lexicoIterador.next();
	}
	
	private SimboloLexico atual() throws ErroDeSintaxeException {
		if (atual == null) throw new ErroDeSintaxeException("EOF Inesperado");
		return atual;
	}
}
