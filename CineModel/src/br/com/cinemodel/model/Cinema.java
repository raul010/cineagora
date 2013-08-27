package br.com.cinemodel.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import android.os.Parcel;
import android.os.Parcelable;

public class Cinema implements Serializable {

	private static final long serialVersionUID = 1574758147832526945L;

	private String nome;

	private Endereco endereco;

	private List<Filme> filmes;
	
	public List<Filme> getFilmes() {
		return filmes;
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