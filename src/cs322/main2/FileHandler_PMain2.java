package cs322.main2;

import cs322.common.FileHandler;

import java.io.FileReader;
import java.io.IOException;

/**
 * Created by RangeWING on 2016-09-29.
 */
public class FileHandler_PMain2 extends FileHandler {
    protected final String RE_FILE;

    public FileHandler_PMain2(){
        this(null);
    }
    public FileHandler_PMain2(String re){
        this(re, "m-dfa.txt");
    }
    public FileHandler_PMain2(String re, String output){
        super(null, output);
        RE_FILE = (re != null) ? re :  "re.txt";
    }

    /**
     * Create FileHandler instance with arguments
     * @param args string array args
     * @return FileHandler instance
     */
    public static FileHandler_PMain2 createHandler(String[] args){
        switch(args.length){
            case 0: return new FileHandler_PMain2();
            case 1: return new FileHandler_PMain2(args[0]);
            case 2: default: return new FileHandler_PMain2(args[0], args[1]);
        }
    }


    /**
     * Read from RE file and returns string.
     * @return RE as string
     * @throws IOException
     */
    public String readRE() throws IOException{
        FileReader reader = new FileReader(RE_FILE);
        StringBuilder sb = new StringBuilder();
        int c;
        do{
            c = reader.read();
            if(c != '\n' && c != -1)	sb.append((char)c);

        }while(c != -1);
        reader.close();
        return sb.toString();
    }

}
