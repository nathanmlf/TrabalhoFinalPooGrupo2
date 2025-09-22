package br.serratec.projeto.model;

import java.time.LocalDate;
import java.util.List;
import java.util.Locale;

public class FolhaPagamento {
	private Integer codigoFolhaPagamento;
	private Funcionario funcionario;
	private LocalDate dataPagamento;
	private Double descontoINSS;
	private Double descontoIR;
	private Double salarioLiquido;

	public FolhaPagamento(Integer codigoFolhaPagamento, Funcionario funcionario, LocalDate dataPagamento) {
		super();
		this.codigoFolhaPagamento = codigoFolhaPagamento;
		this.funcionario = funcionario;
		this.dataPagamento = dataPagamento;
		calcularDescontos();
	}

	private void calcularDescontos() {
		Double salarioBruto = funcionario.getSalarioBruto();
		this.descontoINSS = calcularINSS(salarioBruto);
		this.descontoIR = calcularIR(salarioBruto, this.descontoINSS, this.funcionario.getDependentes());
		this.salarioLiquido = salarioBruto - this.descontoINSS - this.descontoIR;
	}

	private Double calcularINSS(Double salarioBruto) {
		Double inss = 0.0;

		if (salarioBruto <= 1556.94) {
			inss = salarioBruto * 0.075;
		} else if (salarioBruto <= 2924.28) {
			inss = (1556.94 * 0.075) + (salarioBruto - 1556.94) * 0.09;
		} else if (salarioBruto <= 4386.42) {
			inss = (1556.94 * 0.075) + (2924.28 - 1556.94) * 0.09 + (salarioBruto - 2924.28) * 0.12;
		} else if (salarioBruto <= 8673.55) {
			inss = (1556.94 * 0.075) + (2924.28 - 1556.94) * 0.09 + (4386.42 - 2924.28) * 0.12
					+ (salarioBruto - 4386.42) * 0.14;
		} else {
			inss = 1011.39;
		}
		return inss;
	}

	private Double calcularIR(Double salarioBruto, Double inss, List<Dependente> dependentes) {
		Double deducaoPorDependente = 189.59;
		Double baseCalculo = salarioBruto - inss - (dependentes.size() * deducaoPorDependente);

		if (baseCalculo <= 2259.20) {
			return 0.0;
		}

		Double aliquota, parcelaADeduzir;
		if (baseCalculo <= 2826.65) {
			aliquota = 0.075;
			parcelaADeduzir = 169.44;
		} else if (baseCalculo <= 3751.05) {
			aliquota = 0.15;
			parcelaADeduzir = 381.44;
		} else if (baseCalculo <= 4664.68) {
			aliquota = 0.225;
			parcelaADeduzir = 662.77;
		} else {
			aliquota = 0.275;
			parcelaADeduzir = 896.00;
		}

		Double imposto = (baseCalculo * aliquota) - parcelaADeduzir;
		return Math.max(0, imposto);
	}

	@Override
	public String toString() {
		return String.format(new Locale("pt", "BR"), "%s;%s;%.2f;%.2f;%.2f", this.funcionario.getNome(),
				this.funcionario.getCpf(), this.descontoINSS, this.descontoIR, this.salarioLiquido);
	}

	public Integer getCodigoFolhaPagamento() {
		return codigoFolhaPagamento;
	}

	public Funcionario getFuncionario() {
		return funcionario;
	}

	public LocalDate getDataPagamento() {
		return dataPagamento;
	}

	public Double getDescontoINSS() {
		return descontoINSS;
	}

	public Double getDescontoIR() {
		return descontoIR;
	}

	public Double getSalarioLiquido() {
		return salarioLiquido;
	}

}