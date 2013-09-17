package net.protocol.cops.object;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;

/**
 * <pre>
 * 		C-Num = 3[INInt] | 4[OUTInt],
 *
 * 		C-Type = 1, IPv4 Address + Interface
 *
 *                0             1              2             3
 *        +--------------+--------------+--------------+--------------+
 *        |                   IPv4 Address format                     |
 *        +--------------+--------------+--------------+--------------+
 *        |                          ifindex                          |
 *        +--------------+--------------+--------------+--------------+
 *
 * 		C-Type = 2, IPv6 Address + Interface
 *
 *                0             1              2             3
 *        +--------------+--------------+--------------+--------------+
 *        |                                                           |
 *        +                                                           +
 *        |                                                           |
 *        +                    IPv6 Address format                    +
 *        |                                                           |
 *        +                                                           +
 *        |                                                           |
 *        +--------------+--------------+--------------+--------------+
 *        |                          ifindex                          |
 *        +--------------+--------------+--------------+--------------+
 * </pre>
 *
 * @author jinhongw@gmail.com
 */
public class Int extends CopsObject {
    private final static int IPV4_LENGTH = 4;
    private final static int IPV6_LENGTH = 16;

    public final static int LENGTH_FOR_IPV4 = Header.HEADER_LENGTH + IPV4_LENGTH + 4;
    public final static int LENGTH_FOR_IPV6 = Header.HEADER_LENGTH + IPV6_LENGTH + 4;

    private InetAddress address;
    private int intf;

    /**
     * @param cnum    C-Num
     * @param address InetAddress [Inet4Address, Inet6Address]
     * @param intf    Interface
     */
    protected Int(CNum cnum, InetAddress address, int intf) {
        super(size(IpCType.get(address)), cnum, IpCType.get(address));

        this.address = address;
        this.intf = intf;
    }

    /**
     * Creates a new <code>Int</code> with the given source ByteBuffer
     * 
     * @param src The buffer from which bytes are to be retrieved
     * @throws IllegalCopsObjectException
     */
    public Int(ByteBuffer src) throws IllegalCopsObjectException {
        super(src);

        CNum cn = getCNum();
        if (!(cn == CNum.ININT || cn == CNum.OUTINT)) {
            Object expected = CNum.ININT + " | " + CNum.OUTINT;
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
        this.intf = src.getInt();
    }

    /**
     * @return IP Address
     */
    public InetAddress getAddress() {
        return address;
    }

    /**
     * @return Interface
     */
    public int getIntf() {
        return intf;
    }

    @Override
    public ByteBuffer byteMe(ByteBuffer dst) {
        super.byteMe(dst);

        dst.put(address.getAddress());
        dst.putInt(intf);
        
        return dst;
    }

    @Override
    protected void detail(StringBuilder buf) {
        buf.append(ctype());
        buf.append("=").append(address);
    }

    /**
     * Wraps a value of primitive type <code>byte</code> (C-Type) in an
     * enum(Int.CType)
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
        Int other = (Int) obj;
        if (address == null) {
            if (other.address != null)
                return false;
        } else if (!address.equals(other.address))
            return false;
        if (intf != other.intf)
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((address == null) ? 0 : address.hashCode());
        result = prime * result + intf;
        return result;
    }

    private static int size(byte ctype) {
        if (ctype == IpCType.IPV4.getValue())
            return LENGTH_FOR_IPV4;
        return LENGTH_FOR_IPV6;
    }
}
