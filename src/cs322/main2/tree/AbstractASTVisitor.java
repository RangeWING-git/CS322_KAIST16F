package cs322.main2.tree;

/**
 * Created by RangeWING on 2016-11-21.
 */
public abstract class AbstractASTVisitor<T> {
    public abstract T visit(ClosureNode node);
    public abstract T visit(SymbolNode node);
    public abstract T visit(ConcatenationNode node);
    public abstract T visit(UnionNode node);
    public abstract T visit(EpsilonNode node);

    public T visit(ExpNode node){
        if(node instanceof ClosureNode) return visit((ClosureNode)node);
        else if(node instanceof EpsilonNode) return visit((EpsilonNode)node);
        else if(node instanceof SymbolNode) return visit((SymbolNode)node);
        else if(node instanceof ConcatenationNode) return visit((ConcatenationNode) node);
        else if(node instanceof UnionNode) return visit((UnionNode)node);
        else return null;
    }
}
