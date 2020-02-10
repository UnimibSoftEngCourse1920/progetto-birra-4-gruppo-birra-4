package gruppobirra4.brewday;

import static org.junit.Assert.fail;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import gruppobirra4.brewday.database.Database;
import gruppobirra4.brewday.domain.ingredienti.CatalogoIngredienti;
import gruppobirra4.brewday.domain.ingredienti.ListaSpesa;
import gruppobirra4.brewday.domain.ricette.ListaLotti;
import gruppobirra4.brewday.domain.ricette.Ricettario;

public class CancellazioneDB {
	
	private CancellazioneDB() {
		super();
	}
	
	protected static void eliminaDB() {
		try {
			Files.deleteIfExists(Paths.get("src\\main\\java\\gruppobirra4\\brewday\\database\\Database.db"));
		} catch (IOException e) {
			fail();
		}
		Database.setDBNull();
		CatalogoIngredienti.setIstanzaNull();
		ListaLotti.setIstanzaNull();
		ListaSpesa.setIstanzaNull();
		Ricettario.setIstanzaNull();
	}
}
