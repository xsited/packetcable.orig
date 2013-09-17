package net.protocol.cops;

import net.protocol.cops.message.*;

import java.nio.ByteBuffer;

/**
 * This class parses a Cops Message from ByteBuffer.
 *
 * @author jinhongw@gmail.com
 */
public class CopsMessageParser {

    private final static int OP_CODE_INDEX = 1;

    /**
     * Creates a new <code>CopsMessage</code> with the given source ByteBuffer
     * 
     * @param src The buffer from which bytes are to be retrieved
     * @return a new <code>CopsMessage</code>
     * @throws CopsException
     */
    public static CopsMessage parse(ByteBuffer src) throws CopsException {
        byte v = src.get(OP_CODE_INDEX);
        OpCode op = OpCode.valueOf(v);
        if (op == null)
            throw new IllegalCopsMsgException(OpCode.class, v);
        switch (op) {
            case REQ:
                return new CopsREQ(src);
            case DEC:
                return new CopsDEC(src);
            case KA:
                return new CopsKA(src);
            case DRQ:
                return new CopsDRQ(src);
            case OPN:
                return new CopsOPN(src);
            case RPT:
                return new CopsRPT(src);
            case SSQ:
                return new CopsSSQ(src);
            case CAT:
                return new CopsCAT(src);
            case CC:
                return new CopsCC(src);
            case SSC:
                return new CopsSSC(src);
            default:
                return null;
        }
    }
}
