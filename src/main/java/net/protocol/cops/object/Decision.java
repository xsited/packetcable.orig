package net.protocol.cops.object;

import net.protocol.common.util.Ints;

import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * <h3>Decision Object (Decision)</h3>
 * <p/>
 * Decision made by the PDP. Appears in replies. The specific non- mandatory
 * decision objects required in a decision to a particular request depend on the
 * type of client.
 * <p/>
 * <pre>
 *                C-Num = 6
 *
 *                C-Type = 1, Decision Flags (Mandatory)
 *
 *                0             1              2             3
 *        +--------------+--------------+--------------+--------------+
 *        |        Command-Code         |            Flags            |
 *        +--------------+--------------+--------------+--------------+
 *
 *            Commands:
 *                0 = NULL Decision (No configuration data available)
 *                1 = Install (Admit request/Install configuration)
 *                2 = Remove (Remove request/Remove configuration)
 *
 *            Flags:
 *                 0x01 = Trigger Error (Trigger error message if set)
 *                 Note: Trigger Error is applicable to client-types that
 *                 are capable of sending error notifications for signaled
 *                 messages.
 *
 *
 *   Flag values not applicable to a given context's R-Type or
 *        client-type MUST be ignored by the PEP.
 *
 *               C-Type = 2, Stateless Data
 *
 *        This type of decision object carries additional stateless
 *        information that can be applied by the PEP locally. It is a
 *        variable length object and its internal format SHOULD be
 *        specified in the relevant COPS extension document for the given
 *        client-type. This object is optional in Decision messages and is
 *        interpreted relative to a given context.
 *
 *        It is expected that even outsourcing PEPs will be able to make
 *        some simple stateless policy decisions locally in their LPDP. As
 *        this set is well known and implemented ubiquitously, PDPs are
 *        aware of it as well (either universally, through configuration,
 *        or using the Client-Open message). The PDP may also include this
 *        information in its decision, and the PEP MUST apply it to the
 *        resource allocation event that generated the request.
 *
 *                C-Type = 3, Replacement Data
 *
 *        This type of decision object carries replacement data that is to
 *        replace existing data in a signaled message. It is a variable
 *        length object and its internal format SHOULD be specified in the
 *        relevant COPS extension document for the given client-type. It is
 *        optional in Decision messages and is interpreted relative to a
 *        given context.
 *
 *                C-Type = 4, Client Specific Decision Data
 *
 *        Additional decision types can be introduced using the Client
 *        Specific Decision Data Object. It is a variable length object and
 *        its internal format SHOULD be specified in the relevant COPS
 *        extension document for the given client-type. It is optional in
 *        Decision messages and is interpreted relative to a given context.
 *
 *                C-Type = 5, Named Decision Data
 *
 *        Named configuration information is encapsulated in this version
 *        of the decision object in response to configuration requests. It
 *        is a variable length object and its internal format SHOULD be
 *        specified in the relevant COPS extension document for the given
 *        client-type. It is optional in Decision messages and is
 *        interpreted relative to both a given context and decision flags.
 *
 * </pre>
 *
 * @author jinhongw@gmail.com
 */
public class Decision extends CopsObject {
    public final static CNum C_NUM = CNum.DECISION;

    private final static int FLAG_DEC_LENGTH = Header.HEADER_LENGTH + 4;

    private int command;
    private int flag;

    private byte[] data;

    /**
     * @param command Command-Code
     * @param flag    Decision Flags
     */
    public Decision(int command, int flag) {
        this(C_NUM, command, flag);
    }

    /**
     * @param command Command-Code
     * @param flag    Decision Flags
     */
    public Decision(Decision.Command command, int flag) {
        this(C_NUM, command.getCode(), flag);
    }

    protected Decision(CNum cnum, int command, int flag) {
        super(FLAG_DEC_LENGTH, cnum, CType.FLAG.value);

        this.command = command;
        this.flag = flag;
    }

    /**
     * @param ctype Decision CType
     * @param data  Decision Data
     */
    public Decision(Decision.CType ctype, byte[] data) {
        this(C_NUM, ctype, data);
    }

    protected Decision(CNum cnum, Decision.CType ctype, byte[] data) {
        super(size(data), cnum, ctype.value);

        this.data = data;
    }

    /**
     * Creates a new <code>Decision</code> with the given source ByteBuffer
     * 
     * @param src The buffer from which bytes are to be retrieved
     * @throws IllegalCopsObjectException
     */
    public Decision(ByteBuffer src) throws IllegalCopsObjectException {
        super(src);

        CNum cn = getCNum();
        if (!(cn == CNum.DECISION || cn == CNum.LPDPDECISION)) {
            Object expected = CNum.DECISION + " | " + CNum.LPDPDECISION;
            throw new IllegalCopsObjectException(expected, cn);
        }

        if (ctype() == null)
            throw new IllegalCopsObjectException(Decision.CType.class, getCType());

        if (isFlag()) {
            this.command = src.getShort();
            this.flag = src.getShort();
        } else {
            int length = getLength() - Header.HEADER_LENGTH;
            final byte[] dst = new byte[length];
            src.get(dst);
            this.data = dst;

            // skip a number of zero-valued bytes padding data
            int paddingSize = Ints.paddingSize(data.length);
            if (paddingSize > 0) src.position(src.position() + paddingSize);
        }
    }

    /**
     * @return Command-Code
     * @throws UnsupportedOperationException if the Decision not is Flags
     */
    public int getCommand() {
        if (isFlag())
            return command;
        throw new UnsupportedOperationException();
    }

    /**
     * C-Type = 1, Decision Flags
     *
     * @return int
     * @throws UnsupportedOperationException if the Decision not is Flags
     */
    public int getFlag() {
        if (isFlag())
            return flag;
        throw new UnsupportedOperationException();
    }

    /**
     * <pre>
     * 	C-Type = 2, Stateless Data
     * 	C-Type = 3, Replacement Data
     * 	C-Type = 4, Client Specific Decision Data
     * 	C-Type = 5, Named Decision Data
     * </pre>
     *
     * @return Decision Data
     * @throws UnsupportedOperationException if the Decision is Flags
     */
    public byte[] getData() {
        if (isFlag())
            throw new UnsupportedOperationException();
        return data;
    }

    @Override
    public ByteBuffer byteMe(ByteBuffer dst) {
        super.byteMe(dst);

        if (isFlag()) {
            dst.putShort((short) command);
            dst.putShort((short) flag);
        } else {
            // padding with zeroes to the next 32-bit boundary
            dst.put(data);
            int paddingSize = Ints.paddingSize(data.length);
            if (paddingSize > 0) dst.put(empty, 0, paddingSize);
        }
        
        return dst;
    }

    @Override
    protected void detail(StringBuilder buf) {
        if (isFlag()) {
            Command e = Command.valueOf(command);
            buf.append("Command-Code=").append((e == null) ? command : e).append(", ");
            buf.append("Flags=").append(flag);
        } else {
            buf.append("data");
            buf.append("=").append(new String(data));
        }
    }

    /**
     * Wraps a value of primitive type <code>byte</code> (C-Type) in an
     * enum(Decision.CType)
     *
     * @return ClientSI.CType
     * @see net.protocol.cops.object.CopsObject#getCType()
     */
    public Decision.CType ctype() {
        return CType.valueOf(getCType());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (getClass() != obj.getClass())
            return false;

        Decision other = (Decision) obj;
        if (isFlag()) {
            if (command != other.command)
                return false;
            if (flag != other.flag)
                return false;
        } else {
            if (!Arrays.equals(data, other.data))
                return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        if (isFlag()) {
            result = prime * result + command;
            result = prime * result + flag;
        } else {
            result = prime * result + Arrays.hashCode(data);
        }
        return result;
    }

    private boolean isFlag() {
        if (getCType() == CType.FLAG.value)
            return true;
        return false;
    }

    private static int size(byte[] data) {
        return Header.HEADER_LENGTH + data.length;
    }

    public static enum Command {
        NULL(0), INSTALL(1), REMOVE(2);

        private final int code;

        private final static Map<Integer, Command> KVS = new HashMap<Integer, Command>();

        static {
            for (Command e : values())
                KVS.put(e.code, e);
        }

        Command(int code) {
            this.code = code;
        }

        public int getCode() {
            return code;
        }

        /**
         * @param code int
         * @return Decision.Command
         */
        public static Command valueOf(int code) {
            return KVS.get(code);
        }

        @Override
        public String toString() {
            return name() + "(" + code + ")";
        }
    }

    public static enum CType {
        FLAG((byte) 1),
        STATELESS((byte) 2),
        REPLACEMENT((byte) 3),
        CLIENTSI((byte) 4),
        NAMED((byte) 5);

        private final byte value;

        private final static Map<Byte, CType> KVS = new HashMap<Byte, CType>();

        static {
            for (CType e : values())
                KVS.put(e.value, e);
        }

        CType(byte value) {
            this.value = value;
        }

        public byte getValue() {
            return value;
        }

        public static CType valueOf(byte value) {
            return KVS.get(value);
        }

        @Override
        public String toString() {
            return name() + "(" + value + ")";
        }
    }
}
