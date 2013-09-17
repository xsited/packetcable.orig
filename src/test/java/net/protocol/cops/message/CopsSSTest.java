package net.protocol.cops.message;

import java.util.HashMap;
import java.util.Map;

/**
 * @author jinhongw@gmail.com
 */
public abstract class CopsSSTest extends CopsMessageTest {
    public final static int TPS_UNIT = 350 * 10000;

    @Override
	public void base() {
        assertEquals(getQ(1), getQ(1));
        assertEquals(getQ(1).hashCode(), getQ(1).hashCode());

        assertEquals(getC(1), getC(1));
        assertEquals(getC(1).hashCode(), getC(1).hashCode());

        assertNotEquals(getQ(1), getC(1));
        assertNotEquals(getQ(1).hashCode(), getC(1).hashCode());

        Map<CopsMessage, String> map = new HashMap<CopsMessage, String>();
        map.put(getQ(1), "A");
        map.put(getQ(1), "B");
        assertNotNull(map.get(getQ(1)));

        map.put(getC(1), "C");
        map.put(getC(1), "D");
        assertNotNull(map.get(getC(1)));

        assertEquals(2, map.size());
    }

    @Override
    protected int getTpsUnit() {
        return TPS_UNIT;
    }

    private CopsSS getQ(int handle) {
        return new CopsSSQ(32757, 1);
    }

    private CopsSS getC(int handle) {
        return new CopsSSC(32757, 1);
    }
}
