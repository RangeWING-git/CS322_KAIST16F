package cs322.main2.tree;

import cs322.main2.antlr.REBaseVisitor;
import cs322.main2.antlr.REParser;

/**
 * Created by RangeWING on 2016-11-21.
 */
public class BuildASTVisitor extends REBaseVisitor<ExpNode> {
    @Override
    public ExpNode visitCompileUnit(REParser.CompileUnitContext ctx){
        return visit(ctx.e());
    }
    @Override
    public ExpNode visitConcatenation(REParser.ConcatenationContext ctx) {
        InfixExpNode node = new ConcatenationNode();
        node.left = visit(ctx.e(0));
        node.right = visit(ctx.e(1));
        return node;
    }

    @Override
    public ExpNode visitClosure(REParser.ClosureContext ctx) {
        ClosureNode node = new ClosureNode();
        node.target = visit(ctx.e());
        return node;
    }

    @Override
    public ExpNode visitSymbol(REParser.SymbolContext ctx) {
        return new SymbolNode(ctx.getText().charAt(0));
    }

    @Override
    public ExpNode visitParenthesize(REParser.ParenthesizeContext ctx) {
        return visit(ctx.e());
    }

    @Override
    public ExpNode visitUnion(REParser.UnionContext ctx) {
        InfixExpNode node = new UnionNode();
        node.left = visit(ctx.e(0));
        node.right = visit(ctx.e(1));
        return node;
    }

}
