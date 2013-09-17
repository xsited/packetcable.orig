package net.protocol.cops;

import net.protocol.cops.message.OpCode;
import net.protocol.cops.object.CNum;

/**
 * @author jinhongw@gmail.com
 */
public class CopsFormatErrorException extends CopsException {

    /**
     *
     */
    private static final long serialVersionUID = 5493040749054270244L;

    /**
     * @param op  OpCode
     * @param msg String
     */
    public CopsFormatErrorException(OpCode op, String msg) {
        super("Op Code:" + op + ", " + msg);
    }

    /**
     * @param op   OpCode
     * @param cNum CNum - invalid
     */
    public CopsFormatErrorException(OpCode op, CNum cNum) {
        super("Op Code:" + op + ", Invalid C-Num:" + cNum);
    }

    /**
     * @param op    OpCode
     * @param cNum  CNum
     * @param cType byte - invalid
     */
    public CopsFormatErrorException(OpCode op, CNum cNum, byte cType) {
        super("Op Code:" + op + ", C-Num:" + cNum + ", Invalid C-Type:" + cType);
    }

    /**
     * @param op       OpCode
     * @param expected Object
     * @param actual   Object
     */
    public CopsFormatErrorException(OpCode op, Object expected, Object actual) {
        super("Op Code:" + op + ", expected:<" + expected + "> but was:<" + actual + ">");
    }
}
