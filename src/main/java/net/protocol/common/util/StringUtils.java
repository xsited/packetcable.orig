package net.protocol.common.util;

/**
 * @author jinhongw@gmail.com
 */
public class StringUtils {

    public static void deleteLastDot(StringBuilder buf) {
        int i = buf.length() - 1;
        for (; i > 0; i--) {
            final char c = buf.charAt(i);
            if (c == ' ') {
                buf.deleteCharAt(i);
                continue;
            }
            if (c == ',') {
                buf.deleteCharAt(i);
                break;
            }
            break;
        }
    }
}
