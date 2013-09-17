package net.protocol.cops.message;

import net.protocol.common.coding.Message;
import net.protocol.common.util.StringUtils;
import net.protocol.cops.object.*;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

/**
 * <h3>The Protocol</h3>
 * <p/>
 * This section describes the message formats and objects exchanged between the
 * PEP and remote PDP.
 * <p/>
 * <h4>Common Header</h4>
 * <p/>
 * <pre>
 *           0              1              2              3
 *      +--------------+--------------+--------------+--------------+
 *      |Version| Flags|    Op Code   |       Client-type           |
 *      +--------------+--------------+--------------+--------------+
 *      |                      Message Length                       |
 *      +--------------+--------------+--------------+--------------+
 * </pre>
 * <p/>
 * <h4>COPS Specific Object Formats</h4>
 * <p/>
 * <pre>
 *              0             1              2             3
 *        +-------------+-------------+-------------+-------------+
 *        |       Length (octets)     |    C-Num    |   C-Type    |
 *        +-------------+-------------+-------------+-------------+
 *        |                                                       |
 *        //                  (Object contents)                   //
 *        |                                                       |
 *        +-------------+-------------+-------------+-------------+
 * </pre>
 *
 * @author jinhongw@gmail.com
 */
public abstract class CopsMessage implements Message {
    private final CopsHeader header;

    /**
     * @param opCode     Op Code
     * @param clientType Client-type
     */
    protected CopsMessage(OpCode opCode, int clientType) {
        header = new CopsHeader(opCode, clientType);
    }

    /**
     * @param opCode        Op Code
     * @param clientType    Client-type
     * @param messageLength Message Length
     */
    protected CopsMessage(OpCode opCode, int clientType, int messageLength) {
        header = new CopsHeader(opCode, clientType, messageLength);
    }

    /**
     * @param flags         Flags
     * @param opCode        Op Code
     * @param clientType    Client-type
     * @param messageLength Message Length
     */
    protected CopsMessage(byte flags, OpCode opCode, int clientType,
                          int messageLength) {
        header = new CopsHeader(flags, opCode, clientType, messageLength);
    }

    /**
     * Creates a new <code>CopsMessage</code> with the given source ByteBuffer
     * 
     * @param src The buffer from which bytes are to be retrieved
     * @throws IllegalCopsMsgException
     */
    protected CopsMessage(ByteBuffer src) throws IllegalCopsMsgException {
        this.header = new CopsHeader(src);
    }

    /**
     * @return Version
     */
    public byte getVersion() {
        return header.getVersion();
    }

    /**
     * @param version Version
     */
    public void setVersion(byte version) {
        header.setVersion(version);
    }

    /**
     * @return Flags
     */
    public byte getFlags() {
        return header.getFlags();
    }

    /**
     * @param flags Flags
     */
    public void setFlags(byte flags) {
        header.setFlags(flags);
    }

    /**
     * @return Op Code
     */
    public OpCode getOpCode() {
        return header.getOpCode();
    }

    /**
     * @return Client-type
     */
    public int getClientType() {
        return header.getClientType();
    }

    /**
     * @return Message Length
     */
    public int getMessageLength() {
        return header.getMessageLength();
    }

    /**
     * @param messageLength Message Length
     */
    protected void setMessageLength(int messageLength) {
        header.setMessageLength(messageLength);
    }

    public boolean validate() {
        if (getMessageLength() < CopsHeader.LENGTH)
            return false;
        return true;
    }

    @Override
    public ByteBuffer byteMe(ByteBuffer dst) {
        header.byteMe(dst);
        
        return dst;
    }

    protected abstract void detail(StringBuilder buf);

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append(getClass().getSimpleName()).append(" [");
        buf.append(header);
        buf.append(", ");
        detail(buf);
        StringUtils.deleteLastDot(buf);
        buf.append("]");
        return buf.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        CopsMessage other = (CopsMessage) obj;
        if (header == null) {
            if (other.header != null)
                return false;
        } else if (!header.equals(other.header))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 17;
        int result = 31;
        result = prime * result + ((header == null) ? 0 : header.hashCode());
        return result;
    }

    /**
     * @param src the given source ByteBuffer
     * @return List<CopsObject>
     * @throws net.protocol.cops.object.IllegalCopsObjectException
     *
     */
    protected List<CopsObject> getObjects(final ByteBuffer src)
            throws IllegalCopsObjectException {
        src.position(CopsHeader.LENGTH);
        List<CopsObject> list = new ArrayList<CopsObject>();
        while (src.hasRemaining()) {
            list.add(getObject(src));
        }
        return list;
    }

    private CopsObject getObject(ByteBuffer src)
            throws IllegalCopsObjectException {
        byte v = src.get(src.position() + 2);
        CNum cnum = CNum.valueOf(v);
        if (cnum == null)
            throw new IllegalCopsObjectException(CNum.class, v);

        switch (cnum) {
            case PEPID:
                return new PEPID(src);
            case CLIENTSI:
                return new ClientSI(src);
            case LASTPDPADDR:
                return new LastPDPAddr(src);
            case INTEGRITY:
                return new Integrity(src);

            case HANDLE:
                return new Handle(src);
            case CONTEXT:
                return new Context(src);

            case ININT:
            case OUTINT:
                return new Int(src);

            case DECISION:
            case LPDPDECISION:
                return new Decision(src);

            case REASON:
                return new Reason(src);
            case PDPREDIRADDR:
                return new PDPRedirAddr(src);
            case ERROR:
                return new net.protocol.cops.object.Error(src);

            case KATIMER:
            case ACCTTIMER:
                return new Timer(src);

            case REPORTTYPE:
                return new ReportType(src);
            default:
                return null;
        }
    }
}
