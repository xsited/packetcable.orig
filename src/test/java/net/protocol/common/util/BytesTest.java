package net.protocol.common.util;

import net.protocol.common.AbstractTest;

/**
 * @author jinhongw@gmail.com
 */
public class BytesTest extends AbstractTest {

    public final static byte[] ARRAY1 = {73, 110, 116, 101, 103, 114, 105,
            116, 121, 0, 0, 0};

    public final static byte[] ARRAY2 = {73, 110, 116, 0, 101, 103, 114, 105,
            116, 121, 0, 0, 0};

    @Override
	public void base() throws Exception {
        assertEquals(9, Bytes.trim(ARRAY1).length);
        assertEquals(10, Bytes.trim(ARRAY2).length);
    }

    @Override
	public void logic() throws Exception {
        assertNotEquals("Integrity", new String(ARRAY1));
        assertNotEquals("Integrity", new String(ARRAY2));

        assertEquals("Integrity", new String(Bytes.trim(ARRAY1)));
        assertNotEquals("Integrity", new String(Bytes.trim(ARRAY2)));
    }
}
