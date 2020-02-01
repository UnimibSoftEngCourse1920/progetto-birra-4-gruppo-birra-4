package gruppobirra4.brewday.database; //NOSONAR

import org.mapdb.DB;
import org.mapdb.DBMaker;
import org.mapdb.HTreeMap;


public class Database {
	
	private DB db;
	private static Database istanza;
	
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
	
	//Per test
	public static synchronized void setIstanzaTest() {
		istanza = new Database("src\\test\\java\\gruppobirra4\\brewday\\DatabaseTest.db");	
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
