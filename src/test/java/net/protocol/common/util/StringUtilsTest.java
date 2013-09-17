package net.protocol.common.util;

import net.protocol.common.AbstractTest;

/**
 * @author jinhongw@gmail.com
 */
public class StringUtilsTest extends AbstractTest {
    public final static byte[] ARRAY = {
            0, 73, 110, 116, 0, 101, 103, 114, 105, 116, 121, 0, 0, 0
    };

    @Override
	public void base() throws Exception {

    }

    @Override
	public void logic() throws Exception {
        StringBuilder buf1 = new StringBuilder("[A, B, C]");
        StringUtils.deleteLastDot(new StringBuilder("[A, B, C]"));
        System.out.println(buf1);

        StringBuilder buf2 = new StringBuilder("[A, B, C,  ");
        StringUtils.deleteLastDot(buf2);
        System.out.println(buf2 + "]");

        StringBuilder buf3 = new StringBuilder("[A, B, C,  ,  ]");
        StringUtils.deleteLastDot(buf3);
        System.out.println(buf3);
    }
}
