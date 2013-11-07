/**
 * 
 */
package org.pcmm.threading.impl;

import java.util.concurrent.Callable;

import org.pcmm.threading.IWorker;

/**
 * @author <a href="mailto:rhadjamar@gmail.com">Riadh HAJ AMOR
 * 
 */
public class Worker implements IWorker {

	private int waitTimer;

	private Callable<?> task;

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		if (waitTimer > 0) {
			try {
				wait(waitTimer);
				task.call();
			} catch (Throwable e) {
				e.printStackTrace();
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.pcmm.threading.IWorker#task(java.util.concurrent.Callable)
	 */
	@Override
	public void task(Callable<?> c) {
		this.task = c;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.pcmm.threading.IWorker#shouldWait(int)
	 */
	@Override
	public void shouldWait(int t) {
		waitTimer = t;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.pcmm.threading.IWorker#done()
	 */
	@Override
	public void done() {
		// TODO Auto-generated method stub

	}

}
