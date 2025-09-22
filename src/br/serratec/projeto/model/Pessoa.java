package br.serratec.projeto.model;

import java.time.LocalDate;

public abstract class Pessoa {

	protected String nome;
	protected String cpf;
	protected LocalDate dataNascimento;

	public Pessoa(String nome, String cpf, LocalDate dataNascimento) {
		super();
		this.nome = nome;
		this.cpf = cpf;
		this.dataNascimento = dataNascimento;
	}

	public String getNome() {
		return nome;

	}

	public String getCpf() {
		return cpf;

	}

	public LocalDate getDataNascimento() {
		return dataNascimento;

	}

}
