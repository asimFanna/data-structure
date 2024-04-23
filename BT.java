import java.util.Stack;
enum Order {preOrder, inOrder, postOrder}

public class BT<T> {

    BTNode<T> root, current;


    public enum Relative {Root, Parent, LeftChild, RightChild}

    public static class BTNode<T> {
        public T data;
        public BTNode<T> left, right;

        public BTNode(T val) {
            this.data = val;
        }
    }

    public BT() {
        root = current = null;
    }


    public BT(BTNode<T> root, BTNode<T> current) {
        this.root = root;
        this.current = current;
    }


    public T retrieve() {
        return current.data;
    }

    public void update(T val) {
        current.data = val;
    }

    public boolean empty() {
        return root == null;
    }

    public boolean insert(Relative rel, T val) {

        switch (rel) {
            case Root:
                if (!empty()) return false;
                current = root = new BTNode<>(val);
                return true;
            case LeftChild:
                if (current.left != null) return false;
                current.left = new BTNode<>(val);
                current = current.left;
            case RightChild:
                if (current.right != null) return false;
                current.right = new BTNode<>(val);
                current = current.right;
            default:
                return false;
        }
    }

    public void deleteSubtree() {
        if (current == root) {
            current = root = null;
        } else {
            BTNode<T> p = current;
            find(Relative.Parent);
            if (current.left == p) current.left = null;
            else current.right = null;
            current = root;
        }
    }

    public boolean find(Relative rel) {
        switch (rel) {
            case Root:    // Easy case
                current = root;
                return true;
            case Parent:
                if (current == root) return false;
                current = findParent(current, root);
                return true;
            case LeftChild:
                if (current.left == null) return false;
                current = current.left;
                return true;
            case RightChild:
                if (current.right == null) return false;
                current = current.right;
                return true;
            default:
                return false;
        }
    }

    private BTNode<T> findParent(BTNode<T> p, BTNode<T> t) {
        // Stack is used to store the right pointers of nodes
        Stack<BTNode<T>> stack = new Stack<>();
        BTNode<T> q = t;

        while (q.right != p && q.left != p) {
            if (q.right != null) stack.push(q.right);
            if (q.left != null) q = q.left;
            else q = stack.pop();
        }
        return q;
    }
}

