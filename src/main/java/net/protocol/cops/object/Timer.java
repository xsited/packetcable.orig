package net.protocol.cops.object;

import java.nio.ByteBuffer;

/**
 * <p/>
 * Times are encoded as 2 octet integer values and are in units of seconds. The
 * timer value is treated as a delta.
 * <p/>
 * <pre>
 *       	C-Num = 10[KATimer] | 15[AcctTimer],
 *
 *          C-Type = 1, timer value
 *
 *               0             1              2             3
 *       +--------------+--------------+--------------+--------------+
 *       |        //////////////       |        Timer Value          |
 *       +--------------+--------------+--------------+--------------+
 * </pre>
 *
 * @author jinhongw@gmail.com
 */
public class Timer extends CopsObject {
    public final static int LENGTH = Header.HEADER_LENGTH + 4;
    public final static byte C_TYPE = 1;

    private final int timer;

    /**
     * @param cnum  C-Num
     * @param timer Timer Value [KA | ACCT]
     */
    protected Timer(CNum cnum, int timer) {
        super(LENGTH, cnum, C_TYPE);

        this.timer = timer;
    }

    /**
     * Creates a new <code>Timer</code> with the given source ByteBuffer
     * 
     * @param src The buffer from which bytes are to be retrieved
     * @throws IllegalCopsObjectException
     */
    public Timer(ByteBuffer src) throws IllegalCopsObjectException {
        super(src);

        CNum cn = getCNum();
        if (!(cn == CNum.KATIMER || cn == CNum.ACCTTIMER)) {
            Object expected = CNum.KATIMER + " | " + CNum.ACCTTIMER;
            throw new IllegalCopsObjectException(expected, cn);
        }

        if (getCType() != C_TYPE)
            throw new IllegalCopsObjectException(C_TYPE, getCType());

        src.getShort();
        this.timer = src.getShort();
    }

    /**
     * @return Timer Value [KA | ACCT]
     */
    public int getTimer() {
        return timer;
    }

    @Override
    public ByteBuffer byteMe(ByteBuffer dst) {
        super.byteMe(dst);

        dst.putShort((short) 0);
        dst.putShort((short) timer);
        
        return dst;
    }

    @Override
    protected void detail(StringBuilder buf) {
        buf.append("timer=").append(timer);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (getClass() != obj.getClass())
            return false;
        Timer other = (Timer) obj;
        if (timer != other.timer)
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + timer;
        return result;
    }
}
