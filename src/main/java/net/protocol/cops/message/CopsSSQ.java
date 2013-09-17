package net.protocol.cops.message;

import net.protocol.cops.CopsException;

import java.nio.ByteBuffer;

/**
 * <h3>Synchronize State Request (SSQ) PDP -> PEP</h3>
 * <p/>
 * <pre>
 *   The format of the Synchronize State Query message is as follows:
 *
 *               &lt;Synchronize State&gt; ::= &lt;Common Header&gt;
 *                                       [&lt;Client Handle&gt;]
 *                                       [&lt;Integrity&gt;]
 * </pre>
 * <p/>
 * <p/>
 * This message indicates that the remote PDP wishes the client (which appears
 * in the common header) to re-send its state. If the optional Client Handle is
 * present, only the state associated with this handle is synchronized. If the
 * PEP does not recognize the requested handle, it MUST immediately send a DRQ
 * message to the PDP for the handle that was specified in the SSQ message. If
 * no handle is specified in the SSQ message, all the active client state MUST
 * be synchronized with the PDP.
 * <p/>
 * The client performs state synchronization by re-issuing request queries of
 * the specified client-type for the existing state in the PEP. When
 * synchronization is complete, the PEP MUST issue a synchronize state complete
 * message to the PDP.
 *
 * @author jinhongw@gmail.com
 */
public class CopsSSQ extends CopsSS {
    public final static OpCode OP_CODE = OpCode.SSQ;

    /**
     * @param clientType Client-type
     */
    public CopsSSQ(int clientType) {
        super(OP_CODE, clientType);
    }

    /**
     * @param clientType Client-type
     * @param handle     Client Handle
     */
    public CopsSSQ(int clientType, int handle) {
        super(OP_CODE, clientType, handle);
    }

    /**
     * Creates a new <code>CopsSSQ</code> with the given source ByteBuffer
     * 
     * @param src The buffer from which bytes are to be retrieved
     * @throws CopsException
     */
    public CopsSSQ(ByteBuffer src) throws CopsException {
        super(src);
    }
}
