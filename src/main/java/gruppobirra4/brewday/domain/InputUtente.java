package gruppobirra4.brewday.domain;

import gruppobirra4.brewday.errori.Notifica;

public class InputUtente {
	
	public static boolean isStringaVuota(String str, String field) {
		String s = str;
		if (str == null) {
			s = "";
		}
		String strLW = rimuoviWhiteSpaces(s);
		if (strLW.isEmpty()) {				
			Notifica.getIstanza().addError("Il campo \"" + field + "\" deve contenere dei caratteri");
			return true;
		}
		return false;
	}
	
	public static boolean isNotPositive(String str, String field) {
		double strNum = convertToNumber(str);
		if(strNum < 0) {
			Notifica.getIstanza().addError("Il campo \"" + field + "\" non può essere negativo");
			return false;
		}
		return true;
	}
	
	public static boolean isNumber(String str, String field) {
		String strNum = rimuoviWhiteSpaces(str);
	    try {
	        double d = Double.parseDouble(strNum);
	    } catch (NumberFormatException nfe) {
	    	Notifica.getIstanza().addError("Il campo \"" + field + "\" deve essere un numero");
	    	return false;
	    }
	    return true;
	}
	
	public static String rimuoviWhiteSpaces(String str) {
		if (str != null) {
			return str.replaceAll("\\s+", " ").trim().toLowerCase();
		}
		return null;
	}
	
	public static double convertToNumber(String str) {
		String strNum = rimuoviWhiteSpaces(str);
		return Double.parseDouble(strNum);
	}
}
