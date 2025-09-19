package br.serratec.projeto.model;

public class CpfException extends RuntimeException {
	
	public CpfException (String message) {
		super("CPF Invalido!");
	}
	
}
