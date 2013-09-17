package net.protocol.cops.message;

import net.protocol.cops.CopsException;
import net.protocol.cops.CopsFormatErrorException;
import net.protocol.cops.object.*;

import java.nio.ByteBuffer;
import java.util.Iterator;
import java.util.List;

/**
 * <h3>Client-Open (OPN) PEP -> PDP</h3>
 * <p/>
 * <p/>
 * The Client-Open message can be used by the PEP to specify to the PDP the
 * client-types the PEP can support, the last PDP to which the PEP connected for
 * the given client-type, and/or client specific feature negotiation. A
 * Client-Open message can be sent to the PDP at any time and multiple
 * Client-Open messages for the same client-type are allowed (in case of global
 * state changes).
 * <p/>
 * <pre>
 *         &lt;Client-Open&gt;  ::= &lt;Common Header&gt;
 *                            &lt;PEPID&gt;
 *                            [&lt;ClientSI&gt;]
 *                            [&lt;LastPDPAddr&gt;]
 *                            [&lt;Integrity&gt;]
 * </pre>
 * <p/>
 * <p/>
 * The PEPID is a symbolic, variable length name that uniquely identifies the
 * specific client to the PDP (see Section 2.2.11).
 * <p/>
 * <p/>
 * A named ClientSI object can be included for relaying additional global
 * information about the PEP to the PDP when required (as specified in the
 * appropriate extensions document for the client- type).
 * <p/>
 * <p/>
 * The PEP may also provide a Last PDP Address object in its Client-Open message
 * specifying the last PDP (for the given client-type) for which it is still
 * caching decisions since its last reboot. A PDP can use this information to
 * determine the appropriate synchronization behavior (See section 2.5).
 * <p/>
 * <p/>
 * If the PDP receives a malformed Client-Open message it MUST generate a
 * Client-Close message specifying the appropriate error code.
 *
 * @author jinhongw@gmail.com
 */
public class CopsOPN extends CopsMessage {
    public final static OpCode OP_CODE = OpCode.OPN;

    // Mandatory
    private PEPID pepid;

    // Optional
    private ClientSI clientSI;
    private LastPDPAddr lastPdpAddr;
    private Integrity integrity;

    /**
     * @param clientType Client-type
     * @param pepid      PEPID
     */
    public CopsOPN(int clientType, byte[] pepid) {
        super(OP_CODE, clientType);

        this.pepid = new PEPID(pepid);

        this.setMessageLength(size());
    }

    /**
     * Creates a new <code>CopsOPN</code> with the given source ByteBuffer
     * 
     * @param src The buffer from which bytes are to be retrieved
     * @throws CopsException
     */
    public CopsOPN(ByteBuffer src) throws CopsException {
        super(src);

        if (getOpCode() != OpCode.OPN)
            throw new IllegalCopsMsgException(OpCode.OPN, getOpCode());

        fill(getObjects(src));
    }

    @Override
    public ByteBuffer byteMe(ByteBuffer dst) {
        super.byteMe(dst);

        pepid.byteMe(dst);

        if (clientSI != null) clientSI.byteMe(dst);
        if (lastPdpAddr != null) lastPdpAddr.byteMe(dst);
        if (integrity != null) integrity.byteMe(dst);
        
        return dst;
    }

    @Override
    protected void detail(StringBuilder buf) {
        buf.append(pepid);

        if (clientSI != null)
            buf.append(", ").append(clientSI);
        if (lastPdpAddr != null)
            buf.append(", ").append(lastPdpAddr);
        if (integrity != null)
            buf.append(", ").append(integrity);
    }

    /**
     * @return PEPID - Mandatory
     */
    public PEPID getPepid() {
        return pepid;
    }

    /**
     * @return ClientSI - Optional
     */
    public ClientSI getClientSI() {
        return clientSI;
    }

    /**
     * @param clientSI Optional
     */
    public void setClientSI(ClientSI clientSI) {
        this.clientSI = clientSI;
        this.setMessageLength(size()); // must
    }

    /**
     * @return LastPDPAddr - Optional
     */
    public LastPDPAddr getLastPdpAddr() {
        return lastPdpAddr;
    }

    /**
     * @param lastPdpAddr Optional
     */
    public void setLastPdpAddr(LastPDPAddr lastPdpAddr) {
        this.lastPdpAddr = lastPdpAddr;
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

        CopsOPN other = (CopsOPN) obj;
        if (pepid == null) {
            if (other.pepid != null)
                return false;
        } else if (!pepid.equals(other.pepid))
            return false;

        if (clientSI == null) {
            if (other.clientSI != null)
                return false;
        } else if (!clientSI.equals(other.clientSI))
            return false;

        if (lastPdpAddr == null) {
            if (other.lastPdpAddr != null)
                return false;
        } else if (!lastPdpAddr.equals(other.lastPdpAddr))
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
        result = prime * result + ((pepid == null) ? 0 : pepid.hashCode());
        result = prime * result + ((clientSI == null) ? 0 : clientSI.hashCode());
        result = prime * result + ((lastPdpAddr == null) ? 0 : lastPdpAddr.hashCode());
        result = prime * result + ((integrity == null) ? 0 : integrity.hashCode());
        return result;
    }

    private int size() {
        int length = CopsHeader.LENGTH + pepid.getLength();

        if (clientSI != null)
            length += clientSI.getLength();
        if (lastPdpAddr != null)
            length += lastPdpAddr.getLength();
        if (integrity != null)
            length += integrity.getLength();
        return length;
    }

    private void fill(List<CopsObject> list) throws CopsFormatErrorException {
        if (list.size() == 0)
            throw new CopsFormatErrorException(getOpCode(), "No Object(0)");

        Iterator<CopsObject> its = list.iterator();
        CopsObject pid = its.next();
        if (!(pid instanceof PEPID))
            throw new CopsFormatErrorException(getOpCode(), PEPID.class, pid.getClass());
        pepid = (PEPID) pid;

        while (its.hasNext()) {
            CopsObject e = its.next();
            switch (e.getCNum()) {
                case CLIENTSI:
                    clientSI = (ClientSI) e;
                    break;
                case LASTPDPADDR:
                    lastPdpAddr = (LastPDPAddr) e;
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
