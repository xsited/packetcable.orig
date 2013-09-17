package net.protocol.cops.object;

/**
 * <h3>Accounting Timer Object (AcctTimer)</h3>
 * <p/>
 * Times are encoded as 2 octet integer values and are in units of seconds. The
 * timer value is treated as a delta.
 * <p/>
 * <pre>
 *       C-Num = 15,
 *
 *       C-Type = 1, Accounting timer value
 * </pre>
 * <p/>
 * <p/>
 * Optional timer value used to determine the minimum interval between periodic
 * accounting type reports. It is used by the PDP to describe to the PEP an
 * acceptable interval between unsolicited accounting updates via Report
 * messages where applicable. It provides a method for the PDP to control the
 * amount of accounting traffic seen by the network. The range of finite time
 * values is 1 to 65535 seconds represented as an unsigned two-octet integer. A
 * value of zero means there SHOULD be no unsolicited accounting updates.
 * <p/>
 * <pre>
 *              0             1              2             3
 *        +--------------+--------------+--------------+--------------+
 *        |        //////////////       |        ACCT Timer Value     |
 *        +--------------+--------------+--------------+--------------+
 * </pre>
 *
 * @author jinhongw@gmail.com
 */
public class AcctTimer extends Timer {
    public final static CNum C_NUM = CNum.ACCTTIMER;

    /**
     * @param timer ACCT Timer Value
     */
    public AcctTimer(int timer) {
        super(C_NUM, timer);
    }
}
