package br.serratec.projeto.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import br.serratec.projeto.model.Funcionario;

public class DaoFuncionario {

	Connection connection;

	public DaoFuncionario(Connection connection) {
		this.connection = connection;
	}

	public int inserirFuncionario(Funcionario funcionario) throws SQLException {
		String sql = "insert into tb_funcionarios (nome_do_funcionario, cpf_do_funcionario, data_nascimento_do_funcionario, salario_bruto_do_funcionario) values (?, ?, ?, ?)";

		try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
			stmt.setString(1, funcionario.getNome());
			stmt.setString(2, funcionario.getCpf());
			stmt.setDate(3, java.sql.Date.valueOf(funcionario.getDataNascimento()));
			stmt.setDouble(4, funcionario.getSalarioBruto());
			stmt.executeUpdate();

			try (ResultSet rs = stmt.getGeneratedKeys()) {
				if (rs.next()) {
					System.out.println("Funcionario '" + funcionario.getNome() + "' inserido com sucesso!");
					return rs.getInt(1);
				}
			}
		}
		throw new SQLException("Falha ao inserir funcionário, nenhum ID obtido.");
	}
}