package br.com.cinemodel;

import java.util.List;

import br.com.cinemodel.util.enums.Genero;

public class Filme  {
	private String nome;

	private List<Genero> genero;
	
	private List<Cinema> cinemas;

	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public List<Genero> getGenero() {
		return genero;
	}
	public void setGenero(List<Genero> genero) {
		this.genero = genero;
	}

	public List<Cinema> getCinemas() {
		return cinemas;
	}
	public void setCinemas(List<Cinema> cinemas) {
		this.cinemas = cinemas;
	}
}
