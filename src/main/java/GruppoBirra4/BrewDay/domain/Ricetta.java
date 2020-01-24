package gruppoBirra4.brewDay.domain;

public class Ricetta {
	
	private String nome;
	private String descrizione;
	private Ingrediente[] ingredienti; 
	private String passaggi;
	private double quantitaAcqua;
	private double quantitaBirra;
	
	
	public Ricetta(String nome, Ingrediente[] ingredienti, String passaggi, double quantitaAcqua, double quantitaBirra) {
		super();
		this.nome = nome;
		this.ingredienti = ingredienti;
		this.passaggi = passaggi;
		this.quantitaAcqua = quantitaAcqua;
		this.quantitaBirra = quantitaBirra;
	}
	
	public Ricetta(String nome, String descrizione, Ingrediente[] ingredienti, String passaggi, double quantitaAcqua, double quantitaBirra) {
		super();
		this.nome = nome;
		this.descrizione = descrizione;
		this.ingredienti = ingredienti;
		this.passaggi = passaggi;
		this.quantitaAcqua = quantitaAcqua;
		this.quantitaBirra = quantitaBirra;
	}

	public String getNome() {
		return nome;
	}
	
	public static Ricetta creaRicetta(String nome, String descrizione, Ingrediente[] ingredienti,
			String passaggi, double quantitaAcqua, double quantitaBirra) {
		
		//checkValori(nome, descrizione, ingredienti, passaggi, quantitaAcqua, quantitaBirra);
		return new Ricetta(nome, descrizione, ingredienti, passaggi, quantitaAcqua, quantitaBirra);
	}
	
	
	
	


}
