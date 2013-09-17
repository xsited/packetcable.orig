package net.protocol.cops.message;

import net.protocol.cops.CopsException;
import net.protocol.cops.CopsFormatErrorException;
import net.protocol.cops.object.CopsObject;
import net.protocol.cops.object.Integrity;

import java.nio.ByteBuffer;
import java.util.List;

/**
 * <h3>Keep-Alive (KA) PEP -> PDP, PDP -> PEP</h3>
 * <p/>
 * The keep-alive message MUST be transmitted by the PEP within the period
 * defined by the minimum of all KA Timer values specified in all received CAT
 * messages for the connection. A KA message MUST be generated randomly between
 * 1/4 and 3/4 of this minimum KA timer interval. When the PDP receives a
 * keep-alive message from a PEP, it MUST echo a keep-alive back to the PEP.
 * This message provides validation for each side that the connection is still
 * functioning even when there is no other messaging.
 * <p/>
 * <p/>
 * Note: The client-type in the header MUST always be set to 0 as the KA is used
 * for connection verification (not per client session verification).
 * <p/>
 * <pre>
 *                &lt;Keep-Alive&gt;  ::= &lt;Common Header&gt;
 *                                  [&lt;Integrity&gt;]
 * </pre>
 * <p/>
 * <p/>
 * Both client and server MAY assume the TCP connection is insufficient for the
 * client-type with the minimum time value (specified in the CAT message) if no
 * communication activity is detected for a period exceeding the timer period.
 * For the PEP, such detection implies the remote PDP or connection is down and
 * the PEP SHOULD now attempt to use an alternative/backup PDP.
 *
 * @author jinhongw@gmail.com
 */
public class CopsKA extends CopsMessage {
    public final static OpCode OP_CODE = OpCode.KA;
    public final static int CLIENT_TYPE = 0;

    private final static int MIN_LENGTH = CopsHeader.LENGTH;

    private Integrity integrity; // Optional

    public CopsKA() {
        super(OP_CODE, CLIENT_TYPE);

        this.integrity = null;

        this.setMessageLength(size());
    }

    /**
     * @param keyId          Integrity's Key ID
     * @param sequenceNumber Integrity's Sequence Number
     * @param digest         Integrity's Keyed Message Digest
     */
    public CopsKA(int keyId, int sequenceNumber, byte[] digest) {
        this(new Integrity(keyId, sequenceNumber, digest));
    }

    /**
     * @param integrity Integrity
     */
    public CopsKA(Integrity integrity) {
        super(OP_CODE, CLIENT_TYPE);

        this.integrity = integrity;

        this.setMessageLength(size());
    }


    /**
     * Creates a new <code>CopsKA</code> with the given source ByteBuffer
     * 
     * @param src The buffer from which bytes are to be retrieved
     * @throws CopsException
     */
    public CopsKA(ByteBuffer src) throws CopsException {
        super(src);

        if (getOpCode() != OpCode.KA)
            throw new IllegalCopsMsgException(OpCode.KA, getOpCode());

        if (getClientType() != CLIENT_TYPE)
            throw new IllegalCopsMsgException("client-type=" + CLIENT_TYPE, getClientType());

        this.integrity = getIntegrity(getObjects(src));
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
        this.setMessageLength(size());
    }

    @Override
    public ByteBuffer byteMe(ByteBuffer dst) {
        super.byteMe(dst);
        if (integrity != null) integrity.byteMe(dst);

        return dst;
    }

    @Override
    public boolean validate() {
        final int length = getMessageLength();
        if (length < CopsHeader.LENGTH)
            return false;

        if (integrity != null)
            if (length < CopsHeader.LENGTH + integrity.getLength())
                return false;

        return true;
    }

    @Override
    protected void detail(StringBuilder buf) {
        if (integrity != null)
            buf.append(integrity);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (getClass() != obj.getClass())
            return false;

        CopsKA other = (CopsKA) obj;
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
        result = prime * result + ((integrity == null) ? 0 : integrity.hashCode());
        return result;
    }

    private int size() {
        if (integrity == null)
            return MIN_LENGTH;
        return MIN_LENGTH + integrity.getLength();
    }

    /**
     * @param list
     * @return Integrity
     * @throws CopsFormatErrorException
     */
    private Integrity getIntegrity(List<CopsObject> list)
            throws CopsFormatErrorException {
        if (list.size() > 1)
            throw new CopsFormatErrorException(getOpCode(),
                    "Only allow one Object(" + list.size() + ")");
        for (CopsObject e : list) {
            switch (e.getCNum()) {
                case INTEGRITY:
                    return (Integrity) e;
                default:
                    throw new CopsFormatErrorException(getOpCode(), e.getCNum());
            }
        }
        return null;
    }
}
