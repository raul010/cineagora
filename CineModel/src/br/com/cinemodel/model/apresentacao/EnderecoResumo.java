package br.com.cinemodel.model.apresentacao;

import br.com.cinemodel.model.Endereco;

public class EnderecoResumo extends Endereco {

  private static final long serialVersionUID = -4754655350654168217L;

  private String dadosRecebidos;

  public String getDadosRecebidos() {
		return dadosRecebidos;
  }

  public void setDadosRecebidos(String dadosRecebidos) {
		this.dadosRecebidos = dadosRecebidos;
  }

}