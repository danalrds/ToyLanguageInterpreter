package datatypes;

public interface MyStack<T> {
    void push(T elem);

    T pop();

    T top();

    boolean isEmpty();
    Iterable<T>getAll();
}
