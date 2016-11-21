package cs322.main2;

import cs322.common.DFA;
import cs322.common.E_NFA;
import cs322.main2.antlr.RELexer;
import cs322.main2.antlr.REParser;
import cs322.main2.tree.BuildASTVisitor;
import cs322.main2.tree.ExpNode;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;

import java.io.IOException;

/**
 * Created by RangeWING on 2016-11-21.
 */
public class PMain2 {
    public static void main(String[] args){
        try {
            FileHandler_PMain2 fileHandler = FileHandler_PMain2.createHandler(args);
            String RE = fileHandler.readRE();

            RELexer lexer = new RELexer(new ANTLRInputStream(RE));
            CommonTokenStream tokenStream = new CommonTokenStream((lexer));
            REParser parser = new REParser(tokenStream);
            REParser.CompileUnitContext ctx = parser.compileUnit();
            ExpNode node = new BuildASTVisitor().visitCompileUnit(ctx);

            E_NFA nfa = new ENFACreateVisitor().visit(node);
            DFA dfa = nfa.m_DFA().partialToTotal();

            fileHandler.clearOutput();
            fileHandler.appendToOutput(dfa.toOutputString());

        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
