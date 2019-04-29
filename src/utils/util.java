package utils;

public class util {
	/**
	 * ÅĞ¶Ï×Ö·û´®ÊÇ·ñÊÇnull»òÕß¿Õ×Ö·û´®
	 * @param str
	 * @return
	 */
    public static boolean isBlank_String(String str) {
    	if (str == null || str.equals("")) {
			return true;
		} else {
			return false;
		}
    }
}
