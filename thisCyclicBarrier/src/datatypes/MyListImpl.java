package datatypes;

import java.util.ArrayList;
import java.util.List;

public class MyListImpl<T> implements MyList<T> {
    private List<T> list;

    public MyListImpl() {
        this.list = new ArrayList<>();
    }

    @Override
    public void add(T elem) {
        list.add(elem);

    }

    @Override
    public Iterable<T> getAll() {
        return list;
    }

    @Override
    public String toString() {
        String printlist = "";
        for (int i = 0; i < list.size(); i++) {
            printlist += list.get(i).toString();
            printlist += " ";
        }
        printlist += "\n";
        return printlist;
    }
}
