package gruppobirra4.brewday.domain; //NOSONAR

import java.math.BigDecimal;

public class DecimalUtils {

	private DecimalUtils() {
		super();
	}
	
	public static double round(double value, int numberOfDigitsAfterDecimalPoint) {
        BigDecimal bigDecimal = BigDecimal.valueOf(value);
        bigDecimal = bigDecimal.setScale(numberOfDigitsAfterDecimalPoint,
                BigDecimal.ROUND_HALF_UP);
        return bigDecimal.doubleValue();
    }

}