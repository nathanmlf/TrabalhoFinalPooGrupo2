package br.serratec.projeto.dao;

//TODO BRUNO

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import br.serratec.projeto.model.Funcionario;

	public class DaoFuncionario{
		
		Connection connection;
		
	    public DaoFuncionario(Connection connection) {
	    	this.connection = connection;
	    }

	    public void inserir(Funcionario funcionario) {
	        String sql = "insert into tb_funcionarios (nome_do_funcionario, cpf_do_funcionario, data_nascimento_do_funcionario, salario_bruto_do_funcionario) values (?, ?, ?, ?)";

	        try {
	            PreparedStatement stmt = connection.prepareStatement(sql);

	            stmt.setString(1, funcionario.getNome());
	            stmt.setString(2, funcionario.getCpf());
	            stmt.setDate(3, java.sql.Date.valueOf(funcionario.getDataNascimento()));
	            stmt.setDouble(4, funcionario.getSalarioBruto());

	            stmt.execute();
	            stmt.close();

	            System.out.println("Funcionario inserido com sucesso!");
	        }catch (SQLException e) {
	            throw new RuntimeException(e);
	        }
	}
}