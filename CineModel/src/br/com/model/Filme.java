package br.com.model;

import java.io.Serializable;
import java.util.Set;

import br.com.cinemodel.util.enums.Genero;

/**
 * Representa 1 Filme com o mesmo nome na aplicacao.
 * @author Raul
 *
 */
public class Filme implements Serializable {
	private static final long serialVersionUID = -8925942236632712514L;

	private String nome;
	//nullable=false
	private Set<Genero> genero;
	
	
	private Set<Cinema> cinemas;
	
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	public Set<Genero> getGenero() {
		return genero;
	}

	public void setGenero(Set<Genero> genero) {
		this.genero = genero;
	}
	public Set<Cinema> getCinemas() {
		return cinemas;
	}

	public void setCinemas(Set<Cinema> cinemas) {
		this.cinemas = cinemas;
	}
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	private int id;
}
