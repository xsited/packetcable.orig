package net.protocol.cops.message;

import net.protocol.cops.CopsException;
import net.protocol.cops.CopsFormatErrorException;
import net.protocol.cops.object.*;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * <h3>Request (REQ) PEP -> PDP</h3>
 * <p/>
 * <p/>
 * The PEP establishes a request state client handle for which the remote PDP
 * may maintain state. The remote PDP then uses this handle to refer to the
 * exchanged information and decisions communicated over the TCP connection to a
 * particular PEP for a given client-type.
 * <p/>
 * <p/>
 * Once a stateful handle is established for a new request, any subsequent
 * modifications of the request can be made using the REQ message specifying the
 * previously installed handle. The PEP is responsible for notifying the PDP
 * whenever its local state changes so the PDP's state will be able to
 * accurately mirror the PEP's state.
 * <p/>
 * <pre>
 *    The format of the Request message is as follows:
 *
 *                &lt;Request Message&gt; ::=  &lt;Common Header&gt;
 *                                       &lt;Client Handle&gt;
 *                                       &lt;Context&gt;
 *                                       [&lt;IN-Int&gt;]
 *                                       [&lt;OUT-Int&gt;]
 *                                       [&lt;ClientSI(s)&gt;]
 *                                       [&lt;LPDPLDecision(s)&gt;]
 *                                       [&lt;Integrity&gt;]
 *
 *                &lt;ClientSI(s)&gt; ::= &lt;ClientSI&gt; | &lt;ClientSI(s)&gt; &lt;ClientSI&gt;
 *
 *                &lt;LPDPLDecision(s)&gt; ::= &lt;LPDPLDecision&gt; |
 *                                      &lt;LPDPLDecision(s)&gt; &lt;LPDPLDecision&gt;
 *
 *                &lt;LPDPLDecision&gt; ::= [&lt;Context&gt;]
 *                                   &lt;LPDPLDecision: Flags&gt;
 *                                   [&lt;LPDPLDecision: Stateless Data&gt;]
 *                                   [&lt;LPDPLDecision: Replacement Data&gt;]
 *                                   [&lt;LPDPLDecision: ClientSI Data&gt;]
 *                                   [&lt;LPDPLDecision: Named Data&gt;]
 * </pre>
 * <p/>
 * The context object is used to determine the context within which all the
 * other objects are to be interpreted. It also is used to determine the kind of
 * decision to be returned from the policy server. This decision might be
 * related to admission control, resource allocation, object forwarding and
 * substitution, or configuration.
 * <p/>
 * <p/>
 * The interface objects are used to determine the corresponding interface on
 * which a signaling protocol message was received or is about to be sent. They
 * are typically used if the client is participating along the path of a
 * signaling protocol or if the client is requesting configuration data for a
 * particular interface.
 * <p/>
 * <p/>
 * ClientSI, the client specific information object, holds the client- type
 * specific data for which a policy decision needs to be made. In the case of
 * configuration, the Named ClientSI may include named information about the
 * module, interface, or functionality to be configured. The ordering of
 * multiple ClientSIs is not important.
 * <p/>
 * <p/>
 * Finally, LPDPLDecision object holds information regarding the local decision
 * made by the LPDP.
 * <p/>
 * <p/>
 * Malformed Request messages MUST result in the PDP specifying a LDecision
 * message with the appropriate error code.
 *
 * @author jinhongw@gmail.com
 */
public class CopsREQ extends CopsMessage {
    public final static OpCode OP_CODE = OpCode.REQ;

    private final static int MIN_LENGTH = CopsHeader.LENGTH + Handle.LENGTH + Context.LENGTH;

    // Mandatory
    private Handle handle;
    private Context context;

    // Optional
    private Int iNInt;
    private Int oUTInt;
    private List<ClientSI> clientSIs = new ArrayList<ClientSI>();
    private List<LDec> decs = new ArrayList<LDec>();
    private Integrity integrity;

    /**
     * @param clientType Client-type
     * @param handle     Client Handle
     * @param context    Context
     */
    public CopsREQ(int clientType, int handle, Context context) {
        super(OP_CODE, clientType, MIN_LENGTH);

        this.handle = new Handle(handle);
        this.context = context;
    }

    /**
     * Creates a new <code>CopsREQ</code> with the given source ByteBuffer
     * 
     * @param src The buffer from which bytes are to be retrieved
     * @throws CopsException
     */
    public CopsREQ(ByteBuffer src) throws CopsException {
        super(src);

        if (getOpCode() != OpCode.REQ)
            throw new IllegalCopsMsgException(OpCode.REQ, getOpCode());

        fill(getObjects(src));
    }

    @Override
    public ByteBuffer byteMe(ByteBuffer dst) {
        super.byteMe(dst);

        handle.byteMe(dst);
        context.byteMe(dst);

        if (iNInt != null) iNInt.byteMe(dst);
        if (oUTInt != null) oUTInt.byteMe(dst);

        if (this.clientSIs != null)
            for (ClientSI e : clientSIs)
                e.byteMe(dst);
        if (this.decs != null)
            for (LDec e : decs)
                e.byteMe(dst);

        if (integrity != null) integrity.byteMe(dst);
        
        return dst;
    }

    @Override
    protected void detail(StringBuilder buf) {
        buf.append(handle).append(", ");
        buf.append(context);

        if (iNInt != null)
            buf.append(", ").append(iNInt);
        if (oUTInt != null)
            buf.append(", ").append(oUTInt);

        if (this.clientSIs != null)
            for (ClientSI e : clientSIs)
                buf.append(", ").append(e);
        if (this.decs != null)
            for (LDec e : decs)
                buf.append(", ").append(e);

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
     * @return Context - Mandatory
     */
    public Context getContext() {
        return context;
    }

    /**
     * @return INInt - Optional
     */
    public Int getiNInt() {
        return iNInt;
    }

    /**
     * @param iNInt Optional
     */
    public void setiNInt(Int iNInt) {
        this.iNInt = iNInt;
        this.setMessageLength(size()); // must
    }

    /**
     * @return OUTInt - Optional
     */
    public Int getoUTInt() {
        return oUTInt;
    }

    /**
     * @param oUTInt Optional
     */
    public void setoUTInt(Int oUTInt) {
        this.oUTInt = oUTInt;
        this.setMessageLength(size()); // must
    }

    /**
     * @return List<ClientSI> - Optional
     */
    public List<ClientSI> getClientSIs() {
        return clientSIs;
    }

    /**
     * @param clientSIs Optional
     */
    public void setClientSIs(List<ClientSI> clientSIs) {
        this.clientSIs = clientSIs;
        this.setMessageLength(size()); // must
    }

    /**
     * @return List<LDec> - Optional
     */
    public List<LDec> getLDecs() {
        return decs;
    }

    /**
     * @param decs Optional
     */
    public void setLDecs(List<LDec> decs) {
        this.decs = decs;
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

        CopsREQ other = (CopsREQ) obj;
        if (handle == null) {
            if (other.handle != null)
                return false;
        } else if (!handle.equals(other.handle))
            return false;

        if (context == null) {
            if (other.context != null)
                return false;
        } else if (!context.equals(other.context))
            return false;

        if (iNInt == null) {
            if (other.iNInt != null)
                return false;
        } else if (!iNInt.equals(other.iNInt))
            return false;

        if (oUTInt == null) {
            if (other.oUTInt != null)
                return false;
        } else if (!oUTInt.equals(other.oUTInt))
            return false;

        if (clientSIs == null) {
            if (other.clientSIs != null)
                return false;
        } else if (!clientSIs.equals(other.clientSIs))
            return false;

        if (decs == null) {
            if (other.decs != null)
                return false;
        } else if (!decs.equals(other.decs))
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
        result = prime * result + ((context == null) ? 0 : context.hashCode());
        result = prime * result + ((iNInt == null) ? 0 : iNInt.hashCode());
        result = prime * result + ((oUTInt == null) ? 0 : oUTInt.hashCode());
        result = prime * result + ((clientSIs == null) ? 0 : clientSIs.hashCode());
        result = prime * result + ((decs == null) ? 0 : decs.hashCode());
        result = prime * result + ((integrity == null) ? 0 : integrity.hashCode());
        return result;
    }

    private int size() {
        int length = MIN_LENGTH;
        if (iNInt != null)
            length += iNInt.getLength();
        if (oUTInt != null)
            length += oUTInt.getLength();

        if (clientSIs != null)
            for (ClientSI e : clientSIs)
                length += e.getLength();
        if (decs != null)
            for (LDec e : decs)
                length += e.size();

        if (this.integrity != null)
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

        CopsObject ctx = its.next();
        if (!(ctx instanceof Context))
            throw new CopsFormatErrorException(getOpCode(), Context.class, ctx.getClass());
        context = (Context) ctx;

        LDec dec = null;
        while (its.hasNext()) {
            CopsObject e = its.next();
            switch (e.getCNum()) {
                case ININT:
                    iNInt = (Int) e;
                    break;
                case OUTINT:
                    oUTInt = (Int) e;
                    break;
                case CLIENTSI:
                    clientSIs.add((ClientSI) e);
                    break;
                case LPDPDECISION:
                    dec = new LDec((Decision) e);
                    decs.add(dec);
                    break;
                case CONTEXT:
                    dec.setContext((Context) e);
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
