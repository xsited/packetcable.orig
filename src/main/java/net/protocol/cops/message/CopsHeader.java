package net.protocol.cops.message;

import net.protocol.common.coding.ByteMe;

import java.nio.ByteBuffer;

/**
 * Each COPS message consists of the COPS header followed by a number of typed
 * objects.
 * <p/>
 * <pre>
 *             0              1              2              3
 *      +--------------+--------------+--------------+--------------+
 *      |Version| Flags|    Op Code   |       Client-type           |
 *      +--------------+--------------+--------------+--------------+
 *      |                      Message Length                       |
 *      +--------------+--------------+--------------+--------------+
 *      Global note: //// implies field is reserved, set to 0.
 * </pre>
 * <p/>
 * <p/>
 * The fields in the header are:
 * <p/>
 * <pre>
 *          Version: 4 bits
 *              COPS version number. Current version is 1.
 *
 *          Flags: 4 bits
 *              Defined flag values (all other flags MUST be set to 0):
 *                0x1 Solicited Message Flag Bit
 *                 This flag is set when the message is solicited by
 *                 another COPS message. This flag is NOT to be set
 *                 (value=0) unless otherwise specified in section 3.
 *
 *          Op Code: 8 bits
 *             The COPS operations:
 *               1 = Request                 (REQ)
 *               2 = Decision                (DEC)
 *               3 = Report State            (RPT)
 *               4 = Delete Request State    (DRQ)
 *               5 = Synchronize State Req   (SSQ)
 *               6 = Client-Open             (OPN)
 *               7 = Client-Accept           (CAT)
 *               8 = Client-Close            (CC)
 *               9 = Keep-Alive              (KA)
 *               10= Synchronize Complete    (SSC)
 *
 *        Client-type: 16 bits
 *
 *         The Client-type identifies the policy client. Interpretation of
 *         all encapsulated objects is relative to the client-type. Client-
 *         types that set the most significant bit in the client-type field
 *         are enterprise specific (these are client-types 0x8000 -
 *         0xFFFF). (See the specific client usage documents for particular
 *         client-type IDs). For KA Messages, the client-type in the header
 *         MUST always be set to 0 as the KA is used for connection
 *         verification (not per client session verification).
 *
 *         Message Length: 32 bits
 *
 *         Size of message in octets, which includes the standard COPS
 *         header and all encapsulated objects. Messages MUST be aligned on
 *         4 octet intervals.
 * </pre>
 *
 * @author jinhongw@gmail.com
 */
class CopsHeader implements ByteMe {
    public final static int LENGTH = 8;
    public final static byte DEFAULT_VERSION = 1;
    public final static byte DEFAULT_FLAGS = 0;

    private byte version = (byte) 1;
    private byte flags;
    private final OpCode opCode;
    private final int clientType;
    private int messageLength;

    /**
     * @param opCode     Op Code
     * @param clientType Client-type
     */
    public CopsHeader(OpCode opCode, int clientType) {
        this(DEFAULT_VERSION, DEFAULT_FLAGS, opCode, clientType, LENGTH);
    }

    /**
     * @param opCode        Op Code
     * @param clientType    Client-type
     * @param messageLength Message Length
     */
    public CopsHeader(OpCode opCode, int clientType, int messageLength) {
        this(DEFAULT_VERSION, DEFAULT_FLAGS, opCode, clientType, messageLength);
    }

    /**
     * @param flags         Flags
     * @param opCode        Op Code
     * @param clientType    Client-type
     * @param messageLength Message Length
     */
    public CopsHeader(byte flags, OpCode opCode, int clientType,
                      int messageLength) {
        this(DEFAULT_VERSION, flags, opCode, clientType, messageLength);
    }

    /**
     * @param version       Version
     * @param flags         Flags
     * @param opCode        Op Code
     * @param clientType    Client-type
     * @param messageLength Message Length
     */
    public CopsHeader(byte version, byte flags, OpCode opCode, int clientType,
                      int messageLength) {
        this.version = version;
        this.flags = flags;
        this.opCode = opCode;
        this.clientType = clientType;
        this.setMessageLength(messageLength);
    }

    /**
     * Creates a new <code>CopsHeader</code> with the given source ByteBuffer
     * 
     * @param src The buffer from which bytes are to be retrieved
     * @throws IllegalCopsMsgException
     */
    public CopsHeader(ByteBuffer src) throws IllegalCopsMsgException {
        byte vf = src.get();
        this.version = (byte) (vf >> 4);
        this.flags = (byte) (vf - (version << 4));

        byte op = src.get();
        this.opCode = OpCode.valueOf(op);
        if (opCode == null)
            throw new IllegalCopsMsgException(OpCode.class, op);

        this.clientType = src.getShort();
        this.messageLength = src.getInt();
    }

    /**
     * @return Version
     */
    public byte getVersion() {
        return version;
    }

    /**
     * @param version Version
     */
    public void setVersion(byte version) {
        this.version = version;
    }

    /**
     * @return Flags
     */
    public byte getFlags() {
        return flags;
    }

    /**
     * @param flags Flags
     */
    public void setFlags(byte flags) {
        this.flags = flags;
    }

    /**
     * @return Op Code
     */
    public OpCode getOpCode() {
        return opCode;
    }

    /**
     * @return Client-type
     */
    public int getClientType() {
        return clientType;
    }

    /**
     * @return Message Length
     */
    public int getMessageLength() {
        return messageLength;
    }

    /**
     * @param messageLength Message Length
     */
    public void setMessageLength(int messageLength) {
        this.messageLength = messageLength;
    }

    @Override
    public ByteBuffer byteMe(ByteBuffer dst) {
        dst.put((byte) ((version << 4) + flags));
        dst.put(opCode.getValue());
        dst.putShort((short) clientType);
        dst.putInt(messageLength);
        
        return dst;
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append(getClass().getSimpleName()).append(" [");
        buf.append("Version=").append(version).append(", ");
        buf.append("Flags=").append(flags).append(", ");
        buf.append("Op Code=").append(opCode).append(", ");
        buf.append("Client-type=").append(clientType).append(", ");
        buf.append("Message Length=").append(messageLength);
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
        CopsHeader other = (CopsHeader) obj;
        if (messageLength != other.messageLength)
            return false;
        if (opCode != other.opCode)
            return false;
        if (clientType != other.clientType)
            return false;
        if (flags != other.flags)
            return false;
        if (version != other.version)
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 17;
        int result = 31;
        result = prime * result + clientType;
        result = prime * result + flags;
        result = prime * result + messageLength;
        result = prime * result + ((opCode == null) ? 0 : opCode.hashCode());
        result = prime * result + version;
        return result;
    }
}
