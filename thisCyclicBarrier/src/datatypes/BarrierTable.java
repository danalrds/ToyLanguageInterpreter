package datatypes;

import java.util.Map;

public class BarrierTable<K, V> extends AbstractDictionary<K, V> {

    @Override
    public String toString() {
        String str = "";
        for (Map.Entry<K, V> e : map.entrySet()) {
            str += e.getKey().toString();
            str += " -> ";
            str += e.getValue().toString() + "\n";
        }
        return str;
    }


}
