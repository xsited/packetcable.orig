package net.protocol.cops.object;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.util.HashMap;
import java.util.Map;

/**
 * @author jinhongw@gmail.com
 */
public enum IpCType {
    IPV4((byte) 1), IPV6((byte) 2);

    private final byte value;

    private final static Map<Byte, IpCType> KVS = new HashMap<Byte, IpCType>();

    static {
        for (IpCType e : values())
            KVS.put(e.value, e);
    }

    IpCType(byte value) {
        this.value = value;
    }

    public byte getValue() {
        return value;
    }

    public static IpCType valueOf(byte value) {
        return KVS.get(value);
    }

    @Override
    public String toString() {
        return name() + "(" + value + ")";
    }

    public static byte get(InetAddress address) {
        if (address instanceof Inet4Address)
            return IpCType.IPV4.getValue();
        return IpCType.IPV6.getValue();
    }
}
