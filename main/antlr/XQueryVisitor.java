// Generated from XQuery.g4 by ANTLR 4.13.2
package main.antlr;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link XQueryParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface XQueryVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by the {@code xqAp}
	 * labeled alternative in {@link XQueryParser#xq}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitXqAp(XQueryParser.XqApContext ctx);
	/**
	 * Visit a parse tree produced by the {@code xqVar}
	 * labeled alternative in {@link XQueryParser#xq}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitXqVar(XQueryParser.XqVarContext ctx);
	/**
	 * Visit a parse tree produced by the {@code xqJoin}
	 * labeled alternative in {@link XQueryParser#xq}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitXqJoin(XQueryParser.XqJoinContext ctx);
	/**
	 * Visit a parse tree produced by the {@code xqFLWR}
	 * labeled alternative in {@link XQueryParser#xq}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitXqFLWR(XQueryParser.XqFLWRContext ctx);
	/**
	 * Visit a parse tree produced by the {@code xqElem}
	 * labeled alternative in {@link XQueryParser#xq}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitXqElem(XQueryParser.XqElemContext ctx);
	/**
	 * Visit a parse tree produced by the {@code xqDoubleSlash}
	 * labeled alternative in {@link XQueryParser#xq}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitXqDoubleSlash(XQueryParser.XqDoubleSlashContext ctx);
	/**
	 * Visit a parse tree produced by the {@code xqSlash}
	 * labeled alternative in {@link XQueryParser#xq}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitXqSlash(XQueryParser.XqSlashContext ctx);
	/**
	 * Visit a parse tree produced by the {@code xqString}
	 * labeled alternative in {@link XQueryParser#xq}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitXqString(XQueryParser.XqStringContext ctx);
	/**
	 * Visit a parse tree produced by the {@code xqLet}
	 * labeled alternative in {@link XQueryParser#xq}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitXqLet(XQueryParser.XqLetContext ctx);
	/**
	 * Visit a parse tree produced by the {@code xqParens}
	 * labeled alternative in {@link XQueryParser#xq}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitXqParens(XQueryParser.XqParensContext ctx);
	/**
	 * Visit a parse tree produced by the {@code xqComma}
	 * labeled alternative in {@link XQueryParser#xq}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitXqComma(XQueryParser.XqCommaContext ctx);
	/**
	 * Visit a parse tree produced by {@link XQueryParser#forClause}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitForClause(XQueryParser.ForClauseContext ctx);
	/**
	 * Visit a parse tree produced by {@link XQueryParser#letClause}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLetClause(XQueryParser.LetClauseContext ctx);
	/**
	 * Visit a parse tree produced by the {@code condEmpty}
	 * labeled alternative in {@link XQueryParser#cond}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCondEmpty(XQueryParser.CondEmptyContext ctx);
	/**
	 * Visit a parse tree produced by the {@code condEq}
	 * labeled alternative in {@link XQueryParser#cond}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCondEq(XQueryParser.CondEqContext ctx);
	/**
	 * Visit a parse tree produced by the {@code condIs}
	 * labeled alternative in {@link XQueryParser#cond}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCondIs(XQueryParser.CondIsContext ctx);
	/**
	 * Visit a parse tree produced by the {@code condSome}
	 * labeled alternative in {@link XQueryParser#cond}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCondSome(XQueryParser.CondSomeContext ctx);
	/**
	 * Visit a parse tree produced by the {@code condParens}
	 * labeled alternative in {@link XQueryParser#cond}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCondParens(XQueryParser.CondParensContext ctx);
	/**
	 * Visit a parse tree produced by the {@code condAnd}
	 * labeled alternative in {@link XQueryParser#cond}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCondAnd(XQueryParser.CondAndContext ctx);
	/**
	 * Visit a parse tree produced by the {@code condOr}
	 * labeled alternative in {@link XQueryParser#cond}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCondOr(XQueryParser.CondOrContext ctx);
	/**
	 * Visit a parse tree produced by the {@code condNot}
	 * labeled alternative in {@link XQueryParser#cond}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCondNot(XQueryParser.CondNotContext ctx);
	/**
	 * Visit a parse tree produced by {@link XQueryParser#tagName}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTagName(XQueryParser.TagNameContext ctx);
	/**
	 * Visit a parse tree produced by {@link XQueryParser#var}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVar(XQueryParser.VarContext ctx);
	/**
	 * Visit a parse tree produced by {@link XQueryParser#attrList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAttrList(XQueryParser.AttrListContext ctx);
	/**
	 * Visit a parse tree produced by the {@code apChildren}
	 * labeled alternative in {@link XQueryParser#ap}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitApChildren(XQueryParser.ApChildrenContext ctx);
	/**
	 * Visit a parse tree produced by the {@code apDescendants}
	 * labeled alternative in {@link XQueryParser#ap}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitApDescendants(XQueryParser.ApDescendantsContext ctx);
	/**
	 * Visit a parse tree produced by the {@code apDocChildren}
	 * labeled alternative in {@link XQueryParser#ap}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitApDocChildren(XQueryParser.ApDocChildrenContext ctx);
	/**
	 * Visit a parse tree produced by the {@code apDocDescendants}
	 * labeled alternative in {@link XQueryParser#ap}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitApDocDescendants(XQueryParser.ApDocDescendantsContext ctx);
	/**
	 * Visit a parse tree produced by the {@code rpText}
	 * labeled alternative in {@link XQueryParser#rp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRpText(XQueryParser.RpTextContext ctx);
	/**
	 * Visit a parse tree produced by the {@code rpAllChildren}
	 * labeled alternative in {@link XQueryParser#rp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRpAllChildren(XQueryParser.RpAllChildrenContext ctx);
	/**
	 * Visit a parse tree produced by the {@code rpAttName}
	 * labeled alternative in {@link XQueryParser#rp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRpAttName(XQueryParser.RpAttNameContext ctx);
	/**
	 * Visit a parse tree produced by the {@code rpSlash}
	 * labeled alternative in {@link XQueryParser#rp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRpSlash(XQueryParser.RpSlashContext ctx);
	/**
	 * Visit a parse tree produced by the {@code rpParent}
	 * labeled alternative in {@link XQueryParser#rp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRpParent(XQueryParser.RpParentContext ctx);
	/**
	 * Visit a parse tree produced by the {@code rpParens}
	 * labeled alternative in {@link XQueryParser#rp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRpParens(XQueryParser.RpParensContext ctx);
	/**
	 * Visit a parse tree produced by the {@code rpTagName}
	 * labeled alternative in {@link XQueryParser#rp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRpTagName(XQueryParser.RpTagNameContext ctx);
	/**
	 * Visit a parse tree produced by the {@code rpCurrent}
	 * labeled alternative in {@link XQueryParser#rp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRpCurrent(XQueryParser.RpCurrentContext ctx);
	/**
	 * Visit a parse tree produced by the {@code rpFilter}
	 * labeled alternative in {@link XQueryParser#rp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRpFilter(XQueryParser.RpFilterContext ctx);
	/**
	 * Visit a parse tree produced by the {@code rpComma}
	 * labeled alternative in {@link XQueryParser#rp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRpComma(XQueryParser.RpCommaContext ctx);
	/**
	 * Visit a parse tree produced by the {@code rpDoubleSlash}
	 * labeled alternative in {@link XQueryParser#rp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRpDoubleSlash(XQueryParser.RpDoubleSlashContext ctx);
	/**
	 * Visit a parse tree produced by the {@code fIs}
	 * labeled alternative in {@link XQueryParser#f}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFIs(XQueryParser.FIsContext ctx);
	/**
	 * Visit a parse tree produced by the {@code fNot}
	 * labeled alternative in {@link XQueryParser#f}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFNot(XQueryParser.FNotContext ctx);
	/**
	 * Visit a parse tree produced by the {@code fEq}
	 * labeled alternative in {@link XQueryParser#f}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFEq(XQueryParser.FEqContext ctx);
	/**
	 * Visit a parse tree produced by the {@code fParens}
	 * labeled alternative in {@link XQueryParser#f}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFParens(XQueryParser.FParensContext ctx);
	/**
	 * Visit a parse tree produced by the {@code fRp}
	 * labeled alternative in {@link XQueryParser#f}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFRp(XQueryParser.FRpContext ctx);
	/**
	 * Visit a parse tree produced by the {@code fOr}
	 * labeled alternative in {@link XQueryParser#f}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFOr(XQueryParser.FOrContext ctx);
	/**
	 * Visit a parse tree produced by the {@code fEqString}
	 * labeled alternative in {@link XQueryParser#f}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFEqString(XQueryParser.FEqStringContext ctx);
	/**
	 * Visit a parse tree produced by the {@code fAnd}
	 * labeled alternative in {@link XQueryParser#f}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFAnd(XQueryParser.FAndContext ctx);
}