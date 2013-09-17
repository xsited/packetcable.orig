package net.protocol.cops.message;

import net.protocol.cops.CopsException;
import net.protocol.cops.CopsFormatErrorException;
import net.protocol.cops.object.CopsObject;
import net.protocol.cops.object.Handle;
import net.protocol.cops.object.Integrity;
import net.protocol.cops.object.Reason;

import java.nio.ByteBuffer;
import java.util.Iterator;
import java.util.List;

/**
 * <h3>Delete Request State (DRQ) PEP -> PDP</h3>
 * <p/>
 * <p/>
 * When sent from the PEP this message indicates to the remote PDP that the
 * state identified by the client handle is no longer available/relevant. This
 * information will then be used by the remote PDP to initiate the appropriate
 * housekeeping actions. The reason code object is interpreted with respect to
 * the client-type and signifies the reason for the removal.
 * <p/>
 * <pre>
 *    The format of the Delete Request State message is as follows:
 *
 *               &lt;Delete Request&gt;  ::= &lt;Common Header&gt;
 *                                     &lt;Client Handle&gt;
 *                                     &lt;Reason&gt;
 *                                     [&lt;Integrity&gt;]
 * </pre>
 * <p/>
 * <p/>
 * Given the stateful nature of COPS, it is important that when a request state
 * is finally removed from the PEP, a DRQ message for this request state is sent
 * to the PDP so the corresponding state may likewise be removed on the PDP.
 * Request states not explicitly deleted by the PEP will be maintained by the
 * PDP until either the client session is closed or the connection is
 * terminated.
 * <p/>
 * <p/>
 * Malformed Decision messages MUST trigger a DRQ specifying the appropriate
 * erroneous reason code (Bad Message DiameterFormat) and any associated state on the
 * PEP SHOULD either be removed or re-requested. If a Decision contained an
 * unknown COPS Decision Object, the PEP MUST delete its request specifying the
 * Unknown COPS Object reason code because the PEP will be unable to comply with
 * the information contained in the unknown object. In any case, after issuing a
 * DRQ, the PEP may retry the corresponding Request again.
 *
 * @author jinhongw@gmail.com
 */
public class CopsDRQ extends CopsMessage {
    public final static OpCode OP_CODE = OpCode.DRQ;

    private final static int MIN_LENGTH = CopsHeader.LENGTH + Handle.LENGTH + Reason.LENGTH;

    // Mandatory
    private Handle handle;
    private Reason reason;

    // Optional
    private Integrity integrity;

    /**
     * @param clientType Client-type
     * @param handle     Client Handle
     * @param reason     Reason
     */
    public CopsDRQ(int clientType, int handle, Reason reason) {
        super(OP_CODE, clientType, MIN_LENGTH);

        this.handle = new Handle(handle);
        this.reason = reason;
    }

    /**
     * Creates a new <code>CopsDRQ</code> with the given source ByteBuffer
     * 
     * @param src The buffer from which bytes are to be retrieved
     * @throws CopsException
     */
    public CopsDRQ(ByteBuffer src) throws CopsException {
        super(src);

        if (getOpCode() != OpCode.DRQ)
            throw new IllegalCopsMsgException(OpCode.DRQ, getOpCode());

        fill(getObjects(src));
    }

    @Override
    public ByteBuffer byteMe(ByteBuffer dst) {
        super.byteMe(dst);

        handle.byteMe(dst);
        reason.byteMe(dst);

        if (integrity != null) integrity.byteMe(dst);
        
        return dst;
    }

    @Override
    protected void detail(StringBuilder buf) {
        buf.append(handle).append(", ");
        buf.append(reason);

        if (integrity != null)
            buf.append(", ").append(integrity);
    }

    /**
     * @return Handle - Mandatory
     */
    public Handle getHandle() {
        return handle;
    }

    /**
     * @return Reason - Mandatory
     */
    public Reason getReason() {
        return reason;
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

        CopsDRQ other = (CopsDRQ) obj;
        if (handle == null) {
            if (other.handle != null)
                return false;
        } else if (!handle.equals(other.handle))
            return false;

        if (reason == null) {
            if (other.reason != null)
                return false;
        } else if (!reason.equals(other.reason))
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
        result = prime * result + ((reason == null) ? 0 : reason.hashCode());
        result = prime * result + ((integrity == null) ? 0 : integrity.hashCode());
        return result;
    }

    private int size() {
        int length = MIN_LENGTH;

        if (integrity != null)
            length += integrity.getLength();
        return length;
    }

    private void fill(List<CopsObject> list) throws CopsFormatErrorException {
        if (list.size() == 0)
            throw new CopsFormatErrorException(getOpCode(), "No Object(0)");

        Iterator<CopsObject> its = list.iterator();
        CopsObject hd = its.next();
        if (!(hd instanceof Handle))
            throw new CopsFormatErrorException(getOpCode(), Handle.class, hd.getClass());
        handle = (Handle) hd;

        CopsObject rs = its.next();
        if (!(rs instanceof Reason))
            throw new CopsFormatErrorException(getOpCode(), Reason.class, rs.getClass());
        reason = (Reason) rs;

        while (its.hasNext()) {
            CopsObject e = its.next();
            switch (e.getCNum()) {
                case INTEGRITY:
                    integrity = (Integrity) e;
                    break;
                default:
                    throw new CopsFormatErrorException(getOpCode(), e.getCNum());
            }
        }
    }
}
