package net.protocol.cops.object;

import net.protocol.common.util.Ints;

import java.nio.ByteBuffer;
import java.util.Arrays;

/**
 * <h3>PEP Identification Object (PEPID)</h3>
 * <p/>
 * <p/>
 * The PEP Identification Object is used to identify the PEP client to the
 * remote PDP. It is required for Client-Open messages.
 * <p/>
 * <pre>
 *            C-Num = 11, C-Type = 1
 * </pre>
 * <p/>
 * <p/>
 * Variable-length field. It is a NULL terminated ASCII string that is also zero
 * padded to a 32-bit word boundary (so the object length is a multiple of 4
 * octets). The PEPID MUST contain an ASCII string that uniquely identifies the
 * PEP within the policy domain in a manner that is persistent across PEP
 * reboots. For example, it may be the PEP's statically assigned IP address or
 * DNS name. This identifier may safely be used by a PDP as a handle for
 * identifying the PEP in its policy rules.
 *
 * @author jinhongw@gmail.com
 */
public class PEPID extends CopsObject {

    public final static CNum C_NUM = CNum.PEPID;
    public final static byte C_TYPE = 1;

    private final byte[] pepid;

    /**
     * @param pepid variable-length PEPID field
     */
    public PEPID(byte[] pepid) {
        super(size(pepid), C_NUM, C_TYPE);

        this.pepid = pepid;
    }

    /**
     * Creates a new <code>PEPID</code> with the given source ByteBuffer
     * 
     * @param src The buffer from which bytes are to be retrieved
     * @throws IllegalCopsObjectException
     */
    public PEPID(ByteBuffer src) throws IllegalCopsObjectException {
        super(src);

        if (getCNum() != CNum.PEPID)
            throw new IllegalCopsObjectException(CNum.PEPID, getCNum());

        if (getCType() != C_TYPE)
            throw new IllegalCopsObjectException(C_TYPE, getCType());

        int dataLength = getLength() - Header.HEADER_LENGTH;
        final byte[] dst = new byte[dataLength];
        src.get(dst);
        this.pepid = dst;
        
        // skip a number of zero-valued bytes padding data
        int paddingSize = Ints.paddingSize(pepid.length);
        if (paddingSize > 0) src.position(src.position() + paddingSize);
    }

    /**
     * @return variable-length PEPID field
     */
    public byte[] getPepid() {
        return pepid;
    }

    @Override
    public ByteBuffer byteMe(ByteBuffer dst) {
        super.byteMe(dst);

        dst.put(pepid);
        // padding with zeroes to the next 32-bit boundary
        int paddingSize = Ints.paddingSize(pepid.length);
        if (paddingSize > 0) dst.put(empty, 0, paddingSize);
        
        return dst;
    }

    @Override
    protected void detail(StringBuilder buf) {
        buf.append("pepid=").append(new String(pepid));
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (getClass() != obj.getClass())
            return false;
        PEPID other = (PEPID) obj;
        if (!Arrays.equals(pepid, other.pepid))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + Arrays.hashCode(pepid);
        return result;
    }

    private static int size(byte[] pepid) {
        return Header.HEADER_LENGTH + pepid.length;
    }
}
