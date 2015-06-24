package br.edu.ifsp.cmp.asw_ed2.tinkerscript.sintatico;

import static br.edu.ifsp.cmp.asw_ed2.tinkerscript.lexico.SimboloLexicoCategoria.*;

import java.util.ListIterator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import br.edu.ifsp.cmp.asw_ed2.tinkerscript.lexico.AnalisadorLexico;
import br.edu.ifsp.cmp.asw_ed2.tinkerscript.lexico.SimboloLexico;
import br.edu.ifsp.cmp.asw_ed2.tinkerscript.lexico.SimboloLexicoCategoria;
import br.edu.ifsp.cmp.asw_ed2.tinkerscript.sintatico.arvore.ArvoreSintaticaAbstrata;
import br.edu.ifsp.cmp.asw_ed2.tinkerscript.sintatico.arvore.nos.*;

public class AnalisadorSintatico {
	private static final boolean DEBUG = true;
	private static final boolean EXIBIR_IGNORADO = false;
	
	private AnalisadorLexico lexico;
	private ListIterator<SimboloLexico> lexicoIterador;
	private SimboloLexico lido;
	private SimboloLexico atual;
	private Logger logger;
	
	public AnalisadorSintatico(AnalisadorLexico lexico) {
		this.lexico = lexico;
		logger = new Logger();
	}
	
	public ArvoreSintaticaAbstrata analisar() throws ErroDeSintaxeException {
		lexicoIterador = lexico.iterator();
		proximoSimbolo();
		return new ArvoreSintaticaAbstrata(programa());
	}
	
	/**
	 * Gramática
	 * =========
	 * 
	 * programa:
	 * 		PROGRAMA_INICIO DOIS_PONTOS PULO_DE_LINHA declaracoes comandos PROGRAMA_FIM .
	 * 
	 * declaracoes:
	 * 		  declaracao
	 * 		| declaracao PULO_DE_LINHA declaracoes .
	 * 
	 * declaracao:
	 * 		IDENTIFICADOR IGUAL (NUMERO | VERDADEIRO | FALSO | IDENTIFICADOR) PULO_DE_LINHA .
	 * 
	 * comandos:
	 * 		  posicao DOIS_PONTOS expressoes
	 * 		| posicao DOIS_PONTOS expressoes PULO_DE_LINHA comandos .
	 * 
	 * posicao:
	 * 		  IDENTIFICADOR
	 * 		| posicaoValor posicaoValor .
	 * 
	 * posicaoValor:
	 * 		  ASTERISCO
	 * 		| NUMERO .
	 * 
	 * expressoes:
	 * 		  expressao VIRGULA expressoes
	 * 		| expressao .
	 * 
	 * expressao:
	 * 		  condicao
	 * 		| atribuicao
	 * 		| expr_atomo
	 * 
	 * expr_atomo:
	 * 		  chamada_funcao
	 * 		| operacao_aritmetica .
	 * 
	 * condicao:
	 * 		SE_INICIO expr_atomo SE_CONDICAO_FIM expressao .
	 * 
	 * chamada_funcao:
	 * 		  IDENTIFICADOR ABRE_PARENT FECHA_PARENT
	 * 		| IDENTIFICADOR ABRE_PARENT expr_atomo FECHA_PARENT .
	 * 
	 * atribuicao:
	 * 		IDENTIFICADOR (MAIS IGUAL | MENOS IGUAL | DIVISAO IGUAL | ASTERISCO IGUAL | IGUAL) expr_atomo .
	 * 
	 * operacao_aritmetica:
	 * 		  operacao_aritmetica (MAIS | MENOS | DIVISAO | ASTERISCO) operacao_aritmetica
	 * 		| MENOS operacao_aritmetica
	 * 		| ABRE_PARENT operacao_aritmetica FECHA_PARENT
	 * 		| chamada_funcao
	 * 		| IDENTIFICADOR
	 * 		| NUMERO .
	 */
	
	private NóPrograma programa() throws ErroDeSintaxeException {
		NóPrograma nó = new NóPrograma();
		logger.debug("\nprograma:"); logger.aninhar();
		
		esperado(PROGRAMA_INICIO, null, ESPACO, PULO_DE_LINHA);
		esperado(DOIS_PONTOS, null);
		esperadoComEspacos(PULO_DE_LINHA, null);
		nó.adicionar(declaracoes(null));
		nó.adicionar(comandos(null));
		esperado(PROGRAMA_FIM, null);
		
		return nó;
	}
	
	private NóDeclaracoes declaracoes(NóDeclaracoes raiz) throws ErroDeSintaxeException {
		if (raiz == null) raiz = new NóDeclaracoes();
		logger.debug("declaracoes:");
		logger.aninhar();
		
		raiz.adicionar(declaracao());
		if (aceita(PULO_DE_LINHA, null));
		else { logger.desaninhar(); return declaracoes(raiz); }
		
		logger.desaninhar();
		return raiz;
	}
	
	private NóDeclaracao declaracao() throws ErroDeSintaxeException {
		NóDeclaracao nó = new NóDeclaracao();
		logger.debug("declaracao:");  logger.aninhar();
		
		if (aceitaComEspacos(IDENTIFICADOR, nó)) {
			esperadoComEspacos(IGUAL, null);
			if (aceitaComEspacos(NUMERO, nó) ||
					aceitaComEspacos(VERDADEIRO, nó) || aceitaComEspacos(FALSO, nó) ||
					aceitaComEspacos(IDENTIFICADOR, nó)) ;
			else throw new ErroDeSintaxeException(atual());
			esperadoComEspacos(PULO_DE_LINHA, null);
		} else throw new ErroDeSintaxeException(atual());
		
		logger.desaninhar();
		return nó;
	}
	
	private NóComandos comandos(NóComandos raiz) throws ErroDeSintaxeException {
		if (raiz == null) raiz = new NóComandos();
		logger.debug("comandos:");  logger.aninhar();
		
		NóPosicao posicaoNó = posicao();
		esperadoComEspacos(DOIS_PONTOS, null);
		NóExpressoes expressoesNó = expressoes(null);
		
		raiz.adicionar(new NóComando(posicaoNó, expressoesNó));
		
		if (aceita(PULO_DE_LINHA, null)) { logger.desaninhar(); return comandos(raiz); }
		
		logger.desaninhar();
		return raiz;
	}

	private NóPosicao posicao() throws ErroDeSintaxeException {
		NóPosicao nó = new NóPosicao();
		logger.debug("posicao:");  logger.aninhar();
		
		if (aceita(IDENTIFICADOR, nó, ESPACO, PULO_DE_LINHA)) ;
		else { posicaoValor(nó); posicaoValor(nó); }
		
		logger.desaninhar();
		return nó;
	}
	
	private void posicaoValor(NóPosicao raiz) throws ErroDeSintaxeException {
		if (aceita(ASTERISCO, null, ESPACO, PULO_DE_LINHA))
			raiz.adicionar(new NóPosicaoQualquer(lido));
		else if (aceita(NUMERO, raiz, ESPACO, PULO_DE_LINHA)) ;
		else throw new ErroDeSintaxeException(atual());
	}
	
	private NóExpressoes expressoes(NóExpressoes raiz) throws ErroDeSintaxeException {
		if (raiz == null) raiz = new NóExpressoes();
		logger.debug("expressoes:");  logger.aninhar();
		
		raiz.adicionar(expressao());
		if (aceitaComEspacos(VIRGULA, null)) expressoes(raiz);
		
		logger.desaninhar();
		return raiz;
	}
	
	private NóExpressao expressao() throws ErroDeSintaxeException {
		NóExpressao nó = new NóExpressao();
		logger.debug("expressao:");  logger.aninhar();

		NóAbstrato resultado; 
		if ((resultado = condicao()) != null) nó.adicionar(resultado);
		else if ((resultado = exprAtomo(false)) != null) nó.adicionar(resultado);
		//else throw new RuntimeErrorException("???");
		
		logger.desaninhar();
		return nó;
	}
	
	private NóCondicao condicao() throws ErroDeSintaxeException {
		if (aceitaComEspacos(SE_INICIO, null)) {
			NóCondicao nó = new NóCondicao();
			logger.debug("condicao:");  logger.aninhar();
			
			nó.setCondicao(exprAtomo(true));
			esperadoComEspacos(SE_CONDICAO_FIM, null);
			nó.setCorpo(expressao());
			
			logger.desaninhar();
			return nó;
		} else return null;
	}
	
	private NóExprAtomo exprAtomo(boolean requerido) throws ErroDeSintaxeException {
		NóExprAtomo nó = new NóExprAtomo();
		logger.debug("exprAtomo(" + requerido + "): ");  logger.aninhar();
		
		int marca = marcar();
		if (aceitaComEspacos(IDENTIFICADOR, null)) {
			retornar(marca);
			
			NóChamadaDeFuncao func = new NóChamadaDeFuncao();
			esperadoComEspacos(IDENTIFICADOR, func);
			if (aceita(ABRE_PARENT, null)) {
				func.adicionar(exprAtomo(false));
				esperadoComEspacos(FECHA_PARENT, null);
				nó.adicionar(func);
			} else {
				retornar(marca);

				NóAtribuicao atribuicao = new NóAtribuicao();
				esperadoComEspacos(IDENTIFICADOR, atribuicao);
				if (aceitaComEspacos(MAIS, atribuicao) ||
						aceitaComEspacos(MENOS, atribuicao) ||
						aceitaComEspacos(DIVISAO, atribuicao) ||
						aceitaComEspacos(ASTERISCO, atribuicao)) {
					if (aceita(IGUAL, atribuicao)) {
						atribuicao.adicionar(exprAtomo(true));
						nó.adicionar(atribuicao);
					} 
				} else if (aceitaComEspacos(IGUAL, atribuicao)) {
					atribuicao.adicionar(exprAtomo(true));
					nó.adicionar(atribuicao);
				} else {
					retornar(marca);
					
					NóOperacao op = new NóOperacao();
					esperadoComEspacos(IDENTIFICADOR, op);
					if (aceitaComEspacos(MAIS, op) ||
							aceitaComEspacos(MENOS, op) |
							aceitaComEspacos(ASTERISCO, op) ||
							aceitaComEspacos(DIVISAO, op) ||
							aceitaComEspacos(MAIOR_QUE, op) ||
							aceitaComEspacos(MENOR_QUE, op) ||
							(aceitaComEspacos(IGUAL, op) && aceita(IGUAL, op))) {
						op.adicionar(exprAtomo(true));
						nó.adicionar(op);
		
					} else {
						retornar(marca);
						esperadoComEspacos(IDENTIFICADOR, nó);
					}
				}
			}
		} else {
			if (aceita(NOTACAO_STRING, null)) {
				NóString string = new NóString();
				SimboloLexico parte;
				while ((parte = atual()).getCategoria() != NOTACAO_STRING) { string.concatenar(parte); proximoSimbolo(); }
				esperado(NOTACAO_STRING, null);
				nó.adicionar(string);
			} else if (aceita(NUMERO, nó)) {
				NóOperacao op = new NóOperacao();
				if (aceitaComEspacos(MAIS, op) ||
						aceitaComEspacos(MENOS, op) |
						aceitaComEspacos(ASTERISCO, op) ||
						aceitaComEspacos(DIVISAO, op) ||
						aceitaComEspacos(MAIOR_QUE, op) ||
						aceitaComEspacos(MENOR_QUE, op) ||
						(aceitaComEspacos(IGUAL, op) && aceita(IGUAL, op))) {
					op.adicionar(exprAtomo(true));
					nó.adicionar(op);
				}
			} else if (requerido) throw new ErroDeSintaxeException("Simbolo inesperado. Esperava EXPR_ATOMO"); 
		}
		
		logger.desaninhar();
		return nó;
	}
	
	private void proximoSimbolo() {
		lido = atual;
		if (!lexicoIterador.hasNext()) atual = null;
		else                  		   atual = lexicoIterador.next();
	}
	
	private int marcar() {
		return lexicoIterador.nextIndex();
	}
	
	private void retornar(int marcacao) {
		for (int i = 0; i <= (lexicoIterador.nextIndex() - marcacao) + 3; i++)
			logger.debug("<< " + (atual = lexicoIterador.previous()).toString());
	}
	
	private SimboloLexico atual() throws ErroDeSintaxeException {
		if (atual == null) throw new ErroDeSintaxeException("EOF Inesperado");
		return atual;
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
				if (EXIBIR_IGNORADO) logger.debug("[" + logger.listarEnums(ignorar) + "]");
				proximoSimbolo();
			}
		}
		
		if (atual == null) { return false; }
		if (atual.getCategoria().equals(categoria)) {
			if (nó != null) nó.adicionar(criarNóDe(atual));
			logger.debug(atual.toString());
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
			return new NóCondicao();
		case NOTACAO_STRING:
			return new NóString();
		case IDENTIFICADOR:
			return new NóIdentificador(simbolo);
		default: return null;
		}
	}
	
	private class Logger {
		private String prefixo = "";
		
		public String listarEnums(Enum<?>... enums) {
			return Stream.of(enums).map(Enum::name).collect(Collectors.joining("/"));
		}
		
		public void aninhar()    { prefixo += "  "; }
		public void desaninhar() { prefixo = prefixo.substring(2); }
		
		public void debug(String mensagem) {
			if (DEBUG) {
				System.out.println(prefixo + mensagem);
			}
		}
	}
}