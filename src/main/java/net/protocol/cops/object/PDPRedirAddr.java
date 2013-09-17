package net.protocol.cops.object;

import java.net.InetAddress;
import java.nio.ByteBuffer;

/**
 * <h3>PDP Redirect Address (PDPRedirAddr)</h3>
 * <p/>
 * <p/>
 * A PDP when closing a PEP session for a particular client-type may optionally
 * use this object to redirect the PEP to the specified PDP server address and
 * TCP port number:
 * <p/>
 * <p/>
 * <pre>
 *        C-Num = 13,
 *
 *        C-Type = 1, IPv4 Address + TCP Port
 *                 0             1              2             3
 *        +--------------+--------------+--------------+--------------+
 *        |                   IPv4 Address format                     |
 *        +--------------+--------------+--------------+--------------+
 *        |  /////////////////////////  |       TCP Port Number       |
 *        +-----------------------------+-----------------------------+
 *
 *        C-Type = 2, IPv6 Address + TCP Port
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
 *        |  /////////////////////////  |       TCP Port Number       |
 *        +-----------------------------+-----------------------------+
 * </pre>
 *
 * @author jinhongw@gmail.com
 */
public class PDPRedirAddr extends PDPAddr {
    public final static int LENGTH_FOR_IPV4 = PDPAddr.LENGTH_FOR_IPV4 + 4;
    public final static int LENGTH_FOR_IPV6 = PDPAddr.LENGTH_FOR_IPV6 + 4;

    public final static CNum C_NUM = CNum.PDPREDIRADDR;

    private final int port;

    /**
     * @param address InetAddress [Inet4Address, Inet6Address]
     * @param port    int
     */
    public PDPRedirAddr(InetAddress address, int port) {
        super(size(IpCType.get(address)), C_NUM, address);

        this.port = port;
    }

    /**
     * Creates a new <code>PDPRedirAddr</code> with the given source ByteBuffer
     * 
     * @param src The buffer from which bytes are to be retrieved
     * @throws IllegalCopsObjectException
     */
    public PDPRedirAddr(ByteBuffer src) throws IllegalCopsObjectException {
        super(src);

        if (getCNum() != CNum.PDPREDIRADDR)
            throw new IllegalCopsObjectException(CNum.PDPREDIRADDR, getCNum());

        src.getShort(); // must read padding data
        this.port = src.getShort();
    }

    public int getPort() {
        return this.port;
    }

    @Override
    public ByteBuffer byteMe(ByteBuffer dst) {
        super.byteMe(dst);

        dst.putShort((short) 0); // must write padding data
        dst.putShort((short) port);
        
        return dst;
    }

    @Override
    protected void detail(StringBuilder buf) {
        super.detail(buf);

        buf.append(", port=").append(port);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (getClass() != obj.getClass())
            return false;
        PDPRedirAddr other = (PDPRedirAddr) obj;
        if (port != other.port)
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + port;
        return result;
    }

    private static int size(byte ctype) {
        if (ctype == IpCType.IPV4.getValue())
            return LENGTH_FOR_IPV4;
        return LENGTH_FOR_IPV6;
    }
}
