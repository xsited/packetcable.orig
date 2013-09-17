package net.protocol.cops.message;

import net.protocol.common.AbstractTest;

/**
 * @author jinhongw@gmail.com
 */
public class Test extends AbstractTest {
	private AbstractTest cat = new CopsCATTest();
	private AbstractTest cc = new CopsCCTest();
	private AbstractTest ka = new CopsKATest();
	
	private AbstractTest opn = new CopsOPNTest();
	private AbstractTest req = new CopsREQTest();
	private AbstractTest dec = new CopsDECTest();
	private AbstractTest rpt = new CopsRPTTest();
	private AbstractTest ssq = new CopsSSQTest();
	private AbstractTest ssc = new CopsSSCTest();
	
	@Override
	public void base() throws Exception {
		cat.base();
		cc.base();
		ka.base();
		
		opn.base();
		req.base();
		dec.base();
		rpt.base();
		ssc.base();
		ssq.base();
	}

	@Override
	public void logic() throws Exception {
		cat.logic();
		cc.logic();
		ka.logic();
		
		opn.logic();
		req.logic();
		dec.logic();
		rpt.logic();
		ssc.logic();
		ssq.logic();
	}
}