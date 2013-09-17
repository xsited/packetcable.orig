package net.protocol.cops;

import net.protocol.cops.message.CopsMessage;
import net.protocol.cops.message.CopsMessageTest;
import net.protocol.cops.message.Factory;

import java.nio.ByteBuffer;

/**
 * @author jinhongw@gmail.com
 */
public class CopsMessageParserTest extends CopsMessageTest {

    @Override
	public void base() throws Exception {
        parse(Factory.OPN_ARRAY);
        parse(Factory.KA_ARRAY);
        parse(Factory.REQ_ARRAY);
        parse(Factory.DEC_ARRAY);
        parse(Factory.SSQ_ARRAY);
        parse(Factory.SSC_ARRAY);
        parse(Factory.CAT_ARRAY);
    }

    @Override
	public void logic() throws Exception {

    }

    @Override
    protected Object get(ByteBuffer buffer) {
        return null;
    }

    @Override
    protected Object get() {
        return null;
    }

    @Override
    protected byte[] getBytes() {
        return new byte[0];
    }

    @Override
    protected int getTpsUnit() {
        return 0;
    }

    private void parse(byte[] array) throws Exception {
        ByteBuffer buf = getBuffer(array);
        System.out.println(buf);
        CopsMessage cops = CopsMessageParser.parse(buf);
        System.out.println(cops);
        System.out.println();
    }
}
