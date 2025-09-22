package br.serratec.projeto.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import br.serratec.projeto.model.FolhaPagamento;

public class DaoFolhaPagamento {

	Connection connection;

	public DaoFolhaPagamento(Connection connection) {
		this.connection = connection;
	}

	public void inserirFolhaPagamento(FolhaPagamento folhaPagamento) throws SQLException {

		String sql = "insert into tb_folhas_pagamentos (id_do_funcionario_fk, data_pagamento_da_folha_pagamento, desconto_inss, desconto_ir, salario_liquido) values (?, ?, ?, ?, ?)";

		try (PreparedStatement stmt = connection.prepareStatement(sql)) {
			stmt.setInt(1, folhaPagamento.getFuncionario().getIdFuncionario());
			stmt.setDate(2, java.sql.Date.valueOf(folhaPagamento.getDataPagamento()));
			stmt.setDouble(3, folhaPagamento.getDescontoINSS());
			stmt.setDouble(4, folhaPagamento.getDescontoIR());
			stmt.setDouble(5, folhaPagamento.getSalarioLiquido());

			stmt.execute();
		}
	}
}
