package net.protocol.cops;

import net.protocol.common.MessageTest;
import net.protocol.common.coding.ByteMe;
import net.protocol.common.util.Ints;
import net.protocol.cops.message.CopsMessage;
import net.protocol.cops.object.CopsObject;
import org.junit.Test;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author jinhongw@gmail.com
 */
public abstract class CopsTest extends MessageTest {
    protected final static long TIMEOUT = 1000;


    @Override
	public abstract void base() throws Exception;

    @Override
	public abstract void logic() throws Exception;

    @Test(timeout = TIMEOUT)
    public final void performance4Base() throws Exception {
        long start = System.currentTimeMillis();
        ByteBuffer buffer = getBuffer(getBytes());
        int tps = getTpsUnit();
        for (int i = 0; i < tps; i++) {
            get(buffer);
            buffer.rewind();
        }
        long duration = System.currentTimeMillis() - start;
        // Tps:3500000, duration(ms):882, this(ByteBuffer)
        System.out.println("\nTps:" + tps + ", duration(ms):" + duration + ", this(ByteBuffer)\n");
    }

    @Test(timeout = TIMEOUT)
    public final void performance4ByteMe() throws Exception {
        long start = System.currentTimeMillis();
        Object o = get();
        if (o == null) return;
        int capacity = 0;
        if (o instanceof CopsObject) {
            capacity = ((CopsObject) o).getLength();
        }
        if (o instanceof CopsMessage) {
            capacity = ((CopsMessage) o).getMessageLength();
        }
        ByteBuffer dst = ByteBuffer.allocate(Ints.multiple4(capacity));
        int tps = getTpsUnit();
        for (int i = 0; i < tps; i++) {
            ((ByteMe) o).byteMe(dst);
            dst.clear();
        }
        long duration = System.currentTimeMillis() - start;
        // Tps:3500000, duration(ms):774, ByteMe
        System.out.println("Tps:" + tps + ", duration(ms):" + duration + ", ByteMe\n");
    }

    @Test(timeout = TIMEOUT)
    public final void performance4Set() throws Exception {
        long start = System.currentTimeMillis();
        Set<Object> set = new HashSet<Object>();
        ByteBuffer buffer = getBuffer(getBytes());
        int tps = (int) (getTpsUnit() / 1.6);
        for (int i = 0; i < tps; i++) {
            set.add(get(buffer));
            buffer.rewind();
        }
        long duration = System.currentTimeMillis() - start;
        // Tps:2187500, duration(ms):813, Set<Integrity>:1
        System.out.println("Tps:" + tps + ", duration(ms):" + duration + ", Set<Integrity>:" + set.size());
        assertEquals((tps == 0) ? 0 : 1, set.size());
    }

    @Test(timeout = TIMEOUT)
    public final void performance4Map() throws Exception {
        long start = System.currentTimeMillis();
        Map<Object, Integer> map = new HashMap<Object, Integer>();
        ByteBuffer buffer = getBuffer(getBytes());
        int tps = (int) (getTpsUnit() / 1.6);
        for (int i = 0; i < tps; i++) {
            map.put(get(buffer), i);
            buffer.rewind();
        }
        long duration = System.currentTimeMillis() - start;
        // Tps:2187500, duration(ms):817, Map<Integrity, Integrity>:1
        System.out.println("Tps:" + tps + ", duration(ms):" + duration + ", Map<Integrity, Integrity>:" + map.size());
        assertEquals((tps == 0) ? 0 : 1, map.size());
    }

    protected abstract Object get(ByteBuffer buffer) throws CopsException;

    protected abstract Object get();

    protected abstract byte[] getBytes();

    protected abstract int getTpsUnit();
}
