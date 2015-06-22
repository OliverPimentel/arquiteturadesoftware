package br.edu.ifsp.cmp.asw_ed2.tinkerscript.estruturas;

import java.util.ArrayList;
import java.util.List;

public class ArvoreNo<T> {
	private T valor;
	private List<ArvoreNo<T>> filhos;
	
	public ArvoreNo(T valor) {
		this.valor = valor;
		filhos = new ArrayList<>();
	}

	public T getValor() {
		return valor;
	}

	public void setValor(T valor) {
		this.valor = valor;
	}

	public List<ArvoreNo<T>> filhos() {
		return filhos;
	}
}
