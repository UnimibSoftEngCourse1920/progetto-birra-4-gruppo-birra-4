package gruppobirra4.brewday.database;

import org.mapdb.DB;
import org.mapdb.DBMaker;
import org.mapdb.HTreeMap;

import gruppobirra4.brewday.domain.ingredienti.Ingrediente;

public class Database {
	
	private DB db;
	private static Database istanza;
	
	private Database() {
		db = DBMaker.fileDB("src\\main\\java\\gruppobirra4\\brewday\\database\\Database.db")
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
	
	public HTreeMap<?, ?> openMapDB(String nomeMappa) {
		return getDb().hashMap(nomeMappa).open();
	}
	
	public void closeDB(){
		db.close();
		setDB();
	}
	
	public DB getDb() {
		return db;
	}
	
	private static void setDB() {
		istanza = null;
	}
	
}
