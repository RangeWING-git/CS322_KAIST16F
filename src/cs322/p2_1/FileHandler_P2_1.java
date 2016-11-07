package cs322.p2_1;

import cs322.common.E_NFA;
import cs322.common.FileHandler;
import cs322.common.Mealy;
import cs322.common.State;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by RangeWING on 2016-09-29.
 */
public class FileHandler_P2_1 extends FileHandler {
    protected final String E_NFA_FILE;

    private static final int STATE = 0;
    private static final int SYMBOL = 1;
    private static final int TRANSITION = 2;
    private static final int INIT_STATE = 3;
    private static final int FINAL_STATE = 4;


    public FileHandler_P2_1(){
        this(null, null);
    }
    public FileHandler_P2_1(String e_nfa, String output){
        super(null, output);
        E_NFA_FILE = (e_nfa != null) ? e_nfa :  "e-nfa.txt";
    }

    /**
     * Create FileHandler instance with arguments
     * @param args string array args
     * @return FileHandler instance
     */
    public static FileHandler_P2_1 createHandler(String[] args){
        switch(args.length){
            case 0: return new FileHandler_P2_1(null, null);
            case 1: return new FileHandler_P2_1(args[0], null);
            case 2: default: return new FileHandler_P2_1(args[0], args[1]);
        }
    }


    /**
     * Read from ε-NFA file and creates e-NFA instance.
     * @return e-NFA instance read from e-NFA file.
     * @throws IOException
     */
    public E_NFA readE_FNA() throws IOException{
        FileReader reader = new FileReader(E_NFA_FILE);
        StringBuilder sb = new StringBuilder();
        E_NFA eNFA = new E_NFA();
        String EMPTY_STRING = "E";
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
                            case STATE: eNFA.addQ(State.makeList(tokens)); break;
                            case SYMBOL: eNFA.addS(new ArrayList<>(Arrays.asList(tokens))); break;
                            case TRANSITION:
                                if(tokens[1] == null || tokens[1].trim().equals("") || tokens[1].equals(EMPTY_STRING))
                                    eNFA.addT(new State(tokens[0]), E_NFA.EMPTY, new State(tokens[2]));
                                else
                                    eNFA.addT(new State(tokens[0]), tokens[1], new State(tokens[2]));
                                break;
                            case INIT_STATE: eNFA.setQ0(new State(line)); break;
                            case FINAL_STATE: eNFA.addF(State.makeList(tokens)); break;
                            //default: throw new IOException("Input DFA format is wrong");
                        }
                    }catch(Exception e){
                        e.printStackTrace();
                        throw new IOException("Input e-NFA format is wrong");
                    }
                }
                sb = new StringBuilder();
            }
        }while(c != -1);
        reader.close();
        return eNFA;
    }
}
