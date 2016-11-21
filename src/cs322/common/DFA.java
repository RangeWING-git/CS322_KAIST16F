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

    public static final String DEAD = "~";
    protected static final State DEAD_STATE = new State(DEAD);

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
    public void addS(String symbol){
        S.add(symbol);
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

    public State getQ0() { return q0; }
    public Set<State> getF() { return F; }
    public Map<Pair<State, String>, State> getT(){
        return T;
    }
    public Set<State> getQ() { return Q; }
    public Set<String> getS() { return S; }


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

    public DFA partialToTotal(){
        boolean deadNeeded = false;
        DFA dfa = new DFA();
        dfa.addQ(Q);
        dfa.setQ0(q0);
        dfa.addF(F);
        dfa.addS(S);
        for (State q : Q) {
            for (String s : S) {
                State to = T.get(new Pair<>(q, s));
                if(to != null) dfa.addT(q, s, to);
                else {
                    dfa.addT(q, s, DEAD_STATE);
                    dfa.addT(DEAD_STATE, s, DEAD_STATE);
                    deadNeeded = true;
                }
            }
        }
        if(deadNeeded) dfa.addQ(DEAD_STATE);
        return dfa;
    }

    public DFA reduce(){
        DFA dfa = new DFA();

        Set<State> reachable = new HashSet<>();
        reachable.add(q0);
        Set<State> states = new HashSet<>();
        states.add(q0);
        do{
            Set<State> temp = new HashSet<>();
            states.forEach(q -> {
                S.forEach(s ->{
                    State qt = T.get(new Pair<>(q, s));
                    if(qt != null) temp.add(qt);
                });
            });
            Set<State> temp2 = new HashSet<>(temp);
            temp2.forEach(q ->{
                if(reachable.contains(q)) temp.remove(q);
            });
            states = temp;
            reachable.addAll(states);
        } while(!states.isEmpty());

        dfa.setQ0(q0);
        dfa.addS(S);
        F.forEach(q -> {
            if(reachable.contains(q)) dfa.addF(q);
        });
        dfa.addQ(reachable);
        T.forEach((k, v) -> {
            if(reachable.contains(k.getKey()))
                dfa.addT(k.getKey(), k.getValue(), v);
        });

        return dfa;
    }

    public DFA minimize(){
        DFA dfa = new DFA();

        Set<Set<State>> P = new HashSet<>();
        Set<Set<State>> W = new HashSet<>();

        P.add(F);
        Set<State> QF = new HashSet<>(Q);
        for (State q : Q) {
            if(F.contains(q)) QF.remove(q);
        }
        P.add(QF);
        W.add(F);

        while(!W.isEmpty()){
            Set<Set<State>> W2 = new HashSet<>(W);
            for (Set<State> w : W2) {
                W.remove(w);
                for (String s : S) {
                    Set<State> X = new HashSet<>();
                    T.forEach((k, v) -> {
                        if(k.getValue().equals(s) && w.contains(v))
                            X.add(k.getKey());
                    });
                    Set<Set<State>> P2 = new HashSet<>(P);
                    for (Set<State> Y : P2) {
                        Set<State> Y_X = new HashSet<>();
                        Set<State> XY = new HashSet<>();
                        for (State y : Y) {
                            if(X.contains(y)) XY.add(y);
                            else Y_X.add(y);
                        }
                        if(!XY.isEmpty() && !Y_X.isEmpty()){
                            P.remove(Y);
                            P.add(XY);
                            P.add(Y_X);
                            if(W.contains(Y)){
                                W.remove(Y);
                                W.add(XY);
                                W.add(Y_X);
                            }else{
                                if(XY.size() <= Y_X.size()) W.add(XY);
                                else W.add(Y_X);
                            }
                        }
                    }
                }
                
            }
        }
        P.forEach(qn -> {
            StateN sn = new StateN(qn);
            dfa.addQ(sn);
            if(qn.contains(q0)) dfa.setQ0(sn);
            for (State q : qn) {
                if(F.contains(q)) dfa.addF(sn);
                for (String s : S) {
                    State to = T.get(new Pair<>(q, s));
                    for (Set<State> p : P) {
                        if(p.contains(to)) {
                            dfa.addT(sn, s, new StateN(p));
                            break;
                        }
                    }
                }
                break;
            }
        });


        dfa.addS(S);

        return dfa;
    }

    @Deprecated
    public DFA minimize_table(){
        final int QSIZE = Q.size();
        ArrayList<State> list = new ArrayList<>(Q); //assign the indexes
        int checkList[] = new int[QSIZE];
        for(int i=0; i<QSIZE; i++) checkList[i] = i;

        DFA dfa = new DFA();
        dfa.addS(S);

        boolean table[][] = new boolean[QSIZE-1][QSIZE-1]; //mark is 'false'

        int c = 0, pc;
        for(int i=0; i<QSIZE-1; i++){
            boolean isInF = F.contains(list.get(i+1));
            for(int j=0; j<=i; j++){
                if(F.contains(list.get(j)) != isInF){
                    table[i][j] = false;
                    c++;
                }else table[i][j] = true;
            }
        }

        for(int i=0; i<QSIZE; i++) System.out.print(list.get(i) + " ");
        System.out.println();
        for(int i=0; i<QSIZE-1; i++){
            for(int j=0; j<=i; j++){
                System.out.print(table[i][j]+"\t");
            }
            System.out.println();
        }

        do{
            pc = c;

            for(int i=0; i<QSIZE-1; i++){
                for(int j=0; j<=i; j++){
                    if(table[i][j]){
                        for(String s : S){
                            Pair<State, String> pair1 = new Pair<>(list.get(i+1), s);
                            Pair<State, String> pair2 = new Pair<>(list.get(j), s);
                            int k1 = list.indexOf(T.get(pair1));
                            int k2 = list.indexOf(T.get(pair2));
                            int r1 = Math.max(k1, k2);
                            int r2 = Math.min(k1, k2);
                            if((r2 < 0 && r1 >= 0) || (r1 != r2 && !table[r1-1][r2])) {
                                table[i][j] = false;
                                c--;
                                break;
                            }

                        }
                    }
                }
            }
            System.out.println();
            for(int i=0; i<QSIZE-1; i++){
                for(int j=0; j<=i; j++){
                    System.out.print(table[i][j]+"\t");
                }
                System.out.println();
            }

        }while(c != pc);


        for(int i=0; i<QSIZE-1; i++){
            for(int j=0; j<=i; j++){
                if(table[i][j]) {
                    checkList[i+1] = checkList[j];
                }
            }
        }

        dfa.setQ0(list.get(list.indexOf(q0)));
        for(int i=0; i<QSIZE; i++){
            if(checkList[i] == i) {
                State sq = list.get(i);
                dfa.addQ(sq);
                if(F.contains(sq)) dfa.addF(sq);
            }
        }

        T.forEach((k, v) -> {
            int idx = list.indexOf(k.getKey());
            int idxv = list.indexOf(v);
            if(checkList[idx] == idx){
                dfa.addT(list.get(checkList[idx]), k.getValue(), list.get(checkList[idxv]));
            }
        });




        return dfa;
    }

    public DFA simplify(){
        DFA dfa = new DFA();
        ArrayList<State> qList = new ArrayList<>(Q);
        ArrayList<State> sqList = new ArrayList<>(Q.size());

        int i=1;
        State q0s = new State("q0");
        dfa.addQ(q0s);
        sqList.add(q0s);
        qList.remove(q0);
        qList.add(0, q0);
        dfa.setQ0(q0s);

        for(State q : qList){
            if(q.equals(q0)) continue;

            State sState = new State("q"+i);
            if(q.equals(DEAD_STATE)) sState = DEAD_STATE;
            else i++;
            dfa.addQ(sState);
            sqList.add(sState);
            if(F.contains(q)) dfa.addF(sState);
        }
        dfa.addS(S);
        T.forEach((k, v) -> {
            State qs = sqList.get(qList.indexOf(k.getKey()));
            State qt = sqList.get(qList.indexOf(v));
            dfa.addT(qs, k.getValue(), qt);
        });
        return dfa;
    }

    public String toOutputString(){
        StringBuilder sb = new StringBuilder();

        ArrayList<String> buf = new ArrayList<>();

        sb.append("State\n");
        Q.forEach(q -> buf.add(q.getName()));
        Collections.sort(buf);
        sb.append(String.join(",", buf));

        sb.append("\nInput symbol\n");
        buf.clear();
        S.forEach(buf::add);
        Collections.sort(buf);
        sb.append(String.join(",", buf));

        sb.append("\nState transition function\n");
        buf.clear();
        T.forEach((k, v) -> buf.add(String.format("%s,%s,%s", k.getKey().getName(), k.getValue(), v.getName())));
        Collections.sort(buf);
        sb.append(String.join("\n", buf));

        sb.append("\nInitial state\n");
        sb.append(q0.getName());

        sb.append("\nFinal state\n");
        buf.clear();
        F.forEach(q -> buf.add(q.getName()));
        Collections.sort(buf);
        sb.append(String.join(",", buf));
        return sb.toString();
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
