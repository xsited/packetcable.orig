package pcmm.messages;

/**
 * This defines the messages exchanged between client and server.
 * 
 * @author riadh
 * 
 */
public interface IMessage {

	enum MessageType {
		/**
		 * Client-Open message
		 */
		OPN,
		/**
		 * Client-Accept message
		 */
		CAT,
		/**
		 * Client-Close message
		 */
		CC,
		/**
		 * Request message
		 */
		REQ,
		/**
		 * Decision message
		 */
		DEC,
		/**
		 * Report-State message
		 */
		RPT,
		/**
		 * Delete-Request-State message
		 */
		DRQ,
		/**
		 * Keep-Alive message
		 */
		KA
	}

	/**
	 * sets the message type
	 * 
	 * @param mt
	 *            : message type.
	 */
	void setMessagType(MessageType mt);

	/**
	 * returns the message type.
	 * 
	 * @return message type
	 */
	MessageType getMessageType();

	/**
	 * sets the message content.
	 * 
	 * @param messageContent
	 *            : message content.
	 */
	void setContent(Object messageContent);

	/**
	 * returns this message content.
	 * 
	 * @return message content.
	 */
	Object getContent();

}
