package cs322.main1;

/**
 * Created by RangeWING on 2016-09-29.
 */
public class PMain1 {
    public static void main(String[] args){
        System.out.printf("\n%c", combine('\u1100', '\u1161', '\u11AB'));
    }
    public static int combine(char first, char mid, char last){
        int f = (int) first - '\u1100';
        int m = (int) mid - '\u1161';
        int l = (int) last - '\u11A7';
        System.out.printf("%X %X %X", (int)first, (int)mid, (int)last);
        return (((f * 21 + m) * 28) + l) + '\uAC00';
    }
}
