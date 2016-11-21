package cs322.main2.tree;

public class SymbolNode extends ExpNode {
    public char v;
    public SymbolNode(char v) {
        super(String.valueOf(v));
        this.v = v;
    }
}
