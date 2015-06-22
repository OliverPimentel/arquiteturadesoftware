package br.edu.ifsp.cmp.asw_ed2.tinkerscript.estruturas;

public class Arvore<T> {
	private ArvoreNo<T> raiz;
	
	public Arvore(T raizValor) {
		raiz = new ArvoreNo<T>(raizValor);
	}
	
	public ArvoreNo<T> raiz() {
		return raiz;
	}
}

