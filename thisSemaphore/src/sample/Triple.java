package sample;

public class Triple<K,V,N> {
    private K key;
    private V elems;
    private N number;
    public Triple(K k, V v, N n){
        key=k;
        number=n;
        elems=v;
    }

    public V getElems(){
        return elems;
    }
    public K getKey(){
        return key;
    }
    public N getNumber(){
        return number;
    }

}
