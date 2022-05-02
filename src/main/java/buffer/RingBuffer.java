package buffer;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * The RingBuffer class represents a first-in-first-out (FIFO) circular queue of
 * elements. It has a maximum capacity of elements it can hold. If more elements
 * are added, the oldest element will be overwritten.
 * <p>
 * Originally derived from
 * http://www.cs.princeton.edu/introcs/43stack/RingBuffer.java.html
 */
public class RingBuffer {
    private String[] queue;
    private int numberOfElementsOnQueue = 0;
    private int first = 0; // index of first element of queue
    private int last = 0; // index of next available slot

    public RingBuffer(int capacity) {
        if (capacity < 1) {
            throw new IllegalArgumentException("Initial capacity is less than one");
        }
        queue = new String[capacity];
    }

    /**
     * DONE: capacity Returns the number of elements the buffer can hold.
     */
    public int capacity() {
        return queue.length;
    }

    /**
     * DONE: size Returns the number of elements in the buffer.
     */
    public int size() {
        if (numberOfElementsOnQueue >= queue.length) {
            return queue.length;
        }
        return numberOfElementsOnQueue;
    }

    /**
     * DONE: isEmpty Returns true if the buffer contains no elements.
     */
    public boolean isEmpty() {
        return numberOfElementsOnQueue == 0;
    }

    /**
     * DONE: isFull Returns true if the buffer has reached its capacity, which is
     * the maximum number of elements it can hold, before overwriting elements.
     */
    public boolean isFull() {
        return numberOfElementsOnQueue >= queue.length;
    }

    /**
     * DONE?: enqueue Appends the specified element to the end of the buffer. If the
     * buffer has already reached its capacity, appending overwrites the first
     * element in the buffer.
     *
     * @param item to be appended to the buffer.
     */
    public void enqueue(String item) {
        queue[numberOfElementsOnQueue % queue.length] = item;
        if (isFull()) {
            first++;
        }
        numberOfElementsOnQueue++;
    }

    /**
     * DONE: peek Returns the first element from the buffer without removing it.
     * <p>
     * //@throws a RuntimeException if the buffer is empty.
     */
    public String peek() {
        if (isEmpty()) {
            throw new RuntimeException("Buffer is empty.");
        }
        return queue[first % queue.length];
    }

    /**
     * Returns an iterator over the elements in the buffer.
     */
    public Iterator<String> iterator() {
        return new RingBufferIterator();
    }

    private class RingBufferIterator implements Iterator<String> {
        private int i = 0;

        public boolean hasNext() {
            return i < numberOfElementsOnQueue;
        }

        public String next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }

            return queue[i++ % queue.length];
        }
    }
}