public class Main {

    public static void main (String [] args){


       BST<String> tree =  Helpers.generateTree();
        System.out.println(tree.findKey(10));
        tree.traverse(Order.preOrder);

    }
}
