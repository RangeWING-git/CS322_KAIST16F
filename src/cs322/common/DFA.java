package cs322.common;

import java.util.*;

/**
 * Created by RangeWING on 2016-09-20.
 * Definition of cs322.common.DFA and check/accepted
 */
public class DFA {
    protected Set<State> Q, F;
    protected Set<String> S;
    protected Map<Pair<State, String>, State> T;
    protected State q0;

    public DFA(){
        Q = new HashSet<>();
        F = new HashSet<>();
        S = new HashSet<>();
        T = new HashMap<>();
    }

    public void addQ(Collection<? extends State> states){
        Q.addAll(states);
    }
    public void addQ(State state) {
        Q.add(state);
    }

    public void addS(Collection<String> symbols){
        S.addAll(symbols);
    }

    public void addF(Collection<? extends State> states){
        F.addAll(states);
    }
    public void addF(State state) {
        F.add(state);
    }

    public void addT(State fromState, String symbol, State toState){
        Pair<State, String> pair = new Pair<>(fromState, symbol);
        T.put(pair, toState);
    }

    public boolean isInQ(State state){
        return Q.contains(state);
    }

    public State T(State fromState, String symbol){
        Pair<State, String> pair = new Pair<>(fromState, symbol);
        return T.get(pair);
    }

    public void setQ0(State q0){
        this.q0 = q0;
    }
    /**
     * Checks whether the cs322.common.DFA is valid by checking all the state assigned is in the set Q
     * @return true when valid
     */
    public boolean check(){
        if(!Q.contains(q0)) return false;

        for(Map.Entry<Pair<State, String>, State> entry : T.entrySet()){
            if(!(Q.contains(entry.getKey().getKey()))) return false;
            if(!(Q.contains(entry.getValue()))) return false;
        }
        return true;
    }

    /**
     * Checks if the input sequence x is accepted by cs322.common.DFA
     * @param dfa input cs322.common.DFA
     * @param x input sequence
     * @return true when accepted
     */
    public static boolean isAccepted(DFA dfa, List<String> x){
        State q = dfa.q0;
        int n = x.size();
        Pair<State, String> pair;
        for(int i = 0; i < n; i++){
            pair = new Pair<>(q, x.get(i));
            if(dfa.T.containsKey(pair)){
                q = dfa.T.get(pair);
            }else return false;
        }
        return dfa.F.contains(q);
    }

    @Override
    public String toString(){
        String Qs = "", Fs = "", Ss = "", Ts = "";
        for(State q : Q) Qs += (q + " ");
        for(State f : F) Fs += (f + " ");
        for(String s : S) Ss += (s + " ");
        for(Map.Entry<Pair<State, String>, State> entry : T.entrySet()){
            Ts += ("\t("+ entry.getKey().getKey() + ", " + entry.getKey().getValue() + ", " + entry.getValue() + ")\n\t ");
        }


        return "Q = " + Qs + "\n" +
                "S = " + Ss + "\n" +
                "F = " + Fs + "\n" +
                "q0 = " + q0 + "\n" +
                "T = " + Ts;
    }


}
