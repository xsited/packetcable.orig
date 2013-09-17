package net.protocol.cops.object;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

/**
 * <h3>Context Object (Context)</h3>
 * <p/>
 * <p/>
 * Specifies the type of event(s) that triggered the query. Required for request
 * messages. Admission control, resource allocation, and forwarding requests are
 * all amenable to client-types that outsource their decision making facility to
 * the PDP. For applicable client- types a PEP can also make a request to
 * receive named configuration information from the PDP. This named
 * configuration data may be in a form useful for setting system attributes on a
 * PEP, or it may be in the form of policy rules that are to be directly
 * verified by the PEP.
 * <p/>
 * <p/>
 * Multiple flags can be set for the same request. This is only allowed,
 * however, if the set of client specific information in the combined request is
 * identical to the client specific information that would be specified if
 * individual requests were made for each specified flag.
 * <p/>
 * <pre>
 * 		  C-Num = 2, C-Type = 1
 *
 *
 *               0             1               2               3
 *        +--------------+--------------+--------------+--------------+
 *        |            R-Type           |            M-Type           |
 *        +--------------+--------------+--------------+--------------+
 *
 *            R-Type (Request Type Flag)
 *
 *                0x01 = Incoming-Message/Admission Control request
 *                0x02 = Resource-Allocation request
 *                0x04 = Outgoing-Message request
 *                0x08 = Configuration request
 *
 *            M-Type (Message Type)
 *
 *                Client Specific 16 bit values of protocol message types
 * </pre>
 *
 * @author jinhongw@gmail.com
 */
public class Context extends CopsObject {
    public final static int LENGTH = Header.HEADER_LENGTH + 4;

    public final static CNum C_NUM = CNum.CONTEXT;
    public final static byte C_TYPE = 1;

    private final int rType;
    private final int mType;

    /**
     * @param rType R-Type
     * @param mType M-Type
     */
    public Context(int rType, int mType) {
        super(LENGTH, C_NUM, C_TYPE);

        this.rType = rType;
        this.mType = mType;
    }

    /**
     * @param rType R-Type
     * @param mType M-Type
     */
    public Context(Context.RType rType, int mType) {
        this(rType.getValue(), mType);
    }

    /**
     * Creates a new <code>Context</code> with the given source ByteBuffer
     * 
     * @param src The buffer from which bytes are to be retrieved
     * @throws IllegalCopsObjectException
     */
    public Context(ByteBuffer src) throws IllegalCopsObjectException {
        super(src);

        if (getCNum() != CNum.CONTEXT)
            throw new IllegalCopsObjectException(CNum.CONTEXT, getCNum());

        if (getCType() != C_TYPE)
            throw new IllegalCopsObjectException(C_TYPE, getCType());

        this.rType = src.getShort();
        this.mType = src.getShort();
    }

    /**
     * @return R-Type
     */
    public int getRType() {
        return rType;
    }

    /**
     * @return M-Type
     */
    public int getMType() {
        return mType;
    }

    @Override
    public ByteBuffer byteMe(ByteBuffer dst) {
        super.byteMe(dst);

        dst.putShort((short) rType);
        dst.putShort((short) mType);
        
        return dst;
    }

    @Override
    protected void detail(StringBuilder buf) {
        RType e = RType.valueOf(rType);
        buf.append("R-Type=").append((e == null) ? rType : e).append(", ");
        buf.append("M-Type=").append(mType);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (getClass() != obj.getClass())
            return false;
        Context other = (Context) obj;
        if (mType != other.mType)
            return false;
        if (rType != other.rType)
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + mType;
        result = prime * result + rType;
        return result;
    }

    public static enum RType {
        INCOMING_MESSAGE(0x1),
        RESOURCE_ALLOCATION(0x2),
        OUTGOING_MESSAGE(0x4),
        CONFIGURATION(0x8);

        private final int value;

        private final static Map<Integer, RType> KVS = new HashMap<Integer, RType>();

        static {
            for (RType e : values()) {
                KVS.put(e.value, e);
            }
        }

        RType(int value) {
            this.value = value;
        }

        /**
         * @return R-Type
         */
        public int getValue() {
            return value;
        }

        /**
         * @param value R-Type
         * @return R-Type
         */
        public static RType valueOf(int value) {
            return KVS.get(value);
        }

        @Override
        public String toString() {
            return name() + "(" + value + ")";
        }
    }
}
