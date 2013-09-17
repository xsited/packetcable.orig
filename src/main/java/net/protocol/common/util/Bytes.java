package net.protocol.common.util;

/**
 * @author jinhongw@gmail.com
 */
public class Bytes {

    public static byte[] trim(final byte[] src) {
        if (src == null || src.length == 0)
            return new byte[0];
        int n = 0;
        for (int m = src.length - 1; m > 0; m--) {
            if (src[m] == Character.UNASSIGNED) {
                n++;
                continue;
            } else {
                break;
            }
        }
        byte[] dest = new byte[src.length - n];
        System.arraycopy(src, 0, dest, 0, dest.length);
        return dest;
    }

    public static String dump(final byte[] src) {
        if (src == null || src.length == 0)
            return "[]";
        StringBuilder buf = new StringBuilder("[");
        for (byte e : src) {
            buf.append(e).append(", ");
        }
        buf.deleteCharAt(buf.length() - 1);
        buf.deleteCharAt(buf.length() - 1);
        return buf.append("]").toString();
    }
}
