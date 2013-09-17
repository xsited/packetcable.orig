package net.protocol.cops.object;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

/**
 * <h3>Report-Type Object (Report-Type)</h3>
 * <p/>
 * The Type of Report on the request state associated with a handle:
 * <p/>
 * <pre>
 *     	  C-Num = 12, C-Type = 1
 *
 *                0             1              2             3
 *        +--------------+--------------+--------------+--------------+
 *        |         Report-Type         |        /////////////        |
 *        +--------------+--------------+--------------+--------------+
 *
 *            Report-Type:
 *                1 = Success   : Decision was successful at the PEP
 *                2 = Failure   : Decision could not be completed by PEP
 *                3 = Accounting: Accounting update for an installed state
 * </pre>
 *
 * @author jinhongw@gmail.com
 */
public class ReportType extends CopsObject {
    public final static int LENGTH = Header.HEADER_LENGTH + 4;

    public final static CNum C_NUM = CNum.REPORTTYPE;
    public final static byte C_TYPE = 1;

    private final int type;

    /**
     * @param type Report-Type
     */
    public ReportType(int type) {
        super(LENGTH, C_NUM, C_TYPE);

        this.type = type;
    }

    /**
     * @param type Report-Type
     */
    public ReportType(ReportType.Type type) {
        this(type.getValue());
    }

    /**
     * Creates a new <code>ReportType</code> with the given source ByteBuffer
     * 
     * @param src The buffer from which bytes are to be retrieved
     * @throws IllegalCopsObjectException
     */
    public ReportType(ByteBuffer src) throws IllegalCopsObjectException {
        super(src);

        if (getCNum() != CNum.REPORTTYPE)
            throw new IllegalCopsObjectException(CNum.REPORTTYPE, getCNum());

        if (getCType() != C_TYPE)
            throw new IllegalCopsObjectException(C_TYPE, getCType());

        this.type = src.getShort();
        src.getShort();
    }

    /**
     * @return Report-Type
     */
    public int getType() {
        return type;
    }

    @Override
    public ByteBuffer byteMe(ByteBuffer dst) {
        super.byteMe(dst);

        dst.putShort((short) type);
        dst.putShort((short) 0);
        
        return dst;
    }

    @Override
    protected void detail(StringBuilder buf) {
        Type e = Type.valueOf(type);
        buf.append("Report-Type=").append((e == null) ? type : e);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (getClass() != obj.getClass())
            return false;
        ReportType other = (ReportType) obj;
        if (type != other.type)
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + type;
        return result;
    }

    public static enum Type {
        Success(0x1), Failure(0x2), Accounting(0x3);

        private final int value;

        private final static Map<Integer, Type> KVS = new HashMap<Integer, Type>();

        static {
            for (Type e : values()) {
                KVS.put(e.value, e);
            }
        }

        Type(int code) {
            this.value = code;
        }

        public int getValue() {
            return value;
        }

        /**
         * @param value Report-Type
         * @return Report-Type
         */
        public static Type valueOf(int value) {
            return KVS.get(value);
        }

        @Override
        public String toString() {
            return name() + "(" + value + ")";
        }
    }
}
