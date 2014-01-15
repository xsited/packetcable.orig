/**
 *
 */
package org.pcmm.test;

import org.pcmm.PCMMProperties;

/**
 *
 */
public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// ICMTS icmts = new CMTS();
		// icmts.startServer();
		// IPCMMPolicyServer ps = new PCMMPolicyServer();
		// ps.requestCMTSConnection("localhost");
		// ps.gateSet();
		// IWorkerPool pool = new WorkerPool(2);
		// IWorker worker = new Worker(new Callable<String>() {
		// @Override
		// public String call() throws Exception {
		// System.out
		// .println("Main.main(...).new Callable() {...}.call()");
		// return null;
		// }
		// });
		// IWorker worker2 = new Worker(new Callable<String>() {
		// @Override
		// public String call() throws Exception {
		// System.out
		// .println("|||||||Main.main(...).new Callable() {...}.call()||||||||||||");
		// return null;
		// }
		// });
		// pool.schedule(worker2, 2000);
		// pool.schedule(worker, 500);
		// pool.recycle();
		System.out.println(PCMMProperties.get("pcmm.default.port"));

	}
}
