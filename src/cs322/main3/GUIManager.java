package cs322.main3;

import cs322.common.DFA;
import cs322.main1.HMealy;
import cs322.main1.Hangeul;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by RangeWING on 2016-10-18.
 */
public class GUIManager {
    private JFrame frame;
    private TextField textField;
    private StringBuilder inputString;
    private Set<Character> allowKey;
    private static final String KEY12 = "123qweasdzxc";

    public GUIManager(){
        init();
    }
    public void init(){
        frame = new JFrame("CS322 16F Project 3 by 20150717");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLayout(new FlowLayout());
        frame.setSize(400, 80);
        textField = new TextField(50);
        frame.add(textField);
        frame.setVisible(true);
        inputString = new StringBuilder();
        textField.setEditable(false);


        allowKey = new HashSet<>();
        for(char c : KEY12.toCharArray()){
            allowKey.add(c);
        }
    }
    public void addKeyListener(Hangeul hangeul, HMealy hMealy, DFA dfa, Map<String, String> keymap){
        textField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if(c == '\b') c = '<';
                if(c == '\n') inputString = new StringBuilder();
                if(allowKey.contains(c) || c == '<') {
                    inputString.append(c);
                }
                try {
                    String in = PMain3.key2qwerty(dfa, keymap, inputString.toString());
                    String out = hangeul.getOutput(hMealy, in);
                    if(out != null)
                        textField.setText(out);
                    else textField.setText("Invalid input. Press enter to clear");
                }catch(Exception ex){
                    textField.setText("Invalid input. Press enter to clear");
                }
                textField.requestFocus();
                textField.setCaretPosition(inputString.length());
                System.out.println(inputString);
            }

            @Override
            public void keyPressed(KeyEvent e) {
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });
    }
}
