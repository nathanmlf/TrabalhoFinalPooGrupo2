package br.serratec.projeto.main;

//TODO 

import java.sql.Connection;

import br.serratec.projeto.connection.ConnectionFactory;

public class Principal {

	public static void main(String[] args) {
		// TODO 
		Connection connection = new ConnectionFactory().getConnection();
	}
}
