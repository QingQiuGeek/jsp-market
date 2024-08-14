package Utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;

public class GlobalUtil {

	public static boolean isNotNull(String str) {
		if (str != null && str.equals("") == false &&str.equalsIgnoreCase("null")==false) {
			return true;
		}
		return false;
	}

	public enum UserType{
		sysAdmin,regUser
	}
}
