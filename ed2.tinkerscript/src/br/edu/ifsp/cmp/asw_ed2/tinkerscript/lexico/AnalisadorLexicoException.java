package br.edu.ifsp.cmp.asw_ed2.tinkerscript.lexico;

public class AnalisadorLexicoException extends Exception {
	private static final long serialVersionUID = -1027886543236653627L;
	
	private final static String formatoMensagemErro = "Erro ao ler código-fonte (em %d:%d): %s\n"; 

	public AnalisadorLexicoException(int linha, int coluna, String mensagem) {
		super(String.format(formatoMensagemErro, linha, coluna, mensagem));
	}
	
	public AnalisadorLexicoException(int linha, int coluna, Throwable causa) {
		this(linha, coluna, causa.getMessage());
	}
}
