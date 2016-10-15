package cs322.common;

import java.util.Map;

/**
 * Created by RangeWING on 2016-09-29.
 */
public class Pair<K, V> implements Map.Entry<K, V> {
    private K key;
    private V value;

    public Pair(K key, V value){
        this.key = key;
        this.value = value;
    }

    @Override
    public boolean equals(Object o){
        return (o instanceof Pair && ((Pair)o).getKey().equals(key) && ((Pair)o).getValue().equals(value));
    }

    @Override
    public K getKey(){
        return key;
    }

    @Override
    public V getValue(){
        return value;
    }

    @Override
    public int hashCode(){
        return key.hashCode() + value.hashCode();
    }

    @Override
    public V setValue(V value){
        this.value = value;
        return this.value;
    }
}