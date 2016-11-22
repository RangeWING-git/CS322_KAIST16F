// Generated from ANTLR4/RE.g4 by ANTLR 4.5.3
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link REParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface REVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link REParser#compileUnit}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCompileUnit(REParser.CompileUnitContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Concatenation}
	 * labeled alternative in {@link REParser#e}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitConcatenation(REParser.ConcatenationContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Closure}
	 * labeled alternative in {@link REParser#e}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitClosure(REParser.ClosureContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Symbol}
	 * labeled alternative in {@link REParser#e}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSymbol(REParser.SymbolContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Parenthesize}
	 * labeled alternative in {@link REParser#e}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParenthesize(REParser.ParenthesizeContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Epsilon}
	 * labeled alternative in {@link REParser#e}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEpsilon(REParser.EpsilonContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Union}
	 * labeled alternative in {@link REParser#e}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUnion(REParser.UnionContext ctx);
}