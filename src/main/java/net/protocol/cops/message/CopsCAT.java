package net.protocol.cops.message;

import net.protocol.cops.CopsException;
import net.protocol.cops.CopsFormatErrorException;
import net.protocol.cops.object.*;

import java.nio.ByteBuffer;
import java.util.Iterator;
import java.util.List;

/**
 * <h3>Client-Accept (CAT) PDP -> PEP</h3>
 * <p/>
 * <p/>
 * The Client-Accept message is used to positively respond to the Client-Open
 * message. This message will return to the PEP a timer object indicating the
 * maximum time interval between keep-alive messages. Optionally, a timer
 * specifying the minimum allowed interval between accounting report messages
 * may be included when applicable.
 * <p/>
 * <pre>
 *               &lt;Client-Accept&gt;  ::= &lt;Common Header&gt;
 *                                    &lt;KA Timer&gt;
 *                                    [&lt;ACCT Timer&gt;]
 *                                    [&lt;Integrity&gt;]
 * </pre>
 * <p/>
 * <p/>
 * If the PDP refuses the client, it will instead issue a Client-Close message.
 * <p/>
 * <p/>
 * The KA Timer corresponds to maximum acceptable intermediate time between the
 * generation of messages by the PDP and PEP. The timer value is determined by
 * the PDP and is specified in seconds. A timer value of 0 implies no secondary
 * connection verification is necessary.
 * <p/>
 * <p/>
 * The optional ACCT Timer allows the PDP to indicate to the PEP that periodic
 * accounting reports SHOULD NOT exceed the specified timer interval per client
 * handle. This allows the PDP to control the rate at which accounting reports
 * are sent by the PEP (when applicable).
 * <p/>
 * <p/>
 * In general, accounting type Report messages are sent to the PDP when
 * determined appropriate by the PEP. The accounting timer merely is used by the
 * PDP to keep the rate of such updates in check (i.e. Preventing the PEP from
 * blasting the PDP with accounting reports). Not including this object implies
 * there are no PDP restrictions on the rate at which accounting updates are
 * generated.
 * <p/>
 * <p/>
 * If the PEP receives a malformed Client-Accept message it MUST generate a
 * Client-Close message specifying the appropriate error code.
 *
 * @author jinhongw@gmail.com
 */
public class CopsCAT extends CopsMessage {
    public final static OpCode OP_CODE = OpCode.CAT;

    private final static int MIN_LENGTH = CopsHeader.LENGTH + Timer.LENGTH;

    // Mandatory
    private Timer kaTimer;

    // Optional
    private Timer acctTimer;
    private Integrity integrity;

    /**
     * @param clientType Client-type
     * @param kat        Ka Timer
     */
    public CopsCAT(int clientType, int kat) {
        super(OP_CODE, clientType, MIN_LENGTH);

        this.kaTimer = new KATimer(kat);
    }

    /**
     * Creates a new <code>CopsCAT</code> with the given source ByteBuffer
     * 
     * @param src The buffer from which bytes are to be retrieved
     * @throws CopsException
     */
    public CopsCAT(ByteBuffer src) throws CopsException {
        super(src);

        if (getOpCode() != OpCode.CAT)
            throw new IllegalCopsMsgException(OpCode.CAT, getOpCode());

        fill(getObjects(src));
    }

    @Override
    public ByteBuffer byteMe(ByteBuffer dst) {
        super.byteMe(dst);

        kaTimer.byteMe(dst);

        if (acctTimer != null) acctTimer.byteMe(dst);
        if (integrity != null) integrity.byteMe(dst);
        
        return dst;
    }

    @Override
    protected void detail(StringBuilder buf) {
        buf.append(kaTimer);

        if (acctTimer != null)
            buf.append(", ").append(acctTimer);
        if (integrity != null)
            buf.append(", ").append(integrity);
    }

    /**
     * @return KATimer - Mandatory
     */
    public Timer getKaTimer() {
        return kaTimer;
    }

    /**
     * @return AcctTimer - Optional
     */
    public Timer getAcctTimer() {
        return acctTimer;
    }

    /**
     * @param acctTimer Optional
     */
    public void setAcctTimer(Timer acctTimer) {
        this.acctTimer = acctTimer;
        this.setMessageLength(size()); // must
    }

    /**
     * @return Integrity - Optional
     */
    public Integrity getIntegrity() {
        return integrity;
    }

    /**
     * @param integrity Optional
     */
    public void setIntegrity(Integrity integrity) {
        this.integrity = integrity;
        this.setMessageLength(size()); // must
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (getClass() != obj.getClass())
            return false;

        CopsCAT other = (CopsCAT) obj;
        if (kaTimer == null) {
            if (other.kaTimer != null)
                return false;
        } else if (!kaTimer.equals(other.kaTimer))
            return false;

        if (acctTimer == null) {
            if (other.acctTimer != null)
                return false;
        } else if (!acctTimer.equals(other.acctTimer))
            return false;

        if (integrity == null) {
            if (other.integrity != null)
                return false;
        } else if (!integrity.equals(other.integrity))
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((kaTimer == null) ? 0 : kaTimer.hashCode());
        result = prime * result + ((acctTimer == null) ? 0 : acctTimer.hashCode());
        result = prime * result + ((integrity == null) ? 0 : integrity.hashCode());
        return result;
    }

    private int size() {
        int length = MIN_LENGTH;

        if (acctTimer != null)
            length += acctTimer.getLength();
        if (integrity != null)
            length += integrity.getLength();
        return length;
    }

    private void fill(List<CopsObject> list) throws CopsFormatErrorException {
        if (list.size() == 0)
            throw new CopsFormatErrorException(getOpCode(), "No Object(0)");

        Iterator<CopsObject> its = list.iterator();
        CopsObject kat = its.next();
        if (!(kat instanceof Timer))
            throw new CopsFormatErrorException(getOpCode(), Timer.class, kat.getClass());
        if (kat.getCNum() != CNum.KATIMER)
            throw new CopsFormatErrorException(getOpCode(), KATimer.class, AcctTimer.class);
        kaTimer = (Timer) kat;

        while (its.hasNext()) {
            CopsObject e = its.next();
            switch (e.getCNum()) {
                case ACCTTIMER:
                    acctTimer = (Timer) e;
                    break;
                case INTEGRITY:
                    integrity = (Integrity) e;
                    break;
                default:
                    throw new CopsFormatErrorException(getOpCode(), e.getCNum());
            }
        }
    }
}
