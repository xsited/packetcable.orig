package org.pcmm.concurrent.impl;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.pcmm.concurrent.IWorker;
import org.pcmm.concurrent.IWorkerPool;

/**
 * @author <a href="mailto:rhadjamar@gmail.com">Riadh HAJ AMOR
 * 
 */
public class WorkerPool implements IWorkerPool {

	/**
	 * 
	 */
	private Map<Integer, WeakReference<IWorker>> workersMap;

	public WorkerPool() {
		this(DEFAULT_MAX_WORKERS);
	}

	public WorkerPool(int size) {
		workersMap = new HashMap<Integer, WeakReference<IWorker>>(size);
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
		int ref = workerRef.hashCode();
		workersMap.put(ref, workerRef);
		worker.shouldWait(t);
		new Thread(worker).start();
		return ref;
	}


	/* (non-Javadoc)
	 * @see org.pcmm.concurrent.IWorkerPool#schedule(org.pcmm.concurrent.IWorker)
	 */
	@Override
	public int schedule(IWorker worker) {
		return schedule(worker, 0);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.pcmm.concurrent.IWorkerPool#sendKillSignal(int)
	 */
	@Override
	public void sendKillSignal(int pid) {
		if (workersMap.size() > 0) {
			WeakReference<IWorker> weakRef = workersMap.get(pid);
			if (weakRef != null) {
				IWorker ref = weakRef.get();
				if (ref != null)
					ref.done();
				if (!weakRef.isEnqueued()) {
					weakRef.clear();
					weakRef.enqueue();
				}
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
		for (WeakReference<IWorker> weakRef : workersMap.values()) {
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
		for (Iterator<Integer> pids = workersMap.keySet().iterator(); pids
				.hasNext();) {
			WeakReference<IWorker> weakRef = workersMap.get(pids.next());
			IWorker ref = weakRef.get();
			if (ref == null) {
				if (!weakRef.isEnqueued()) {
					weakRef.clear();
					weakRef.enqueue();
				}
				workersMap.remove(weakRef);
			}
		}

	}

}
