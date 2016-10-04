package cs322.p1_2;

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
public class FileHandler_P1_2 extends FileHandler {
    protected final String MEALY_FILE;

    private static final int STATE = 0;
    private static final int SYMBOL = 1;
    private static final int TRANSITION = 2;
    private static final int OUTPUT_SYMBOL = 3;
    private static final int OUTPUT_FUNC = 4;
    private static final int INIT_STATE = 5;

    public FileHandler_P1_2(){
        this(null, null, null);
    }
    public FileHandler_P1_2(String mealy, String input, String output){
        super(input, output);
        MEALY_FILE = (mealy != null) ? mealy :  "mealy.txt";
    }

    /**
     * Create FileHandler instance with arguments
     * @param args string array args
     * @return FileHandler instance
     */
    public static FileHandler_P1_2 createHandler(String[] args){
        switch(args.length){
            case 0: return new FileHandler_P1_2(null, null, null);
            case 1: return new FileHandler_P1_2(args[0], null, null);
            case 2: return new FileHandler_P1_2(args[0], args[1], null);
            case 3: default: return new FileHandler_P1_2(args[0], args[1], args[2]);
        }
    }


    /**
     * Read from Mealy file and creates Mealy instance.
     * @return Mealy instance read from Mealy file.
     * @throws IOException
     */
    public Mealy readMealy() throws IOException{
        FileReader reader = new FileReader(MEALY_FILE);
        StringBuilder sb = new StringBuilder();
        Mealy mealy = new Mealy();
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
                else if(line.equals("Output symbol")) k = OUTPUT_SYMBOL;
                else if(line.equals("Output function")) k = OUTPUT_FUNC;
                else {
                    String[] tokens = line.split(",");
                    try{
                        switch(k){
                            case STATE: mealy.addQ(State.makeList(tokens)); break;
                            case SYMBOL: mealy.addS(new ArrayList<>(Arrays.asList(tokens))); break;
                            case TRANSITION: mealy.addT(new State(tokens[0]), tokens[1], new State(tokens[2])); break;
                            case INIT_STATE: mealy.setQ0(new State(line)); break;
                            case OUTPUT_SYMBOL: mealy.addP(new ArrayList<>(Arrays.asList(tokens))); break;
                            case OUTPUT_FUNC: mealy.addL(new State(tokens[0]), tokens[1], tokens[2]); break;
                            //default: throw new IOException("Input cs322.common.DFA format is wrong");
                        }
                    }catch(Exception e){
                        e.printStackTrace();
                        throw new IOException("Input Mealy format is wrong");
                    }
                }
                sb = new StringBuilder();
            }
        }while(c != -1);
        reader.close();
        return mealy;
    }
}
