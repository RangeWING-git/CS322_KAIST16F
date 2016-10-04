package cs322.common;

import java.util.ArrayList;

/**
 * Created by RangeWING on 2016-09-20.
 * Definition of cs322.common.State
 */
public class State {
    private String name;
    public State(String name){
        this.name = name;
    }

    @Override
    public boolean equals(Object o){
        if(o instanceof State){
            State s = (State) o;
            return this.name.equals(s.getName());
        }
        return false;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public static ArrayList<State> makeList(String[] names){
        ArrayList<State> list = new ArrayList<>(names.length);
        for(String name : names){
            list.add(new State(name));
        }
        return list;
    }

    @Override
    public String toString(){
        return "cs322.common.State("+name+")";
    }

    @Override
    public int hashCode(){
        return name.hashCode();
    }
}
