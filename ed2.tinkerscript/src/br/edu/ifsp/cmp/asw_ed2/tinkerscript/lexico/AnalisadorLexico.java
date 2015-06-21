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
	private boolean puloDeLinhaLido;
	
	public AnalisadorLexico executar(String codigoFonte) throws FileNotFoundException, UnsupportedEncodingException {
		return new AnalisadorLexico(new InputStreamReader(new ByteArrayInputStream(codigoFonte.getBytes())));
	}
	
	public AnalisadorLexico(Reader entrada) {
		this.entrada = entrada;
		simbolos = new ArrayList<SimboloLexico>();
		lexema = new StringBuilder();
		
		linha = 1;
		coluna = 0;
		puloDeLinhaLido = false;
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
	
	private void _compilar(char caracter) throws AnalisadorLexicoException {
		if (caracter == (char) -1) { gerarSimboloLexico(); return; } // Reached EOF
		
		if (SimboloLexicoCategoria.ehSeparador(caracter)) {
			gerarSimboloLexico();
			
			// avoid creating multiple tokens for consecutive white-spaces
			if (SimboloLexicoCategoria.ESPACO.casaCom(String.valueOf(caracter))) {
				StringBuilder espacosBuilder = new StringBuilder();
				do { espacosBuilder.append(caracter); }
				while (SimboloLexicoCategoria.ESPACO.casaCom(caracter = lerCaracter()));
				simbolos.add(gerarSimboloLexicoDoLexema(espacosBuilder.toString()));
			} else {
				coluna++;
				simbolos.add(gerarSimboloLexicoDoLexema(caracter));
				caracter = lerCaracter();
			}
		} else {
			lexema.append(caracter);
			caracter = lerCaracter();
		}
		
		_compilar(caracter);
	}
	
	private void gerarSimboloLexico() throws AnalisadorLexicoException {
		if (lexema.length() > 0)
			simbolos.add(gerarSimboloLexicoDoLexema(lexema.toString()));
		lexema.delete(0, lexema.length());
	}
	
	private char lerCaracter() throws AnalisadorLexicoException {
		if (puloDeLinhaLido) { linha += 1; coluna = 0; puloDeLinhaLido = false; }
		
		try {
			char character = (char) entrada.read();
			coluna++;
			if (character == '\n') puloDeLinhaLido = true;
			return character;
		} catch (IOException e) {
			throw new AnalisadorLexicoException(linha, coluna, e);
		}
	}
	
	private SimboloLexico gerarSimboloLexicoDoLexema(String lexema) throws AnalisadorLexicoException {
		SimboloLexicoCategoria categoria = SimboloLexicoCategoria.buscarCasamentoCom(lexema);
		int lexemaInicio = coluna - lexema.length();
		
		if (categoria == null)
			throw new AnalisadorLexicoException(linha, lexemaInicio, "Simbolo inesperado: \"" + lexema + "\"");
		
		return new SimboloLexico(lexema, categoria, new Posicao(linha, lexemaInicio));
	}
	
	private SimboloLexico gerarSimboloLexicoDoLexema(char simbolo) throws AnalisadorLexicoException {
		return gerarSimboloLexicoDoLexema(Character.toString(simbolo));
	}
}
