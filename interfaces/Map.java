package interfaces;

public interface Map<K extends Comparable<K>, T> {
    // Returns the number of e l e m e n t s in the map .
    int size();

    // Return true if the tree is empty . Must be O (1) .
    boolean empty();

    // Removes all e l e m e n t s in the map .
    void clear();

    // Return the key and data of the current element
    T retrieve();

    // Update the data of current element .
    void update(T e);

    // Search for element with key k and make it the current element if it exists .
// If the element does not exist the current is u n c h a n g e d and false is r e t u r n e d .
// This method must be O ( log ( n ) ) in average .
    boolean find(K key);

    // Return the number of key c o m p a r i s o n s needed to find key .
    int nbKeyComp(K key);

    // Insert a new element if does not exist and return true . The current points to
// the new element . If the element already exists , current does not change and
// false is r e t u r n e d . This method must be O ( log ( n ) ) in average .
    boolean insert(K key, T data);

    // Remove the element with key k if it exists and return true . If the element
// does not exist false is r e t u r n e d ( the p o s i t i o n of current is u n s p e c i f i e d
// after calling this method ) . This method must be O ( log ( n ) ) in average .
    boolean remove(K key);

    // Return all keys in the map as a list sorted in i n c r e a s i n g order .
    DLLComp<K> getKeys();
}
