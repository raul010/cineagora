package br.com.cineagora.view;

import br.com.cinemodel.apresentacao.FilmeCartaz;

public class FilmeCartazView extends FilmeCartaz {
	@Override
	public String toString() {
		return this.getNome() + "\n" + this.getDiaSemana() + "\n" + this.getHorarios();
	}
}
