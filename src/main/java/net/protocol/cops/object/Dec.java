package net.protocol.cops.object;

import net.protocol.common.coding.ByteMe;
import net.protocol.cops.CopsFormatErrorException;
import net.protocol.cops.message.OpCode;
import net.protocol.cops.object.Decision.CType;

import java.nio.ByteBuffer;
import java.util.Collection;
import java.util.EnumMap;

/**
 * <pre>
 * Decision (DEC)  PDP -> PEP
 *                &lt;Decision&gt; ::= &lt;Context&gt;
 *                               &lt;Decision: Flags&gt;
 *                               [&lt;Decision: Stateless Data&gt;]
 *                               [&lt;Decision: Replacement Data&gt;]
 *                               [&lt;Decision: ClientSI Data&gt;]
 *                               [&lt;Decision: Named Data&gt;]
 * </pre>
 *
 * @author jinhongw@gmail.com
 */
public class Dec implements ByteMe {
    // Mandatory
    private Context context;
    private Decision flag;

    // Optional
    private EnumMap<Decision.CType, Decision> datas = new EnumMap<Decision.CType, Decision>(Decision.CType.class);

    /**
     * @param context Context
     */
    public Dec(Context context) {
        this.context = context;
    }

    /**
     * @param context Context
     * @param flag    Decision
     */
    public Dec(Context context, Decision flag) {
        this.context = context;
        setFlag(flag);
    }

    /**
     * @return Context - Mandatory
     */
    public Context getContext() {
        return context;
    }

    /**
     * @param ctype Decision.CType
     * @return Decision
     */
    public Decision getDecision(Decision.CType ctype) {
        if (ctype.equals(Decision.CType.FLAG))
            return flag;
        return datas.get(ctype);
    }

    /**
     * @param e Decision
     * @throws net.protocol.cops.CopsFormatErrorException
     *
     */
    public void add(Decision e) throws CopsFormatErrorException {
        Decision.CType ctype = CType.valueOf(e.getCType());
        if (ctype == null)
            throw new CopsFormatErrorException(OpCode.DEC, e.getCNum(),
                    e.getCType());
        if (ctype.equals(Decision.CType.FLAG))
            this.flag = e;
        else
            datas.put(e.ctype(), e);
    }

    public int size() {
        int length = context.getLength() + flag.getLength();

        Collection<Decision> list = datas.values();
        for (Decision e : list) {
            length += e.getLength();
        }

        return length;
    }

    public ByteBuffer byteMe(ByteBuffer dst) {
        if (context != null)
            context.byteMe(dst);
        flag.byteMe(dst);

        Collection<Decision> list = datas.values();
        for (Decision e : list) e.byteMe(dst);
        
        return dst;
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append(getClass().getSimpleName()).append(" [");
        buf.append(context).append(", ");
        buf.append(flag);

        Collection<Decision> list = datas.values();
        for (Decision e : list)
            buf.append(", ").append(e);

        buf.append("]");
        return buf.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;

        Dec other = (Dec) obj;
        if (flag == null) {
            if (other.flag != null)
                return false;
        } else if (!flag.equals(other.flag))
            return false;

        if (context == null) {
            if (other.context != null)
                return false;
        } else if (!context.equals(other.context))
            return false;

        if (datas == null) {
            if (other.datas != null)
                return false;
        } else if (!datas.equals(other.datas))
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((flag == null) ? 0 : flag.hashCode());
        result = prime * result + ((context == null) ? 0 : context.hashCode());
        result = prime * result + ((datas == null) ? 0 : datas.hashCode());
        return result;
    }

    private void setFlag(Decision flag) {
        if (!flag.ctype().equals(Decision.CType.FLAG))
            throw new IllegalArgumentException(
                    "Decision must is Flags - but now it is " + flag.ctype());
        this.flag = flag;
    }
}
