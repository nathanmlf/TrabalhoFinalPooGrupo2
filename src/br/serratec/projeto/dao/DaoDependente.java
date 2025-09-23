package br.serratec.projeto.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Period;

import br.serratec.projeto.model.Dependente;
import br.serratec.projeto.model.DependenteException;

public class DaoDependente {

	private Connection connection;

	public DaoDependente(Connection connection) {
		this.connection = connection;
	}

	public void inserirDependente(Dependente dependente, Integer idDoFuncionario)
			throws DependenteException, SQLException {
		validarIdadeDependente(dependente);
		String sql = "insert into tb_dependentes (nome_do_dependente, cpf_do_dependente, data_nascimento_do_dependente, parentesco_do_dependente, id_do_funcionario_fk) VALUES (?, ?, ?, ?, ?)";

		try (PreparedStatement stmt = connection.prepareStatement(sql)) {
			stmt.setString(1, dependente.getNome());
			stmt.setString(2, dependente.getCpf());
			stmt.setObject(3, dependente.getDataNascimento());
			stmt.setString(4, dependente.getParentesco().name());
			stmt.setInt(5, idDoFuncionario);

			stmt.executeUpdate();
			System.out.println(
					"--> Dependente '" + dependente.getNome() + "' inserido para o funcionário ID: " + idDoFuncionario);
		}
	}

	private void validarIdadeDependente(Dependente dependente) throws DependenteException {
		if (Period.between(dependente.getDataNascimento(), LocalDate.now()).getYears() >= 18) {
			throw new DependenteException("O dependente " + dependente.getNome() + " precisa ser menor de 18 anos!");
		}
	}

}