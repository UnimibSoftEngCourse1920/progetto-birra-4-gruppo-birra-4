package GruppoBirra4.BrewDay.domain;

public class Ricetta {
	
	private String nome;
	private String descrizione;
	private QuantitaIngrediente[] quantitaIngredienti;
	//private String passaggi;
	private double quantitaAcqua;
	private double quantitaBirra;
	
	
	public Ricetta(String nome, String descrizione, QuantitaIngrediente[] quantitaIngredienti, 
					double quantitaAcqua, double quantitaBirra) {
		setNome(nome); //Solleva eccezione
		setDescrizione(descrizione); 
		setQuantitaIngredienti(quantitaIngredienti); //Solleva eccezione
		//setPassaggi(passaggi);
		setQuantitaAcqua(quantitaAcqua); //Solleva eccezione
		setQuantitaBirra(quantitaBirra); //Solleva eccezione	
	}

	public String getNome() {
		return nome;
	}
	
	private void setNome(String nome) {
		nome.replaceAll("\\s+", " ").trim(); //sostituisce tutti i whitespaces (spazi + newline + tab +ecc)
		if(nome.isEmpty()) {				//con un singolo spazio e rimuove tutti gli spazi iniziali e finali
			//Solleva eccezione
		} else if (nome.length() >= 30) {
			//Solleve eccezione
		}
		this.nome = nome;
	}
	
	public String getDescrizione() {
		return descrizione;
	}

	private void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public QuantitaIngrediente[] getQuantitaIngredienti() {
		return quantitaIngredienti;
	}

	private void setQuantitaIngredienti(QuantitaIngrediente[] quantitaIngredienti) {
		this.quantitaIngredienti = quantitaIngredienti;
	}

	/*public String getPassaggi() {
		return passaggi;
	}

	private void setPassaggi(String passaggi) {
		this.passaggi = passaggi;
	}*/

	public double getQuantitaAcqua() {
		return quantitaAcqua;
	}

	private void setQuantitaAcqua(double quantitaAcqua) {
		if (quantitaAcqua <= 0) {
			// Solleva eccezione
		} else if (quantitaAcqua < quantitaBirra) {
			//Solleva eccezione
		}
		this.quantitaAcqua = quantitaAcqua;
	}

	public double getQuantitaBirra() {
		return quantitaBirra;
	}

	private void setQuantitaBirra(double quantitaBirra) {
		if (quantitaBirra <= 0) {
			// Solleva eccezione
		} else if (quantitaAcqua < quantitaBirra) {
			//Solleva eccezione
		}
		this.quantitaBirra = quantitaBirra;
	}	
	

}
