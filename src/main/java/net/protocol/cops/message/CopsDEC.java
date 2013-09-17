package net.protocol.cops.message;

import net.protocol.cops.CopsException;
import net.protocol.cops.CopsFormatErrorException;
import net.protocol.cops.object.*;
import net.protocol.cops.object.Error;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * <h3>Decision (DEC) PDP -> PEP</h3>
 * <p/>
 * The PDP responds to the REQ with a DEC message that includes the associated
 * client handle and one or more decision objects grouped relative to a Context
 * object and Decision Flags object type pair. If there was a protocol error an
 * error object is returned instead.
 * <p/>
 * <p/>
 * It is required that the first decision message for a new/updated request will
 * have the solicited message flag set (value = 1) in the COPS header. This
 * avoids the issue of keeping track of which updated request (that is, a
 * request reissued for the same handle) a particular decision corresponds. It
 * is important that, for a given handle, there be at most one outstanding
 * solicited decision per request. This essentially means that the PEP SHOULD
 * NOT issue more than one REQ (for a given handle) before it receives a
 * corresponding DEC with the solicited message flag set. The PDP MUST always
 * issue decisions for requests on a particular handle in the order they arrive
 * and all requests MUST have a corresponding decision.
 * <p/>
 * <p/>
 * To avoid deadlock, the PEP can always timeout after issuing a request that
 * does not receive a decision. It MUST then delete the timed-out handle, and
 * may try again using a new handle.
 * <p/>
 * <p/>
 * The format of the Decision message is as follows:
 * <p/>
 * <pre>
 *                &lt;Decision Message&gt; ::= &lt;Common Header&gt;
 *                                       &lt;Client Handle&gt;
 *                                       &lt;Decision(s)&gt; | &lt;Error&gt;
 *                                       [&lt;Integrity&gt;]
 *
 *                &lt;Decision(s)&gt; ::= &lt;Decision&gt; | &lt;Decision(s)&gt; &lt;Decision&gt;
 *
 *                &lt;Decision&gt; ::= &lt;Context&gt;
 *                               &lt;Decision: Flags&gt;
 *                               [&lt;Decision: Stateless Data&gt;]
 *                               [&lt;Decision: Replacement Data&gt;]
 *                               [&lt;Decision: ClientSI Data&gt;]
 *                               [&lt;Decision: Named Data&gt;]
 * </pre>
 * <p/>
 * <p/>
 * The Decision message may include either an Error object or one or more
 * context plus associated decision objects. COPS protocol problems are reported
 * in the Error object (e.g. an error with the format of the original request
 * including malformed request messages, unknown COPS objects in the Request,
 * etc.). The applicable Decision object(s) depend on the context and the type
 * of client. The only ordering requirement for decision objects is that the
 * required Decision Flags object type MUST precede the other Decision object
 * types per context binding.
 *
 * @author jinhongw@gmail.com
 */
public class CopsDEC extends CopsMessage {
    public final static OpCode OP_CODE = OpCode.DEC;

    private Handle handle; // Mandatory

    // <Decision(s)> | <Error> Mandatory
    private List<Dec> decs = new ArrayList<Dec>();
    private net.protocol.cops.object.Error error;

    private Integrity integrity; // Optional

    /**
     * @param clientType Client-type
     * @param handle     Client Handle
     * @param decs       List<Dec>
     */
    public CopsDEC(int clientType, int handle, List<Dec> decs) {
        this(clientType, new Handle(handle), decs, null);
    }

    /**
     * @param clientType Client-type
     * @param handle     Client Handle
     * @param error      Error
     */
    public CopsDEC(int clientType, int handle, Error error) {
        this(clientType, new Handle(handle), new ArrayList<Dec>(), error);
    }

    protected CopsDEC(int clientType, Handle handle, List<Dec> decs, Error error) {
        super(OP_CODE, clientType);

        this.handle = handle;
        this.decs = decs;
        this.error = error;

        this.setMessageLength(size());
    }

    /**
     * Creates a new <code>CopsDEC</code> with the given source ByteBuffer
     * 
     * @param src The buffer from which bytes are to be retrieved
     * @throws net.protocol.cops.CopsException
     */
    public CopsDEC(ByteBuffer src) throws CopsException {
        super(src);

        if (getOpCode() != OpCode.DEC)
            throw new IllegalCopsMsgException(OpCode.DEC, getOpCode());

        fill(getObjects(src));
    }

    /**
     * @return Handle - Mandatory
     */
    public Handle getHandle() {
        return handle;
    }

    /**
     * @return List<Dec> unmodifiable list
     * @throws UnsupportedOperationException if isDecision() == false
     */
    public List<Dec> getDecs() {
        if (isDec())
            return Collections.unmodifiableList(decs);
        throw new UnsupportedOperationException();
    }

    /**
     * @return net.protocol.cops.object.Error
     * @throws UnsupportedOperationException if isDecision() == true
     */
    public net.protocol.cops.object.Error getError() {
        if (isDec())
            throw new UnsupportedOperationException();
        return error;
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
    public ByteBuffer byteMe(ByteBuffer dst) {
        super.byteMe(dst);

        handle.byteMe(dst);
        if (isDec())
            for (Dec e : decs)
                e.byteMe(dst);
        else
            error.byteMe(dst);
        if (integrity != null) integrity.byteMe(dst);
        
        return dst;
    }

    @Override
    protected void detail(StringBuilder buf) {
        buf.append(handle);
        if (isDec())
            for (Dec e : decs)
                buf.append(", ").append(e);
        else
            buf.append(", ").append(error);
        if (integrity != null)
            buf.append(", ").append(integrity);
    }

    public boolean isDec() {
        if ((decs != null && decs.size() != 0) && (error == null))
            return true;
        return false;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (getClass() != obj.getClass())
            return false;

        CopsDEC other = (CopsDEC) obj;
        if (handle == null) {
            if (other.handle != null)
                return false;
        } else if (!handle.equals(other.handle))
            return false;

        if (isDec()) {
            if (decs == null) {
                if (other.decs != null)
                    return false;
            } else if (!decs.equals(other.decs))
                return false;
        } else {
            if (error == null) {
                if (other.error != null)
                    return false;
            } else if (!error.equals(other.error))
                return false;
        }

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
        if (isDec()) {
            result = prime * result + ((decs == null) ? 0 : decs.hashCode());
        } else {
            result = prime * result + ((error == null) ? 0 : error.hashCode());
        }
        result = prime * result + ((integrity == null) ? 0 : integrity.hashCode());
        return result;
    }

    private int size() {
        int length = CopsHeader.LENGTH + Handle.LENGTH;
        if (isDec())
            length += size(decs);
        else
            length += Error.LENGTH;
        if (integrity != null)
            length += integrity.getLength();
        return length;
    }

    private int size(List<Dec> decs) {
        if (decs == null || decs.size() == 0)
            return 0;
        int length = 0;
        for (Dec e : decs) {
            length += e.size();
        }
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

        Dec dec = null;
        while (its.hasNext()) {
            CopsObject e = its.next();
            switch (e.getCNum()) {
                case ERROR:
                    error = (net.protocol.cops.object.Error) e;
                    break;
                case INTEGRITY:
                    integrity = (Integrity) e;
                    break;
                case CONTEXT:
                    dec = new Dec((Context) e);
                    decs.add(dec);
                    break;
                case DECISION:
                    if (dec == null)
                        throw new CopsFormatErrorException(getOpCode(),
                                CNum.CONTEXT);

                    dec.add((Decision) e);
                    break;
                default:
                    throw new CopsFormatErrorException(getOpCode(), e.getCNum());
            }
        }
    }
}
