package hami.mainapp.Util;

/**
 * Created by renjer on 2017-03-14.
 */

public class ValidateNationalCode {
    public static Boolean isValidNationalCode(String value) {
        if (value.length() != 10)
            return false;
        if (value.contentEquals("1111111111") ||
                value.contentEquals("0000000000") ||
                value.contentEquals("2222222222") ||
                value.contentEquals("3333333333") ||
                value.contentEquals("4444444444") ||
                value.contentEquals("5555555555") ||
                value.contentEquals("6666666666") ||
                value.contentEquals("7777777777") ||
                value.contentEquals("8888888888") ||
                value.contentEquals("9999999999"))
            return false;
        int codeInt = 0;
        int codeInt9 = Integer.valueOf(value.substring(9, 10));
        for (int i = 0; i < value.length() - 1; i++) {
            codeInt += Integer.valueOf(value.substring(i, i+1)) * (value.length() - i);
        }
        int res = codeInt - ((codeInt / 11) * 11);
        if ((res == 0 && res == codeInt9) || (res == 1 && codeInt9 == 1) || (res > 1 && codeInt9 == (11 - res)))
            return true;
        return false;
    }
}
