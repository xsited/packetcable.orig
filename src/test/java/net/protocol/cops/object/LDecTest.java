package net.protocol.cops.object;

import net.protocol.cops.CopsFormatErrorException;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

/**
 * @author jinhongw@gmail.com
 */
public class LDecTest extends CopsObjectTest {

    @Override
	public void base() throws Exception {
        assertEquals(get("A"), get("A"));
        assertEquals(get("A").hashCode(), get("A").hashCode());

        assertNotEquals(get("A"), get("B"));
        assertNotEquals(get("A").hashCode(), get("B").hashCode());

        Map<LDec, String> map = new HashMap<LDec, String>();
        map.put(get("A"), "A");
        map.put(get("A"), "B");
        assertNotNull(map.get(get("A")));

        map.put(get("C"), "C");
        map.put(get("C"), "D");
        assertNotNull(map.get(get("C")));

        assertEquals(2, map.size());
    }

    @Override
	public void logic() throws Exception {

    }

    @Override
    protected LDec get(ByteBuffer buffer) {
        return null;
    }

    @Override
    protected LDec get() {
        try {
            return get(1, 1, "LDec");
        } catch (CopsFormatErrorException e) {
            e.printStackTrace();
        }
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

    private LDec get(String msg) {
        try {
            return get(1, 1, msg);
        } catch (CopsFormatErrorException e) {
            e.printStackTrace();
        }
        return null;
    }

    private LDec get(int mType, int flag, String msg)
            throws CopsFormatErrorException {
        Context cxt = new Context(Context.RType.CONFIGURATION, mType);
        LPDPDecision flagDec = new LPDPDecision(Decision.Command.INSTALL, flag);
        LDec ldec = new LDec(cxt, flagDec);
        ldec.add(new LPDPDecision(Decision.CType.STATELESS, msg.getBytes()));
        ldec.add(new LPDPDecision(Decision.CType.NAMED, msg.getBytes()));

        return ldec;
    }
}
