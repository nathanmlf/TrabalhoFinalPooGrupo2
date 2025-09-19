package br.serratec.projeto.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Period;

import br.serratec.projeto.model.Dependente;
import br.serratec.projeto.model.DependenteException;
import br.serratec.projeto.model.Funcionario;

public class DaoDependente {

	// TODO REVISAR NECESSIDADE DO CONNECTION

	private Connection connection;

	public DaoDependente(Connection connection) {
		this.connection = connection;
	}

	public void inserirDependente(Funcionario funcionario, Dependente dependente)
			throws DependenteException, SQLException {

		validarDependente(funcionario, dependente);
		inserirDependenteBancoDeDados(dependente);
	}

	private void validarDependente(Funcionario funcionario, Dependente dependente) throws DependenteException {

		Integer idade = Period.between(dependente.getDataNascimento(), LocalDate.now()).getYears();

		if (idade >= 18) {
			throw new DependenteException("O dependente " + dependente.getNome() + " precisa ser menor de 18 anos!");
		}

		// TODO
		boolean cpfDependenteExiste = funcionario.dependente.stream()
				.anyMatch(dependenteExistente -> dependenteExistente.getCpf().equals(dependente.getCpf()));
		if (cpfDependenteExiste) {
			throw new DependenteException("O cpf do dependente não pode ser repetido");
		}
	}

	private void inserirDependenteBancoDeDados(Dependente dependente) throws SQLException {
		String sql = "insert into tb_dependentes (nome_do_dependente, cpf_do_dependente, "
				+ "data_nascimento_do_dependente, parentesco_do_dependente) VALUES (?, ?, ?, ?)";

		try {
			PreparedStatement stmt = connection.prepareStatement(sql);
			stmt.setString(1, dependente.getNome());
			stmt.setString(2, dependente.getCpf());
			stmt.setObject(3, dependente.getDataNascimento());
			stmt.setString(4, dependente.getParentesco().name());

			stmt.execute();
			stmt.close();
			System.err.println("Dependente inserido com sucesso!");

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

}