import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by RangeWING on 2016-09-21.
 * CS322 예비 프로젝트 1-1 Main
 * 20150717 조영걸
 */
public class P1_1 {
    public static void main(String[] args){
        try {

            FileHandler fileHandler = FileHandler.createHandler(args);
            DFA dfa = fileHandler.readDFA();
            ArrayList<ArrayList<String>> input = fileHandler.readInput();
            if(!dfa.check()){
                System.out.println("Input DFA is wrong: there are some symbols not in Σ");
                System.exit(1);
            }

            fileHandler.clearOutput();
            for(ArrayList<String> s : input){
                fileHandler.appendToOutput(DFA.isAccepted(dfa, s) ? "네\n" : "아니요\n");
            }
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
