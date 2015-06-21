package br.edu.ifsp.cmp.asw_ed2.tinkerscript.lexico;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class AnalisadorLexico implements Iterable<SimboloLexico> {
	private Reader entrada;
	List<SimboloLexico> simbolos;
	StringBuilder lexema;
	private int linha, coluna;
	private boolean novaLinha;
	
	public AnalisadorLexico executar(String codigoFonte) throws FileNotFoundException, UnsupportedEncodingException {
		return new AnalisadorLexico(new InputStreamReader(new ByteArrayInputStream(codigoFonte.getBytes())));
	}
	
	public AnalisadorLexico(Reader entrada) {
		this.entrada = entrada;
		simbolos = new ArrayList<SimboloLexico>();
		lexema = new StringBuilder();
		
		linha = 1;
		coluna = 0;
		novaLinha = false;
	}
	
	public AnalisadorLexico(File arquivoFonte, String charset) throws FileNotFoundException, UnsupportedEncodingException {
		this(new InputStreamReader(new FileInputStream(arquivoFonte), charset));
	}
	
	public AnalisadorLexico(String caminhoArquivoFonte, String charset) throws FileNotFoundException, UnsupportedEncodingException {
		this(new InputStreamReader(new FileInputStream(new File(caminhoArquivoFonte)), charset));
	}
	
	public AnalisadorLexico(String caminhoArquivoFonte) throws FileNotFoundException, UnsupportedEncodingException {
		this(caminhoArquivoFonte, "utf-8");
	}
	
	@Override
	public Iterator<SimboloLexico> iterator() {
		if (simbolos == null) throw new RuntimeException("É necessário analisar antes de iterar nos simbolos léxicos.");
		return simbolos.iterator(); 
	}
	
	public void analisar() throws AnalisadorLexicoException {
		_compilar(lerCaracter());
	}
	
	private char lerCaracter() throws AnalisadorLexicoException {
		if (novaLinha) { linha += 1; coluna = 0; novaLinha = false; }
		
		try {
			char character = (char) entrada.read();
			coluna++;
			if (character == '\n') novaLinha = true;
			return character;
		} catch (IOException e) {
			throw new AnalisadorLexicoException(linha, coluna, e);
		}
	}
	
	private void _compilar(char caractere) throws AnalisadorLexicoException {
		// Fim do arquivo (EOF)
		if (caractere == (char) -1) {
			salvarSimboloLexico();
			salvarSimboloLexico(caractere);
			return;
		}
		
		if (SimboloLexicoCategoria.ehSeparador(caractere)) {
			salvarSimboloLexico();
			
			// avoid creating multiple tokens for consecutive white-spaces
			if (SimboloLexicoCategoria.ESPACO.casaCom(String.valueOf(caractere))) {
				StringBuilder espacos = new StringBuilder();
				do { espacos.append(caractere); }
				while (SimboloLexicoCategoria.ESPACO.casaCom(caractere = lerCaracter()));
				salvarSimboloLexico(espacos);
			} else {
				coluna++;
				salvarSimboloLexico(caractere);
				caractere = lerCaracter();
			}
		} else {
			lexema.append(caractere);
			caractere = lerCaracter();
		}
		
		_compilar(caractere);
	}
	
	private void salvarSimboloLexico() throws AnalisadorLexicoException {
		salvarSimboloLexico(lexema);
	}

	private void salvarSimboloLexico(StringBuilder lexema) throws AnalisadorLexicoException {
		if (lexema.length() > 0) salvarSimboloLexico(lexema.toString());
		lexema.delete(0, lexema.length());
	}
	
	private void salvarSimboloLexico(String lexema) throws AnalisadorLexicoException {
		simbolos.add(gerarSimboloLexicoDoLexema(lexema.toString()));
	}
	
	private void salvarSimboloLexico(char simbolo) throws AnalisadorLexicoException {
		simbolos.add(gerarSimboloLexicoDoLexema(simbolo));
	}
	
	private SimboloLexico gerarSimboloLexicoDoLexema(String lexema) throws AnalisadorLexicoException {
		SimboloLexicoCategoria categoria = SimboloLexicoCategoria.buscarCasamentoCom(lexema);
		int lexemaInicio = coluna - lexema.length();
		
		if (categoria == null)
			throw new AnalisadorLexicoException(linha, lexemaInicio, "Simbolo inesperado: \"" + lexema + "\"");
		
		return new SimboloLexico(lexema, categoria, new Posicao(linha, lexemaInicio));
	}
	
	private SimboloLexico gerarSimboloLexicoDoLexema(char caractere) throws AnalisadorLexicoException {
		String simbolo;
		
		if (caractere == ((char) -1)) simbolo = ""; // EOF
		else 						  simbolo = Character.toString(caractere);
		return gerarSimboloLexicoDoLexema(simbolo);
	}
}
