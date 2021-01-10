package org.monjasa.carfactory.util;

import javafx.application.Platform;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ReadOnlyIntegerWrapper;
import javafx.collections.ObservableListBase;
import lombok.Getter;

import java.util.Collection;
import java.util.Iterator;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

public class ObservableBlockingQueue<E> extends ObservableListBase<E> implements BlockingQueue<E> {

    private final IntegerProperty capacity;
    private final BlockingQueue<E> backingBlockingQueue;

    public ObservableBlockingQueue(BlockingQueue<E> backingBlockingQueue) {
        this.backingBlockingQueue = backingBlockingQueue;
        this.capacity = new ReadOnlyIntegerWrapper(backingBlockingQueue.remainingCapacity());
    }

    @Override
    public boolean offer(E e) {

        final FutureTask<Boolean> query = new FutureTask<>(() -> {
            beginChange();
            boolean result = backingBlockingQueue.offer(e);
            if (result) {
                nextAdd(backingBlockingQueue.size() - 1, backingBlockingQueue.size());
            }
            endChange();
            return result;
        });

        Platform.runLater(query);

        try {
            return query.get();
        } catch (InterruptedException | ExecutionException exception) {
            exception.printStackTrace();
            return false;
        }
    }

    @Override
    public void put(E e) throws InterruptedException {

        backingBlockingQueue.put(e);

        Platform.runLater(() -> {
            beginChange();
            nextAdd(backingBlockingQueue.size() - 1, backingBlockingQueue.size());
            endChange();
        });
    }

    @Override
    public boolean offer(E e, long timeout, TimeUnit unit) throws InterruptedException {
        return false;
    }

    @Override
    public E take() throws InterruptedException {

        E e = backingBlockingQueue.take();

        Platform.runLater(() -> {
            beginChange();
            nextRemove(0, e);
            endChange();
        });

        return e;
    }

    @Override
    public E poll(long timeout, TimeUnit unit) throws InterruptedException {
        return null;
    }

    @Override
    public int remainingCapacity() {
        return 0;
    }

    @Override
    public int drainTo(Collection<? super E> c) {
        return 0;
    }

    @Override
    public int drainTo(Collection<? super E> c, int maxElements) {
        return 0;
    }

    @Override
    public E remove() {
        return null;
    }

    @Override
    public E poll() {

        final FutureTask<E> query = new FutureTask<>(() -> {
            beginChange();
            E e = backingBlockingQueue.poll();
            if (e != null) {
                nextRemove(0, e);
            }
            endChange();
            return e;
        });

        Platform.runLater(query);

        try {
            return query.get();
        } catch (InterruptedException | ExecutionException exception) {
            exception.printStackTrace();
            return null;
        }
    }

    @Override
    public E element() {
        return backingBlockingQueue.element();
    }

    @Override
    public E peek() {
        return backingBlockingQueue.peek();
    }

    @Override
    public E get(int index) {
        synchronized (backingBlockingQueue) {
            Iterator<E> iterator = backingBlockingQueue.iterator();
            for (int i = 0; i < index; i++) iterator.next();
            return iterator.next();
        }
    }

    @Override
    public int size() {
        return backingBlockingQueue.size();
    }

    public IntegerProperty capacityProperty() {
        return capacity;
    }
}
