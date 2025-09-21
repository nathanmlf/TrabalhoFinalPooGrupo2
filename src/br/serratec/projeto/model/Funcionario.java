package br.serratec.projeto.model;

import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;

//TODO BRUNO

public class Funcionario extends Pessoa {

	private Double salarioBruto;
	//descontoInss, descontoIr, baseIR;
	private List<Dependente> dependentes;
	//private Dependente dependentes;

	public Funcionario(String nome, String cpf, LocalDate dataNascimento, Double salarioBruto) {
		super(nome, cpf, dataNascimento);
		this.salarioBruto = salarioBruto;
		this.dependentes = new ArrayList<>(); // add
	}

	//public Funcionario(String nome, String cpf, LocalDate dataNascimento) {
		//super(nome, cpf, dataNascimento);
	//}

	public Double getSalarioBruto() {
		return salarioBruto;
	}

	public List<Dependente> getDependentes() {
		return dependentes;
	}

	public void adicionarDependente(Dependente dependente) {
		this.dependentes.add(dependente);
	}

	public void removerDependente(Dependente dependente) {
		this.dependentes.remove(dependente);
	}
	/*public Double getDescontoInss() {
		return descontoInss;
	}

	public Double getDescontoIr() {
		return descontoIr;
	}

	public Double getBaseIR() {
		return baseIR;
	}*/

	/*public Double CalcularInss(Double salarioBruto) {
		if (salarioBruto > 8157.41) {
			descontoInss = (salarioBruto * 0.14);
			baseIR = (salarioBruto - descontoInss) - 190.42;
			return baseIR;
		} else if (salarioBruto > 4190.83) {
			descontoInss = (salarioBruto * 0.14);
			baseIR = (salarioBruto - descontoInss) - 190.42;
			return baseIR;
		} else if (salarioBruto > 2793.88) {
			descontoInss = (salarioBruto * 0.12);
			baseIR = (salarioBruto - descontoInss) - 106.60;
			return baseIR;
		} else if (salarioBruto > 1518.00) {
			descontoInss = (salarioBruto * 0.09);
			baseIR = (salarioBruto - descontoInss) - 22.77;
			return baseIR;
		} else {
			descontoInss = (salarioBruto * 0.075);
			baseIR = (salarioBruto - descontoInss);
			return baseIR;
		}
	

	public Double CalcularIr(Double baseIR) {
		double valorDependentes = dependentes.size() * 189.59;
		if (salarioBruto > 4664.68) {
			return descontoIr = (baseIR * 0.275) - (valorDependentes);
		} else if (salarioBruto > 3751.05) {
			return descontoIr = (baseIR * 0.225) - (valorDependentes);
		} else if (salarioBruto > 2826.66) {
			return descontoIr = (baseIR * 0.15) - (valorDependentes);
		} else if (salarioBruto > 2259.21) {
			return descontoIr = (baseIR * 0.075) - (valorDependentes);
		} else {
			return descontoIr = 0.00;
		}
	}*/

}