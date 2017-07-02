package org.cubeengine.logscribe.target.file;

import java.util.concurrent.atomic.AtomicInteger;

final class BlockingRingBuffer<T>
{
    private final T[] buffer;
    private final AtomicInteger start;
    private final AtomicInteger offset;
    private final int capacity;
    private final Object full = new byte[0];
    private final Object empty = new byte[0];

    @SuppressWarnings("unchecked")
    public BlockingRingBuffer(int capacity)
    {
        this.buffer = (T[])new Object[capacity];
        this.start = new AtomicInteger(0);
        this.offset = new AtomicInteger(0);
        this.capacity = capacity;
    }

    private int wrapped(int i)
    {
        return i % capacity;
    }

    public boolean add(T obj, int timeout, int nanos) throws InterruptedException
    {
        if (isFull())
        {
            full.wait(timeout, nanos);
        }
        if (isFull())
        {
            return false;
        }

        synchronized (this)
        {
            buffer[offset.accumulateAndGet(1, (a, b) -> (a + b) % capacity)] = obj;
        }

        full.notifyAll();
        empty.notifyAll();
        return true;
    }

    public T get(int timeout, int nanos) throws InterruptedException
    {
        if (isEmpty())
        {
            empty.wait(timeout, nanos);
        }
        if (isEmpty())
        {
            return null;
        }

        T out;
        synchronized (this)
        {
            int index = start.getAndAccumulate(1, (a, b) -> (a + b) % capacity);
            out = buffer[index];
            buffer[index] = null;
        }
        empty.notifyAll();
        full.notifyAll();
        return out;
    }

    public int getCapacity()
    {
        return capacity;
    }

    public int getLength()
    {
        return (offset.get() + capacity) - (start.get() + capacity);
    }

    public synchronized boolean isFull()
    {
        return wrapped(offset.get() + 1) == start.get();
    }

    public synchronized boolean isEmpty()
    {
        return offset.get() == start.get();
    }
}
