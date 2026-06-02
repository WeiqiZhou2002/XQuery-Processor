grammar XQuery;
import XPath; 

/*
 * XQuery grammar for CSE 232B, Milestone 2.
 *
 * Matches Section 2 of xpath-semantics.pdf.
 * Each alternative is labeled so that ANTLR generates a dedicated
 * visit<Label>() method in XQueryBaseVisitor.
 */

// Xquery(Xq): rules (22)-(40)
xq  : var                                                     # xqVar           // rule (22)
    | STRING                                                  # xqString        // rule (23)
    | ap                                                      # xqAp            // rule (24)
    | '(' xq ')'                                              # xqParens        // rule (25)
    | xq ',' xq                                               # xqComma         // rule (26)
    | xq '/'  rp                                              # xqSlash         // rule (27)
    | xq '//' rp                                              # xqDoubleSlash   // rule (28)
    | '<' tagName '>' '{' xq '}' '</' tagName '>'             # xqElem          // rule (29)
    | 'let' letClause 'return' retExpr=xq                     # xqLet           // rule (38)
    | 'for' forClause
      ('let' letClause)?
      ('where' cond)?
      'return' retExpr=xq                                     # xqFLWR          // rule (40)
    ;

forClause : var 'in' xq (',' var 'in' xq)* ;
letClause : var ':=' xq (',' var ':=' xq)* ;

// Conditions(Cond): rules (30)-(37) 
cond: xq '='  xq                                              # condEq          // rule (30)
    | xq 'eq' xq                                              # condEq          // rule (30)
    | xq '==' xq                                              # condIs          // rule (31)
    | xq 'is' xq                                              # condIs          // rule (31)
    | 'empty' '(' xq ')'                                      # condEmpty       // rule (32)
    | 'some' forClause 'satisfies' cond                       # condSome        // rule (33)
    | '(' cond ')'                                            # condParens      // rule (34)
    | cond 'and' cond                                         # condAnd         // rule (35)
    | cond 'or'  cond                                         # condOr          // rule (36)
    | 'not' cond                                              # condNot         // rule (37)
    ;

tagName : NAME ;
var     : VAR ;
VAR    : '$' [a-zA-Z_] [a-zA-Z0-9_]* ;
NAME    : [a-zA-Z_] [a-zA-Z0-9_.\-]* ;
STRING  : '"' ~'"'* '"'  | '\'' ~'\''* '\'' ;
WS      : [ \t\r\n]+ -> skip ;