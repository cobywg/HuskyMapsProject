package minpq;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * Unsorted array (or {@link ArrayList}) implementation of the {@link ExtrinsicMinPQ} interface.
 *
 * @param <T> the type of elements in this priority queue.
 * @see ExtrinsicMinPQ
 */
public class UnsortedArrayMinPQ<T> implements ExtrinsicMinPQ<T> {
    /**
     * {@link List} of {@link PriorityNode} objects representing the item-priority pairs in no specific order.
     */
    private final List<PriorityNode<T>> items;

    /**
     * Constructs an empty instance.
     */
    public UnsortedArrayMinPQ() {
        items = new ArrayList<>();
    }

    @Override
    public void add(T item, double priority) {
        if (contains(item)) {
            throw new IllegalArgumentException("Already contains " + item);
        }
        items.add(new PriorityNode<>(item, priority));
    }

    @Override
    public boolean contains(T item) {
        for (PriorityNode<T> curr : items) {
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
        PriorityNode<T> minNode = items.get(0);
        T minItem = minNode.item();
        double minPrio = minNode.priority();

        for (PriorityNode<T> curr : items) {
            if (curr.priority() < minPrio) {
                minPrio = curr.priority();
                minItem = curr.item();
            }
        }
        
        return minItem;
    }

    @Override
    public T removeMin() {
        if (isEmpty()) {
            throw new NoSuchElementException("PQ is empty");
        }
        PriorityNode<T> minNode = items.get(0);
        T minItem = minNode.item();
        double minPrio = minNode.priority();
        int index = 0;

        for (int i = 1; i < items.size(); i++) {
            PriorityNode<T> currNode = items.get(i);
            if (currNode.priority() < minPrio) {
                minPrio = currNode.priority();
                minItem = currNode.item();
                index = i;
            }
        }
        items.remove(index);
        return minItem;
    }

    @Override
    public void changePriority(T item, double priority) {
        if (!contains(item)) {
            throw new NoSuchElementException("PQ does not contain " + item);
        }

        for (PriorityNode<T> curr : items) {
            if (Objects.equals(curr.item(), item)) {
                curr.setPriority(priority);
                return;
            }
        }
    }

    @Override
    public int size() {
        return items.size();
    }
}
