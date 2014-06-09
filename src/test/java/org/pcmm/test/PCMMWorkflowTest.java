package org.pcmm.test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.pcmm.rcd.ICMTS;
import org.pcmm.rcd.IPCMMPolicyServer;
import org.pcmm.rcd.IPCMMPolicyServer.IPSCMTSClient;
import org.pcmm.rcd.impl.CMTS;
import org.pcmm.rcd.impl.PCMMPolicyServer;

public class PCMMWorkflowTest {

	/**
	 * CMTS emulator, when testing with a real CMTS this should be set to null
	 * and shoudln't be started
	 */
	private static ICMTS cmts;
	/**
	 * CMTS host address, when testing with a real CMTS this should be CMTS
	 * address
	 */
	private static InetAddress host;

	private static IPCMMPolicyServer server;
	private IPSCMTSClient client;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		// comment this when using real CMTS
		// ###################################
		cmts = new CMTS();
		cmts.startServer();
		// ###################################

		server = new PCMMPolicyServer();
		try {
			// this should be set to the cmts host ex :
			// InetAddress.getByName("10.10.10.10") or
			// InetAddress.getByName("my-cmts-host-name")
			host = InetAddress.getLocalHost();
			assertNotNull(host);
		} catch (UnknownHostException uhe) {
			fail("could not get host address ");
		}
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		if (cmts != null)
			cmts.stopServer();
	}

	@Before
	public void testConnect() {
		client = server.requestCMTSConnection(host);
		assertNotNull(client);
		assertNotNull("No MM version info", client.getVersionInfo());
		assertTrue("MM minor version =  MM major version :: CMTS exhausted all protocol selection attempts", client.getVersionInfo().getMajorVersionNB() != client.getVersionInfo().getMinorVersionNB());
	}

	@After
	public void tearDown() throws Exception {
		assertNotNull(client);
		assertTrue("Client disconnection failed", client.disconnect());
	}

	@Test
	public void testGateSet() {
		assertNotNull(client);
		assertTrue("Gate-Set failed", !client.gateSet());
	}

	@Test
	public void testGateDelete() {
		assertNotNull(client);
		assertTrue("Gate-Delete failed", !client.gateDelete());

	}

	@Test
	public void testGateInfo() {
		assertNotNull(client);
		assertTrue("Gate-Info failed", !client.gateInfo());
	}

	@Test
	public void testGateSynchronize() {
		assertNotNull(client);
		assertTrue("Gate-Synchronize failed", !client.gateSynchronize());
	}

}
