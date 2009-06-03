package net.fortunes.util;

public class PinYin {
	public static String GetPYChar(String c) {
        int[] a = {-20539, -20031, -19474, -18966, -18782, -18495, -18178,
                -17673, -55555, -16730, -16468, -15896, -15421, -15178,
                -15170, -14886, -14405, -14346, -13574, -13094,
                -55555, -55555, -12812, -12103, -11311, -10503
        };
        String s = "abcdefghijklmnopqrstuvwxyz";

        byte[] array = c.getBytes();
        int i = (short) (array[0]) * 256 + ((short) (array[1]));

        if (i < -20575) return c;
        for (int index = 0; index < a.length; index++) {
            if (i < a[index]) {
                return s.substring(index, index + 1);
            }
        }
        return "";
    }

    /**
     * 取得汉字拼音缩写
     *
     * @param str 汉字
     * @return 拼音缩写大写
     */
    public static String toPYString(String str) {
        String tempStr = "";
        for (int i = 0; i < str.length(); i++) {
            if ((int) str.charAt(i) >= 33 && (int) str.charAt(i) <= 126) {//字母和符号原样保留
                tempStr += str.charAt(i) + "";
            } else {//累加拼音声母
                tempStr += GetPYChar(str.charAt(i) + "");
            }
        }
        return tempStr.toUpperCase();
    }
    
    public static void main(String[] args) {
		System.out.println(PinYin.toPYString("廖清平"));
		System.out.println(PinYin.toPYString("我的最爱"));
		System.out.println(PinYin.toPYString("shzy"));
	}
}
