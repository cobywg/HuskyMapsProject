package minpq;

import java.util.Comparator;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.PriorityQueue;

/**
 * {@link PriorityQueue} implementation of the {@link ExtrinsicMinPQ} interface.
 *
 * @param <T> the type of elements in this priority queue.
 * @see ExtrinsicMinPQ
 */
public class HeapMinPQ<T> implements ExtrinsicMinPQ<T> {
    /**
     * {@link PriorityQueue} storing {@link PriorityNode} objects representing each item-priority pair.
     */
    private final PriorityQueue<PriorityNode<T>> pq;

    /**
     * Constructs an empty instance.
     */
    public HeapMinPQ() {
        pq = new PriorityQueue<>(Comparator.comparingDouble(PriorityNode::priority));
    }

    @Override
    public void add(T item, double priority) {
        if (contains(item)) {
            throw new IllegalArgumentException("Already contains " + item);
        }

        PriorityNode<T> newItem = new PriorityNode<T>(item, priority);
        pq.add(newItem);
    }

    @Override
    public boolean contains(T item) {
        for (PriorityNode<T> curr : pq) {
            if (Objects.equals(curr.item(), item)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public T peekMin() {
        if (isEmpty()) {
            throw new NoSuchElementException("PQ is empty");
        }
        return pq.peek().item();
    }

    @Override
    public T removeMin() {
        if (isEmpty()) {
            throw new NoSuchElementException("PQ is empty");
        }
        return pq.poll().item();
    }

    @Override
    public void changePriority(T item, double priority) {
        if (!contains(item)) {
            throw new NoSuchElementException("PQ does not contain " + item);
        }
        for (PriorityNode<T> curr : pq) {
            if (Objects.equals(curr.item(), item)) {
                pq.remove(curr);
                break;
            }
        }
        add(item, priority);
    }

    @Override
    public int size() {
        return pq.size();
    }

    private static int parent (int index) {
        return index / 2;
    }

    private static int left (int index) {
        return index * 2;
    }

    private static int right (int index) {
        return left(index) + 1;
    }
}
