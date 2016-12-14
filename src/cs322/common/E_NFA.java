package cs322.common;

import java.util.*;

/**
 * Created by RangeWING on 2016-09-20.
 * Definition of cs322.common.DFA and check/accepted
 */
public class E_NFA extends DFA{
    protected Map<Pair<State, String>, Set<State>> T;
    public static final String EMPTY = "ε";

    public E_NFA(){
        super();
        T = new HashMap<>();
    }

    @Override
    public void addT(State fromState, String symbol, State toState){
        Pair<State, String> pair = new Pair<>(fromState, symbol);
        if(!T.containsKey(pair)) T.put(pair, new HashSet<>());
        T.get(pair).add(toState);
    }

    public Map<Pair<State, String>, Set<State>> getT_NFA(){
        return T;
    }

    public Set<State> eps(State q){
        StateN qn = new StateN();
        qn.add(q);
        return eps(qn);
    }
    public Set<State> eps(StateN q){
        Set<State> result = new HashSet<>();

        T.forEach((k, v) -> {
            if(q.contains(k.getKey()) && k.getValue().equals(EMPTY)){
                v.forEach(result::add);
            }
        });

        return result;
    }

    public Set<State> eStar(State q){
        Set<State> result = new HashSet<>();
        Stack<State> stack = new Stack<>();
        stack.push(q);
        result.add(q);

        while(!stack.empty()){
            Set<State> eps = new HashSet<>();
            eps.addAll(eps(stack.pop()));

            if(eps.size() > 0) {
                eps.forEach(e -> {
                    if(!result.contains(e)){
                        result.add(e);
                        stack.push(e);
                    }
                });
            }
        }
        return result;
    }



    public DFA subsetConstruction() {
        HashMap<State, Set<State>> eStarMap = new HashMap<>();
        Q.forEach(q -> eStarMap.put(q, eStar(q)));
        DFA dfa = new DFA();
        dfa.addS(S);
        StateN q0d = new StateN(eStar(q0));
        dfa.setQ0(q0d);
        Stack<StateN> stack = new Stack<>();
        stack.push(q0d);

        while(!stack.empty()){
            StateN q = stack.pop();
            HashMap<String, StateN> map = new HashMap<>();
            T.forEach((k, v) -> {
                if(q.contains(k.getKey()) && !k.getValue().equals(EMPTY)){
                    StateN sn = map.get(k.getValue());
                    if(sn == null) {
                        sn = new StateN();
                        map.put(k.getValue(), sn);
                    }
                    for (State w : v) {
                        sn.addAll(eStar(w));
                    }
                }
            });
            map.forEach((k, v) -> {
                dfa.addT(q, k, v);
                if(!dfa.isInQ(v)) stack.push(v);
                for(State f : F) {
                    if(v.contains(f)){
                        dfa.addF(v);
                        break;
                    }
                }
            });
            dfa.addQ(q);
        }

        return dfa;
    }

    public void clearQ0F(){
        q0 = null;
        F = new HashSet<>();
    }


    public DFA m_DFA(){
        return subsetConstruction().minimize_table().reduce().simplify();
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append(super.toString());
        sb.append("\nNFA_T = \n");
        T.forEach((k,v) ->
            sb.append(String.format("(%s,%s,%s), ", k.getKey(), k.getValue(), v))
        );
        return sb.toString();

    }

}
