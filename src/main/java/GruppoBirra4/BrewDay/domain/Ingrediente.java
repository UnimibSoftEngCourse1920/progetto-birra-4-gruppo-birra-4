package GruppoBirra4.BrewDay.domain;

import java.util.UUID;

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
	private double quantitaDisponibile;
	
	
	public Ingrediente(String nome, Categoria categoria, double quantitaDisponibile) {
		id = UUID.randomUUID().toString(); 
		setNome(nome); //Solleva eccezione
		setCategoria(categoria); //Solleva eccezione
		setQuantitaDisponibile(quantitaDisponibile); //solleva eccezione
	}

	public  String getId() {
		return id;
	}
	
	public String getNome() {
		return nome;
	}

	private void setNome(String nome) {
		String nomeR = nome.replaceAll("\\s+", " ").trim(); //sostituisce tutti i whitespaces (spazi + newline + tab +ecc)
		if(nomeR.isEmpty()) {				//con un singolo spazio e rimuove tutti gli spazi iniziali e finali
			//Solleva eccezione
		} else if (nomeR.length() >= 30) {
			//Solleve eccezione
		}
		this.nome = nomeR;
	}

	public double getQuantitaDisponibile() {
		return quantitaDisponibile;
	}

	private void setQuantitaDisponibile(double quantitaDisponibile) {
		if(quantitaDisponibile<0) {
			//Solleva eccezione
		}
		this.quantitaDisponibile = quantitaDisponibile;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	private void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}
	
	public static Ingrediente creaIngrediente(String nome, Categoria categoria, double quantitaDisponibile) {
			return new Ingrediente(nome, categoria, quantitaDisponibile);
	}
	
	public void modificaIngrediente(Ingrediente ingrediente, double nuovaQuantita) {
		ingrediente.setQuantitaDisponibile(nuovaQuantita);
	}

	public void modificaIngrediente(Ingrediente ingrediente, String nuovoNome) {
		ingrediente.setNome(nuovoNome);
	}
	
	public void modificaIngrediente(Ingrediente ingrediente, Categoria categoria) {
		ingrediente.setCategoria(categoria);
	}
	
}
