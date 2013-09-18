package br.com.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class Cinema implements Serializable {
	private static final long serialVersionUID = 1574758147832526945L;

	private String nome;
	// nullable=false
	private Endereco endereco;
	
	private Set<Filme> filmes;

	public Cinema() {
		filmes = new HashSet<Filme>();
	}

	public boolean addFilme(Filme filme) {
		return this.filmes.add(filme);
	}

	public Set<Filme> getFilmes() {
		return filmes;
	}
	
	public void setFilmes(Set<Filme> filmes) {
		this.filmes = filmes;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	private int id;

}
