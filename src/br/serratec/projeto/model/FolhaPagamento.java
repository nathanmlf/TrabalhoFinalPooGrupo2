package br.serratec.projeto.model;

import java.time.LocalDate;

import java.util.List;

//TODO DIANA

public class FolhaPagamento {
	private int codigo;
	private Funcionario funcionario; // relacionamento com funcionario
	private LocalDate dataPagamento;
	private double descontoINSS;
	private double descontoIR;
	private double salarioLiquido;
	//private Funcionario dependentes;
	
	

	public FolhaPagamento() {
		super();
		// TODO Auto-generated constructor stub
		// funcionario.getDependentes().size();
	}

	// Construtor
	public FolhaPagamento(int codigo, Funcionario funcionario, LocalDate dataPagamento){
		super();
		this.codigo = codigo;
		this.funcionario = funcionario;
		this.dataPagamento = dataPagamento;
		//this.dependentes = dependentes;
		calcularDescontos();
	}
	
	// metodo para determinar a faixa salarial do INSS
	private int determinarFaixaINSS(double salarioBruto) {
		if (salarioBruto <= 1518.00) {
			return 1;
		} else if (salarioBruto <= 2793.88) {
			return 2;
		} else if (salarioBruto <= 4190.83) {
			return 3;
		} else if (salarioBruto <= 8157.41) {
			return 4;
		} else {
			return 5; // acima do teto
		}
	}

	// metodo para calcular INSS
	private double calcularINSS(double salarioBruto) {
		double INSS = 0.0;
		int faixa = determinarFaixaINSS(salarioBruto);

		switch (faixa) {
		case 1:
			// faixa 1: até 1518.00 - aliquota 7.50%
			INSS = salarioBruto * 0.075;
			break;

		case 2:
			// faixa 2: de 1518.01 até 2793.88 - aliquota 9%
			// Calculo:
			// faixa 1 : R$1518.00 * 7.5% = R$ 113.85
			// faixa 2 : (salario - 1518.00) * 9%
			INSS = (1518.00 * 0.075) + ((salarioBruto - 1518.00) * 0.09);
			break;

		case 3:
			// faixa 3: de 2793.89 até 4190.83 - aliquota 12%
			// calculo:
			// faixa 1 : R$ 113.85
			// faixa 2 : ( 2793.88 - 1518.00) * 9% = R$ 114,83
			// faixa 3 : (salario - 2793.88) * 12%
			INSS = (1518.00 * 0.075) + ((salarioBruto - 1518.00) * 0.09) + ((salarioBruto - 2793.88) * 0.12);

		case 4:
			// faixa 4: 4190.84 até 8157.41 - aliquota 14%
			// calculo:
			// faixa 1 : R$ 113.85
			// faixa 2 : R% 114.83
			// faixa 3 : R$ 167.63
			// faixa 4: ((salario - 4190.83) * 14%
			INSS = (1518.00 * 0.075) + ((salarioBruto - 1518.00) * 0.09) + ((salarioBruto - 2793.88) * 0.12)
					+ ((salarioBruto - 4190.41) * 0.14);
			break;

		case 5:
		default:
			// Acima do teto - valor limite de contribuição R$ 951.63
			INSS = 951.63;
			break;
		}
		return INSS;
	}

	// metodo para calcular IR

	private double calcularIR(double salarioBruto, double INSS, List<Dependente> dependentes) {
		double deducaoDependente =  189.59;
		double totalDeducaoDependentes = dependentes.size() * deducaoDependente;

		
		// double deducaoPorDependente = dependentes * deducaoPorDependente;
	
		double baseCalculo = salarioBruto - INSS - totalDeducaoDependentes;
	
		// se a base de calculo for negativa ou zero, não há IR
		if (baseCalculo <= 0) {
			return 0;
		}
		double aliquotaIR = 0;
		double parcelaDeduzir = 0;

		// Determinar aliquota e parcela a deduzir
		if (baseCalculo <= 2259.20) {
			// Até R$ 2259.20 é isento
			return 0;
		} else if (baseCalculo <= 2826.65) {
			// De R$ 2259.21 até 2826.65 - 7,5%
			aliquotaIR = 0.075;
			parcelaDeduzir = 169.44;
		} else if (baseCalculo <= 3751.05) {
			// De R$ 2826.66 até 3751.05 - 15%
			aliquotaIR = 0.15;
			parcelaDeduzir = 381.44;
		} else if (baseCalculo <= 4664.68) {
			aliquotaIR = 0.225;
			parcelaDeduzir = 662.77;

		} else {
			aliquotaIR = 0.275;
			parcelaDeduzir = 896.00;
		}

		// aplicando a formula (((salário bruto - dedução dependentes - INSS) × alíquota) - parcela a deduzir
		double calculoIR = (baseCalculo * aliquotaIR) - parcelaDeduzir;
		return Math.max(0, calculoIR);
			
		}

	// metodo principal para calcular todos os descontos
	private void calcularDescontos() {
		double salarioBruto = funcionario.getSalarioBruto();
	
	// calcular INSS 
		this.descontoINSS = calcularINSS(salarioBruto);
		
	// calculando o IR
		this.descontoIR = calcularIR(salarioBruto, this.descontoINSS, this.funcionario.getDependentes());
		
	//Calcular salario liquido
		this.salarioLiquido = salarioBruto - this.descontoINSS - this.descontoIR;
		
	}
	
	// metodo para exibir os detalhes da folha de pagamento
	public void exibirFolhaPagamento() {
		double salarioBruto = funcionario.getSalarioBruto();
		int faixaINSS = determinarFaixaINSS(salarioBruto);
		int numDependentes = funcionario.getDependentes().size();
		double deducaoDependente = 189.59 * numDependentes;
		double baseCalculoIR = salarioBruto - this.descontoINSS - deducaoDependente ;
		
		
	// Exibir faixa do IR p exibição
		
		String faixaIR = "isento";
		if (baseCalculoIR > 2259.20 && baseCalculoIR <= 2826.65) {
			faixaIR = "7,5%";
		}else if (baseCalculoIR > 2826.65 && baseCalculoIR <= 3751.05) {
			faixaIR ="15%";
			
		}else if (baseCalculoIR > 3751.05 && baseCalculoIR <= 4664.68) {
			faixaIR = "22.5%";
			
		}else if(baseCalculoIR > 4664.68) {
			faixaIR = "27.5%";
		}
			
		System.out.println("\uD83D\uDCB0 Folha de Pagamento \uD83D\uDCB0");
        System.out.println("Código: " + codigo);
        System.out.println("Funcionário: " + funcionario.getNome());
        System.out.println("CPF: " + funcionario.getCpf());
        System.out.println("Data de Pagamento: " + dataPagamento);
        System.out.printf("Número de Dependentes: %d%n", numDependentes);
        System.out.println("---------------------------------------");
        System.out.printf("Salário Bruto: R$ %.2f%n", salarioBruto);
        System.out.printf("Dedução Dependentes: R$ %.2f%n", deducaoDependente);
        System.out.printf("Desconto INSS (Faixa %d): R$ %.2f%n", faixaINSS, descontoINSS);
        System.out.printf("Base de Cálculo IR: R$ %.2f%n", baseCalculoIR);
        System.out.printf("Desconto IR (%s): R$ %.2f%n", faixaIR, descontoIR);
        System.out.println("-----------------------------------------");
        System.out.printf("Salário Líquido: R$ %.2f%n", salarioLiquido);
        System.out.println("*******************************************\n");
		}
		

	// Getters e setters
	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public Funcionario getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
		calcularDescontos();// Recalcular qdo mudar funcionario
	}

	public LocalDate getDataPagamento() {
		return dataPagamento;
	}

	public void setDataPagamento(LocalDate dataPagamento) {
		this.dataPagamento = dataPagamento;
	}

	public double getDescontoINSS() {
		return descontoINSS;
	}


	public double getDescontoIR() {
		return descontoIR;
	}

	/* public Funcionario getDependentes() {
	        return dependentes;
	 }
	 
	 public void setDependentes(Funcionario dependentes) {
	        this.dependentes = dependentes;
	        calcularDescontos(); // Recalcular quando mudar dependentes
	 } */
	public double getSalarioLiquido() {
		return salarioLiquido;
	}

	@Override
	public String toString() {
		return "FolhaPagamento[" + 
				"codigo= " + codigo + 
				", funcionario=" + funcionario.getNome() +
				", dataPagamento=" + dataPagamento +
				", descontoINSS=" + String.format("%.2f",descontoINSS) + 
				", descontoIR="  + String.format("%.2f",descontoIR)+ 
				", salarioLiquido=" + String.format("%.2f",salarioLiquido) +
				 "]";
	}

}
