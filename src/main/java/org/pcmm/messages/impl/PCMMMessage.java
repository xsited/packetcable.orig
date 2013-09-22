/**
 * 
 */
package org.pcmm.messages.impl;

import org.pcmm.messages.IMessage;

/**
 * @author rhadjamor@gmail.com
 * 
 */
public class PCMMMessage implements IMessage {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * message type
	 */
	private MessageType messageType;

	/**
	 * message content
	 */
	private Object content;

	PCMMMessage() {

	}

	PCMMMessage(MessageType type, Object content) {
		setMessagType(type);
		setContent(content);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * pcmm.messages.IMessage#setMessagType(pcmm.messages.IMessage.MessageType)
	 */
	public void setMessagType(MessageType mt) {
		this.messageType = mt;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see pcmm.messages.IMessage#getMessageType()
	 */
	public MessageType getMessageType() {
		return this.messageType;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see pcmm.messages.IMessage#setContent(java.lang.Object)
	 */
	public void setContent(Object messageContent) {
		this.content = messageContent;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see pcmm.messages.IMessage#getContent()
	 */
	public Object getContent() {
		return this.content;
	}

}
