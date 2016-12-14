package cs322.main3;

import cs322.common.Mealy;
import cs322.common.State;

import java.util.*;

/**
 * Created by RangeWING on 2016-12-11.
 */
public class PMealy extends Mealy {
    private static final Set<String> vowelSet = new HashSet<>();
    public static final String VOWEL = "v";
    public static final String CONSONANT = "c";
    public PMealy(){
        vowelSet.add("3");
        vowelSet.add("e");
        vowelSet.add("d");
        vowelSet.add("x");
    }
    public PMealy(Mealy mealy){
        this();
        this.Q = mealy.getQ();
        this.S = mealy.getS();
        this.P = mealy.getP();  //
        this.q0 = mealy.getQ0();
        this.L = mealy.getL();  //
        this.T = mealy.getT();
        init();
    }
    public static boolean isVowel(String s){
        return vowelSet.contains(s);
    }
    public void init(){
        Set<State> vSet = new HashSet<>();
        T.forEach((k, v) ->{
            if(vowelSet.contains(k.getValue())){
                vSet.add(v);
            }
        });
        T.forEach((k, v) ->
            addL(k.getKey(), k.getValue(), vSet.contains(v) ? VOWEL : CONSONANT)
        );

    }
}
