// Generated from C:/Users/range/OneDrive/Study/16F/CS322/Project/IdeaProject/src/cs322/main2/antlr\RE.g4 by ANTLR 4.5.3
package cs322.main2.antlr;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link REParser}.
 */
public interface REListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link REParser#compileUnit}.
	 * @param ctx the parse tree
	 */
	void enterCompileUnit(REParser.CompileUnitContext ctx);
	/**
	 * Exit a parse tree produced by {@link REParser#compileUnit}.
	 * @param ctx the parse tree
	 */
	void exitCompileUnit(REParser.CompileUnitContext ctx);
	/**
	 * Enter a parse tree produced by the {@code Concatenation}
	 * labeled alternative in {@link REParser#e}.
	 * @param ctx the parse tree
	 */
	void enterConcatenation(REParser.ConcatenationContext ctx);
	/**
	 * Exit a parse tree produced by the {@code Concatenation}
	 * labeled alternative in {@link REParser#e}.
	 * @param ctx the parse tree
	 */
	void exitConcatenation(REParser.ConcatenationContext ctx);
	/**
	 * Enter a parse tree produced by the {@code Closure}
	 * labeled alternative in {@link REParser#e}.
	 * @param ctx the parse tree
	 */
	void enterClosure(REParser.ClosureContext ctx);
	/**
	 * Exit a parse tree produced by the {@code Closure}
	 * labeled alternative in {@link REParser#e}.
	 * @param ctx the parse tree
	 */
	void exitClosure(REParser.ClosureContext ctx);
	/**
	 * Enter a parse tree produced by the {@code Symbol}
	 * labeled alternative in {@link REParser#e}.
	 * @param ctx the parse tree
	 */
	void enterSymbol(REParser.SymbolContext ctx);
	/**
	 * Exit a parse tree produced by the {@code Symbol}
	 * labeled alternative in {@link REParser#e}.
	 * @param ctx the parse tree
	 */
	void exitSymbol(REParser.SymbolContext ctx);
	/**
	 * Enter a parse tree produced by the {@code Parenthesize}
	 * labeled alternative in {@link REParser#e}.
	 * @param ctx the parse tree
	 */
	void enterParenthesize(REParser.ParenthesizeContext ctx);
	/**
	 * Exit a parse tree produced by the {@code Parenthesize}
	 * labeled alternative in {@link REParser#e}.
	 * @param ctx the parse tree
	 */
	void exitParenthesize(REParser.ParenthesizeContext ctx);
	/**
	 * Enter a parse tree produced by the {@code Union}
	 * labeled alternative in {@link REParser#e}.
	 * @param ctx the parse tree
	 */
	void enterUnion(REParser.UnionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code Union}
	 * labeled alternative in {@link REParser#e}.
	 * @param ctx the parse tree
	 */
	void exitUnion(REParser.UnionContext ctx);
}