package br.com.cinemodel.util.enums;

public enum Estado {
	SAO_PAULO(1);
	
	private int codEstado;
	
	private Estado(int codEstado) {
		this.codEstado = codEstado;
	}

	public int getEstado() {
		return codEstado;
	}
}
