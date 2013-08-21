package br.com.cinemodel.util.enums;



public enum Genero {
		ACAO(1), COMEDIA(2), ROMANCE(3);
		
		private int codGenero;
		
		private Genero(int codGenero) {
			this.codGenero = codGenero;
		}

		public int getGenero() {
			return codGenero;
		}
}
