package net.protocol.cops.message;

import net.protocol.cops.CopsException;
import net.protocol.cops.CopsFormatErrorException;
import net.protocol.cops.object.CopsObject;
import net.protocol.cops.object.Error;
import net.protocol.cops.object.Integrity;
import net.protocol.cops.object.PDPRedirAddr;

import java.nio.ByteBuffer;
import java.util.Iterator;
import java.util.List;

/**
 * <h3>Client-Close (CC) PEP -> PDP, PDP -> PEP</h3>
 * <p/>
 * <p/>
 * The Client-Close message can be issued by either the PDP or PEP to notify the
 * other that a particular type of client is no longer being supported.
 * <p/>
 * <pre>
 *                &lt;Client-Close&gt;  ::= &lt;Common Header&gt;
 *                                    &lt;Error&gt;
 *                                    [&lt;PDPRedirAddr&gt;]
 *                                    [&lt;Integrity&gt;]
 * </pre>
 * <p/>
 * <p/>
 * The Error object is included to describe the reason for the close (e.g. the
 * requested client-type is not supported by the remote PDP or client failure).
 * <p/>
 * <p/>
 * A PDP MAY optionally include a PDP Redirect Address object in order to inform
 * the PEP of the alternate PDP it SHOULD use for the client- type specified in
 * the common header.
 *
 * @author jinhongw@gmail.com
 */
public class CopsCC extends CopsMessage {
    public final static OpCode OP_CODE = OpCode.CC;

    private final static int MIN_LENGTH = CopsHeader.LENGTH + Error.LENGTH;

    // Mandatory
    private Error error;

    // Optional
    private PDPRedirAddr pdpRedirAddr;
    private Integrity integrity;

    /**
     * @param clientType Client-type
     * @param error      Error
     */
    public CopsCC(int clientType, Error error) {
        super(OP_CODE, clientType, MIN_LENGTH);

        this.error = error;
    }

    /**
     * Creates a new <code>CopsCC</code> with the given source ByteBuffer
     * 
     * @param src The buffer from which bytes are to be retrieved
     * @throws CopsException
     */
    public CopsCC(ByteBuffer src) throws CopsException {
        super(src);

        if (getOpCode() != OpCode.CC)
            throw new IllegalCopsMsgException(OpCode.CC, getOpCode());

        fill(getObjects(src));
    }

    @Override
    public ByteBuffer byteMe(ByteBuffer dst) {
        super.byteMe(dst);

        error.byteMe(dst);

        if (pdpRedirAddr != null) pdpRedirAddr.byteMe(dst);
        if (integrity != null) integrity.byteMe(dst);
        
        return dst;
    }

    @Override
    protected void detail(StringBuilder buf) {
        buf.append(error);

        if (pdpRedirAddr != null)
            buf.append(", ").append(pdpRedirAddr);
        if (integrity != null)
            buf.append(", ").append(integrity);
    }

    /**
     * @return Error - Mandatory
     */
    public Error getError() {
        return error;
    }

    /**
     * @return PDPRedirAddr - Optional
     */
    public PDPRedirAddr getPdpRedirAddr() {
        return pdpRedirAddr;
    }

    /**
     * @param pdpRedirAddr Optional
     */
    public void setPdpRedirAddr(PDPRedirAddr pdpRedirAddr) {
        this.pdpRedirAddr = pdpRedirAddr;
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

        CopsCC other = (CopsCC) obj;
        if (error == null) {
            if (other.error != null)
                return false;
        } else if (!error.equals(other.error))
            return false;

        if (pdpRedirAddr == null) {
            if (other.pdpRedirAddr != null)
                return false;
        } else if (!pdpRedirAddr.equals(other.pdpRedirAddr))
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
        result = prime * result + ((error == null) ? 0 : error.hashCode());
        result = prime * result + ((pdpRedirAddr == null) ? 0 : pdpRedirAddr.hashCode());
        result = prime * result + ((integrity == null) ? 0 : integrity.hashCode());
        return result;
    }

    private int size() {
        int length = MIN_LENGTH;

        if (pdpRedirAddr != null)
            length += pdpRedirAddr.getLength();
        if (integrity != null)
            length += integrity.getLength();
        return length;
    }

    private void fill(List<CopsObject> list) throws CopsFormatErrorException {
        if (list.size() == 0)
            throw new CopsFormatErrorException(getOpCode(), "No Object(0)");

        Iterator<CopsObject> its = list.iterator();
        CopsObject er = its.next();
        if (!(er instanceof Error))
            throw new CopsFormatErrorException(getOpCode(), Error.class, er.getClass());
        error = (Error) er;

        while (its.hasNext()) {
            CopsObject e = its.next();
            switch (e.getCNum()) {
                case PDPREDIRADDR:
                    pdpRedirAddr = (PDPRedirAddr) e;
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
