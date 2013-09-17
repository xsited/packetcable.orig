package net.protocol.cops.object;

import java.util.HashMap;
import java.util.Map;

/**
 * Typically, C-Num identifies the class of information contained in the object,
 * and the C-Type identifies the subtype or version of the information contained
 * in the object.
 * <p/>
 * <pre>
 *      C-num: 8 bits
 *                1  = Handle
 *                2  = Context
 *                3  = In Interface
 *                4  = Out Interface
 *                5  = Reason code
 *                6  = Decision
 *                7  = LPDP Decision
 *                8  = Error
 *                9  = Client Specific Info
 *                10 = Keep-Alive Timer
 *                11 = PEP Identification
 *                12 = Report Type
 *                13 = PDP Redirect Address
 *                14 = Last PDP Address
 *                15 = Accounting Timer
 *                16 = Message Integrity
 *
 *       C-type: 8 bits
 *                Values defined per C-num.
 *
 * </pre>
 *
 * @author jinhongw@gmail.com
 */
public enum CNum {
    HANDLE((byte) 1),
    CONTEXT((byte) 2),
    ININT((byte) 3),
    OUTINT((byte) 4),
    REASON((byte) 5),
    DECISION((byte) 6),
    LPDPDECISION((byte) 7),
    ERROR((byte) 8),
    CLIENTSI((byte) 9),
    KATIMER((byte) 10),
    PEPID((byte) 11),
    REPORTTYPE((byte) 12),
    PDPREDIRADDR((byte) 13),
    LASTPDPADDR((byte) 14),
    ACCTTIMER((byte) 15),
    INTEGRITY((byte) 16);

    private final byte value;

    private final static Map<Byte, CNum> KVS = new HashMap<Byte, CNum>();

    static {
        for (CNum e : values()) {
            KVS.put(e.value, e);
        }
    }

    CNum(byte value) {
        this.value = value;
    }

    public byte getValue() {
        return value;
    }

    /**
     * @param value byte
     * @return CNum
     */
    public static CNum valueOf(byte value) {
        return KVS.get(value);
    }

    /**
     * <pre>
     * Handle(1)
     * Context(2)
     * IN-Int(3)
     * OUT-Int(4)
     * Reason(5)
     * Decision(6)
     * LPDPDecision(7)
     * Error(8)
     * ClientSI(9)
     * KATimer(10)
     * PEPID(11)
     * Report-Type(12)
     * PDPRedirAddr(13)
     * LastPDPAddr(14)
     * AcctTimer(15)
     * Integrity(16)
     * </pre>
     * <p/>
     * (non-Javadoc)
     *
     * @see java.lang.Enum#toString()
     */
    @Override
    public String toString() {
        String id = name();
        switch (this) {
            case ININT:
                id = "IN-Int";
                break;
            case OUTINT:
                id = "OUT-Int";
                break;
            case LPDPDECISION:
                id = "LPDPDecision";
                break;
            case KATIMER:
                id = "KATimer";
                break;
            case PEPID:
                id = "PEPID";
                break;
            case CLIENTSI:
                id = "ClientSI";
                break;
            case REPORTTYPE:
                id = "Report-Type";
                break;
            case PDPREDIRADDR:
                id = "PDPRedirAddr";
                break;
            case LASTPDPADDR:
                id = "LastPDPAddr";
                break;
            case ACCTTIMER:
                id = "AcctTimer";
                break;
            default:
                id = id.charAt(0) + id.substring(1).toLowerCase();
                break;
        }
        return id + "(" + value + ")";
    }
}
