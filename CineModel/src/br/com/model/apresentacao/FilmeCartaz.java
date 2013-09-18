package br.com.model.apresentacao;

import java.util.HashSet;
import java.util.Set;

import br.com.cinemodel.util.enums.DataApos;
import br.com.model.Filme;
import br.com.model.base.NomeFilme;

/**
 * Contem a informacao dos horarios, data e ordenacao para apresentacao ao usuario
 * @author Raul
 */
public class FilmeCartaz extends Filme {
	private static final long serialVersionUID = -5445640160801563619L;
	
	private DataApos dia;
	
	private NomeFilme nomeFilme;

	private Set<String> horarios;
	
	private String diaSemana;

	public FilmeCartaz() {
	}
	
	public DataApos getdia() {
		return dia;
	}
	public String getDiaSemana() {
		return diaSemana;
	}

	public Set<String> getHorarios() {
		return horarios;
	}

	public void addHorario(String horarios) {
		if (!this.horarios.add(horarios))
			throw new RuntimeException("Set retornou null");
	}
	
	public NomeFilme getNomeFilme() {
		return nomeFilme;
	}
	public void setNomeFilme(NomeFilme nomeFilme) {
		this.nomeFilme = nomeFilme;
	}
	
}
