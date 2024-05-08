package impl;

import interfaces.DLL;
import interfaces.Node;

public class DLLImp<T> implements DLL<T> {
    public Node<T> head;
    public Node<T> cur;
    protected int count = 0;

    @Override
    public int size() {
        return count;
    }

    @Override
    public boolean empty() {
        return head == null;
    }

    @Override
    public boolean last() {
        if (empty()) return false;
        return cur.next == null;
    }

    @Override
    public boolean first() {
        if (empty()) return false;
        return cur.prev == null;
    }

    @Override
    public void findFirst() {
        cur = head;
        cur.prev = null;
    }

    @Override
    public void findNext() {
        cur = cur.next;
    }

    @Override
    public void findPrevious() {
            cur = cur.prev;
    }

    @Override
    public T retrieve() {
        return cur.data;
    }

    @Override
    public void update(T val) {
        cur.data = val;
    }

    @Override
    public void insert(T val) {
        Node<T> newNode = new Node<>(val);
        count++;

        if (empty()) {
            head = cur = newNode;
            return;
        }

        newNode.next = cur.next;
        newNode.prev = cur;

        if (cur.next != null)
            cur.next.prev = newNode;

        cur.next = newNode;
        cur = newNode;
    }

    @Override
    public void remove() {
        count--;
        if (cur == head) {
            if (cur.next != null)
                cur.next.prev = null;
            head = cur = cur.next;
            return;
        }

        if (cur.next != null)
            cur.next.prev = cur.prev;
        if (cur.prev != null)
            cur.prev.next = cur.next;
        cur = cur.next;
    }
}
