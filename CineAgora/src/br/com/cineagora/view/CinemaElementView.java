package br.com.cineagora.view;

import br.com.cinemodel.element.CinemaElement;

public class CinemaElementView extends CinemaElement {
	@Override
	public String toString() {
		return this.getNome();
	}
}
