package net.protocol.cops.message;

import net.protocol.cops.CopsException;
import net.protocol.cops.CopsFormatErrorException;
import net.protocol.cops.object.*;

import java.nio.ByteBuffer;
import java.util.Iterator;
import java.util.List;

/**
 * <h3>Report State (RPT) PEP -> PDP</h3>
 * <p/>
 * <p/>
 * The RPT message is used by the PEP to communicate to the PDP its success or
 * failure in carrying out the PDP's decision, or to report an accounting
 * related change in state. The Report-Type specifies the kind of report and the
 * optional ClientSI can carry additional information per Client-Type.
 * <p/>
 * <p/>
 * For every DEC message containing a configuration context that is received by
 * a PEP, the PEP MUST generate a corresponding Report State message with the
 * Solicited Message flag set describing its success or failure in applying the
 * configuration decision. In addition, outsourcing decisions from the PDP MAY
 * result in a corresponding solicited Report State from the PEP depending on
 * the context and the type of client. RPT messages solicited by decisions for a
 * given Client Handle MUST set the Solicited Message flag and MUST be sent in
 * the same order as their corresponding Decision messages were received. There
 * MUST never be more than one Report State message generated with the Solicited
 * Message flag set per Decision.
 * <p/>
 * <p/>
 * The Report State may also be used to provide periodic updates of client
 * specific information for accounting and state monitoring purposes depending
 * on the type of the client. In such cases the accounting report type should be
 * specified utilizing the appropriate client specific information object.
 * <p/>
 * <pre>
 *               &lt;Report State&gt; ::== &lt;Common Header&gt;
 *                                   &lt;Client Handle&gt;
 *                                   &lt;Report-Type&gt;
 *                                   [&lt;ClientSI&gt;]
 *                                   [&lt;Integrity&gt;]
 * </pre>
 *
 * @author jinhongw@gmail.com
 */
public class CopsRPT extends CopsMessage {
    public final static OpCode OP_CODE = OpCode.RPT;

    private final static int MIN_LENGTH = CopsHeader.LENGTH + Handle.LENGTH + ReportType.LENGTH;

    // Mandatory
    private Handle handle;
    private ReportType reportType;

    // Optional
    private ClientSI clientSI;
    private Integrity integrity;

    /**
     * @param clientType Client-type
     * @param handle     Client Handle
     * @param reportType Report-Type
     */
    public CopsRPT(int clientType, int handle, int reportType) {
        super(OP_CODE, clientType, MIN_LENGTH);

        this.handle = new Handle(handle);
        this.reportType = new ReportType(reportType);
    }

    /**
     * @param clientType Client-type
     * @param handle     Client Handle
     * @param reportType Report-Type
     */
    public CopsRPT(int clientType, int handle, ReportType.Type reportType) {
        this(clientType, handle, reportType.getValue());
    }

    /**
     * Creates a new <code>CopsRPT</code> with the given source ByteBuffer
     * 
     * @param src The buffer from which bytes are to be retrieved
     * @throws CopsException
     */
    public CopsRPT(ByteBuffer src) throws CopsException {
        super(src);

        if (getOpCode() != OpCode.RPT)
            throw new IllegalCopsMsgException(OpCode.RPT, getOpCode());

        fill(getObjects(src));
    }

    @Override
    public ByteBuffer byteMe(ByteBuffer dst) {
        super.byteMe(dst);

        handle.byteMe(dst);
        reportType.byteMe(dst);

        if (clientSI != null) clientSI.byteMe(dst);
        if (integrity != null) integrity.byteMe(dst);
        
        return dst;
    }

    @Override
    protected void detail(StringBuilder buf) {
        buf.append(handle).append(", ");
        buf.append(reportType);

        if (clientSI != null)
            buf.append(", ").append(clientSI);
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
     * @return ReportType - Mandatory
     */
    public ReportType getReportType() {
        return reportType;
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

        CopsRPT other = (CopsRPT) obj;
        if (handle == null) {
            if (other.handle != null)
                return false;
        } else if (!handle.equals(other.handle))
            return false;

        if (reportType == null) {
            if (other.reportType != null)
                return false;
        } else if (!reportType.equals(other.reportType))
            return false;

        if (clientSI == null) {
            if (other.clientSI != null)
                return false;
        } else if (!clientSI.equals(other.clientSI))
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
        result = prime * result + ((reportType == null) ? 0 : reportType.hashCode());
        result = prime * result + ((clientSI == null) ? 0 : clientSI.hashCode());
        result = prime * result + ((integrity == null) ? 0 : integrity.hashCode());
        return result;
    }

    private int size() {
        int length = MIN_LENGTH;

        if (clientSI != null)
            length += clientSI.getLength();
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

        CopsObject rt = its.next();
        if (!(rt instanceof ReportType))
            throw new CopsFormatErrorException(getOpCode(), ReportType.class, rt.getClass());
        reportType = (ReportType) rt;

        while (its.hasNext()) {
            CopsObject e = its.next();
            switch (e.getCNum()) {
                case CLIENTSI:
                    clientSI = (ClientSI) e;
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
