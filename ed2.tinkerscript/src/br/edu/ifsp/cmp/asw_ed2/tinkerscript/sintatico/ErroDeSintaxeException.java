package br.edu.ifsp.cmp.asw_ed2.tinkerscript.sintatico;

import br.edu.ifsp.cmp.asw_ed2.tinkerscript.lexico.SimboloLexico;

public class ErroDeSintaxeException extends Exception {
	private static final long serialVersionUID = 2871102525129311223L;
	
	private final static String mensagemGenerica = "Erro de sintaxe: %s\n";
	private final static String mensagemSimboloInesperado = "Erro de sintaxe: Simbolo inesperado: %s\n";
	
	public ErroDeSintaxeException(String mensagem) {
		super(String.format(mensagemGenerica, mensagem));
	}

	public ErroDeSintaxeException(SimboloLexico simbolo) {
		super(String.format(mensagemSimboloInesperado, simbolo.toString()));
	}

	public ErroDeSintaxeException(SimboloLexico simbolo, String mensagem) {
		super(String.format(mensagemSimboloInesperado, simbolo.toString() + ": " + mensagem));
	}
}
