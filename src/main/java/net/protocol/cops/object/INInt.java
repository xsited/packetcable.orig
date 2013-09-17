package net.protocol.cops.object;

import java.net.InetAddress;

/**
 * <h3>In-Interface Object (IN-Int)</h3>
 * <p/>
 * <p/>
 * The In-Interface Object is used to identify the incoming interface on which a
 * particular request applies and the address where the received message
 * originated. For flows or messages generated from the PEP's local host, the
 * loop back address and ifindex are used.
 * <p/>
 * <p/>
 * This Interface object is also used to identify the incoming (receiving)
 * interface via its ifindex. The ifindex may be used to differentiate between
 * sub-interfaces and unnumbered interfaces (see RSVP's LIH for an example).
 * When SNMP is supported by the PEP, this ifindex integer MUST correspond to
 * the same integer value for the interface in the SNMP MIB-II interface index
 * table.
 * <p/>
 * <p/>
 * Note: The ifindex specified in the In-Interface is typically relative to the
 * flow of the underlying protocol messages. The ifindex is the interface on
 * which the protocol message was received.
 * <p/>
 * <pre>
 *            C-Num = 3,
 *
 *            C-Type = 1, IPv4 Address + Interface
 * </pre>
 * <p/>
 * For this type of the interface object, the IPv4 address specifies the IP
 * address that the incoming message came from.
 * <p/>
 * <pre>
 *
 *            C-Type = 2, IPv6 Address + Interface
 * </pre>
 * <p/>
 * <p/>
 * For this type of the interface object, the IPv6 address specifies the IP
 * address that the incoming message came from. The ifindex is used to refer to
 * the MIB-II defined local incoming interface on the PEP as described above.
 *
 * @author jinhongw@gmail.com
 */
public class INInt extends Int {
    public final static CNum C_NUM = CNum.ININT;

    /**
     * @param address InetAddress [Inet4Address, Inet6Address]
     * @param intf    Interface
     */
    public INInt(InetAddress address, int intf) {
        super(C_NUM, address, intf);
    }
}
