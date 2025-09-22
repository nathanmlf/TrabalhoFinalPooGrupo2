package br.serratec.projeto.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import br.serratec.projeto.model.Dependente;
import br.serratec.projeto.model.Funcionario;
import br.serratec.projeto.model.Parentesco;

public class LeitorArquivo {

	public List<Funcionario> lerArquivoEntrada(String caminhoArquivo) throws FileNotFoundException {
		List<Funcionario> listaDeFuncionarios = new ArrayList<>();
		File arquivoLido = new File(caminhoArquivo);
		DateTimeFormatter formatadorData = DateTimeFormatter.ofPattern("yyyyMMdd");

		try (Scanner leitorArquivo = new Scanner(arquivoLido)) {

			Funcionario funcionarioAtual = null;

			while (leitorArquivo.hasNextLine()) {
				String linhaLida = leitorArquivo.nextLine();

				if (linhaLida.trim().isEmpty() || linhaLida.startsWith(";;;")) {
					continue;
				}

				String[] camposLidos = linhaLida.split(";");

				try {
					Double salarioBrutoFuncionario = Double.parseDouble(camposLidos[3].replace(",", "."));

					if (funcionarioAtual != null) {
						listaDeFuncionarios.add(funcionarioAtual);
					}

					String nomeFuncionario = camposLidos[0];
					String cpfFuncionario = camposLidos[1];
					LocalDate dataNascimentoFuncionario = LocalDate.parse(camposLidos[2], formatadorData);

					funcionarioAtual = new Funcionario(nomeFuncionario, cpfFuncionario, dataNascimentoFuncionario, null,
							salarioBrutoFuncionario);

				} catch (NumberFormatException e) {
					if (funcionarioAtual != null) {
						String nomeDependente = camposLidos[0];
						String cpfDependente = camposLidos[1];
						LocalDate dataNascimentoDependente = LocalDate.parse(camposLidos[2], formatadorData);
						Parentesco parentescoDependente = Parentesco.valueOf(camposLidos[3].toUpperCase());

						Dependente dependente = new Dependente(nomeDependente, cpfDependente, dataNascimentoDependente,
								parentescoDependente);
						funcionarioAtual.adicionarDependente(dependente);
					}
				}

			}

			if (funcionarioAtual != null) {
				listaDeFuncionarios.add(funcionarioAtual);
			}
		}

		return listaDeFuncionarios;
	}
}
