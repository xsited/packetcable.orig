/**
 * 
 */
package org.pcmm.messages.impl;

import org.pcmm.messages.IMessage;
import org.pcmm.messages.IMessageFactory;
import org.pcmm.messages.IMessage.MessageType;

/**
 * factory to create messages.
 * 
 * @author rhadjamor@gmail.com
 * 
 */
public class MessageFactory implements IMessageFactory {

	private static MessageFactory instance;

	private MessageFactory() {

	}

	public static MessageFactory getInstance() {
		if (instance == null)
			instance = new MessageFactory();
		return instance;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * pcmm.messages.IMessageFactory#create(pcmm.messages.IMessage.MessageType)
	 */
	public IMessage create(MessageType messageType) {
		return new PCMMMessage(messageType, null);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * pcmm.messages.IMessageFactory#create(pcmm.messages.IMessage.MessageType,
	 * java.lang.Object)
	 */
	public IMessage create(MessageType messageType, Object content) {
		return new PCMMMessage(messageType, content);
	}

}
