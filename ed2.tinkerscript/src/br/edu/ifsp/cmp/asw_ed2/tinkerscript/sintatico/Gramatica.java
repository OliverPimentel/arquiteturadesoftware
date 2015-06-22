package br.edu.ifsp.cmp.asw_ed2.tinkerscript.sintatico;

public class Gramatica {
	public static final String TINKERSCRIPT = ""
			+ "programa"
			+ "-> PROGRAMA_INICIO PULO_DE_LINHA declaracoes comandos PROGRAMA_FIM"
			+ ""
			+ "declaracoes"
			+ "-> declaracao"
			+ "-> declaracao declaracoes"
			+ ""
			+ "declaracao"
			+ "-> IDENTIFICADOR IGUAL NUMERO PULO_DE_LINHA"
			+ "-> IDENTIFICADOR IGUAL IDENTIFICADOR PULO_DE_LINHA"
			+ "-> PULO_DE_LINHA"
			+ ""
			+ "comandos"
			+ "-> positions DOIS_PONTOS expressions PULO_DE_LINHA"
			+ ""
			+ "posicao"
			+ "-> IDENTIFICADOR"
			+ "-> posicao_valor posicao_valor"
			+ ""
			+ "posicao_valor"
			+ "-> ASTERISCO"
			+ "-> NUMERO"
			+ ""
			/* Oliver, faz as regras de expressões (a regra "-> NUMERO" tem que existir e ser a última delas) */
			+ "expressoes" 
			+ "-> NUMERO PULO_DE_LINHA";

//	private Map<String,Regra> regras;
//	private Regra regraAtual;
//	
//	public static Gramatica tinkerscript() {
//		return new Gramatica(TINKERSCRIPT);
//	}
//	
//	private Gramatica(String definicao) {
//		regras = new HashMap<>();
//		regraAtual = null;
//		
//		for (String linha : definicao.split("\n"))
//			lerLinhaDefinicao(linha);
//	}
//	
//	public Regra regraPrincipal() {
//		return regras.get(REGRA_PRINCIPAL_IDENTIFICADOR);
//	}
//	
//	private void lerLinhaDefinicao(String linha) {
//		try (Scanner scan = new Scanner(linha)) {
//			if (!scan.hasNext()) return;
//			
//			String palavra = scan.next();
//			
//			if (palavra.startsWith("->")) {
//				while (scan.hasNext()) { regraAtual.adicionarDefinicao(buscarRegra(scan.next())); } 
//			} else {
//				regraAtual = buscarRegra(palavra);
//			}
//		}
//	}
//	
//	private Regra buscarRegra(String identificador) {
//		if (regras.containsKey(identificador))
//			return regras.get(identificador);
//		
//		Regra novaRegra = new Regra(identificador);
//		regras.put(identificador, novaRegra);
//		
//		return novaRegra;
//	}
}
