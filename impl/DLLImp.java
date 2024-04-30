package impl;

import interfaces.DLL;
import interfaces.Node;

public class DLLImp<T> implements DLL<T> {
    public Node<T> head;
    public Node<T> current;
    protected int size = 0;

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean empty() {
        return head == null;
    }

    @Override
    public boolean last() {
        if (empty()) return false;
        return current.next == null;
    }

    @Override
    public boolean first() {
        if (empty()) return false;
        return current.previous == null;
    }

    @Override
    public void findFirst() {
        current = head;
        current.previous = null;
    }

    @Override
    public void findNext() {
        current = current.next;
    }

    @Override
    public void findPrevious() {
            current = current.previous;
    }

    @Override
    public T retrieve() {
        return current.value;
    }

    @Override
    public void update(T val) {
        current.value = val;
    }

    @Override
    public void insert(T val) {
        Node<T> newNode = new Node<>(val);
        size++;

        if (empty()) {
            head = current = newNode;
            return;
        }
        newNode.next = current.next;
        newNode.previous = current;

        if (current.next != null)
            current.next.previous = newNode;

        current.next = newNode;
        current = newNode;
    }

    @Override
    public void remove() {
        size--;
        if (current == head) {
            if (current.next != null)
                current.next.previous = null;
            head = current = current.next;
            return;
        }

        if (current.next != null)
            current.next.previous = current.previous;
        if (current.previous != null)
            current.previous.next = current.next;
        current = current.next;
    }
}
