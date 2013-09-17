package net.protocol.cops.object;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;

/**
 * <pre>
 *        C-Num = 13[PDPRedirAddr] | 14[LastPDPAddr],
 *
 *        C-Type = 1, IPv4 Address
 *                 0             1              2             3
 *        +--------------+--------------+--------------+--------------+
 *        |                   IPv4 Address format                     |
 *        +--------------+--------------+--------------+--------------+
 *
 *        C-Type = 2, IPv6 Address
 *                 0             1              2             3
 *        +--------------+--------------+--------------+--------------+
 *        |                                                           |
 *        +                                                           +
 *        |                                                           |
 *        +                    IPv6 Address format                    +
 *        |                                                           |
 *        +                                                           +
 *        |                                                           |
 *        +--------------+--------------+--------------+--------------+
 * </pre>
 *
 * @author jinhongw@gmail.com
 */
public class PDPAddr extends CopsObject {
    private final static int IPV4_LENGTH = 4;
    private final static int IPV6_LENGTH = 16;

    protected final static int LENGTH_FOR_IPV4 = Header.HEADER_LENGTH + IPV4_LENGTH;
    protected final static int LENGTH_FOR_IPV6 = Header.HEADER_LENGTH + IPV6_LENGTH;

    private InetAddress address;

    /**
     * @param cnum    C-Num
     * @param address InetAddress [Inet4Address, Inet6Address]l
     */
    protected PDPAddr(CNum cnum, InetAddress address) {
        this(size(IpCType.get(address)), cnum, address);
    }

    /**
     * @param length  PDPAddr's length
     * @param cnum    C-Num
     * @param address InetAddress [Inet4Address, Inet6Address]
     */
    protected PDPAddr(int length, CNum cnum, InetAddress address) {
        super(length, cnum, IpCType.get(address));

        this.address = address;
    }

    /**
     * Creates a new <code>PDPAddr</code> with the given source ByteBuffer
     * 
     * @param src The buffer from which bytes are to be retrieved
     * @throws IllegalCopsObjectException
     */
    protected PDPAddr(ByteBuffer src) throws IllegalCopsObjectException {
        super(src);

        CNum cn = getCNum();
        if (!(cn == CNum.LASTPDPADDR || cn == CNum.PDPREDIRADDR)) {
            Object expected = CNum.LASTPDPADDR + " | " + CNum.PDPREDIRADDR;
            throw new IllegalCopsObjectException(expected, cn);
        }

        byte ct = getCType();
        IpCType ipct = IpCType.valueOf(ct);
        if (ipct == null)
            throw new IllegalCopsObjectException(IpCType.class, ct);

        int length = (ipct == IpCType.IPV4) ? IPV4_LENGTH : IPV6_LENGTH;
        byte[] data = new byte[length];
        src.get(data);
        try {
            this.address = InetAddress.getByAddress(data);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    /**
     * @return InetAddress
     *         <p/>
     *         <p/>
     *         if C-Type is IpCType.IPV4 then Inet4Address
     *         <p/>
     *         if C-Type is IpCType.IPV6 then Inet6Address
     */
    public InetAddress getAddress() {
        return this.address;
    }

    /**
     * @throws UnsupportedOperationException
     */
    public int getPort() {
        throw new UnsupportedOperationException();
    }

    @Override
    public ByteBuffer byteMe(ByteBuffer dst) {
        super.byteMe(dst);

        dst.put(address.getAddress());
        
        return dst;
    }

    @Override
    protected void detail(StringBuilder buf) {
        buf.append(ctype());
        buf.append("=").append(address);
    }

    /**
     * Wraps a value of primitive type <code>byte</code> (C-Type) in an
     * enum(IpCType)
     *
     * @return ClientSI.CType
     * @see net.protocol.cops.object.CopsObject#getCType()
     */
    public IpCType ctype() {
        return IpCType.valueOf(getCType());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (getClass() != obj.getClass())
            return false;
        PDPAddr other = (PDPAddr) obj;
        if (address == null) {
            if (other.address != null)
                return false;
        } else if (!address.equals(other.address))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((address == null) ? 0 : address.hashCode());
        return result;
    }

    private static int size(byte ctype) {
        if (ctype == IpCType.IPV4.getValue())
            return LENGTH_FOR_IPV4;
        return LENGTH_FOR_IPV6;
    }
}
