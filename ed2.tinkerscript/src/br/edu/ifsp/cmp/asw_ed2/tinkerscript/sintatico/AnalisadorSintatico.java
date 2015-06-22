package br.edu.ifsp.cmp.asw_ed2.tinkerscript.sintatico;

import static br.edu.ifsp.cmp.asw_ed2.tinkerscript.lexico.SimboloLexicoCategoria.*;

import java.util.Iterator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import br.edu.ifsp.cmp.asw_ed2.tinkerscript.lexico.AnalisadorLexico;
import br.edu.ifsp.cmp.asw_ed2.tinkerscript.lexico.SimboloLexico;
import br.edu.ifsp.cmp.asw_ed2.tinkerscript.lexico.SimboloLexicoCategoria;


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
		return aceita(categoria, new SimboloLexicoCategoria[] {});
	}
	
	private boolean aceitaComEspacos(SimboloLexicoCategoria categoria) {
		return aceita(categoria, ESPACO);
	}
	
	private boolean aceita(SimboloLexicoCategoria categoria, SimboloLexicoCategoria... ignorar) {
		if (ignorar.length > 0)	{
			while (atual != null && Stream.of(ignorar).anyMatch(atual.getCategoria()::equals)) {
				System.out.print("[" + Stream.of(ignorar).map(Enum::name).collect(Collectors.joining("/")) + "] ");
				proximoSimbolo();
			}
		}
		
		if (atual == null) { return false; }
		if (atual.getCategoria().equals(categoria)) { System.out.print(categoria.name() + (categoria == PULO_DE_LINHA ? "\n" : " ")); proximoSimbolo(); return true; }
		return false;		
	}
	
	private void esperado(SimboloLexicoCategoria categoria) throws ErroDeSintaxeException {
		esperado(categoria, new SimboloLexicoCategoria[] {});
	}
	
	private void esperadoComEspacos(SimboloLexicoCategoria categoria) throws ErroDeSintaxeException {
		esperado(categoria, ESPACO);
	}
	
	private void esperado(SimboloLexicoCategoria categoria, SimboloLexicoCategoria... ignorar) throws ErroDeSintaxeException {
		if (aceita(categoria, ignorar)) return;
		throw new ErroDeSintaxeException(atual(), "Esperava " + categoria.name());
	}

	// Gramatica
	private void programa() throws ErroDeSintaxeException {
		System.out.print("programa( ");
		esperado(PROGRAMA_INICIO, ESPACO, PULO_DE_LINHA);
		esperado(DOIS_PONTOS);
		esperadoComEspacos(PULO_DE_LINHA);
		declaracoes();
		comandos();
		esperado(PROGRAMA_FIM);
		System.out.print("\n)\n");
	}
	
	private void declaracoes() throws ErroDeSintaxeException {
		System.out.print("declaracoes( ");
		declaracao();
		if (aceita(PULO_DE_LINHA));
		else declaracoes();
		System.out.print(") ");
	}
	
	private void declaracao() throws ErroDeSintaxeException {
		System.out.print("declaracao( ");
		if (aceitaComEspacos(IDENTIFICADOR)) {
			esperadoComEspacos(IGUAL);
			if (!aceitaComEspacos(NUMERO)) esperadoComEspacos(IDENTIFICADOR);
			esperadoComEspacos(PULO_DE_LINHA);
		} else throw new ErroDeSintaxeException(atual());
		System.out.print(") ");
	}
	
	private void comandos() throws ErroDeSintaxeException {
		System.out.print("comandos( ");
		posicao();
		esperadoComEspacos(DOIS_PONTOS);
		expressoes();
		if (aceita(PULO_DE_LINHA)) comandos();
		System.out.print(") ");
	}
	
	private void posicao() throws ErroDeSintaxeException {
		System.out.print("posicao( ");
		if (aceita(IDENTIFICADOR, ESPACO, PULO_DE_LINHA)) ;
		else { posicaoValor(); posicaoValor(); }
		System.out.print(") ");
	}
	
	private void posicaoValor() throws ErroDeSintaxeException {
		if (aceita(ASTERISCO, ESPACO, PULO_DE_LINHA) || aceita(NUMERO, ESPACO, PULO_DE_LINHA)) ;
		else throw new ErroDeSintaxeException(atual());
	}
	
	private void expressoes() throws ErroDeSintaxeException {
		System.out.print("expressoes( ");
		esperadoComEspacos(NUMERO);
		System.out.print(") ");
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
