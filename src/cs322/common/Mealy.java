package cs322.common;

import java.util.*;

/**
 * Created by RangeWING on 2016-09-29.
 */
public class Mealy {
    protected Set<State> Q; //Set of states
    protected Set<String> S, P; //Set of input symbols, set of output symbols
    protected Map<Pair<State, String>, State> T; //transition func
    protected Map<Pair<State, String>, String> L; //output func
    protected State q0;

    public Mealy(){
        Q = new HashSet<>();
        P = new HashSet<>();
        S = new HashSet<>();
        T = new HashMap<>();
        L = new HashMap<>();
    }

    public void addQ(Collection<? extends State> states){
        Q.addAll(states);
    }

    public void addS(Collection<String> symbols){
        S.addAll(symbols);
    }

    public void addP(Collection<String> symbols){
        P.addAll(symbols);
    }

    public void addT(State fromState, String symbol, State toState){
        Pair<State, String> pair = new Pair<>(fromState, symbol);
        T.put(pair, toState);
    }
    public void addL(State fromState, String symbol, String outString){
        Pair<State, String> pair = new Pair<>(fromState, symbol);
        L.put(pair, outString);
    }
    public void setQ0(State q0){
        this.q0 = q0;
    }

    /**
     * Checks whether the cs322.common.Mealy is valid by checking all the state assigned is in the set Q
     * @return true when valid
     */
    public boolean check(){
        if(!Q.contains(q0)) return false;

        for(Map.Entry<Pair<State, String>, State> entry : T.entrySet()){
            if(!(Q.contains(entry.getKey().getKey()))) return false;
            if(!(Q.contains(entry.getValue()))) return false;
        }
        for(Map.Entry<Pair<State, String>, String> entry : L.entrySet()){
            if(!(Q.contains(entry.getKey().getKey()))) return false;
            if(!(P.contains(entry.getValue()))) return false;
        }


        return true;
    }

    /**
     * Checks if the input sequence x is accepted by cs322.common.DFA
     * @param mealy input mealy
     * @param x input sequence
     * @return output string
     */
    public static List<String> getOutput(Mealy mealy, List<String> x){
        State q = mealy.q0;
        int n = x.size();
        List<String> output = new ArrayList<>(n);
        Pair<State, String> pair;
        for(int i = 0; i < n; i++){
            pair = new Pair<>(q, x.get(i));
            if(mealy.T.containsKey(pair)){
                q = mealy.T.get(pair);
                output.add(mealy.L.get(pair));
            }else return null;
        }
        return output;
    }
}
