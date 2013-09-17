package net.protocol.cops.object;

import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * <h3>Client Specific Information Object (ClientSI)</h3>
 * <p/>
 * <p/>
 * The various types of this object are required for requests, and used in
 * reports and opens when required. It contains client-type specific
 * information.
 * <p/>
 * <pre>
 *            C-Num = 9,
 *
 *            C-Type = 1, Signaled ClientSI.
 * </pre>
 * <p/>
 * <p/>
 * Variable-length field. All objects/attributes specific to a client's
 * signaling protocol or internal state are encapsulated within one or more
 * signaled Client Specific Information Objects. The format of the data
 * encapsulated in the ClientSI object is determined by the client-type.
 * <p/>
 * <pre>
 *
 *            C-Type = 2, Named ClientSI.
 * </pre>
 * <p/>
 * Variable-length field. Contains named configuration information useful for
 * relaying specific information about the PEP, a request, or configured state
 * to the PDP server.
 *
 * @author jinhongw@gmail.com
 */
public class ClientSI extends CopsObject {
    public final static CNum C_NUM = CNum.CLIENTSI;

    private final byte[] data;

    /**
     * @param ctype ClientSI C-Type
     * @param data  ClientSI data
     */
    public ClientSI(ClientSI.CType ctype, byte[] data) {
        super(size(data), C_NUM, ctype.value);

        this.data = data;
    }

    /**
     * Creates a new <code>ClientSI</code> with the given source ByteBuffer
     * 
     * @param src The buffer from which bytes are to be retrieved
     * @throws IllegalCopsObjectException
     */
    public ClientSI(ByteBuffer src) throws IllegalCopsObjectException {
        super(src);

        if (getCNum() != CNum.CLIENTSI)
            throw new IllegalCopsObjectException(CNum.CLIENTSI, getCNum());

        if (ctype() == null)
            throw new IllegalCopsObjectException(ClientSI.CType.class, getCType());

        int length = getLength() - Header.HEADER_LENGTH;
        final byte[] dst = new byte[length];
        src.get(dst);
        this.data = dst;

        // skip a number of zero-valued bytes padding data
        // int paddingSize = Ints.paddingSize(data.length);
        // if (paddingSize > 0) src.position(src.position() + paddingSize);
    }

    /**
     * @return ClientSI data
     */
    public byte[] getData() {
        return data;
    }

    @Override
    public ByteBuffer byteMe(ByteBuffer dst) {
        super.byteMe(dst);

        dst.put(data);
        // padding with zeroes to the next 32-bit boundary
        // int paddingSize = Ints.paddingSize(data.length);
        // if (paddingSize > 0) dst.put(empty, 0, paddingSize);
        
        return dst;
    }

    @Override
    protected void detail(StringBuilder buf) {
        // buf.append(ctype());
        buf.append("data");
        buf.append("=").append(new String(data));
    }

    /**
     * Wraps a value of primitive type <code>byte</code> (C-Type) in an
     * enum(ClientSI.CType)
     *
     * @return ClientSI.CType
     * @see CopsObject#getCType()
     */
    public ClientSI.CType ctype() {
        return CType.valueOf(getCType());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (getClass() != obj.getClass())
            return false;
        ClientSI other = (ClientSI) obj;
        if (!Arrays.equals(data, other.data))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + Arrays.hashCode(data);
        return result;
    }

    private static int size(byte[] data) {
        return Header.HEADER_LENGTH + data.length;
    }

    public static enum CType {
        SIGNALED((byte) 1), NAMED((byte) 2);

        private final byte value;

        private final static Map<Byte, CType> KVS = new HashMap<Byte, CType>();

        static {
            for (CType e : values())
                KVS.put(e.value, e);
        }

        CType(byte value) {
            this.value = value;
        }

        public byte getValue() {
            return value;
        }

        public static CType valueOf(byte value) {
            return KVS.get(value);
        }

        @Override
        public String toString() {
            String id = name();
            id = id.charAt(0) + id.substring(1).toLowerCase();
            return id + "(" + value + ")";
        }
    }
}
