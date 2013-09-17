package net.protocol.cops.message;

import net.protocol.common.util.Ints;
import net.protocol.cops.CopsTest;

import java.nio.ByteBuffer;

/**
 * @author jinhongw@gmail.com
 */
public abstract class CopsMessageTest extends CopsTest {

    /**
     * @param cops CopsMessage
     */
    public static ByteBuffer byteMe(CopsMessage cops) {
        return byteMe(cops, Ints.multiple4(cops.getMessageLength()));
    }

    /**
     *
     * @param cops
     * @param capacity
     * @return ByteBuffer
     */
    public static ByteBuffer byteMe(CopsMessage cops, int capacity) {
        System.out.println(cops);

        ByteBuffer dst = ByteBuffer.allocate(capacity);
        System.out.println("Allocation:\t" + dst);

        cops.byteMe(dst);
        System.out.println("Filling:\t" + dst);

        dst.flip();
        System.out.println("Draining:\t" + dst);

        System.out.println("Buffer:\t\t" + array(dst));

        return (ByteBuffer) dst.flip();
    }
}
