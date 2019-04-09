package datatypes;

import java.util.Map;

public class MyDictionary<K, V> extends AbstractDictionary<K, V> {
    public MyDictionary(Map<K, V> map) {
        super(map);
    }

    public MyDictionary() {
    }

    @Override
    public String toString() {
        String str = "";
        for (Map.Entry<K, V> e : map.entrySet()) {
            str += e.getKey().toString();
            str += "=";
            str += e.getValue().toString() + "\n";
        }
        return str;
    }
}
