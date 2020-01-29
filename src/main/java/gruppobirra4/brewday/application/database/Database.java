package gruppobirra4.brewday.application.database;

import org.mapdb.DB;
import org.mapdb.DBMaker;

import gruppobirra4.brewday.domain.ingredienti.CatalogoIngredienti;

public class Database {
	
	private DB db;
	private static Database istanza;
	
	private Database() {
		db = DBMaker.fileDB("Prova.db")
				.transactionEnable()
	            .closeOnJvmShutdown()
	            .fileChannelEnable()
	            .make();
	}
	
	public static synchronized Database getIstanza() {
		if (istanza == null){
			istanza = new Database();	
		}
		return istanza;
	}
	
	public DB getDb() {
		return db;
	}
}
