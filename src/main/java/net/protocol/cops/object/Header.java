package net.protocol.cops.object;

import net.protocol.common.coding.ByteMe;

import java.nio.ByteBuffer;

/**
 * <pre>
 *               0             1              2             3
 *        +-------------+-------------+-------------+-------------+
 *        |       Length (octets)     |    C-Num    |   C-Type    |
 *        +-------------+-------------+-------------+-------------+
 * </pre>
 *
 * @author jinhongw@gmail.com
 */
class Header implements ByteMe {
    public final static int HEADER_LENGTH = 4;

    private final int length;
    private final CNum cNum;
    private final byte cType;

    /**
     * @param length the Length of cops Object
     * @param cnum   the C-Num of cops Object
     * @param ctype  the C-Type of cops Object
     */
    public Header(int length, CNum cnum, byte ctype) {
        this.length = length;
        this.cNum = cnum;
        this.cType = ctype;
    }

    /**
     * @param src the given source ByteBuffer
     * @throws IllegalCopsObjectException
     */
    public Header(ByteBuffer src) throws IllegalCopsObjectException {
        this.length = src.getShort();

        byte v = src.get();
        this.cNum = CNum.valueOf(v);
        if (this.cNum == null)
            throw new IllegalCopsObjectException(CNum.class, v);

        this.cType = src.get();
    }

    /**
     * @return the Length of cops Object
     */
    public int getLength() {
        return length;
    }

    /**
     * @return the C-Num of cops Object
     */
    public CNum getCNum() {
        return cNum;
    }

    /**
     * @return the C-Type of cops Object
     */
    public byte getCType() {
        return cType;
    }

    /**
     * @param dst ByteBuffer
     */
    @Override
    public ByteBuffer byteMe(ByteBuffer dst) {
        dst.putShort((short) length);
        dst.put(cNum.getValue());
        dst.put(cType);
        
        return dst;
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append(getClass().getSimpleName()).append(" [");
        buf.append("Length=").append(length).append(", ");
        buf.append("C-Num=").append(cNum).append(", ");
        buf.append("C-Type=").append(cType);
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
        Header other = (Header) obj;
        if (length != other.length)
            return false;
        if (cNum != other.cNum)
            return false;
        if (cType != other.cType)
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 17;
        int result = 31;
        result = prime * result + ((cNum == null) ? 0 : cNum.hashCode());
        result = prime * result + cType;
        result = prime * result + length;
        return result;
    }
}
