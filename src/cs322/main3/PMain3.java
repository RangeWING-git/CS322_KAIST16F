package cs322.main3;

import cs322.common.DFA;
import cs322.common.E_NFA;
import cs322.common.Mealy;
import cs322.main1.*;
import cs322.main2.ENFACreateVisitor;
import cs322.main2.antlr.RELexer;
import cs322.main2.antlr.REParser;
import cs322.main2.tree.BuildASTVisitor;
import cs322.main2.tree.ExpNode;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;

import java.io.IOException;
import java.util.*;

/**
 * Created by RangeWING on 2016-11-21.
 */
public class PMain3 {
    public static void main(String[] args){
        FileHandler_Main3 fileHandler = FileHandler_Main3.createHandler(args);
        Map<String, String> keymap;
        int mode = Hangeul.MODE_BATCHIM;
        GUIManager guiManager;

        if(args.length < 1){
            System.out.println("USAGE: java cs322.main3.PMain3 <mode> <GUI/TUI>");
            System.out.println("mode: 0 = BATCHIM-prior, 1 = CHOSUNG-prior");
            System.out.println("Default mode = 0, TUI\n");
        }else{
            int a = Integer.parseInt(args[0]);
            switch(a){
                case 1: mode = Hangeul.MODE_CHOSUNG; break;
                case 0:default: mode = Hangeul.MODE_BATCHIM; break;
            }
        }

        System.out.println("CS322 16F Project 3");
        System.out.println("by Young-Geol Cho, 20150717, SoC");
        System.out.printf("%s-prior mode executed [%s]\n", (mode == Hangeul.MODE_CHOSUNG ? "CHOSUNG" : "BATCHIM"), (args.length > 1 && args[1].equals("GUI"))?"GUI":"TUI");


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

        try {
            if(!fileHandler.isExistDFA()){
                createDFA(fileHandler);
            }

            DFA dfa = fileHandler.readDFA();
            keymap = fileHandler.readKeyMap();

            //start

            if(args.length > 1 && args[1].toLowerCase().equals("gui")){
                guiManager = new GUIManager();
                guiManager.addKeyListener(hangeul, hMealy, dfa, keymap);
            }else {
                Scanner sc = new Scanner(System.in);

                while (true) {
                    String s = sc.nextLine();
                    if (s.equals("`")) break;
                    String r = key2qwerty(dfa, keymap, s);
                    //System.out.println(r);
                    if (r == null) {
                        System.out.println("Invalid input");
                    } else {
                        String out = hangeul.getOutput(hMealy, r);
                        if (out != null)
                            System.out.println("Output>\t" + hangeul.getOutput(hMealy, r));
                        else System.out.println("Invalid mInput");
                    }

                }

                sc.close();
            }

        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public static void createDFA(FileHandler_Main3 fileHandler) throws IOException{
        String cho = "1+1z+2+2z+2zz+q+w+wz+wzz+a+az+azz+s+sz+1c+2zc+wzc+ac+azc";
        String jung = "3+3z+33+33z+e+ez+ee+eez+x+d+xd+3d+33d+3zd+33zd+e3+e3d+ed+ee3+ee3d+eed";
        String jong = "()+1+1z+1a+1c+2+2z+2zz+2az+2sz+q+q1+qw+qwz+qa+q2zz+qwzz+qsz+w+wz+wzz+wza+a+az+azz+ac+s+sz";
        String RE = String.format("((%s)(%s)(%s))*", cho, jung, jong);

        RELexer lexer = new RELexer(new ANTLRInputStream(RE));
        CommonTokenStream tokenStream = new CommonTokenStream((lexer));
        REParser parser = new REParser(tokenStream);
        REParser.CompileUnitContext ctx = parser.compileUnit();
        ExpNode node = new BuildASTVisitor().visitCompileUnit(ctx);

        E_NFA nfa = new ENFACreateVisitor().visit(node);
        DFA dfa = nfa.subsetConstruction().minimize_table().reduce().simplify();

        fileHandler.writeDFA(dfa.toOutputString());
    }
    public static List<String> stringToStringList(String s){
        char[] chars = s.toCharArray();
        List<String> list = new ArrayList<>(chars.length);
        for(char c : chars){
            list.add(String.valueOf(c));
        }
        return list;
    }
    static int countZC(String str, int idx){
        int i;
        char arr[] = str.toCharArray();
        for(i=idx+1; i<arr.length; i++){
            char c = arr[i];
            if(c != 'z' && c != 'c') break;
        }
        return i - idx;
    }
    public static Set<Character> stringToCharSet(String s){
        char[] chars = s.toCharArray();
        Set<Character> set = new HashSet<>();
        for(char c : chars){
            set.add(c);
        }
        return set;
    }
    static String handleDelete(String str, Mealy mealy){
        List<String> list = stringToStringList(str);
        Stack<String> stack = new Stack<>();

        for(int i=0; i<list.size(); i++){
            String s = list.get(i);
            if(s.equals("<")){
                if(stack.empty()) continue;
                List<String> mList = Mealy.getOutput(mealy, stringToStringList(stackString(stack)));
                int idx = mList.size()-1;
                String t = stack.pop();
                String v = mList.get(idx);
                if(v.equals(PMealy.VOWEL)){
                    while(t.equals("z") || t.equals(stack.peek())){
                        t = stack.pop();
                        v = mList.get(--idx);
                    }
//                    while(mList.get(idx-1).equals(PMealy.VOWEL)){
//                        t = stack.pop();
//                        v = mList.get(--idx);
//                    }
                }else{
                    while(t.equals("c") || t.equals("z")){
                        t = stack.pop();
                        v = mList.get(--idx);
                    }
                }

            }else{
                stack.push(s);
            }
        }

        return stackString(stack);
    }
    static String stackString(Stack<String> stck){
        Stack<String> stack = (Stack<String>)stck.clone();
        StringBuilder sb = new StringBuilder();
        while(!stack.empty()) {
            sb.append(stack.pop());
        }
        return sb.reverse().toString();
    }
    public static String key2qwerty(DFA dfa, Map<String, String> keymap, String s){
        StringBuilder sb, sbr;
        boolean vw = false;
        PMealy mealy = new PMealy(dfa.toMealy());
        s = handleDelete(s, mealy);
        List<String> list = Mealy.getOutput(mealy, stringToStringList(s));
        //System.out.println(list);
        if(list != null){
            sbr = new StringBuilder();
            sb = new StringBuilder();
            for(int i=0; i<s.length(); i++){
                String type = list.get(i);
                int cnt = countZC(s, i);

                String key = keymap.get(sb.toString());
                if(type.equals(PMealy.CONSONANT)){
                    if(vw) {
                        if(key != null) sbr.append(key);
                        sb = new StringBuilder();
                        vw = false;
                    }
                    if(cnt > 1){
                        sbr.append(keymap.get(s.substring(i, i+cnt)));
                        i += (cnt - 1);
                    }else sbr.append(keymap.get(String.valueOf(s.charAt(i))));

                }else{
                    vw = true;
                    sb.append(s.charAt(i));
                }
            }
            if(sb.length() > 0)
                sbr.append(keymap.get(sb.toString()));
            //System.out.println(sbr.toString());
            return sbr.toString();
        }else{
            //System.out.println("invalid");
            return null;
        }
    }
}
