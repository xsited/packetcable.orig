package net.protocol.cops.object;

import net.protocol.cops.CopsFormatErrorException;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

/**
 * @author jinhongw@gmail.com
 */
public class DecTest extends CopsObjectTest {

    @Override
	public void base() throws Exception {
        assertEquals(get("A"), get("A"));
        assertEquals(get("A").hashCode(), get("A").hashCode());

        assertNotEquals(get("A"), get("B"));
        assertNotEquals(get("A").hashCode(), get("B").hashCode());

        Map<Dec, String> map = new HashMap<Dec, String>();
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
    protected Dec get(ByteBuffer buffer) {
        return null;
    }

    @Override
    protected Dec get() {
        try {
            return get(1, 1, "Dec");
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

    private Dec get(String msg) {
        try {
            return get(1, 1, msg);
        } catch (CopsFormatErrorException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Dec get(int mType, int flag, String msg)
            throws CopsFormatErrorException {
        Context cxt = new Context(Context.RType.CONFIGURATION, mType);
        Decision flagDec = new Decision(Decision.Command.INSTALL, flag);
        Dec dec = new Dec(cxt, flagDec);
        dec.add(new Decision(Decision.CType.STATELESS, msg.getBytes()));
        dec.add(new Decision(Decision.CType.NAMED, msg.getBytes()));

        return dec;
    }
}
