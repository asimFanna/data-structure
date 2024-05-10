public class BST<K extends Comparable<K>, T> implements Map<K, T> {
    public BSTNode<K, T> root, current;
    DLLCompImp<K> keys = new DLLCompImp<>();

    @Override
    public int size() {
        return keys.size();
    }

    @Override
    public boolean empty() {
        return root == null;
    }

    @Override
    public void clear() {
        root = current = null;
        keys = new DLLCompImp<>();
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
    public boolean find(K tkey) {
        BSTNode<K, T> p = root, q = root;
        if (empty())
            return false;

        while (p != null) {
            q = p;
            if (p.key.compareTo(tkey) == 0) {
                current = p;
                return true;
            } else if (tkey.compareTo(p.key) < 0)
                p = p.left;
            else
                p = p.right;
        }

        current = q;
        return false;
    }

    @Override
    public int nbKeyComp(K tkey) {
        BSTNode<K, T> p = root, q = root;
        int moves = 0;
        if (empty())
            return 0;

        while (p != null) {
            q = p;
            if (p.key.compareTo(tkey) == 0) {
                current = p;
                return moves;
            } else if (tkey.compareTo(p.key) < 0)
                p = p.left;
            else
                p = p.right;

            moves++;
        }
        current = q;
        return moves;
    }

    @Override
    public boolean insert(K key, T data) {
        BSTNode<K, T> p, q = current;

        if (find(key)) {
            current = q;
            return false;
        }

        p = new BSTNode<>(key, data);
        keys.insert(key);
        if (empty()) {
            root = current = p;
            return true;
        } else {
            // current is pointing to parent of the new key
            if (key.compareTo(current.key) < 0)
                current.left = p;
            else
                current.right = p;
            current = p;
            return true;
        }
    }

    @Override
    public boolean remove(K key) {
        boolean result = remove_key(key);
        if (result) keys.remove();
        return result;
    }

    @Override
    public DLLComp<K> getKeys() {
        return keys;
    }

    private boolean remove_key(K tkey) {
        BooleanWrapper removed = new BooleanWrapper(false);
        BSTNode<K, T> p;
        p = remove_aux(tkey, root, removed);
        current = root = p;
        return removed.get();
    }

    private BSTNode<K, T> remove_aux(K key, BSTNode<K, T> p, BooleanWrapper flag) {
        BSTNode<K, T> q, child = null;
        if (p == null)
            return null;
        if (key.compareTo(p.key) < 0)
            p.left = remove_aux(key, p.left, flag); //go left
        else if (key.compareTo(p.key) > 0)
            p.right = remove_aux(key, p.right, flag); //go right
        else {
            flag.set(true);
            if (p.left != null && p.right != null) { //two children
                q = find_min(p.right);
                p.key = q.key;
                p.data = q.data;
                p.right = remove_aux(q.key, p.right, flag);
            } else {
                if (p.right == null) //one child
                    child = p.left;
                else if (p.left == null) //one child
                    child = p.right;
                return child;
            }
        }
        return p;
    }

    private BSTNode<K, T> find_min(BSTNode<K, T> p) {
        if (p == null)
            return null;

        while (p.left != null) {
            p = p.left;
        }

        return p;
    }

    public static class BSTNode<K, T> {
        public T data;
        public K key;
        public BSTNode<K, T> left, right;

        public BSTNode(K k, T val) {
            this.data = val;
            this.key = k;
        }
    }

    public static class BooleanWrapper {

        boolean value;

        public BooleanWrapper(boolean val) {
            this.value = val;
        }

        public boolean get() {
            return this.value;
        }

        public void set(boolean val) {
            this.value = val;
        }
    }
}

