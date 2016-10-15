package cs322.main1;

import cs322.common.Pair;

import java.io.IOException;
import java.util.*;

/**
 * Created by RangeWING on 2016-10-14.
 */
public class HMealy {
    protected Set<HState> Q; //Set of states
    protected Set<Character> S, P; //Set of input symbols, set of output symbols
    protected Map<Pair<HState, Character>, HState> T; //transition func
    protected Map<Pair<HState, Character>, Character> L; //output func
    protected HState q0;

    public HMealy(){
        Q = new HashSet<>();
        P = new HashSet<>();
        S = new HashSet<>();
        T = new HashMap<>();
        L = new HashMap<>();
    }

    public void addQ(Collection<? extends HState> states){
        Q.addAll(states);
    }

    public void addS(Collection<Character> symbols){
        S.addAll(symbols);
    }

    public void addP(Collection<Character> symbols){
        P.addAll(symbols);
    }

    public void addT(HState fromHState, Character symbol, HState toHState){
        Pair<HState, Character> pair = new Pair<>(fromHState, symbol);
        T.put(pair, toHState);
    }
    public void addL(HState fromHState, Character symbol, Character out){
        Pair<HState, Character> pair = new Pair<>(fromHState, symbol);
        L.put(pair, out);
    }
    public void setQ0(HState q0){
        this.q0 = q0;
    }

    public void addT(HState fromHState, String symbols, HState toHState){
        for(char c : symbols.toCharArray()){
            addT(fromHState, c, toHState);
        }
    }

    public void addL(HState fromHState, String symbols, char out){
        for(char c : symbols.toCharArray()){
            addL(fromHState, c, out);
        }
    }

    public void addT(char fromHState, String symbols, char toHState){
        for(char c : symbols.toCharArray()){
            addT(HState.get(fromHState), c, HState.get(toHState));
        }
    }

    public void addL(char fromHState, String symbols, char out){
        for(char c : symbols.toCharArray()){
            addL(HState.get(fromHState), c, out);
        }
    }

    public void addT(Map<Pair<HState, Character>, HState> T){
        this.T.putAll(T);
    }
    public void addL(Map<Pair<HState, Character>, Character> L){
        this.L.putAll(L);
    }



}
