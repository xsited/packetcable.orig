package net.protocol.cops;

import net.protocol.common.AbstractTest;

/**
 * @author jinhongw@gmail.com
 */
public class Test extends AbstractTest {
	private AbstractTest objects = new net.protocol.cops.object.Test();
	private AbstractTest messages = new net.protocol.cops.message.Test();
	
	@Override
	public void base() throws Exception {
		objects.base();
		messages.base();
	}
	
	@Override
	public void logic() throws Exception {
		objects.logic();
		messages.logic();
	}
}
