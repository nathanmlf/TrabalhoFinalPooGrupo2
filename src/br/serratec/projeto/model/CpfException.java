package br.serratec.projeto.model;

//TODO

public class CpfException extends RuntimeException {
	
	public CpfException (String message) {
		super("CPF Invalido!");
	}
	
}
