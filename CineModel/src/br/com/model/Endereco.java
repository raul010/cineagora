package br.com.model;

import java.io.Serializable;

public class Endereco implements Serializable {
	private static final long serialVersionUID = 6348400782533816916L;

	//@Enumerated(EnumType.STRING)
	private String cidade;
	
	//@Enumerated(EnumType.STRING)
	private String estado;
	
	public String getCidade() {
		return cidade;
	}
	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	private int id;
}
