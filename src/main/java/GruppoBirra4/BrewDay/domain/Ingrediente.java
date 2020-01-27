package GruppoBirra4.BrewDay.domain;

import java.util.UUID;

import GruppoBirra4.BrewDay.errori.Notifica;

public class Ingrediente {
	
	public enum Categoria {
		MALTO,
		LUPPOLO,
		LIEVITO,
		ZUCCHERO,
		ADDITIVO
	}
	
	private String id;
	private String nome;
	private Categoria categoria;
	private double quantita;
	
	
	public Ingrediente(String nome, Categoria categoria, double quantita) {
		id = UUID.randomUUID().toString(); 
		setNome(nome); //Solleva eccezione
		setCategoria(categoria); //Solleva eccezione
		setQuantita(quantita); //solleva eccezione
	}

	public  String getId() {
		return id;
	}
	
	private boolean validateNome(String nome) {
		String nomeUC = nome.replaceAll("\\s+", " ").trim().toUpperCase();
		if(nomeUC.isEmpty()) {				
			Notifica.getIstanza().addError("Il nome deve contenere dei caratteri");
			return false;
		} else if (nomeUC.length() >= 30) {
			Notifica.getIstanza().addError("Il nome deve contenere al massimo 30 caratteri");
			return false;
		}
		return true;
	}
	
	public String getNome() {
		return nome;
	}
	
	private void setNome(String nome) {
		String nomeUC = nome.replaceAll("\\s+", " ").trim().toUpperCase();
				//sostituisce tutti i whitespaces (spazi + newline + tab +ecc)
				//con un singolo spazio e rimuove tutti gli spazi iniziali e finali
		this.nome = nomeUC;
	}
	
	private boolean validateQuantita() {
		if(quantita<0) {
			Notifica.getIstanza().addError("La quantità inserita deve essere maggiore o uguale a 0");
			return false;
		}
		return true;
	}

	public double getQuantita() {
		return quantita;
	}

	private void setQuantita(double quantita) {
		this.quantita = quantita;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	private void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}
	
	public void modificaIngrediente(Ingrediente ingrediente, double nuovaQuantita) {
		ingrediente.setQuantita(nuovaQuantita);
	}

	public void modificaIngrediente(Ingrediente ingrediente, String nuovoNome) {
		ingrediente.setNome(nuovoNome);
	}
	
	public void modificaIngrediente(Ingrediente ingrediente, Categoria nuovaCategoria) {
		ingrediente.setCategoria(nuovaCategoria);
	}
	
}
