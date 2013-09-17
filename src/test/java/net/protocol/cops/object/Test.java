package net.protocol.cops.object;

import net.protocol.common.AbstractTest;

/**
 * @author jinhongw@gmail.com
 */
public class Test extends AbstractTest {
	private AbstractTest clientSI = new ClientSITest();
	private AbstractTest context = new ContextTest();
	private AbstractTest handle = new HandleTest();
	private AbstractTest integrity = new IntegrityTest();
	private AbstractTest pepid = new PEPIDTest();
	private AbstractTest reason = new ReasonTest();
	private AbstractTest reportType = new ReportTypeTest();
	private AbstractTest timer = new TimerTest();

	private AbstractTest decision = new DecisionTest();
	private AbstractTest dec = new DecTest();
	private AbstractTest lDec = new LDecTest();

	private AbstractTest iNInt = new INIntTest();
	private AbstractTest oUTInt = new OUTIntTest();
	private AbstractTest lastPDPAddr = new LastPDPAddrTest();
	private AbstractTest pDPRedirAddr = new PDPRedirAddrTest();

	@Override
	public void base() throws Exception {
		clientSI.base();
		context.base();
		handle.base();
		integrity.base();
		pepid.base();
		reason.base();
		reportType.base();
		timer.base();

		decision.base();
		dec.base();
		lDec.base();

		iNInt.base();
		oUTInt.base();
		lastPDPAddr.base();
		pDPRedirAddr.base();
	}

	@Override
	public void logic() throws Exception {
		clientSI.logic();
		context.logic();
		handle.logic();
		integrity.logic();
		pepid.logic();
		reason.logic();
		reportType.logic();
		timer.logic();

		decision.logic();
		dec.logic();
		lDec.logic();

		iNInt.logic();
		oUTInt.logic();
		lastPDPAddr.logic();
		pDPRedirAddr.logic();
	}
}
