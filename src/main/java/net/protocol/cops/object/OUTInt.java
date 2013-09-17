package net.protocol.cops.object;

import java.net.InetAddress;

/**
 * <h3>Out-Interface Object (OUT-Int)</h3>
 * <p/>
 * <p/>
 * The Out-Interface is used to identify the outgoing interface to which a
 * specific request applies and the address for where the forwarded message is
 * to be sent. For flows or messages destined to the PEP's local host, the loop
 * back address and ifindex are used. The Out- Interface has the same formats as
 * the In-Interface Object.
 * <p/>
 * <p/>
 * This Interface object is also used to identify the outgoing (forwarding)
 * interface via its ifindex. The ifindex may be used to differentiate between
 * sub-interfaces and unnumbered interfaces (see RSVP's LIH for an example).
 * When SNMP is supported by the PEP, this ifindex integer MUST correspond to
 * the same integer value for the interface in the SNMP MIB-II interface index
 * table.
 * <p/>
 * <p/>
 * Note: The ifindex specified in the Out-Interface is typically relative to the
 * flow of the underlying protocol messages. The ifindex is the one on which a
 * protocol message is about to be forwarded.
 * <p/>
 * <p/>
 * <pre>
 *            C-Num = 4,
 *
 *            C-Type = 1, IPv4 Address + Interface
 * </pre>
 * <p/>
 * Same C-Type format as the In-Interface object. The IPv4 address specifies the
 * IP address to which the outgoing message is going. The ifindex is used to
 * refer to the MIB-II defined local outgoing interface on the PEP.
 * <p/>
 * <p/>
 * Same C-Type format as the In-Interface object. For this type of the interface
 * object, the IPv6 address specifies the IP address to which the outgoing
 * message is going. The ifindex is used to refer to the MIB-II defined local
 * outgoing interface on the PEP.
 *
 * @author jinhongw@gmail.com
 */
public class OUTInt extends Int {
    public final static CNum C_NUM = CNum.OUTINT;

    /**
     * @param address InetAddress [Inet4Address, Inet6Address]
     * @param intf    Interface
     */
    public OUTInt(InetAddress address, int intf) {
        super(C_NUM, address, intf);
    }
}
