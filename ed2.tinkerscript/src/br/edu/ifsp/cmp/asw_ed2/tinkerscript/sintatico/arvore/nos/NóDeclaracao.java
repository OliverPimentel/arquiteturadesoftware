package br.edu.ifsp.cmp.asw_ed2.tinkerscript.sintatico.arvore.nos;

public class NóDeclaracao extends NóAbstrato {
	public void adicionar(NóIdentificador identificadorOuValor) {
		if (filhos.get(0) != null)
			filhos.add(1, identificadorOuValor); // valor
		else
			filhos.add(0, identificadorOuValor); // ident
	}
	
	public void adicionar(NóNumero valor) {
		filhos.add(1, valor);
	}
}
