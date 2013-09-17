package net.protocol.cops.object;

import net.protocol.common.coding.ByteMe;
import net.protocol.common.util.StringUtils;

import java.nio.ByteBuffer;

/**
 * <h3>COPS Specific Object Formats</h3>
 * <p/>
 * All the objects follow the same object format; each object consists of one or
 * more 32-bit words with a four-octet header, using the following format:
 * <p/>
 * <pre>
 *               0             1              2             3
 *        +-------------+-------------+-------------+-------------+
 *        |       Length (octets)     |    C-Num    |   C-Type    |
 *        +-------------+-------------+-------------+-------------+
 *        |                                                       |
 *        //                  (Object contents)                   //
 *        |                                                       |
 *        +-------------+-------------+-------------+-------------+
 * </pre>
 * <p/>
 * <p/>
 * The length is a two-octet value that describes the number of octets
 * (including the header) that compose the object. If the length in octets does
 * not fall on a 32-bit word boundary, padding MUST be added to the end of the
 * object so that it is aligned to the next 32-bit boundary before the object
 * can be sent on the wire. On the receiving side, a subsequent object boundary
 * can be found by simply rounding up the previous stated object length to the
 * next 32-bit boundary.
 * <p/>
 * <p/>
 * Typically, C-Num identifies the class of information contained in the object,
 * and the C-Type identifies the subtype or version of the information contained
 * in the object.
 * <p/>
 * <pre>
 *       C-num: 8 bits
 *                1  = Handle
 *                2  = Context
 *                3  = In Interface
 *                4  = Out Interface
 *                5  = Reason code
 *                6  = Decision
 *                7  = LPDP Decision
 *                8  = Error
 *                9  = Client Specific Info
 *                10 = Keep-Alive Timer
 *                11 = PEP Identification
 *                12 = Report Type
 *                13 = PDP Redirect Address
 *                14 = Last PDP Address
 *                15 = Accounting Timer
 *                16 = Message Integrity
 *
 *       C-type: 8 bits
 *                Values defined per C-num.
 * </pre>
 *
 * @author jinhongw@gmail.com
 */
public abstract class CopsObject implements ByteMe {
	protected final static byte[] empty = new byte[4];
	
    private final Header header;

    /**
     * @param length the Length of cops Object
     * @param cnum   the C-Num of cops Object
     * @param ctype  the C-Type of cops Object
     */
    protected CopsObject(int length, CNum cnum, byte ctype) {
        header = new Header(length, cnum, ctype);
    }

    /**
     * Creates a new <code>CopsObject</code> with the given source ByteBuffer
     * 
     * @param src The buffer from which bytes are to be retrieved
     * @throws IllegalCopsObjectException
     */
    protected CopsObject(ByteBuffer src) throws IllegalCopsObjectException {
        header = new Header(src);
    }

    /**
     * @return the Length of cops Object
     */
    public int getLength() {
        return header.getLength();
    }

    /**
     * @return the C-Num of cops Object
     */
    public CNum getCNum() {
        return header.getCNum();
    }

    /**
     * @return the C-Type of cops Object
     */
    public byte getCType() {
        return header.getCType();
    }

    protected Object ctype() {
        return getCType();
    }

    @Override
    public ByteBuffer byteMe(ByteBuffer dst) {
        header.byteMe(dst);
        
        return dst;
    }

    protected abstract void detail(StringBuilder buf);

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append(getClass().getSimpleName()).append(" [");
        appendHeader(buf, header);
        buf.append(", ");
        detail(buf);
        StringUtils.deleteLastDot(buf);
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
        CopsObject other = (CopsObject) obj;
        if (header == null) {
            if (other.header != null)
                return false;
        } else if (!header.equals(other.header))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 17;
        int result = 31;
        result = prime * result + ((header == null) ? 0 : header.hashCode());
        return result;
    }

    private void appendHeader(StringBuilder buf, Header header) {
        buf.append(header.getClass().getSimpleName()).append(" [");
        buf.append("Length=").append(header.getLength()).append(", ");
        buf.append("C-Num=").append(header.getCNum()).append(", ");
        buf.append("C-Type=").append(ctype());
        buf.append("]");
    }
}
