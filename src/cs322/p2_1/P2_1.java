package cs322.p2_1;

import cs322.common.DFA;
import cs322.common.E_NFA;

import java.io.IOException;

/**
 * Created by RangeWING on 2016-11-05.
 */
public class P2_1 {
    public static void main(String[] args){
        try {
            FileHandler_P2_1 fileHandler = FileHandler_P2_1.createHandler(args);
            E_NFA e_nfa = fileHandler.readE_FNA();
            DFA dfa = e_nfa.m_DFA();
            fileHandler.appendToOutput(dfa.toOutputString());
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
