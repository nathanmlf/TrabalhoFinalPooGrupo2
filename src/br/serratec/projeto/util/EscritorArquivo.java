package br.serratec.projeto.util;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import br.serratec.projeto.model.FolhaPagamento;

public class EscritorArquivo {

	public static void escreverArquivoSaida(String path, List<FolhaPagamento> folhaDePagamento) {
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(path))) {

			for (FolhaPagamento folha : folhaDePagamento) {

				bw.write(folha.formatarParaLinha());
				bw.newLine();
			}
			System.out.println("Arquivo criado com sucesso!");

		} catch (IOException e) {

			System.out.println("Erro ao acessar arquivo: " + e.getMessage());
		}
	}
}