package net.protocol.cops.message;

import net.protocol.cops.CopsException;

import java.nio.ByteBuffer;

/**
 * <h3>Synchronize State Complete (SSC) PEP -> PDP</h3>
 * <p/>
 * <pre>
 *          &lt;Synchronize State Complete&gt;  ::= &lt;Common Header&gt;
 *                                            [&lt;Client Handle&gt;]
 *                                            [&lt;Integrity&gt;]
 * </pre>
 * <p/>
 * <p/>
 * The Synchronize State Complete is sent by the PEP to the PDP after the PDP
 * sends a synchronize state request to the PEP and the PEP has finished
 * synchronization. It is useful so that the PDP will know when all the old
 * client state has been successfully re-requested and, thus, the PEP and PDP
 * are completely synchronized. The Client Handle object only needs to be
 * included if the corresponding Synchronize State Message originally referenced
 * a specific handle.
 *
 * @author jinhongw@gmail.com
 */
public class CopsSSC extends CopsSS {

    public final static OpCode OP_CODE = OpCode.SSC;

    /**
     * @param clientType Client-type
     */
    public CopsSSC(int clientType) {
        super(OP_CODE, clientType);
    }

    /**
     * @param clientType Client-type
     * @param handle     Client Handle
     */
    public CopsSSC(int clientType, int handle) {
        super(OP_CODE, clientType, handle);
    }

    /**
     * Creates a new <code>CopsSSC</code> with the given source ByteBuffer
     * 
     * @param src The buffer from which bytes are to be retrieved
     * @throws CopsException
     */
    public CopsSSC(ByteBuffer src) throws CopsException {
        super(src);
    }
}
