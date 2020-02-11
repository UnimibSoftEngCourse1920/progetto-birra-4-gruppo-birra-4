package gruppobirra4.brewday.database; //NOSONAR

import org.mapdb.DB;
import org.mapdb.DBMaker;
import org.mapdb.HTreeMap;


public class Database {
	
	private DB db;
	private static Database istanza;
	
	//Apre il database presente nel sistema o ne crea uno se non e' gia' presente
	private Database(String path) {
		db = DBMaker.fileDB(path)
				.transactionEnable()
	            .closeOnJvmShutdown()
	            .fileChannelEnable()
	            .make();
	}
	
	public static synchronized Database getIstanza() {
		if (istanza == null){
			istanza = new Database("src\\main\\java\\gruppobirra4\\brewday\\database\\Database.db");	
		}
		return istanza;
	}
	
	public HTreeMap<?, ?> openMapDB(String nomeMappa) {
		return getDb().hashMap(nomeMappa).open();
	}
	
	//Chiude il database
	public void closeDB(){
		db.commit();
		db.close();
		setDBNull();
	}
	
	public DB getDb() {
		return db;
	}
	
	/*public static boolean isIstanzaNull() {
		return istanza == null;
	}*/
	
	public static void setDBNull() {
		istanza = null;
	}
	
}
