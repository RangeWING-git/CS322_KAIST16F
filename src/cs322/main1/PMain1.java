package cs322.main1;

import cs322.common.State;

import java.io.IOException;
import java.util.*;

/**
 * Created by RangeWING on 2016-09-29.
 */
public class PMain1 {
    public static void main(String[] args){
        HMealy hMealy = new HMealy();
        FileHandler_Main1 handler = FileHandler_Main1.createHandler(args);
        Hangeul hangeul = new Hangeul();

        hMealy.addQ(HState.makeList("SVOUAIKNRL"));
        hMealy.addS(stringToCharSet(Hangeul.start + Hangeul.mid + Hangeul.last));
        hMealy.addP(stringToCharSet("01234"));
        hMealy.setQ0(HState.get('S'));
        try {
            hMealy.addT(handler.getTFunc());
            hMealy.addL(handler.getOutFunc());
        }catch(IOException e){
            e.printStackTrace();
        }

        Scanner sc = new Scanner(System.in);
        while(true){
            String line = sc.nextLine();
            if(line.equals("EXIT")) break;
            System.out.println(">" + hangeul.getOutput(hMealy, line));
        }
        sc.close();
        //System.out.printf("\n%c", combine('\u1100', '\u1161', '\u11AB'));
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
