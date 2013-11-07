package org.pcmm.threading;

import java.util.concurrent.Callable;
/**
 * @author <a href="mailto:rhadjamar@gmail.com">Riadh HAJ AMOR
 * 
 */
public interface IWorker extends Runnable {

	/**
	 * defines the task to be performed by this worker
	 * 
	 * @param c
	 */
	void task(Callable<?> c);

	/**
	 * defines wait time before start working on the task
	 * 
	 * @param t
	 */
	void shouldWait(int t);

	/**
	 * ends the current working task.
	 */
	void done();

}
