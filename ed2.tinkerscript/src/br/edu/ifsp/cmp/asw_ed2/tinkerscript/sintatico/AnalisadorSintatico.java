package br.edu.ifsp.cmp.asw_ed2.tinkerscript.sintatico;

import static br.edu.ifsp.cmp.asw_ed2.tinkerscript.lexico.SimboloLexicoCategoria.*;

import java.util.Iterator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import br.edu.ifsp.cmp.asw_ed2.tinkerscript.lexico.AnalisadorLexico;
import br.edu.ifsp.cmp.asw_ed2.tinkerscript.lexico.SimboloLexico;
import br.edu.ifsp.cmp.asw_ed2.tinkerscript.lexico.SimboloLexicoCategoria;
import br.edu.ifsp.cmp.asw_ed2.tinkerscript.sintatico.arvore.ArvoreSintaticaAbstrata;
import br.edu.ifsp.cmp.asw_ed2.tinkerscript.sintatico.arvore.nos.*;

public class AnalisadorSintatico {
	private AnalisadorLexico lexico;
	private Iterator<SimboloLexico> lexicoIterador;
	private SimboloLexico atual;
	
	public AnalisadorSintatico(AnalisadorLexico lexico) {
		this.lexico = lexico;
	}
	
	public ArvoreSintaticaAbstrata analisar() throws ErroDeSintaxeException {
		lexicoIterador = lexico.iterator();
		proximoSimbolo();
		return new ArvoreSintaticaAbstrata(programa());
	}
	
	private boolean aceita(SimboloLexicoCategoria categoria, NóAbstrato nó) {
		return aceita(categoria, nó, new SimboloLexicoCategoria[] {});
	}
	
	private boolean aceitaComEspacos(SimboloLexicoCategoria categoria, NóAbstrato nó) {
		return aceita(categoria, nó, ESPACO);
	}
	
	private boolean aceita(SimboloLexicoCategoria categoria, NóAbstrato nó, SimboloLexicoCategoria... ignorar) {
		if (ignorar.length > 0)	{
			while (atual != null && Stream.of(ignorar).anyMatch(atual.getCategoria()::equals)) {
				//System.out.print("[" + Stream.of(ignorar).map(Enum::name).collect(Collectors.joining("/")) + "] ");
				proximoSimbolo();
			}
		}
		
		if (atual == null) { return false; }
		if (atual.getCategoria().equals(categoria)) {
			if (nó != null) nó.adicionar(criarNóDe(atual));
			//System.out.print(categoria.name() + (categoria == PULO_DE_LINHA ? "\n" : " "));
			proximoSimbolo();
			return true;
		}
		return false;		
	}
	

	private void esperado(SimboloLexicoCategoria categoria, NóAbstrato nó) throws ErroDeSintaxeException {
		esperado(categoria, nó, new SimboloLexicoCategoria[] {});
	}
	
	private void esperadoComEspacos(SimboloLexicoCategoria categoria, NóAbstrato nó) throws ErroDeSintaxeException {
		esperado(categoria, nó, ESPACO);
	}
	
	private void esperado(SimboloLexicoCategoria categoria, NóAbstrato nó, SimboloLexicoCategoria... ignorar) throws ErroDeSintaxeException {
		if (aceita(categoria, nó, ignorar)) return;
		throw new ErroDeSintaxeException(atual(), "Esperava " + categoria.name());
	}

	private NóAbstrato criarNóDe(SimboloLexico simbolo) {
		switch (simbolo.getCategoria()) {
		case NUMERO:
			return new NóNumero(simbolo);
		case ASTERISCO:
		case MENOR_QUE: 
		case MAIOR_QUE:
		case IGUAL:
			return new NóOperador(simbolo);
		case E:
		case OU:
			return new NóOperadorLogico(simbolo);
		case VERDADEIRO:
		case FALSO:
			return new NóBooleano(simbolo);
		case SE_INICIO:
			return new NóCondicaoSe();
		case NOTACAO_STRING:
			return new NóString(simbolo);
		case IDENTIFICADOR:
			return new NóIdentificador(simbolo);
		default: return null;
		}
	}
	// Gramatica
	private NóPrograma programa() throws ErroDeSintaxeException {
		NóPrograma nó = new NóPrograma();
		
		//System.out.print("programa( ");
		esperado(PROGRAMA_INICIO, null, ESPACO, PULO_DE_LINHA);
		esperado(DOIS_PONTOS, null);
		esperadoComEspacos(PULO_DE_LINHA, null);
		nó.adicionar(declaracoes(null));
		nó.adicionar(comandos(null));
		esperado(PROGRAMA_FIM, null);
		//System.out.print("\n)\n");
		
		return nó;
	}
	
	private NóDeclaracoes declaracoes(NóDeclaracoes raiz) throws ErroDeSintaxeException {
		if (raiz == null) raiz = new NóDeclaracoes();
		
		//System.out.print("declaracoes( ");
		raiz.adicionar(declaracao());
		if (aceita(PULO_DE_LINHA, null));
		else return declaracoes(raiz);
		//System.out.print(") ");
		
		return raiz;
	}
	
	private NóDeclaracao declaracao() throws ErroDeSintaxeException {
		NóDeclaracao nó = new NóDeclaracao();
		
		//System.out.print("declaracao( ");
		if (aceitaComEspacos(IDENTIFICADOR, nó)) {
			esperadoComEspacos(IGUAL, null);
			if (aceitaComEspacos(NUMERO, nó) ||
					aceitaComEspacos(VERDADEIRO, nó) || aceitaComEspacos(FALSO, nó) ||
					aceitaComEspacos(IDENTIFICADOR, nó)) ;
			else throw new ErroDeSintaxeException(atual());
			esperadoComEspacos(PULO_DE_LINHA, null);
		} else throw new ErroDeSintaxeException(atual());
		//System.out.print(") ");
		
		return nó;
	}
	
	private NóComandos comandos(NóComandos raiz) throws ErroDeSintaxeException {
		if (raiz == null) raiz = new NóComandos();
		
		
		//System.out.print("comandos( ");
		NóPosicao posicaoNó = posicao();
		esperadoComEspacos(DOIS_PONTOS, null);
		NóExpressao expressaoNó = expressao();
		
		raiz.adicionar(new NóComando(posicaoNó, expressaoNó));
		
		if (aceita(PULO_DE_LINHA, null)) return comandos(raiz);
		//System.out.print(") ");
		
		return raiz;
	}
	
	private NóPosicao posicao() throws ErroDeSintaxeException {
		NóPosicao nó = new NóPosicao();
		
		//System.out.print("posicao( ");
		if (aceita(IDENTIFICADOR, nó, ESPACO, PULO_DE_LINHA)) ;
		else { posicaoValor(nó); posicaoValor(nó); }
		//System.out.print(") ");
		
		return nó;
	}
	
	private void posicaoValor(NóPosicao raiz) throws ErroDeSintaxeException {
		if (aceita(ASTERISCO, null, ESPACO, PULO_DE_LINHA))
			raiz.adicionar(new NóPosicaoQualquer());
		else if (aceita(NUMERO, raiz, ESPACO, PULO_DE_LINHA)) ;
		else throw new ErroDeSintaxeException(atual());
	}
	
	private NóExpressao expressao() throws ErroDeSintaxeException {
		NóExpressao nó = new NóExpressao();
		
		//System.out.print("expressoes( ");
		esperadoComEspacos(NUMERO, nó);
		//System.out.print(") ");
		
		return nó;
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
