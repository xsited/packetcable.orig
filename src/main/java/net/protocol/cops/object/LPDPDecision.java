package net.protocol.cops.object;

/**
 * <h3>LPDP Decision Object (LPDPDecision)</h3>
 * <p/>
 * <p/>
 * Decision made by the PEP's local policy decision point (LPDP). May appear in
 * requests. These objects correspond to and are formatted the same as the
 * client specific decision objects defined above.
 * <p/>
 * <pre>
 *            C-Num = 7
 *
 *            C-Type = (same C-Type as for Decision objects)
 * </pre>
 *
 * @author jinhongw@gmail.com
 */
public class LPDPDecision extends Decision {
    public final static CNum C_NUM = CNum.LPDPDECISION;

    /**
     * @param command Command-Code
     * @param flag    Decision Flags
     */
    public LPDPDecision(int command, int flag) {
        super(C_NUM, command, flag);
    }

    /**
     * @param command Command-Code
     * @param flag    Decision Flags
     */
    public LPDPDecision(Decision.Command command, int flag) {
        super(C_NUM, command.getCode(), flag);
    }

    /**
     * @param ctype Decision CType
     * @param data  Decision Data
     */
    public LPDPDecision(Decision.CType ctype, byte[] data) {
        super(C_NUM, ctype, data);
    }
}
