package utils;

public class util {
	/**
	 * �ж��ַ����Ƿ���null���߿��ַ���
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
