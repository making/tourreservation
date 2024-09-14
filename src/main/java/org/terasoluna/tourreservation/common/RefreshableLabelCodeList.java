package org.terasoluna.tourreservation.common;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.function.Supplier;

public final class RefreshableLabelCodeList implements LabelCodeList {

	private final Supplier<LabelCodeList> supplier;

	private final AtomicReference<LabelCodeList> delegate = new AtomicReference<>();

	private final ReadWriteLock lock = new ReentrantReadWriteLock();

	private final AtomicBoolean initialized = new AtomicBoolean(false);

	RefreshableLabelCodeList(Supplier<LabelCodeList> supplier) {
		this.supplier = supplier;
	}

	@Override
	public List<LabelCode> labelCodes() {
		if (!this.initialized.get()) {
			this.refresh(true);
		}
		this.lock.readLock().lock();
		try {
			return this.delegate.get().labelCodes();
		}
		finally {
			this.lock.readLock().unlock();
		}
	}

	public void refresh() {
		this.refresh(false);
	}

	private void refresh(boolean initial) {
		this.lock.writeLock().lock();
		try {
			if (initial && !this.initialized.compareAndSet(false, true)) {
				return;
			}
			this.delegate.set(this.supplier.get());
		}
		finally {
			this.lock.writeLock().unlock();
		}
	}

}
