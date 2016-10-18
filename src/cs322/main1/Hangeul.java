package cs322.main1;

import cs322.common.Pair;

import java.io.IOException;
import java.util.Map;

/**
 * Created by RangeWING on 2016-10-14.
 */
public class Hangeul {
    private static final boolean DEBUG_MODE = false;
    public static final String start = "rsefaqtdwczxvgREQTW";
    public static final String mid = "kijuhynbmloOpP";
    public static final String last = "rsefaqtdwczxvgRT";

    public static final int MODE_BATCHIM = 0;
    public static final int MODE_CHOSUNG = 1;

    private int mode = MODE_BATCHIM;
    private Map<String, Integer> sMap0, sMap1, sMap2, sMap3;

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

    public void setMode(int mode){
        this.mode = mode;
    }


    public String getOutput(HMealy hMealy, String x){
        switch(mode){
            case MODE_BATCHIM: return getOutput_batchim(hMealy, x);
            case MODE_CHOSUNG: return getOutput_chosung(hMealy, x);
        }
        return null;
    }
    public String getOutput_batchim(HMealy hMealy, String x){
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
                if(lt2 > 0) lt2 = 0;
                else if(lt > 0) lt = 0;
                else if(md2 > 0) md2 = 0;
                else if(md > 0) md = 0;
                else if(st > 0) st = 0;
                else {
                    output.deleteCharAt(output.length()-1);
                }
                //String ss = ""+st+md+md2+lt+lt2 + (i < n-1 ? x.substring(i+1): "");
                //append(output, getOutput(hMealy, ss));
            }else if(hMealy.T.containsKey(pair)){
                q = hMealy.T.get(pair);
                char r = hMealy.L.get(pair);
                if(DEBUG_MODE) System.out.println(r + "\t:  " + q);
                switch(r){
                    case '0': st = c; break;    //초성 = 입력
                    case '1': md = c; md2 = 0; break;   //중성 = 입력
                    case '2': md2 = c; break;   //중성 += 입력
                    case '3':  //받침=e, 완성, 초성=Bfr
                        append(output, combine(st, md, md2, '0', (char)0));
                        st = lt;
                        md = c;
                        md2 = lt = lt2 = 0;
                        break;
                    case '4': lt = c; lt2 = 0; break;   //받침 = 입력
                    case '5': lt2 = c; break;   //받침 += 입력
                    case '6':case '7':  //받침=Bfr(완성) -> 0 / 받침=Bfr/OBfr+Bfr (완성) -> 0
                        append(output, combine(st, md, md2, lt, lt2));
                        st = c;
                        md = md2 = lt = lt2 = 0;
                        break;
                    case '8': // 받침=e, 완성 -> 0
                        append(output, combine(st, md, md2, lt, (char)0));
                        st = c;
                        md = md2 = lt = lt2 = 0;
                        break;
                    case '9':   // 받침=e,초성=Bfr/받침=OBfr,초성=Bfr
                        if(lt2 > 0) {
                            append(output, combine(st, md, md2, lt, (char)0));
                            st = lt2;
                        }else {
                            append(output, combine(st, md, md2, '0', (char)0));
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
        append(output, combine(st, md, md2, lt, lt2));
        return output.toString();
    }

    public String getOutput_chosung(HMealy hMealy, String x){
        HState q = hMealy.q0;
        int n = x.length();
        StringBuilder output = new StringBuilder();
        Letter l0 = new Letter(), l1 = new Letter();

        Pair<HState, Character> pair;
        for(int i = 0; i < n; i++){
            char c = x.charAt(i);
            pair = new Pair<>(q, c);
            if(c == '<'){
                if(!l1.isEmpty()){
                    l1.deleteLast();
                }else if(!l0.deleteLast()){
                    output.deleteCharAt(output.length()-1);
                }
            }else if(hMealy.T.containsKey(pair)){
                q = hMealy.T.get(pair);
                char r = hMealy.L.get(pair);
                if(DEBUG_MODE) System.out.println(r + "\t:  " + q);
                switch(r){
                    case '0': l0.st = c; break;    //초성 = 입력
                    case '1':
                        if(l0.md > 0){
                            append(output, combine(l0));
                            l0 = l1;
                            l1 = new Letter();
                        }
                        l0.md = c;
                        l0.md2 = 0;
                        break;   //중성 = 입력
                    case '2': l0.md2 = c; break;   //중성 += 입력
                    case '3':  //받침=e, 완성, 초성=Bfr
                        append(output, combine(l0.st, l0.md, l0.md2, '0', (char)0));
                        l0 = l1;
                        l1 = new Letter();
                        //l0.st = l0.lt;
                        l0.md = c;
                        l0.md2 = l0.lt = l0.lt2 = 0;
                        break;
                    case '4': l1.st = c; break;   //초성2 = 입력
                    case '5': l0.lt = l1.st; l1.st = c; break;   //받침 += 입력
                    case '6': //받침=Bfr(완성) -> 0
                        if(l0.lt > 0) l0.lt2 = l1.st;
                        else l0.lt = l1.st;
                        l1.st = c;
                        break;
                    case '7':  //받침=Bfr/OBfr+Bfr (완성) -> 0
                        if(l0.lt > 0) l0.lt2 = l1.st;
                        else l0.lt = l1.st;
                        l1.st = c;
                        break;
                    case '8': // 받침=e, 완성 -> 0
                        l1.st = c;
                        break;
                    case '9':   // 받침=e,초성=Bfr/받침=OBfr,초성=Bfr
                        append(output, combine(l0));
                        l0 = l1;
                        l1 = new Letter();
                        l0.md = c;
                        break;
                }
            }else return null;
        }
        if(DEBUG_MODE) {System.out.printf("%s, %s\n", l0.toString(), l1.toString()); }
        append(output, combine(l0));
        append(output, combine(l1));
        return output.toString();
    }

    private boolean append(StringBuilder sb, char c){
        if(c > 0){
            sb.append(c);
            return true;
        }else{
            return false;
        }
    }

    public char combine(Letter l){
        return combine(l.st, l.md, l.md2, l.lt, l.lt2);
    }

    public char combine(char st, char md, char md2, char lt, char lt2){
        int f, m, l;
        try {
            if(st == 0) return 0;
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

    class Letter {
        char st=0, md=0, md2=0, lt=0, lt2=0;

        public boolean isEmpty(){
            return (st == 0);
        }

        public boolean deleteLast(){
            if(lt2 > 0) lt2 = 0;
            else if(lt > 0) lt = 0;
            else if(md2 > 0) md2 = 0;
            else if(md > 0) md = 0;
            else if(st > 0) st = 0;
            else {
                return false;
            }
            return true;
        }

        @Override
        public String toString(){
            return String.format("(%c,%c,%c,%c,%c)", st, md, md2, lt, lt2);
        }
    }

}
