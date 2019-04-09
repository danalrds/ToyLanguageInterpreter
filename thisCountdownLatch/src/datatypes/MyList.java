package datatypes;

public interface MyList<T> {
    void add(T elem);

    Iterable<T> getAll();
}
