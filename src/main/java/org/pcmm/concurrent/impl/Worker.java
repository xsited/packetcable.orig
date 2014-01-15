/**
 * 
 */
package org.pcmm.concurrent.impl;

import java.util.concurrent.Callable;

import org.pcmm.concurrent.IWorker;

/**
 * @author <a href="mailto:rhadjamar@gmail.com">Riadh HAJ AMOR
 * 
 */
public class Worker implements IWorker {

	private int waitTimer;

	private Callable<?> task;

	public Worker() {

	}

	public Worker(Callable<?> task) {
		this.task = task;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		try {
			if (waitTimer > 0)
				Thread.sleep(waitTimer);
			task.call();
		} catch (Throwable e) {
			e.printStackTrace();
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
