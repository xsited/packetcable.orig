package net.protocol.cops.message;

import net.protocol.cops.CopsException;
import net.protocol.cops.CopsFormatErrorException;
import net.protocol.cops.object.CopsObject;
import net.protocol.cops.object.Handle;
import net.protocol.cops.object.Integrity;

import java.nio.ByteBuffer;
import java.util.List;

/**
 * <pre>
 *          &lt;Synchronize State&gt; ::= &lt;Common Header&gt;
 *                                  [&lt;Client Handle&gt;]
 *                                  [&lt;Integrity&gt;]
 *
 *          &lt;Synchronize State Complete&gt;  ::= &lt;Common Header&gt;
 *                                            [&lt;Client Handle&gt;]
 *                                            [&lt;Integrity&gt;]
 * </pre>
 *
 * @author jinhongw@gmail.com
 */
class CopsSS extends CopsMessage {
    private final static int MIN_LENGTH = CopsHeader.LENGTH;
    private final static int Handle_LENGTH = CopsHeader.LENGTH + Handle.LENGTH;

    // Optional
    private Handle handle;
    private Integrity integrity;

    /**
     * @param clientType Client-type
     */
    protected CopsSS(OpCode opCode, int clientType) {
        super(opCode, clientType, MIN_LENGTH);
    }

    /**
     * @param clientType Client-type
     * @param handle     Client Handle
     */
    protected CopsSS(OpCode opCode, int clientType, int handle) {
        super(opCode, clientType, Handle_LENGTH);

        this.handle = new Handle(handle);
    }

    /**
     * Creates a new <code>CopsSS</code> with the given source ByteBuffer
     * 
     * @param src The buffer from which bytes are to be retrieved
     * @throws CopsException
     */
    protected CopsSS(ByteBuffer src) throws CopsException {
        super(src);

        OpCode op = getOpCode();
        if (!(op == OpCode.SSQ || op == OpCode.SSC)) {
            Object expected = OpCode.SSQ + " | " + OpCode.SSC;
            throw new IllegalCopsMsgException(expected, op);
        }

        fill(getObjects(src));
    }

    @Override
    public ByteBuffer byteMe(ByteBuffer dst) {
        super.byteMe(dst);

        if (handle != null) handle.byteMe(dst);
        if (integrity != null) integrity.byteMe(dst);
        
        return dst;
    }

    @Override
    protected void detail(StringBuilder buf) {
        if (handle != null)
            buf.append(", ").append(handle);
        if (integrity != null)
            buf.append(", ").append(integrity);
    }

    /**
     * @return Handle - Optional
     */
    public Handle getHandle() {
        return handle;
    }

    /**
     * @param handle Optional
     */
    public void setHandle(Handle handle) {
        this.handle = handle;
        this.setMessageLength(size()); // must
    }

    /**
     * @return Integrity - Optional
     */
    public Integrity getIntegrity() {
        return integrity;
    }

    /**
     * @param integrity Optional
     */
    public void setIntegrity(Integrity integrity) {
        this.integrity = integrity;
        this.setMessageLength(size()); // must
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (getClass() != obj.getClass())
            return false;

        CopsSS other = (CopsSS) obj;
        if (handle == null) {
            if (other.handle != null)
                return false;
        } else if (!handle.equals(other.handle))
            return false;

        if (integrity == null) {
            if (other.integrity != null)
                return false;
        } else if (!integrity.equals(other.integrity))
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((handle == null) ? 0 : handle.hashCode());
        result = prime * result + ((integrity == null) ? 0 : integrity.hashCode());
        return result;
    }

    private int size() {
        int length = MIN_LENGTH;
        if (handle != null)
            length += handle.getLength();
        if (integrity != null)
            length += integrity.getLength();
        return length;
    }

    private void fill(List<CopsObject> list) throws CopsFormatErrorException {
        for (CopsObject e : list) {
            switch (e.getCNum()) {
                case HANDLE:
                    handle = (Handle) e;
                    break;
                case INTEGRITY:
                    integrity = (Integrity) e;
                    break;
                default:
                    throw new CopsFormatErrorException(getOpCode(), e.getCNum());
            }
        }
    }
}
