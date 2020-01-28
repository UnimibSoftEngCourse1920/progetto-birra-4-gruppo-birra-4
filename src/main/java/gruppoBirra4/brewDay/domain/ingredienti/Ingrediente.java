package gruppoBirra4.brewDay.domain.ingredienti;

import java.util.UUID;

import gruppoBirra4.brewDay.errori.Notifica;

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
	
	
	private Ingrediente(String nome, Categoria categoria, double quantita) {
		id = UUID.randomUUID().toString(); 
		setNome(nome);
		setCategoria(categoria);
		setQuantita(quantita);
	}
	
	public static Ingrediente creaIngrediente(String nome, Categoria categoria, double quantita) {
		boolean valid = validation(nome, categoria, quantita);
		if (!valid)
			return null;
		else
			return new Ingrediente(nome, categoria, quantita);
		
	}
	
	private static boolean validation(String nome, Categoria categoria, double quantita) {
		return validateNome(nome) &&
				validateQuantita(quantita) &&
				validateCategoria(categoria);
	}
	
	private static boolean validateNome(String nome) {
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
	
	private static boolean validateQuantita(double quantita) {
		if(quantita<0) {
			Notifica.getIstanza().addError("La quantità inserita non può essere negativa");
			return false;
		}
		return true;
	}
	
	// sarà necessario???
	private static boolean validateCategoria(Categoria categoria) {
		for (Categoria categ : Categoria.values()) {
				if(categ.equals(categoria))
						return true;
		}
		Notifica.getIstanza().addError("L'ingrediente inserito non fa parte di nessuna categoria ammissibile");
		return false;
	}

	public  String getId() {
		return id;
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
	
	public Categoria getCategoria() {
		return categoria;
	}

	private void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	public double getQuantita() {
		return quantita;
	}

	private void setQuantita(double quantita) {
		this.quantita = quantita;
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
