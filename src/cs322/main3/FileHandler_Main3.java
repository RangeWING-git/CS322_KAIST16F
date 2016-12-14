package cs322.main3;

import cs322.common.DFA;
import cs322.common.FileHandler;
import cs322.common.Pair;
import cs322.common.State;
import cs322.main1.HState;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by RangeWING on 2016-09-20.
 * R/W files
 */
public class FileHandler_Main3 extends FileHandler {
    protected final File DATA_PATH;
    protected final File DFA_FILE;
    protected final File KEYMAP_FILE;

    private static final int STATE = 0;
    private static final int SYMBOL = 1;
    private static final int TRANSITION = 2;
    private static final int INIT_STATE = 3;
    private static final int FINAL_STATE = 4;

    public FileHandler_Main3(){
        this(null);
    }
    public FileHandler_Main3(String dataPath){
        super(null, null);
        String dp = (dataPath != null) ? dataPath: "data/";
        DATA_PATH = findFile(dp);
        DFA_FILE = new File(DATA_PATH, "dfa12key.txt");
        KEYMAP_FILE = new File(DATA_PATH, "12keymapping.txt");
    }

    public boolean isExistDFA(){
        return DFA_FILE.exists();
    }

    public void writeDFA(String str) throws IOException{
        clearFile(DFA_FILE);
        appendTo(DFA_FILE, str);
    }

    /**
     * Create FileHandler instance with arguments
     * @param args string array args
     * @return FileHandler instance
     */
    public static FileHandler_Main3 createHandler(String[] args){
        switch(args.length){
            case 0: case 1:case 2: return new FileHandler_Main3(null);
            case 3: default: return new FileHandler_Main3(args[2]);
        }
    }


    public Map<String, String> readKeyMap() throws IOException{
        Map<String, String> keymap = new HashMap<>();

        FileReader reader = new FileReader(KEYMAP_FILE);
        StringBuilder sb = new StringBuilder();
        int c;
        do {
            c = reader.read();
            if (c != '\n' && c != -1) sb.append((char) c);
            else {
                String line = sb.toString().trim();
                String sp[] = line.split(" ");
                if(sp.length == 2) keymap.put(sp[0], sp[1]);
                sb = new StringBuilder();
            }
        }while(c != -1);
        reader.close();
        return keymap;
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
