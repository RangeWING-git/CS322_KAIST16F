package cs322.p1_1;

import cs322.common.DFA;
import cs322.common.FileHandler;
import cs322.common.State;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by RangeWING on 2016-09-20.
 * R/W files
 */
public class FileHandler_P1_1 extends FileHandler {
    protected final String DFA_FILE;

    private static final int STATE = 0;
    private static final int SYMBOL = 1;
    private static final int TRANSITION = 2;
    private static final int INIT_STATE = 3;
    private static final int FINAL_STATE = 4;

    public FileHandler_P1_1(){
        this(null, null, null);
    }
    public FileHandler_P1_1(String dfa, String input, String output){
        super(input, output);
        DFA_FILE = (dfa != null) ? dfa :  "dfa.txt";
    }

    /**
     * Create FileHandler instance with arguments
     * @param args string array args
     * @return FileHandler instance
     */
    public static FileHandler_P1_1 createHandler(String[] args){
        switch(args.length){
            case 0: return new FileHandler_P1_1(null, null, null);
            case 1: return new FileHandler_P1_1(args[0], null, null);
            case 2: return new FileHandler_P1_1(args[0], args[1], null);
            case 3: default: return new FileHandler_P1_1(args[0], args[1], args[2]);
        }
    }

    /**
     * Read from DFA file and creates DFA instance.
     * @return DFA instance read from DFA file.
     * @throws IOException
     */
    public DFA readDFA() throws IOException{
        FileReader reader = new FileReader(DFA_FILE);
        StringBuilder sb = new StringBuilder();
        DFA dfa = new DFA();
        int c, k = -1;
        do{
            c = reader.read();
            if(c != '\n' && c != -1)	sb.append((char)c);
            else {
                String line = sb.toString().trim();
                if(line.equals("State")) k = STATE;
                else if(line.equals("Input symbol")) k = SYMBOL;
                else if(line.equals("State transition function")) k = TRANSITION;
                else if(line.equals("Initial state")) k = INIT_STATE;
                else if(line.equals("Final state")) k = FINAL_STATE;
                else {
                    String[] tokens = line.split(",");
                    try{
                        switch(k){
                            case STATE: dfa.addQ(State.makeList(tokens)); break;
                            case SYMBOL: dfa.addS(new ArrayList<>(Arrays.asList(tokens))); break;
                            case TRANSITION: dfa.addT(new State(tokens[0]), tokens[1], new State(tokens[2])); break;
                            case INIT_STATE: dfa.setQ0(new State(line)); break;
                            case FINAL_STATE: dfa.addF(State.makeList(tokens)); break;
                            //default: throw new IOException("Input DFA format is wrong");
                        }
                    }catch(Exception e){
                        e.printStackTrace();
                        throw new IOException("Input DFA format is wrong");
                    }
                }
                sb = new StringBuilder();
            }
        }while(c != -1);
        reader.close();
        return dfa;
    }
}
