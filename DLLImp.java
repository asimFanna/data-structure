class DLLImp<T> implements DLL<T> {

    public node<T> head;
    public node<T> cur;
    public int count;


    public DLLImp() {
        head = cur = null;
        count = 0;

    }

    public int size() {
        return count;
    }

    public boolean empty() {
        return head == null;
    }

    public boolean first() {
        return cur.prev == null;
    }

    public boolean last() {
        return cur.next == null;
    }

    public void findFirst() {
        cur = head;
    }

    public void findNext() {
        cur = cur.next;
    }


    public void findPrevious() {
        cur = cur.prev;
    }


    public T retrieve() {
        return cur.data;
    }

    public void update(T val) {
        cur.data = val;
    }


    public void insertNotWorking(T val) {
        node<T> e = new node<T>(val);
        if (empty())
            head = cur = e;
        else {
            if (cur != null) {
                e.next = cur;
                e.prev = cur.prev;
                if (cur.prev != null)
                    cur.prev.next = e;
                else
                    head = e;
                cur.prev = e;
                cur = e;
            } else {
                e.next = head;
                head.prev = e;
                head = e;
            }
        }
        count++;

    }

    @Override
    public void insert(T val) {
        node<T> newNode = new node<>(val);
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

    public void remove() {
        if (cur == head) {
            head = head.next;
            if (head.next != null)
                head.prev = null;
        } else {
            cur.prev.next = cur.next;
            if (cur.next != null)
                cur.next.prev = cur.prev;
        }

        if (cur.next == null)
            cur = head;
        else
            cur = cur.next;
        count--;

    }

}