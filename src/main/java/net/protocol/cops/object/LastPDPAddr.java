package net.protocol.cops.object;

import java.net.InetAddress;
import java.nio.ByteBuffer;

/**
 * <h3>Last PDP Address (LastPDPAddr)</h3>
 * <p/>
 * <p/>
 * When a PEP sends a Client-Open message for a particular client-type the PEP
 * SHOULD specify the last PDP it has successfully opened (meaning it received a
 * Client-Accept) since the PEP last rebooted. If no PDP was used since the last
 * reboot, the PEP will simply not include this object in the Client-Open
 * message.
 * <p/>
 * <pre>
 *        C-Num = 14,
 *
 *        C-Type = 1, IPv4 Address (Same format as PDPRedirAddr)
 *
 *        C-Type = 2, IPv6 Address (Same format as PDPRedirAddr)
 * </pre>
 *
 * @author jinhongw@gmail.com
 */
public class LastPDPAddr extends PDPAddr {
    public final static int LENGTH_FOR_IPV4 = PDPAddr.LENGTH_FOR_IPV4;
    public final static int LENGTH_FOR_IPV6 = PDPAddr.LENGTH_FOR_IPV6;

    public final static CNum C_NUM = CNum.LASTPDPADDR;

    /**
     * @param address InetAddress [Inet4Address, Inet6Address]
     */
    public LastPDPAddr(InetAddress address) {
        super(C_NUM, address);
    }

    /**
     * Creates a new <code>LastPDPAddr</code> with the given source ByteBuffer
     * 
     * @param src The buffer from which bytes are to be retrieved
     * @throws IllegalCopsObjectException
     */
    public LastPDPAddr(ByteBuffer src) throws IllegalCopsObjectException {
        super(src);
    }
}
