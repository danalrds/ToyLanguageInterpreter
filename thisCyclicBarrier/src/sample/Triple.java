package sample;

public class Triple<K,N,V> {
    private K key;
    private N number;
    private V elems;
    public Triple(K k, N n,V v){
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
