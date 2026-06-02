// Generated from XPath.g4 by ANTLR 4.13.2
package main.antlr;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link XPathParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface XPathVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by the {@code apChildren}
	 * labeled alternative in {@link XPathParser#ap}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitApChildren(XPathParser.ApChildrenContext ctx);
	/**
	 * Visit a parse tree produced by the {@code apDescendants}
	 * labeled alternative in {@link XPathParser#ap}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitApDescendants(XPathParser.ApDescendantsContext ctx);
	/**
	 * Visit a parse tree produced by the {@code apDocChildren}
	 * labeled alternative in {@link XPathParser#ap}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitApDocChildren(XPathParser.ApDocChildrenContext ctx);
	/**
	 * Visit a parse tree produced by the {@code apDocDescendants}
	 * labeled alternative in {@link XPathParser#ap}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitApDocDescendants(XPathParser.ApDocDescendantsContext ctx);
	/**
	 * Visit a parse tree produced by the {@code rpText}
	 * labeled alternative in {@link XPathParser#rp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRpText(XPathParser.RpTextContext ctx);
	/**
	 * Visit a parse tree produced by the {@code rpAllChildren}
	 * labeled alternative in {@link XPathParser#rp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRpAllChildren(XPathParser.RpAllChildrenContext ctx);
	/**
	 * Visit a parse tree produced by the {@code rpAttName}
	 * labeled alternative in {@link XPathParser#rp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRpAttName(XPathParser.RpAttNameContext ctx);
	/**
	 * Visit a parse tree produced by the {@code rpSlash}
	 * labeled alternative in {@link XPathParser#rp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRpSlash(XPathParser.RpSlashContext ctx);
	/**
	 * Visit a parse tree produced by the {@code rpParent}
	 * labeled alternative in {@link XPathParser#rp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRpParent(XPathParser.RpParentContext ctx);
	/**
	 * Visit a parse tree produced by the {@code rpParens}
	 * labeled alternative in {@link XPathParser#rp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRpParens(XPathParser.RpParensContext ctx);
	/**
	 * Visit a parse tree produced by the {@code rpTagName}
	 * labeled alternative in {@link XPathParser#rp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRpTagName(XPathParser.RpTagNameContext ctx);
	/**
	 * Visit a parse tree produced by the {@code rpCurrent}
	 * labeled alternative in {@link XPathParser#rp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRpCurrent(XPathParser.RpCurrentContext ctx);
	/**
	 * Visit a parse tree produced by the {@code rpFilter}
	 * labeled alternative in {@link XPathParser#rp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRpFilter(XPathParser.RpFilterContext ctx);
	/**
	 * Visit a parse tree produced by the {@code rpComma}
	 * labeled alternative in {@link XPathParser#rp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRpComma(XPathParser.RpCommaContext ctx);
	/**
	 * Visit a parse tree produced by the {@code rpDoubleSlash}
	 * labeled alternative in {@link XPathParser#rp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRpDoubleSlash(XPathParser.RpDoubleSlashContext ctx);
	/**
	 * Visit a parse tree produced by the {@code fIs}
	 * labeled alternative in {@link XPathParser#f}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFIs(XPathParser.FIsContext ctx);
	/**
	 * Visit a parse tree produced by the {@code fNot}
	 * labeled alternative in {@link XPathParser#f}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFNot(XPathParser.FNotContext ctx);
	/**
	 * Visit a parse tree produced by the {@code fEq}
	 * labeled alternative in {@link XPathParser#f}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFEq(XPathParser.FEqContext ctx);
	/**
	 * Visit a parse tree produced by the {@code fParens}
	 * labeled alternative in {@link XPathParser#f}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFParens(XPathParser.FParensContext ctx);
	/**
	 * Visit a parse tree produced by the {@code fRp}
	 * labeled alternative in {@link XPathParser#f}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFRp(XPathParser.FRpContext ctx);
	/**
	 * Visit a parse tree produced by the {@code fOr}
	 * labeled alternative in {@link XPathParser#f}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFOr(XPathParser.FOrContext ctx);
	/**
	 * Visit a parse tree produced by the {@code fEqString}
	 * labeled alternative in {@link XPathParser#f}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFEqString(XPathParser.FEqStringContext ctx);
	/**
	 * Visit a parse tree produced by the {@code fAnd}
	 * labeled alternative in {@link XPathParser#f}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFAnd(XPathParser.FAndContext ctx);
	/**
	 * Visit a parse tree produced by {@link XPathParser#tagName}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTagName(XPathParser.TagNameContext ctx);
}