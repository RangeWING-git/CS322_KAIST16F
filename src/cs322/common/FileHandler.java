package cs322.common;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by RangeWING on 2016-09-20.
 * R/W files
 */
public class FileHandler {
    protected final String INPUT_FILE;
    protected final String OUTPUT_FILE;

    public FileHandler(){
        this(null, null);
    }
    public FileHandler(String input, String output){
        INPUT_FILE = (input != null) ? input : "input.txt";
        OUTPUT_FILE = (output != null) ? output : "output.txt";
    }

    /**
     * Create cs322.common.FileHandler instance with arguments
     * @param args string array args
     * @return cs322.common.FileHandler instance
     */
    public static FileHandler createHandler(String[] args){
        switch(args.length){
            case 0: return new FileHandler(null, null);
            case 1: return new FileHandler(args[0], null);
            case 2: default: return new FileHandler(args[0], args[1]);
        }
    }

    /**
     * read input symbols from the input file
     * @return ArrayList of strings, in List
     * @throws IOException
     */
    public ArrayList<ArrayList<String>> readInput() throws IOException{
        FileReader reader = new FileReader(INPUT_FILE);
        StringBuilder sb = new StringBuilder();
        ArrayList<ArrayList<String>> list = new ArrayList<>();
        ArrayList<String> sublist;
        int c;
        do {
            c = reader.read();
            if (c != '\n' && c != -1) sb.append((char) c);
            else {
                String line = sb.toString().trim();
                if(line.equals("end")) break;
                sublist = new ArrayList<>();
                for(char cc : line.toCharArray()){
                    sublist.add(String.valueOf(cc));
                }
                list.add(sublist);
                sb = new StringBuilder();
            }
        }while(c != -1);
        reader.close();
        return list;
    }

    public void clearOutput(){
        File output = new File(OUTPUT_FILE);
        if(output.exists()) output.delete();
    }
    public void appendToOutput(String str) throws IOException{
        FileWriter writer = new FileWriter(OUTPUT_FILE, true);
        writer.append(str);
        writer.close();
    }
}
