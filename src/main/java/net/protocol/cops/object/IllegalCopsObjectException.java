package net.protocol.cops.object;

import net.protocol.cops.CopsException;

/**
 * @author jinhongw@gmail.com
 */
public class IllegalCopsObjectException extends CopsException {

    /**
     *
     */
    private static final long serialVersionUID = -7383217816416978879L;

    /**
     * Constructs a new <code>IllegalCopsObjectException</code> with the
     * specified detail message.
     *
     * @param expected
     * @param actual
     */
    public IllegalCopsObjectException(Object expected, Object actual) {
        super("expected:<" + expected + "> but was:<" + actual + ">");
    }
}