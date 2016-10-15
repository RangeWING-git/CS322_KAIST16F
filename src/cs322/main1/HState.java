package cs322.main1;

import java.util.ArrayList;

/**
 * Created by RangeWING on 2016-09-20.
 * Definition of cs322.common.State
 */
public class HState {
    private Character name;
    private Object output = null;
    public HState(Character name){
        this.name = name;
    }

    @Override
    public boolean equals(Object o){
        if(o instanceof HState){
            HState s = (HState) o;
            return this.name.equals(s.getName());
        }
        return false;
    }

    public Character getName(){
        return name;
    }

    public void setName(Character name){
        this.name = name;
    }

    public void setOutput(Object output){
        this.output = output;
    }

    public Object getOutput(){
        return output;
    }

    public static ArrayList<HState> makeList(String namesString){
        char[] names = namesString.toCharArray();
        ArrayList<HState> list = new ArrayList<>(names.length);
        for(Character name : names){
            list.add(new HState(name));
        }
        return list;
    }

    public static HState get(char c){
        return new HState(c);
    }

    @Override
    public String toString(){
        return "HState("+name+")";
    }

    @Override
    public int hashCode(){
        return name.hashCode();
    }
}
