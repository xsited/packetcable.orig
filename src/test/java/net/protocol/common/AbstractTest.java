package net.protocol.common;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author jinhongw@gmail.com
 */
public abstract class AbstractTest {

    @Test
    public final void test() throws Exception {
        base();
        logic();
    }

    public abstract void base() throws Exception;

    public abstract void logic() throws Exception;

    /**
     * @param object Object
     */
    public static void assertNotNull(Object object) {
        System.out.println(object + " (not null?)");
        org.junit.Assert.assertNotNull(object);
        System.out.println();
    }

    /**
     * @param expected Object
     * @param actual   Object
     */
    public static void assertEquals(Object expected, Object actual) {
        System.out.println(expected + " (equals?) " + actual);
        org.junit.Assert.assertEquals(expected, actual);
        System.out.println();
    }

    /**
     * @param expected Object
     * @param actual   Object
     */
    public static void assertNotEquals(Object expected, Object actual) {
        System.out.println(expected + " (not equals?) " + actual);
        assertNotEquals(null, expected, actual);
        System.out.println();
    }

    protected static void assertNotEquals(String message, Object expected, Object actual) {
        if (expected == null && actual == null)
            failEquals(message, expected, actual);

        if (expected.equals(actual) == false)
            return;

        failEquals(message, expected, actual);
    }

    private static void failEquals(String message, Object expected,
                                   Object actual) {
        Assert.fail(format(message, expected, actual));
    }

    private static String format(String message, Object expected, Object actual) {
        String formatted = "";
        if (message != null)
            formatted = message + " ";
        return formatted + "expected:<" + expected + "> but was:<" + actual + ">";
    }
}
