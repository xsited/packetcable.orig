/**
 * 
 */
package org.pcmm.messages;

import org.pcmm.messages.IMessage.MessageType;

/**
 * @author rhadjamor@gmail.com 
 * 
 */
public interface IMessageFactory {

	/**
	 * creates a new message with the specified message type.
	 * 
	 * @param messageType
	 *            message type
	 * @return new message.
	 */
	IMessage create(MessageType messageType);

	/**
	 * creates a new message with the specified message type and content
	 * 
	 * @param messageType
	 *            message type
	 * @param content
	 *            message content.
	 * @return new message.
	 */
	IMessage create(MessageType messageType, Object content);
}
