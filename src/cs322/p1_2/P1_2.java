package cs322.p1_2;

import cs322.common.Mealy;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by RangeWING on 2016-09-29.
 * CS322 예비 프로젝트 1-2 Main
 * 20150717 조영걸
 */
public class P1_2 {
    private static final String NO_PATH = "No path exists!";

    public static void main(String[] args){
        try {
            FileHandler_P1_2 fileHandler = FileHandler_P1_2.createHandler(args);
            Mealy mealy = fileHandler.readMealy(); //read cs322.common.Mealy
            ArrayList<ArrayList<String>> input = fileHandler.readInput(); //read input
            //check whether all symbols in cs322.common.DFA are in dfa.Q
            if(!mealy.check()){
                System.out.println("Input Mealy is wrong");
                System.exit(1);
            }

            //check if each input string is accepted and write the result.
            fileHandler.clearOutput();
            for(ArrayList<String> s : input){
                List<String> output = Mealy.getOutput(mealy, s);
                String result;
                if(output == null) result = NO_PATH;
                else{
                    result = "";
                    for(String t : output) result += t;
                }
                fileHandler.appendToOutput(result + "\n");
            }
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
