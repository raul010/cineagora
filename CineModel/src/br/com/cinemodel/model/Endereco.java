package br.com.cinemodel.model;

import java.io.Serializable;

import br.com.cinemodel.util.enums.Cidade;
import br.com.cinemodel.util.enums.Estado;

public class Endereco implements Serializable {

  private static final long serialVersionUID = 6348400782533816916L;

  private Cidade cidade;

  private Estado estado;

  public Cidade getCidade() {
		return cidade;
  }

  public void setCidade(Cidade cidade) {
		this.cidade = cidade;
  }

  public Estado getEstado() {
		return estado;
  }

  public void setEstado(Estado estado) {
		this.estado = estado;
  }

}