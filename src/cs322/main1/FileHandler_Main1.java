package cs322.main1;

import cs322.common.DFA;
import cs322.common.FileHandler;
import cs322.common.Pair;
import cs322.common.State;

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
public class FileHandler_Main1 extends FileHandler {
    protected final File DATA_PATH;
    protected final File TFUNC_FILE, OUTFUNC_FILE;
    protected final String SYMBOL_FILE_BASE;

    public FileHandler_Main1(){
        this(null);
    }
    public FileHandler_Main1(String dataPath){
        super(null, null);
        String dp = (dataPath != null) ? dataPath: "data/";
        DATA_PATH = findFile(dp);
        TFUNC_FILE = new File(DATA_PATH, "TFunc.txt");
        OUTFUNC_FILE = new File(DATA_PATH, "OutFunc.txt");
        SYMBOL_FILE_BASE = "SymbolTable%d.txt";
    }

    /**
     * Create FileHandler instance with arguments
     * @param args string array args
     * @return FileHandler instance
     */
    public static FileHandler_Main1 createHandler(String[] args){
        switch(args.length){
            case 0: case 1:case 2: return new FileHandler_Main1(null);
            case 3: default: return new FileHandler_Main1(args[2]);
        }
    }

    public File findFile(String path){
        File file = new File(path);
        if(file.exists()) return file;
        file = new File("./"+path);
        if(file.exists()) return file;
        file = new File("../"+path);
        if(file.exists()) return file;
        file = new File("../../"+path);
        if(file.exists()) return file;
        file = new File("../../../"+path);
        if(file.exists()) return file;
        return null;
    }

    public Map<Pair<HState, Character>, HState> getTFunc() throws IOException{
        FileReader reader = new FileReader(TFUNC_FILE);
        StringBuilder sb = new StringBuilder();
        Map<Pair<HState, Character>, HState> map = new HashMap<>();
        int c, k = -1;
        do {
            c = reader.read();
            if (c != '\n' && c != -1) sb.append((char) c);
            else {
                String line = sb.toString().trim();
                String sp[] = line.split(",");
                HState qi, qf;
                char[] sp0s = sp[0].toCharArray();
                char[] sp1s = sp[1].toCharArray();
                char sp2 = sp[2].charAt(0);
                qf = HState.get(sp2);
                for(char sp0 : sp0s){
                    qi = HState.get(sp0);
                    for(char sp1 : sp1s){
                        Pair<HState, Character> pair = new Pair<>(qi, sp1);
                        map.put(pair, qf);
                    }
                }

                sb = new StringBuilder();
            }
        }while(c != -1);
        reader.close();
        return map;
    }

    public Map<Pair<HState, Character>, Character> getOutFunc() throws IOException{
        FileReader reader = new FileReader(OUTFUNC_FILE);
        StringBuilder sb = new StringBuilder();
        Map<Pair<HState, Character>, Character> map = new HashMap<>();
        int c, k = -1;
        do {
            c = reader.read();
            if (c != '\n' && c != -1) sb.append((char) c);
            else {
                String line = sb.toString().trim();
                String sp[] = line.split(",");
                HState qi;
                char[] sp0s = sp[0].toCharArray();
                char[] sp1s = sp[1].toCharArray();
                char sp2 = sp[2].charAt(0);
                for(char sp0 : sp0s){
                    qi = HState.get(sp0);
                    for(char sp1 : sp1s){
                        Pair<HState, Character> pair = new Pair<>(qi, sp1);
                        map.put(pair, sp2);
                    }
                }

                sb = new StringBuilder();
            }
        }while(c != -1);
        reader.close();
        return map;
    }

    public Map<String, Integer> getSymbol(int i) throws IOException{
        String f = String.format(SYMBOL_FILE_BASE, i);
        File file = new File(DATA_PATH, f);

        FileReader reader = new FileReader(file);
        StringBuilder sb = new StringBuilder();
        Map<String, Integer> map = new HashMap<>();
        int c, k = -1;
        do {
            c = reader.read();
            if (c != '\n' && c != -1) sb.append((char) c);
            else {
                String line = sb.toString().trim();
                String sp[] = line.split(",");
                map.put(sp[0], Integer.parseInt(sp[1]));
                sb = new StringBuilder();
            }
        }while(c != -1);
        reader.close();
        return map;
    }
}
