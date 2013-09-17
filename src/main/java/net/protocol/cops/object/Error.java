package net.protocol.cops.object;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

/**
 * <h3>Error Object (Error)</h3>
 * <p/>
 * This object is used to identify a particular COPS protocol error. The error
 * sub-code field contains additional detailed client specific error codes. The
 * appropriate Error Sub-codes for a particular client-type SHOULD be specified
 * in the relevant COPS extensions document.
 * <p/>
 * <pre>
 *             C-Num = 8, C-Type = 1
 *
 *                0             1              2             3
 *        +--------------+--------------+--------------+--------------+
 *        |          Error-Code         |        Error Sub-code       |
 *        +--------------+--------------+--------------+--------------+
 *
 *            Error-Code:
 *
 *                1 = Bad handle
 *                2 = Invalid handle reference
 *                3 = Bad message format (Malformed Message)
 *                4 = Unable to process (server gives up on query)
 *                5 = Mandatory client-specific info missing
 *                6 = Unsupported client-type
 *                7 = Mandatory COPS object missing
 *                8 = Client Failure
 *                9 = Communication Failure
 *                10= Unspecified
 *                11= Shutting down
 *                12= Redirect to Preferred Server
 *                13= Unknown COPS Object:
 *                    Sub-code (octet 2) contains unknown object's C-Num
 *                    and (octet 3) contains unknown object's C-Type.
 *                14= Authentication Failure
 *                15= Authentication Required
 * </pre>
 *
 * @author jinhongw@gmail.com
 */
public class Error extends CopsObject {
    public final static int LENGTH = Header.HEADER_LENGTH + 4;

    public final static CNum C_NUM = CNum.ERROR;
    public final static byte C_TYPE = 1;

    private final int code;
    private final int subCode;

    /**
     * @param code    Error-Code
     * @param subCode Error Sub-code
     */
    public Error(int code, int subCode) {
        super(LENGTH, C_NUM, C_TYPE);

        this.code = code;
        this.subCode = subCode;
    }

    /**
     * @param code    Error-Code
     * @param subCode Error Sub-code
     */
    public Error(Error.Code code, int subCode) {
        this(code.getValue(), subCode);
    }

    /**
     * Creates a new <code>Error</code> with the given source ByteBuffer
     * 
     * @param src The buffer from which bytes are to be retrieved
     * @throws IllegalCopsObjectException
     */
    public Error(ByteBuffer src) throws IllegalCopsObjectException {
        super(src);

        if (getCNum() != CNum.ERROR)
            throw new IllegalCopsObjectException(CNum.ERROR, getCNum());

        if (getCType() != C_TYPE)
            throw new IllegalCopsObjectException(C_TYPE, getCType());

        this.code = src.getShort();
        this.subCode = src.getShort();
    }

    /**
     * @return Error-Code
     */
    public int getCode() {
        return code;
    }

    /**
     * @return Error Sub-code
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
        Error other = (Error) obj;
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
        BAD_HANDLE(1),
        INVALID_HANDLE_REFERENCE(2),
        BAD_MESSAGE_FORMAT(3),
        UNABLE_TO_PROCESS(4),
        MANDATORY_CLIENTSPECIFIC_INFO_MISSING(5),
        UNSUPPORTED_CLIENTTYPE(6),
        MANDATORY_COPS_OBJECT_MISSING(7),
        CLIENT_FAILURE(8),
        COMMUNICATION_FAILURE(9),
        UNSPECIFIED(10),
        SHUTTING_DOWN(11),
        REDIRECT_TO_PREFERRED_SERVER(12),
        UNKNOWN_COPS_OBJECT(13),
        AUTHENTICATION_FAILURE(14),
        AUTHENTICATION_REQUIRED(15);

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
         * @return Error-Code
         */
        public int getValue() {
            return value;
        }

        /**
         * @param value Error-Code
         * @return Error-Code
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
