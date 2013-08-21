package br.com.cinemodel;

import java.util.ArrayList;
import java.util.List;

public class Cinema  {
	private String nome;
	private Endereco endereco;
	private List<Filme> filmes;
	
	public Cinema() {
		filmes = new ArrayList<Filme>();
	}
	
	public void addFilme(Filme filme) {
		this.filmes.add(filme);
	}
	
	public List<Filme> getFilmes() {
		return filmes;
	}
	
	public void setFilmes(List<Filme> filmes) {
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
}
