public class Helpers {
    public static BST<String> generateTree(){

        BST<String> tree = new BST<>();
        tree.insert(60,"Root");
        tree.insert( 20,"Random20");
        tree.insert( 10,"Random10");
        tree.insert(30,"Random30");
        tree.insert( 50,"Random50");
        tree.insert( 40,"Random40");
        tree.insert(70,"Random70");
        tree.insert(110,"Random110");
        tree.insert(90,"Random90");
        tree.insert(100,"Random100");
        return tree;
    }
}
