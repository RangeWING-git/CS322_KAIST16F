package cs322.p1_1;

import cs322.common.DFA;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;

/**
 * Created by RangeWING on 2016-09-21.
 * CS322 예비 프로젝트 1-1 Main
 * 20150717 조영걸
 */
public class P1_1 {
    private static final String YES = new String("네\n".getBytes(),  Charset.defaultCharset());
    private static final String NO = new String("아니요\n".getBytes(),  Charset.defaultCharset());

    public static void main(String[] args){
        try {
            FileHandler_P1_1 fileHandler = FileHandler_P1_1.createHandler(args);
            DFA dfa = fileHandler.readDFA(); //read cs322.common.DFA
            ArrayList<ArrayList<String>> input = fileHandler.readInput(); //read input
            //check whether all symbols in cs322.common.DFA are in dfa.Q
            if(!dfa.check()){
                System.out.println("Input cs322.common.DFA is wrong: there are some symbols not in Σ");
                System.exit(1);
            }

            //check if each input string is accepted and write the result.
            fileHandler.clearOutput();
            for(ArrayList<String> s : input){
                fileHandler.appendToOutput(DFA.isAccepted(dfa, s) ? YES : NO);
            }
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
