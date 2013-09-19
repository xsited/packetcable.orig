package net.protocol.pcmm.message;

import net.protocol.common.coding.ByteMe;

import java.nio.ByteBuffer;

/**
 * COPS Gate Control objects :
 *
 * Transaction Id
 * <p/>
 * <pre>
 *             0              1              2              3
 *      +--------------+--------------+--------------+--------------+
 *      | Length = 8                  |S-Num = 1     |S-Type = 1    |
 *      +--------------+--------------+--------------+--------------+
 *      | Transaction Identifier      | Gate Command Type           |
 *      +--------------+--------------+--------------+--------------+
 *      Global note: //// implies field is reserved, set to 0.
 * </pre>
 * <p/>
 * <p/>
 * The fields are:
 * <p/>
 * <pre>
 * The Transaction Identifier MUST be set to 0 when included in a Gate-Report-State message.
 * 
 * Gate Command Type is a 2-byte unsigned integer value which identifies the Gate Control message 
 * type and MUST be one of the following:
 *              <Reserved> 		1-3 
 *		Gate-Set 		4 
 *		Gate-Set-Ack 		5 
 *		Gate-Set-Err 		6 
 *		Gate-Info 		7	 
 *		Gate-Info-Ack 		8 
 *		Gate-Info-Err 		9 
 *		Gate-Delete 		10 
 *		Gate-Delete-Ack 	11 
 *		Gate-Delete-Err 	12 
 *		<Reserved> 		13 
 *		<Reserved> 		14 
 *		Gate-Report-State 	15 
 *		Gate-Cmd-Err 		16 
 *		PDP-Config 		17 
 *		PDP-Config-Ack 		18 
 *		PDP-Config-Err 		19 
 *		Synch-Request 		20 
 *		Synch-Report 		21 
 *		Synch-Complete 		22 
 *		Msg-Receipt 		23
 * </pre>
 *
 * 
gate_specification_add

    This function builds a gate with the attributes specified. Possible
    attributes are

        Direction      -  This can be 'Upstream' or 'Downstream' only.
                          If specified this overrides Gate_Flags as
                          direction is one bit of the Gate_Flags 
                          parameter.

        Priority       -  This is a value of 0 to 7. If specified this
                          overrides Gate_Class as Priority is 3 bits
                          of that parameter.

        PreEmption     -  This has a value of 0 or 1. This allows this
                          gate to take bandwidth from any other gates
                          already set against this subscriber. If
                          specified this overrides Gate_Class as this is
                          1 bit of that parameter.

        DSCPToSMark    -  This has a value of 0 or 1

        Priority       -  This has a value between 0 and 255 and should
                          determine the priority of the gate.

        Gate_Flags     -  This field is broken down into 2 used bits and
                          6 unused bits.

                          Bit 0    -  Direction. 
                                      0 is Downstream
                                      1 is Upstream
 
                                      If you use the Direction parameter
                                      this is set for you.

                          Bit 1    -  DSCP/TOS Field
                                      0 is enable
                                      1 is overwrite

        GateTOSField    - IP TOS and Precedence value.

        GateTOSMask     - IP TOS Mask settings

        GateClass       - This field is broken down into 8 bits as follows
                       
                          Bit 0-2   - Priority of 0-7
                          Bit 3     - PreEmption bit
                          Bit 4-7   - Configurable but should default 0

        Gate_T1         - Gate T1 timer

        Gate_T2         - Gate T2 timer

        Gate_T3         - Gate T3 timer

        Gate_T4         - Gate T4 timer
 *
 * @author xsited@yahoo.com
 */
class TransactionId implements ByteMe {
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
