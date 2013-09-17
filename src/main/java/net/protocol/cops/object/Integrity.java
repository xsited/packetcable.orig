package net.protocol.cops.object;

import java.nio.ByteBuffer;
import java.util.Arrays;

/**
 * <h3>Message Integrity Object (Integrity)</h3>
 * <p/>
 * The integrity object includes a sequence number and a message digest useful
 * for authenticating and validating the integrity of a COPS message. When used,
 * integrity is provided at the end of a COPS message as the last COPS object.
 * The digest is then computed over all of a particular COPS message up to but
 * not including the digest value itself. The sender of a COPS message will
 * compute and fill in the digest portion of the Integrity object. The receiver
 * of a COPS message will then compute a digest over the received message and
 * verify it matches the digest in the received Integrity object.
 * <p/>
 * <pre>
 *            C-Num = 16,
 *
 *            C-Type = 1, HMAC digest
 * </pre>
 * <p/>
 * <p/>
 * The HMAC integrity object employs HMAC (Keyed-Hashing for Message
 * Authentication) [HMAC] to calculate the message digest based on a key shared
 * between the PEP and its PDP.
 * <p/>
 * <p/>
 * This Integrity object specifies a 32-bit Key ID used to identify a specific
 * key shared between a particular PEP and its PDP and the cryptographic
 * algorithm to be used. The Key ID allows for multiple simultaneous keys to
 * exist on the PEP with corresponding keys on the PDP for the given PEPID. The
 * key identified by the Key ID was used to compute the message digest in the
 * Integrity object. All implementations, at a minimum, MUST support
 * HMAC-MD5-96, which is HMAC employing the MD5 Message-Digest Algorithm [MD5]
 * truncated to 96-bits to calculate the message digest.
 * <p/>
 * <p/>
 * This object also includes a sequence number that is a 32-bit unsigned integer
 * used to avoid replay attacks. The sequence number is initiated during an
 * initial Client-Open Client-Accept message exchange and is then incremented by
 * one each time a new message is sent over the TCP connection in the same
 * direction. If the sequence number reaches the value of 0xFFFFFFFF, the next
 * increment will simply rollover to a value of zero.
 * <p/>
 * <p/>
 * The variable length digest is calculated over a COPS message starting with
 * the COPS Header up to the Integrity Object (which MUST be the last object in
 * a COPS message) INCLUDING the Integrity object's header, Key ID, and Sequence
 * Number. The Keyed Message Digest field is not included as part of the digest
 * calculation. In the case of HMAC-MD5-96, HMAC-MD5 will produce a 128-bit
 * digest that is then to be truncated to 96-bits before being stored in or
 * verified against the Keyed Message Digest field as specified in [HMAC]. The
 * Keyed Message Digest MUST be 96-bits when HMAC-MD5-96 is used.
 * <p/>
 * <h4>Integrity Object = Header + Content</h4>
 * <p/>
 * <pre>
 * Header
 *                 0             1              2             3
 *        +-------------+-------------+-------------+-------------+
 *        |       Length (octets)     |    C-Num=16 |   C-Type=1  |
 *        +-------------+-------------+-------------+-------------+
 *
 * Content
 *              0             1              2             3
 *        +-------------+-------------+-------------+-------------+
 *        |                        Key ID                         |
 *        +-------------+-------------+-------------+-------------+
 *        |                    Sequence Number                    |
 *        +-------------+-------------+-------------+-------------+
 *        |                                                       |
 *        +                                                       +
 *        |               ...Keyed Message Digest...              |
 *        +                                                       +
 *        |                                                       |
 *        +-------------+-------------+-------------+-------------+
 * </pre>
 *
 * @author jinhongw@gmail.com
 */
public class Integrity extends CopsObject {
    public final static CNum C_NUM = CNum.INTEGRITY;
    public final static byte C_TYPE = 1;

    private final static int MAX_DIGEST_LENGTH = 12;
    private final static int NO_DIGEST_LENGTH = Header.HEADER_LENGTH + 4 + 4;
    private final static int MAX_LENGTH = NO_DIGEST_LENGTH + MAX_DIGEST_LENGTH;

    private final int keyId;
    private final int sequenceNumber;
    private final byte[] digest;

    /**
     * @param keyId          Key ID
     * @param sequenceNumber Sequence Number
     * @param digest         Keyed Message Digest
     */
    public Integrity(int keyId, int sequenceNumber, byte[] digest) {
        super(size(digest), C_NUM, C_TYPE);

        this.keyId = keyId;
        this.sequenceNumber = sequenceNumber;

        if (digest.length > MAX_DIGEST_LENGTH) {
            this.digest = new byte[MAX_DIGEST_LENGTH];
            System.arraycopy(digest, 0, this.digest, 0, MAX_DIGEST_LENGTH);
        } else {
            this.digest = digest;
        }
    }

    /**
     * Creates a new <code>Integrity</code> with the given source ByteBuffer
     * 
     * @param src The buffer from which bytes are to be retrieved
     * @throws IllegalCopsObjectException
     */
    public Integrity(ByteBuffer src) throws IllegalCopsObjectException {
        super(src);

        if (getCNum() != CNum.INTEGRITY)
            throw new IllegalCopsObjectException(CNum.INTEGRITY, getCNum());

        if (getCType() != C_TYPE)
            throw new IllegalCopsObjectException(C_TYPE, getCType());

        this.keyId = src.getInt();
        this.sequenceNumber = src.getInt();

        int length = getLength() - NO_DIGEST_LENGTH;
        final byte[] dst = new byte[(length > MAX_LENGTH ? MAX_LENGTH : length)];
        src.get(dst);
        this.digest = dst;

        // skip a number of zero-valued bytes padding data
        // int paddingSize = Ints.paddingSize(digest.length);
        // if (paddingSize > 0) src.position(src.position() + paddingSize);
    }

    /**
     * @return Key ID
     */
    public int getKeyId() {
        return keyId;
    }

    /**
     * @return Key ID
     */
    public int getSequenceNumber() {
        return sequenceNumber;
    }

    /**
     * @return Keyed Message Digest
     */
    public byte[] getDigest() {
        return digest;
    }

    @Override
    public ByteBuffer byteMe(ByteBuffer dst) {
        super.byteMe(dst);

        dst.putInt(keyId);
        dst.putInt(sequenceNumber);

        dst.put(digest);
        // padding with zeroes to the next 32-bit boundary
        // int paddingSize = Ints.paddingSize(digest.length);
        // if (paddingSize > 0) dst.put(empty, 0, paddingSize);
        
        return dst;
    }

    @Override
    protected void detail(StringBuilder buf) {
        buf.append("Key ID=").append(keyId).append(", ");
        buf.append("Sequence Number=").append(sequenceNumber).append(", ");
        buf.append("Message Digest=").append(new String(digest));
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (getClass() != obj.getClass())
            return false;
        Integrity other = (Integrity) obj;
        if (keyId != other.keyId)
            return false;
        if (sequenceNumber != other.sequenceNumber)
            return false;
        if (!Arrays.equals(digest, other.digest))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + keyId;
        result = prime * result + sequenceNumber;
        result = prime * result + Arrays.hashCode(digest);
        return result;
    }

    private static int size(byte[] digest) {
        int length = NO_DIGEST_LENGTH + digest.length;
        return length > MAX_LENGTH ? MAX_LENGTH : length;
    }
}
