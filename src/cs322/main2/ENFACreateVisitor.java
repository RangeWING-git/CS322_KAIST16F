package cs322.main2;

import cs322.common.E_NFA;
import cs322.common.State;
import cs322.main2.tree.*;

import java.util.Set;

/**
 * Created by RangeWING on 2016-11-21.
 */
public class ENFACreateVisitor extends AbstractASTVisitor<E_NFA> {

    @Override
    public E_NFA visit(ClosureNode node) {
        E_NFA nfa = visit(node.target);
        State Rq0 = nfa.getQ0();
        Set<State> RF = nfa.getF();
        nfa.clearQ0F();

        State qi = State.createNotDupState();
        State qf = State.createNotDupState();
        nfa.addQ(qi);
        nfa.addQ(qf);
        nfa.setQ0(qi);

        nfa.addT(qi, E_NFA.EMPTY, Rq0);
        nfa.addT(qi, E_NFA.EMPTY, qf);
        RF.forEach(f -> {
            nfa.addT(f, E_NFA.EMPTY, qf);
            nfa.addT(f, E_NFA.EMPTY, Rq0);
        });
        nfa.addF(qf);

        return nfa;
    }

    @Override
    public E_NFA visit(SymbolNode node) {
        E_NFA nfa = new E_NFA();
        State qi = State.createNotDupState();
        State qf = State.createNotDupState();
        nfa.addQ(qi);
        nfa.addQ(qf);
        nfa.setQ0(qi);
        nfa.addF(qf);

        nfa.addS(String.valueOf(node.v));
        nfa.addT(qi, String.valueOf(node.v), qf);

        return nfa;
    }

    @Override
    public E_NFA visit(EpsilonNode node) {
        E_NFA nfa = new E_NFA();
        State qi = State.createNotDupState();
        State qf = State.createNotDupState();
        nfa.addQ(qi);
        nfa.addQ(qf);
        nfa.setQ0(qi);
        nfa.addF(qf);
        nfa.addT(qi, E_NFA.EMPTY, qf);

        return nfa;
    }

    @Override
    public E_NFA visit(ConcatenationNode node) {
        E_NFA nfa = new E_NFA();
        E_NFA R = visit(node.left);
        E_NFA S = visit(node.right);

        State Rq0 = R.getQ0();
        Set<State> RF = R.getF();
        R.clearQ0F();

        State Sq0 = S.getQ0();
        Set<State> SF = S.getF();
        S.clearQ0F();

        nfa.setQ0(Rq0);
        nfa.addQ(R.getQ());
        nfa.addQ(S.getQ());
        nfa.addS(R.getS());
        nfa.addS(S.getS());

        R.getT_NFA().forEach((k, v) -> {
            v.forEach(w -> nfa.addT(k.getKey(), k.getValue(), w));
        });
        S.getT_NFA().forEach((k, v) -> {
            v.forEach(w -> nfa.addT(k.getKey(), k.getValue(), w));
        });
        RF.forEach(f -> nfa.addT(f, E_NFA.EMPTY, Sq0));
        nfa.addF(SF);

        return nfa;
    }

    @Override
    public E_NFA visit(UnionNode node) {
        E_NFA nfa = new E_NFA();
        E_NFA R = visit(node.left);
        E_NFA S = visit(node.right);
        State qi = State.createNotDupState();
        State qf = State.createNotDupState();
        nfa.addQ(qi);
        nfa.addQ(qf);
        nfa.setQ0(qi);

        State Rq0 = R.getQ0();
        Set<State> RF = R.getF();
        R.clearQ0F();

        State Sq0 = S.getQ0();
        Set<State> SF = S.getF();
        S.clearQ0F();

        nfa.addQ(R.getQ());
        nfa.addQ(S.getQ());
        nfa.addS(R.getS());
        nfa.addS(S.getS());

        R.getT_NFA().forEach((k, v) -> {
            v.forEach(w -> nfa.addT(k.getKey(), k.getValue(), w));
        });
        S.getT_NFA().forEach((k, v) -> {
            v.forEach(w -> nfa.addT(k.getKey(), k.getValue(), w));
        });
        nfa.addT(qi, E_NFA.EMPTY, Rq0);
        nfa.addT(qi, E_NFA.EMPTY, Sq0);
        RF.forEach(f -> nfa.addT(f, E_NFA.EMPTY, qf));
        SF.forEach(f -> nfa.addT(f, E_NFA.EMPTY, qf));
        nfa.addF(qf);


        return nfa;
    }
}
