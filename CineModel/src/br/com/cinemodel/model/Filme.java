package br.com.cinemodel.model;

import java.io.Serializable;
import java.util.Set;

import br.com.cinemodel.util.enums.Genero;

public class Filme implements Serializable {

  private static final long serialVersionUID = -8925942236632712514L;

  private String nome;

  private Set<Genero> genero;

  private Set<Cinema> cinemas;

  private int id;

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

  public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		return result;
  }

  public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Filme))
			return false;
		Filme other = (Filme) obj;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		return true;
  }

}