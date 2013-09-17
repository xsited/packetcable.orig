package net.protocol.cops.message;

import net.protocol.cops.CopsException;

/**
 * @author jinhongw@gmail.com
 */
public class IllegalCopsMsgException extends CopsException {

    /**
     *
     */
    private static final long serialVersionUID = -7383217816416978879L;

    /**
     * Constructs a new <code>IllegalCopsMsgException</code> with the specified
     * detail message.
     *
     * @param expected
     * @param actual
     */
    public IllegalCopsMsgException(Object expected, Object actual) {
        super("expected:<" + expected + "> but was:<" + actual + ">");
    }
}
