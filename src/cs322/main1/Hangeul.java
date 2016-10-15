package cs322.main1;

import cs322.common.Pair;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by RangeWING on 2016-10-14.
 */
public class Hangeul {
    private static final boolean DEBUG_MODE = false;
    public static final String start = "rsefaqtdwczxvgREQTW";
    public static final String mid = "kijuhynbmloOpP";
    public static final String last = "rsefaqtdwczxvgRT";
    Map<String, Integer> sMap0, sMap1, sMap2, sMap3;

    public Hangeul(){
        FileHandler_Main1 fileHandler = new FileHandler_Main1();
        try {
            sMap0 = fileHandler.getSymbol(0);
            sMap1 = fileHandler.getSymbol(1);
            sMap2 = fileHandler.getSymbol(2);
            sMap3 = fileHandler.getSymbol(3);
        }catch(IOException e){
            e.printStackTrace();
        }
    }
    /**
     * Checks if the input sequence x is accepted by cs322.common.DFA
     * @param hMealy input mealy
     * @param x input sequence
     * @return output string
     */
    public String getOutput(HMealy hMealy, String x){
        HState q = hMealy.q0;
        int n = x.length();
        //List<Character> output = new ArrayList<>(n);
        StringBuilder output = new StringBuilder();
        char st=0, md=0, md2=0, lt=0, lt2=0;

        Pair<HState, Character> pair;
        for(int i = 0; i < n; i++){
            char c = x.charAt(i);
            pair = new Pair<>(q, c);
            if(c == '<'){
                String ss = "";
                if(lt2 > 0) lt2 = 0;
                else if(lt > 0) lt = 0;
                else if(md2 > 0) md2 = 0;
                else if(md > 0) md = 0;
                else if(st > 0) st = 0;
                else {
                    output.deleteCharAt(output.length()-1);
                }
                ss = ""+st+md+md2+lt+lt2 + (i < n-1 ? x.substring(i+1): "");
                //output.append(getOutput(hMealy, ss));
            }else if(hMealy.T.containsKey(pair)){
                q = hMealy.T.get(pair);
                char r = hMealy.L.get(pair);
                if(DEBUG_MODE) System.out.println(r + "\t:  " + q);
                switch(r){
                    case '0': st = c; break;    //초성 = 입력
                    case '1': md = c; md2 = 0; break;   //중성 = 입력
                    case '2': md2 = c; break;   //중성 += 입력
                    case '3':  //받침=e, 완성, 초성=Bfr
                        output.append(combine(st, md, md2, '0', (char)0));
                        st = lt;
                        md = c;
                        md2 = lt = lt2 = 0;
                        break;
                    case '4': lt = c; lt2 = 0; break;   //받침 = 입력
                    case '5': lt2 = c; break;   //박침 += 입력
                    case '6':case '7':  //받침=Bfr(완성) -> 0 / 받침=Bfr/OBfr+Bfr (완성) -> 0
                        output.append(combine(st, md, md2, lt, lt2));
                        st = c;
                        md = md2 = lt = lt2 = 0;
                        break;
                    case '8': // 받침=e, 완성 -> 0
                        output.append(combine(st, md, md2, lt, (char)0));
                        st = c;
                        md = md2 = lt = lt2 = 0;
                        break;
                    case '9':   // 받침=e,초성=Bfr/받침=OBfr,초성=Bfr
                        if(lt2 > 0) {
                            output.append(combine(st, md, md2, lt, (char)0));
                            st = lt2;
                        }else {
                            output.append(combine(st, md, md2, '0', (char)0));
                            st = lt;
                        }
                        lt = 0;
                        md = c;
                        md2 = 0;
                        lt2 = 0;
                        break;
                }
            }else return null;
        }
        if(DEBUG_MODE) System.out.println(String.format("(%c,%c,%c,%c,%c)", st, md, md2, lt, lt2));
        output.append(combine(st, md, md2, lt, lt2));
        return output.toString();
    }

    public char combine(char st, char md, char md2, char lt, char lt2){
        int f, m, l;
        try {
            if(st == 0) return ' ';
            else if(md == 0) return (char)(sMap3.get(String.valueOf(st)) + '\u3130');
            f = sMap0.get(String.valueOf(st));
            m = sMap1.get(md2 > 0 ? String.valueOf(md) + String.valueOf(md2) : String.valueOf(md));
            l = lt > 0 ? sMap2.get(lt2 > 0 ? String.valueOf(lt) + String.valueOf(lt2) : String.valueOf(lt)) : 0;
        }catch(Exception e){
            System.err.println(String.format("Err at (%c,%c,%c,%c,%c)", st, md, md2, lt, lt2));
            e.printStackTrace();
            return 0;
        }
        return (char)((((f * 21 + m) * 28) + l) + '\uAC00');

    }

}
