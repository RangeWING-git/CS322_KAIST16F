package cs322.common;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by RangeWING on 2016-11-05.
 */
public class StateN extends State{
    protected Set<State> set;
    public StateN(){
        super("");
        set = new HashSet<>();
    }
    public StateN(State q){
        this();
        set.add(q);
        this.name = name();
    }
    public StateN(Set<State> set) {
        super("");
        this.set = set;
        this.name = name();
    }
    public void add(State q){
        set.add(q);
        this.name = name();
    }
    public void addAll(Collection<? extends State> sq){
        set.addAll(sq);
        this.name = name();
    }

    public Iterator<State> getSetIter(){
        return set.iterator();
    }

    public boolean contains(State q){
        return set.contains(q);
    }

    @Override
    public boolean equals(Object o){
        if(o instanceof StateN){
            StateN s = (StateN) o;
            boolean eq = true;
            for(State q : set){
                if(!s.contains(q)){
                    eq = false;
                    break;
                }
            }
            return eq;
        }
        return false;
    }
    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        set.forEach(q -> {sb.append(q.getName()); sb.append(", ");});
        return "StateN("+sb+")";
    }

    @Override
    public int hashCode(){
        int code = 0;
        for(State q : set){
            code += q.hashCode();
        }
        return code;
    }

    public String name(){
        return String.valueOf(hashCode());
    }
}
