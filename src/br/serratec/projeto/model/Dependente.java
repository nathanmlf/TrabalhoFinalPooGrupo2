package br.serratec.projeto.model;

import java.time.LocalDate;

public class Dependente extends Pessoa {
	private Parentesco parentesco;

	public Dependente(String nome, String cpf, LocalDate dataNascimento, Parentesco parentesco) {
		super(nome, cpf, dataNascimento);
		this.parentesco = parentesco;
	}

	public Parentesco getParentesco() {
		return parentesco;
	}

}