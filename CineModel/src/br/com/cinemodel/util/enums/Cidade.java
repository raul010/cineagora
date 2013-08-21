package br.com.cinemodel.util.enums;


public enum Cidade {
	SAO_PAULO("SP");

	private String estado;

	private Cidade(String estado) {
		this.estado = estado;
	}

	public String getEstado() {
		return estado;
	}
}
