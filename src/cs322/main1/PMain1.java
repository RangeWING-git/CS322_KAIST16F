package cs322.main1;
import java.io.IOException;
import java.util.*;

/**
 * Created by RangeWING on 2016-09-29.
 */
public class PMain1 {
    public static void main(String[] args){
        GUIManager guiManager;

        int mode = Hangeul.MODE_BATCHIM;
        if(args.length < 1){
            System.out.println("USAGE: java cs322.main1.PMain1 <mode> <GUI/TUI>");
            System.out.println("mode: 0 = BATCHIM-prior, 1 = CHOSUNG-prior");
            System.out.println("Default mode = 0\n");
        }else{
            int a = Integer.parseInt(args[0]);
            switch(a){
                case 1: mode = Hangeul.MODE_CHOSUNG; break;
                case 0:default: mode = Hangeul.MODE_BATCHIM; break;
            }
        }
        System.out.println("CS322 16F Project 1");
        System.out.println("by Young-Geol Cho, 20150717, SoC");
        System.out.printf("%s-prior mode executed\n", (mode == Hangeul.MODE_CHOSUNG ? "CHOSUNG" : "BATCHIM"));

        HMealy hMealy = new HMealy();
        FileHandler_Main1 handler = FileHandler_Main1.createHandler(args);
        Hangeul hangeul = new Hangeul();
        hangeul.setMode(mode);

        hMealy.addQ(HState.makeList("SVOUAIKNRL"));
        hMealy.addS(stringToCharSet(Hangeul.start + Hangeul.mid + Hangeul.last));
        hMealy.addP(stringToCharSet("0123456789"));
        hMealy.setQ0(HState.get('S'));
        try {
            hMealy.addT(handler.getTFunc());
            hMealy.addL(handler.getOutFunc());
        }catch(IOException e){
            e.printStackTrace();
        }

        if(args.length > 1 && args[1].toLowerCase().equals("gui")){
            guiManager = new GUIManager();
            guiManager.addKeyListener(hangeul, hMealy);
        }else {
            Scanner sc = new Scanner(System.in);
            while (true) {
                System.out.print("Input>\t");
                String line = sc.nextLine();
                if (line.equals("EXIT")) {
                    System.out.println("Exit");
                    break;
                }
                String out = hangeul.getOutput(hMealy, line);
                if(out != null)
                    System.out.println("Output>\t" + hangeul.getOutput(hMealy, line));
                else System.out.println("Not valid input");
            }
            sc.close();
        }
    }

    public static void clear(){
        try{
            final String os = System.getProperty("os.name");
            if(os.contains("Windows")){
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            }else{
                Runtime.getRuntime().exec("clear");
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public static Set<Character> stringToCharSet(String s){
        char[] chars = s.toCharArray();
        Set<Character> set = new HashSet<>();
        for(char c : chars){
            set.add(c);
        }
        return set;
    }
}
