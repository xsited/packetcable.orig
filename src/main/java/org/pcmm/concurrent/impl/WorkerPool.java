package org.pcmm.concurrent.impl;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.pcmm.concurrent.IWorker;
import org.pcmm.concurrent.IWorkerPool;
/**
 * @author <a href="mailto:rhadjamar@gmail.com">Riadh HAJ AMOR
 * 
 */
public class WorkerPool implements IWorkerPool {

	List<WeakReference<IWorker>> filoQueue;

	public WorkerPool() {
		this(DEFAULT_MAX_WORKERS);
	}

	public WorkerPool(int size) {
		filoQueue = new ArrayList<WeakReference<IWorker>>(size);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.pcmm.threading.IWorkerPool#schedule(org.pcmm.threading.IWorker,
	 * int)
	 */
	@Override
	public int schedule(IWorker worker, int t) {
		if (worker == null)
			return -1;
		WeakReference<IWorker> workerRef = new WeakReference<IWorker>(worker);
		if (filoQueue.add(workerRef)) {
			worker.shouldWait(t);
			new Thread(worker).start();
			return (workerRef.hashCode());
		}
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.pcmm.threading.IWorkerPool#sendKillSignal(int)
	 */
	@Override
	public void sendKillSignal(int pid) {
		if (filoQueue.size() > pid) {
			WeakReference<IWorker> weakRef = filoQueue.get(pid);
			IWorker ref = weakRef.get();
			if (ref != null)
				ref.done();
			if (!weakRef.isEnqueued()) {
				weakRef.clear();
				weakRef.enqueue();
			}
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.pcmm.threading.IWorkerPool#killAll()
	 */
	@Override
	public void killAll() {
		for (WeakReference<IWorker> weakRef : filoQueue) {
			IWorker ref = weakRef.get();
			if (ref != null)
				ref.done();
			if (!weakRef.isEnqueued()) {
				weakRef.clear();
				weakRef.enqueue();
			}
		}
		recycle();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.pcmm.threading.IWorkerPool#recycle()
	 */
	@Override
	public void recycle() {
		for (Iterator<WeakReference<IWorker>> weakRefIt = filoQueue.iterator(); weakRefIt
				.hasNext();) {
			WeakReference<IWorker> weakRef = weakRefIt.next();
			IWorker ref = weakRef.get();
			if (ref == null) {
				if (!weakRef.isEnqueued()) {
					weakRef.clear();
					weakRef.enqueue();
				}
				filoQueue.remove(weakRef);
			}
		}

	}

}
