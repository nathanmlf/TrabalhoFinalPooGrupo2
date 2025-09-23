package br.serratec.projeto.main;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import br.serratec.projeto.connection.ConnectionFactory;
import br.serratec.projeto.dao.DaoDependente;
import br.serratec.projeto.dao.DaoFolhaPagamento;
import br.serratec.projeto.dao.DaoFuncionario;
import br.serratec.projeto.model.Dependente;
import br.serratec.projeto.model.FolhaPagamento;
import br.serratec.projeto.model.Funcionario;
import br.serratec.projeto.util.EscritorArquivo;
import br.serratec.projeto.util.LeitorArquivo;

public class Principal {

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);

		System.out.print("Digite o caminho do arquivo de entrada (CSV): ");
		String caminhoEntrada = scanner.nextLine();
		System.out.print("Digite o caminho completo do arquivo de saída (CSV): ");
		String caminhoSaida = scanner.nextLine();

		Connection conexao = null;
		try {
			System.out.println("\n(Etapa 1/5) Lendo arquivo CSV...\n");
			LeitorArquivo leitor = new LeitorArquivo();
			List<Funcionario> funcionariosDoArquivo = leitor.lerArquivoEntrada(caminhoEntrada);
			System.out.println("-> Leitura do arquivo CSV concluída: " + funcionariosDoArquivo.size()
					+ " funcionários encontrados.");

			System.out.println("\n(Etapa 2/5) Conexão Banco de dados:\n");

			ConnectionFactory connectionFactory = new ConnectionFactory();
			conexao = connectionFactory.getConnection();

			if (conexao == null) {
				System.err.println("Falha na conexão com o banco de dados. O programa foi encerrado.");
				scanner.close();
				return;
			}

			DaoFuncionario daoFuncionario = new DaoFuncionario(conexao);
			DaoDependente daoDependente = new DaoDependente(conexao);
			DaoFolhaPagamento daoFolhaPagamento = new DaoFolhaPagamento(conexao);

			System.out.println("\n(Etapa 3/5) Populando tabelas de funcionários e dependentes:\n");
			for (Funcionario funcionario : funcionariosDoArquivo) {
				Integer idFuncionarioGerado = daoFuncionario.inserirFuncionario(funcionario);

				funcionario.setIdFuncionario(idFuncionarioGerado);

				for (Dependente dependente : funcionario.getDependentes()) {
					daoDependente.inserirDependente(dependente, idFuncionarioGerado);
				}
			}
			System.out.println("-> Tabelas de funcionários e dependentes populadas com sucesso!\n");

			System.out.println("\n(Etapa 4/5) Calculando, exibindo e gerando arquivo de folha de pagamento:\n");
			List<FolhaPagamento> folhasCalculadas = new ArrayList<>();
			for (Funcionario funcionario : funcionariosDoArquivo) {
				FolhaPagamento folha = new FolhaPagamento(0, funcionario, LocalDate.now());
				folhasCalculadas.add(folha);

				System.out.println(folha.formatarParaLinha());
			}

			EscritorArquivo.escreverArquivoSaida(caminhoSaida, folhasCalculadas);
			System.out.println("-> Arquivo de saída gerado em: " + caminhoSaida);

			System.out.println("\n(Etapa 5/5) Populando tabela de folha de pagamento:\n ");
			for (FolhaPagamento folha : folhasCalculadas) {
				daoFolhaPagamento.inserirFolhaPagamento(folha);
			}
			System.out.println("-> Tabela de folha de pagamento populada com sucesso!");
			System.out.println("\n--- Programa Concluído com Sucesso ---");

		} catch (IOException e) {
			System.err.println("\nErro de Arquivo!");
			System.err.println("Mensagem: " + e.getMessage());
		} catch (SQLException e) {
			System.err.println("\nErro de Banco de Dados!");
			System.err.println("Mensagem: " + e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			System.err.println("\nErro inesperado!");
			System.err.println("Mensagem: " + e.getMessage());
			e.printStackTrace();
		} finally {
			scanner.close();
			if (conexao != null) {
				try {
					conexao.close();
					System.out.println("\nConexão com o banco de dados fechada.");
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
}