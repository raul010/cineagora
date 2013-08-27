package br.com.cinemodel.model.apresentacao;

import java.util.Set;

import br.com.cinemodel.model.Filme;
import br.com.cinemodel.util.enums.DataApos;

public class FilmeCartaz extends Filme {

  private static final long serialVersionUID = -5445640160801563619L;

  private DataApos dia;

  private Set horarios;

  private String diaSemana;

  public FilmeCartaz(int diaIteracao) {
  }

  public FilmeCartaz() {

  }

  public DataApos getdia() {
		return dia;
  }

  public String getDiaSemana() {
		return diaSemana;
  }

  public Set getHorarios() {
		return horarios;
  }

  public void addHorario(String horarios) {
  }

  public int compareTo(FilmeCartaz f) {
		return 0;
  }

}