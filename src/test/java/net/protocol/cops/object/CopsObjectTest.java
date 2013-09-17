package net.protocol.cops.object;

import net.protocol.common.util.Ints;
import net.protocol.cops.CopsTest;

import java.nio.ByteBuffer;

/**
 * @author jinhongw@gmail.com
 */
public abstract class CopsObjectTest extends CopsTest {

    /**
     * @param o CopsObject
     * @return ByteBuffer
     */
    public static ByteBuffer byteMe(CopsObject o) {
        System.out.println(o);

        ByteBuffer dest = ByteBuffer.allocate(Ints.multiple4(o.getLength()));
        System.out.println("Allocation:\t" + dest);

        o.byteMe(dest);
        System.out.println("Filling:\t" + dest);

        dest.flip();
        System.out.println("Draining:\t" + dest);

        System.out.println("Buffer:\t\t" + array(dest));

        return (ByteBuffer) dest.flip();
    }
}
