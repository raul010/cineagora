package br.com.model.base;

import java.io.Serializable;

public class NomeFilme implements Serializable {

	private static final long serialVersionUID = -8422182943359724062L;

	private String nomeDoFilme;

	public String getNomeDoFilme() {
		return nomeDoFilme;
	}

	public void setNomeDoFilme(String nomeDoFilme) {
		this.nomeDoFilme = nomeDoFilme;
	}

	private int id;

}
