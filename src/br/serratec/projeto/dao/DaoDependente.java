package br.serratec.projeto.dao;

import br.serratec.projeto.model.Dependente;

public class DaoDependente {
	public void inserir(Dependente dependente) {
		String sql = "insert into tb_dependente (nome_dependente, cpf_do_dependente, data_de_nascimento,
			parentesco_do_dependente) VALUES (?, ?, ?,?)";
		
		try {
		PreparedStatement stmt = connection.prepareStatement(sql);
		stmt.setString(1, dependente.getNome());
		stmt.setString(2, dependente.getCpf());
		stmt.setString(3, dependente.DataNascimento);
		
		stmt.execute();
		stmt.close();
		System.err.println("Dependente inserido com sucesso!");
		
		} catch (SQLException e) {
		throw new RuntimeException(e);		

	}
}
}