package impl;

import interfaces.DLLComp;
import interfaces.Node;

public class DLLCompImp<T extends Comparable<T>> extends DLLImp<T> implements DLLComp<T> {


    protected boolean isIncreasing;

    @Override
    public void sort(boolean increasing) {
        //bubble sort
        isIncreasing = increasing;
        Node<T> index = head;
        while (index != null) {
            Node<T> innerIndex = index.next;
            while (innerIndex != null) {
                boolean compareResult = increasing ? innerIndex.value.compareTo(index.value) < 0 : innerIndex.value.compareTo(index.value) > 0;
                if (compareResult) {
                    //swapping values
                    T temp = innerIndex.value;
                    innerIndex.value = index.value;
                    index.value = temp;
                }
                innerIndex = innerIndex.next;
            }
            index = index.next;
            current = index;
        }
    }

    @Override
    public T getMax() {
        if(!isIncreasing)return head.value;

        Node<T> index = current;

        while (index.next!=null)
            index = index.next;


        return  index.value;
    }

    @Override
    public T getMin() {
        if(isIncreasing)return head.value;

        Node<T> index = current;

        while (index.next!=null)
            index = index.next;


        return  index.value;
    }


    public T find (T query){
        /*
         * Find node and make the current = foundedNode
         */
        Node<T> index = head;
        while (index!=null){

            if(index.value.compareTo(query) == 0)
            {
                current = index;
                return  index.value;
            }
            index = index.next;
        }
        return  null;
    }


}
