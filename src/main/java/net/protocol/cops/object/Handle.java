package net.protocol.cops.object;

import java.nio.ByteBuffer;

/**
 * <h3>Handle Object (Handle)</h3>
 * <p/>
 * The Handle Object encapsulates a unique value that identifies an installed
 * state. This identification is used by most COPS operations. A state
 * corresponding to a handle MUST be explicitly deleted when it is no longer
 * applicable. See Section 2.4 for details.
 * <p/>
 * <pre>
 *            C-Num = 1
 *
 *            C-Type = 1, Client Handle.
 * </pre>
 * <p/>
 * <p/>
 * Variable-length field, no implied format other than it is unique from other
 * client handles from the same PEP (a.k.a. COPS TCP connection) for a
 * particular client-type. It is always initially chosen by the PEP and then
 * deleted by the PEP when no longer applicable. The client handle is used to
 * refer to a request state initiated by a particular PEP and installed at the
 * PDP for a client-type. A PEP will specify a client handle in its Request
 * messages, Report messages and Delete messages sent to the PDP. In all cases,
 * the client handle is used to uniquely identify a particular PEP's request for
 * a client-type.
 * <p/>
 * <p/>
 * The client handle value is set by the PEP and is opaque to the PDP. The PDP
 * simply performs a byte-wise comparison on the value in this object with
 * respect to the handle object values of other currently installed requests.
 *
 * @author jinhongw@gmail.com
 */
public class Handle extends CopsObject {
    public final static int LENGTH = Header.HEADER_LENGTH + 4;

    public final static CNum C_NUM = CNum.HANDLE;
    public final static byte C_TYPE = 1;

    private final int handle;

    /**
     * @param handle Client Handle
     */
    public Handle(int handle) {
        super(LENGTH, C_NUM, C_TYPE);

        this.handle = handle;
    }

    /**
     * Creates a new <code>Handle</code> with the given source ByteBuffer
     * 
     * @param src The buffer from which bytes are to be retrieved
     * @throws IllegalCopsObjectException
     */
    public Handle(ByteBuffer src) throws IllegalCopsObjectException {
        super(src);

        if (getCNum() != CNum.HANDLE)
            throw new IllegalCopsObjectException(CNum.HANDLE, getCNum());

        if (getCType() != C_TYPE)
            throw new IllegalCopsObjectException(C_TYPE, getCType());

        this.handle = src.getInt();
    }

    /**
     * @return Client Handle
     */
    public int getHandle() {
        return handle;
    }

    @Override
    public ByteBuffer byteMe(ByteBuffer dst) {
        super.byteMe(dst);

        dst.putInt(handle);
        
        return dst;
    }

    @Override
    protected void detail(StringBuilder buf) {
        buf.append("Client Handle=").append(handle);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (getClass() != obj.getClass())
            return false;
        Handle other = (Handle) obj;
        if (handle != other.handle)
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + handle;
        return result;
    }
}
