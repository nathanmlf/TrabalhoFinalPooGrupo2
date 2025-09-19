package br.serratec.projeto.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import br.serratec.projeto.model.Dependente;

public class DaoDependente {

	// TODO REVISAR NECESSIDADE DO CONNECTION
	
	private Connection connection;

	public DaoDependente(Connection connection) {
		this.connection = connection;
	}

	public void inserirDependente(Dependente dependente) {
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