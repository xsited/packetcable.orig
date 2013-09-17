package net.protocol.cops.object;

import net.protocol.common.AbstractTest;

import java.util.EnumMap;

/**
 * @author jinhongw@gmail.com (Bruce Wang)
 */
public class EnumTest extends AbstractTest {
    private static EnumMap<Decision.CType, Decision> map = new EnumMap<Decision.CType, Decision>(
            Decision.CType.class);

    public void base() {
        assertEquals(CNum.HANDLE, CNum.HANDLE);
        assertEquals(false, CNum.HANDLE.equals(CNum.CONTEXT));

        add(Decision.CType.FLAG);
        add(Decision.CType.STATELESS);
        add(Decision.CType.CLIENTSI);
        add(Decision.CType.NAMED);
        add(Decision.CType.REPLACEMENT);
        add(Decision.CType.STATELESS);

        assertEquals(Decision.CType.values().length, map.size());
    }

    @Override
	public void logic() throws Exception {

    }

    private static void add(Decision.CType ctype) {
        map.put(ctype, dec(ctype));
    }

    private static Decision dec(Decision.CType ctype) {
        return new Decision(Decision.CType.STATELESS, ctype.toString()
                .getBytes());
    }
}
