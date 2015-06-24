package com.github.awvalenti.arquiteturadesoftware.rpg1.versao6.melhoriasdospareceres.logica;

import com.github.awvalenti.arquiteturadesoftware.rpg1.versao6.melhoriasdospareceres.logica.comportamentos.ComportamentoProgramavel;
import com.github.awvalenti.arquiteturadesoftware.rpg1.versao6.melhoriasdospareceres.logica.comportamentos.TinkerscriptComportamento;
import com.github.awvalenti.arquiteturadesoftware.rpg1.versao6.melhoriasdospareceres.logica.fases.Fase;
import com.github.awvalenti.arquiteturadesoftware.rpg1.versao6.melhoriasdospareceres.logica.fases.FaseProgramavel;

public class FabricaFasesProgramaveis extends FabricaFases {
	private static String[] tinkerscripts = new String[] {
		"/fase1.tinkerscript",
	};
	private int indiceComportamento = 0;
	
	@Override
	public Fase proximaFase() {
		Elemento[][] elementos = fabricaElementosTabuleiros.obterNovosElementos();
		if (elementos == null) return null;
		FaseProgramavel fase = new FaseProgramavel(new Tabuleiro(elementos));
		fase.setFabricaFases(this);
		fase.setComportamentoProgramavel(proximoComportamento());
		return fase;
	}
	
	private ComportamentoProgramavel proximoComportamento() {
		int indiceCircular = tinkerscripts.length % ++indiceComportamento;
		String caminho = getClass().getResource(tinkerscripts[indiceCircular]).getFile();
		return new TinkerscriptComportamento(caminho);
	}
}
