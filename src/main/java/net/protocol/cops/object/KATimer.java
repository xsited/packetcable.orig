package net.protocol.cops.object;

/**
 * <h3>Keep-Alive Timer Object (KATimer)</h3>
 * <p/>
 * Times are encoded as 2 octet integer values and are in units of seconds. The
 * timer value is treated as a delta.
 * <p/>
 * <pre>
 *       C-Num = 10,
 *
 *       C-Type = 1, Keep-alive timer value
 * </pre>
 * <p/>
 * <p/>
 * Timer object used to specify the maximum time interval over which a COPS
 * message MUST be sent or received. The range of finite timeouts is 1 to 65535
 * seconds represented as an unsigned two-octet integer. The value of zero
 * implies infinity.
 * <p/>
 * <pre>
 *               0             1              2             3
 *       +--------------+--------------+--------------+--------------+
 *       |        //////////////       |        KA Timer Value       |
 *       +--------------+--------------+--------------+--------------+
 * </pre>
 *
 * @author jinhongw@gmail.com
 */
public class KATimer extends Timer {
    public final static CNum C_NUM = CNum.KATIMER;

    /**
     * @param timer KA Timer Value
     */
    public KATimer(int timer) {
        super(C_NUM, timer);
    }
}
