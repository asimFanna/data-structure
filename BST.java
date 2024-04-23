public class BST<T> {

    BSTNode<T> root, current;



    public static class BSTNode<T> {
        public T data;
        public int key;
        public BSTNode<T> left, right;

        public BSTNode(int k, T val) {
            this.data = val;
            this.key = k;
        }
    }

    public BST() {
        root = current = null;
    }


    public BST(BSTNode<T> root, BSTNode<T> current) {
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

    public boolean insert(int k, T val) {
        BSTNode<T> p, q = current;

        if(findKey(k)) {
            current = q;  // findkey() modified current
            return false; // key already in the BST
        }

        p = new BSTNode<T>(k, val);
        if (empty()) {
            root = current = p;
            return true;
        }
        else {
            // current is pointing to parent of the new key
            if (k < current.key)
                current.left = p;
            else
                current.right = p;
            current = p;
            return true;
        }
    }




    public boolean findKey(int tkey) {
        BSTNode<T> p = root,q = root;

        if(empty())
            return false;

        while(p != null) {
            q = p;
            if(p.key == tkey) {
                current = p;
                return true;
            }
            else if(tkey < p.key)
                p = p.left;
            else
                p = p.right;
        }

        current = q;
        return false;
    }

    void traverse(Order order)
    {

        if (order == Order.inOrder) {
            inOrder(root);
            return;
        }

        if (order == Order.postOrder) {
            postOrder(root);
            return;
        }
        if (order == Order.preOrder) {
            preOrder(root);
        }
    }

    private void  inOrder ( BSTNode<T> node){
        if (node == null)
            return;

        // Visit Left SubTree
        inOrder(node.left);

        // Print Key
        System.out.print(node.key + " ");

        // Visit Left SubTree
        inOrder(node.right);
    }

    private void  postOrder ( BSTNode<T> node){
        if (node == null)
            return;

        // Visit Left SubTree
        postOrder(node.left);

        // Visit Left SubTree
        postOrder(node.right);
        // Print Key
        System.out.print(node.key + " ");


    }private void  preOrder ( BSTNode<T> node){
        if (node == null)
            return;

        // Print Key
        System.out.print(node.key + " ");

        // Visit Left SubTree
        preOrder(node.left);
        // Visit Left SubTree
        preOrder(node.right);


    }
}

