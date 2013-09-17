package net.protocol.cops.object;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

/**
 * <h3>Reason Object (Reason)</h3>
 * <p/>
 * This object specifies the reason why the request state was deleted. It
 * appears in the delete request (DRQ) message. The Reason Sub-code field is
 * reserved for more detailed client-specific reason codes defined in the
 * corresponding documents.
 * <p/>
 * <p/>
 * <pre>
 *           C-Num = 5, C-Type = 1
 *
 *                0             1              2             3
 *        +--------------+--------------+--------------+--------------+
 *        |         Reason-Code         |       Reason Sub-code       |
 *        +--------------+--------------+--------------+--------------+
 *
 *            Reason Code:
 *                1 = Unspecified
 *                2 = Management
 *                3 = Preempted (Another request state takes precedence)
 *                4 = Tear (Used to communicate a signaled state removal)
 *                5 = Timeout (Local state has timed-out)
 *                6 = Route Change (Change invalidates request state)
 *                7 = Insufficient Resources (No local resource available)
 *                8 = PDP's Directive (PDP decision caused the delete)
 *                9 = Unsupported decision (PDP decision not supported)
 *                10= Synchronize Handle Unknown
 *                11= Transient Handle (stateless event)
 *                12= Malformed Decision (could not recover)
 *                13= Unknown COPS Object from PDP:
 *                    Sub-code (octet 2) contains unknown object's C-Num
 *                    and (octet 3) contains unknown object's C-Type.
 * </pre>
 *
 * @author jinhongw@gmail.com
 */
public class Reason extends CopsObject {
    public final static int LENGTH = Header.HEADER_LENGTH + 4;

    public final static CNum C_NUM = CNum.REASON;
    public final static byte C_TYPE = 1;

    private final int code;
    private final int subCode;

    /**
     * @param code    Reason-Code
     * @param subCode Reason Sub-code
     */
    public Reason(int code, int subCode) {
        super(LENGTH, C_NUM, C_TYPE);

        this.code = code;
        this.subCode = subCode;
    }

    /**
     * @param code    Reason-Code
     * @param subCode Reason Sub-code
     */
    public Reason(Reason.Code code, int subCode) {
        this(code.getValue(), subCode);
    }

    /**
     * Creates a new <code>Reason</code> with the given source ByteBuffer
     * 
     * @param src The buffer from which bytes are to be retrieved
     * @throws IllegalCopsObjectException
     */
    public Reason(ByteBuffer src) throws IllegalCopsObjectException {
        super(src);

        if (getCNum() != CNum.REASON)
            throw new IllegalCopsObjectException(CNum.REASON, getCNum());

        if (getCType() != C_TYPE)
            throw new IllegalCopsObjectException(C_TYPE, getCType());

        this.code = src.getShort();
        this.subCode = src.getShort();
    }

    /**
     * @return Reason-Code
     */
    public int getCode() {
        return code;
    }

    /**
     * @return Reason Sub-code
     */
    public int getSubCode() {
        return subCode;
    }

    @Override
    public ByteBuffer byteMe(ByteBuffer dst) {
        super.byteMe(dst);

        dst.putShort((short) code);
        dst.putShort((short) subCode);
        
        return dst;
    }

    @Override
    protected void detail(StringBuilder buf) {
        Code e = Code.valueOf(code);
        buf.append("Code=").append((e == null) ? code : e).append(", ");
        buf.append("Sub-code=").append(subCode);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (getClass() != obj.getClass())
            return false;
        Reason other = (Reason) obj;
        if (code != other.code)
            return false;
        if (subCode != other.subCode)
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + code;
        result = prime * result + subCode;
        return result;
    }

    public static enum Code {
        UNSPECIFIED(1),
        MANAGEMENT(2),
        PREEMPTED(3),
        TEAR(4),
        TIMEOUT(5),
        ROUTE_CHANGE(6),
        INSUFFICIENT_RESOURCES(7),
        PDP_DIRECTIVE(8),
        UNSUPPORTED_DECISION(9),
        SYNC_HANDLE_UNKNOWN(10),
        TRANSIENT_HANDLE(11),
        MALFORMED_DECISION(12),
        UNKNOWN_COPS_OBJECT(13);

        private final int value;

        private final static Map<Integer, Code> KVS = new HashMap<Integer, Code>();

        static {
            for (Code e : values())
                KVS.put(e.value, e);
        }

        Code(int code) {
            this.value = code;
        }

        /**
         * @return Reason-Code
         */
        public int getValue() {
            return value;
        }

        /**
         * @param value Reason-Code
         * @return Reason-Code
         */
        public static Code valueOf(int value) {
            return KVS.get(value);
        }

        @Override
        public String toString() {
            return name() + "(" + value + ")";
        }
    }
}
