package net.protocol.cops.message;

import java.util.HashMap;
import java.util.Map;

/**
 * <pre>
 *   Op Code: 8 bits
 *             The COPS operations:
 *               1 = Request                 (REQ)
 *               2 = Decision                (DEC)
 *               3 = Report State            (RPT)
 *               4 = Delete Request State    (DRQ)
 *               5 = Synchronize State Req   (SSQ)
 *               6 = Client-Open             (OPN)
 *               7 = Client-Accept           (CAT)
 *               8 = Client-Close            (CC)
 *               9 = Keep-Alive              (KA)
 *               10= Synchronize Complete    (SSC)
 * </pre>
 *
 * @author jinhongw@gmail.com
 */
public enum OpCode {
    REQ((byte) 1),
    DEC((byte) 2),
    RPT((byte) 3),
    DRQ((byte) 4),
    SSQ((byte) 5),
    OPN((byte) 6),
    CAT((byte) 7),
    CC((byte) 8),
    KA((byte) 9),
    SSC((byte) 10);

    private final byte value;

    private final static Map<Byte, OpCode> KVS = new HashMap<Byte, OpCode>();

    static {
        for (OpCode e : values()) {
            KVS.put(e.value, e);
        }
    }

    OpCode(byte value) {
        this.value = value;
    }

    public byte getValue() {
        return value;
    }

    /**
     * @param value byte
     * @return OpCode
     */
    public static OpCode valueOf(byte value) {
        return KVS.get(value);
    }

    /**
     * <pre>
     * REQ(1)
     * DEC(2)
     * RPT(3)
     * DRQ(4)
     * SSQ(5)
     * OPN(6)
     * CAT(7)
     * CC(8)
     * KA(9)
     * SSC(10)
     * </pre>
     * <p/>
     * (non-Javadoc)
     *
     * @see java.lang.Enum#toString()
     */
    @Override
    public String toString() {
        return name() + "(" + value + ")";
    }
}
