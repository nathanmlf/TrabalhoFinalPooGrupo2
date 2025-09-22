package br.serratec.projeto.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Funcionario extends Pessoa {

	private Integer idFuncionario;
	private Double salarioBruto;
	private List<Dependente> dependentes;

	public Funcionario(String nome, String cpf, LocalDate dataNascimento, Integer idFuncionario, Double salarioBruto) {
		super(nome, cpf, dataNascimento);
		this.idFuncionario = idFuncionario;
		this.salarioBruto = salarioBruto;
		this.dependentes = new ArrayList<>();
	}

	public void adicionarDependente(Dependente dependente) {
		this.dependentes.add(dependente);
	}

	public void removerDependente(Dependente dependente) {
		this.dependentes.remove(dependente);
	}

	public Double getSalarioBruto() {
		return salarioBruto;
	}

	public Integer getIdFuncionario() {
		return idFuncionario;
	}

	public void setIdFuncionario(Integer idFuncionario) {
		this.idFuncionario = idFuncionario;
	}

	public List<Dependente> getDependentes() {
		return dependentes;
	}

	public void setDependentes(List<Dependente> dependentes) {
		this.dependentes = dependentes;
	}

}
