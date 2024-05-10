public class node<T> {
    public T data;
    public node<T> next;
    public node<T> prev;

    public node(T data, node<T> next, node<T> prev) {
        this.data = data;
        this.next = next;
        this.prev = prev;
    }


    public node() {
        data = null;
        next = null;
        prev = null;
    }

    public node(T dat) {
        this.data = dat;
        next = null;
        prev = null;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public node<T> getNext() {
        return next;
    }

    public void setNext(node<T> next) {
        this.next = next;
    }

    public node<T> getPrev() {
        return prev;
    }

    public void setPrev(node<T> prev) {
        this.prev = prev;
    }

}
