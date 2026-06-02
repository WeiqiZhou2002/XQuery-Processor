grammar XPath;

/*
 * XPath grammar for CSE 232B, Milestone 1.
 *
 * Matches Section 1 of the project note (xpath-semantics.pdf).
 * Each alternative is labeled so that ANTLR generates a dedicated
 * visit<Label>() method for it in XPathBaseVisitor.
 *
 * Keep the labels stable — XPathEngine.java dispatches on them.
 */

// Absolute paths (ap): rule (1) and (2) in the note.
ap  : 'doc' '(' STRING ')' '/'  rp       # apChildren        // doc(fn)/rp
    | 'doc' '(' STRING ')' '//' rp       # apDescendants     // doc(fn)//rp
    | 'document' '(' STRING ')' '/'  rp  # apDocChildren     // document(fn)//rp
    | 'document' '(' STRING ')' '//' rp  # apDocDescendants  // document(fn)//rp
    ;

// Relative paths (rp): rules (3)-(13) in the note.
rp  : tagName                           # rpTagName         // rule (3)
    | '*'                               # rpAllChildren     // rule (4)
    | '.'                               # rpCurrent         // rule (5)
    | '..'                              # rpParent          // rule (6)
    | 'text' '(' ')'                    # rpText            // rule (7)
    | '@' NAME                          # rpAttName         // rule (8)
    | '(' rp ')'                        # rpParens          // rule (9)
    | rp '/'  rp                        # rpSlash           // rule (10)
    | rp '//' rp                        # rpDoubleSlash     // rule (11)
    | rp '[' f ']'                      # rpFilter          // rule (12)
    | rp ',' rp                         # rpComma           // rule (13)
    ;

// Filters (f): rules (14)-(21) in the note.
f   : rp                                # fRp               // rule (14)
    | rp '=' rp                         # fEq               // rule (15) - value equality
    | rp 'eq' rp                        # fEq               // rule (15) - value equality
    | rp '==' rp                        # fIs               // rule (16) - id equality
    | rp 'is' rp                        # fIs               // rule (16) - id equality
    | rp '=' STRING                     # fEqString         // rule (17)
    | '(' f ')'                         # fParens           // rule (18)
    | f 'and' f                         # fAnd              // rule (19)
    | f 'or'  f                         # fOr               // rule (20)
    | 'not' f                           # fNot              // rule (21)
    ;

tagName : NAME ;

NAME    : [a-zA-Z_] [a-zA-Z0-9_.\-]* ;
STRING  : '"' ~'"'* '"'  | '\'' ~'\''* '\'' ;
WS      : [ \t\r\n]+ -> skip ;
