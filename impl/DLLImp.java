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
        return current.prev == null;
    }

    @Override
    public void findFirst() {
        current = head;
        current.prev = null;
    }

    @Override
    public void findNext() {
        current = current.next;
    }

    @Override
    public void findPrevious() {
            current = current.prev;
    }

    @Override
    public T retrieve() {
        return current.data;
    }

    @Override
    public void update(T val) {
        current.data = val;
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
        newNode.prev = current;

        if (current.next != null)
            current.next.prev = newNode;

        current.next = newNode;
        current = newNode;
    }

    @Override
    public void remove() {
        size--;
        if (current == head) {
            if (current.next != null)
                current.next.prev = null;
            head = current = current.next;
            return;
        }

        if (current.next != null)
            current.next.prev = current.prev;
        if (current.prev != null)
            current.prev.next = current.next;
        current = current.next;
    }
}
