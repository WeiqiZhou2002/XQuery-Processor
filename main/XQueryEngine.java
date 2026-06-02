package main;

import main.antlr.XQueryBaseVisitor;
import main.antlr.XQueryParser;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.swing.text.html.parser.Element;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Milestone 2 — Naïve XQuery evaluator.
 *
 * This class implements the recursive semantics of Section 2 in xpath-semantics.pdf.
 * The note defines two evaluation functions:
 *
 * [[XQ]]_X(C)    : returns list of Nodes  — XQuery expression evaluation
 * [[Cond]]_C(C)  : returns boolean        — condition evaluation
 * 
 * Semantic rule numbers below refer to equations in the note.
 */
public class XQueryEngine extends XQueryBaseVisitor<List<Node>> {
    public static String docPathOverride = null;
    private Context context = new Context();

    private List<Node> contextNodes = new ArrayList<>();

    // Shared document used to create new element / text nodes (rule 23, 29).
    private static final Document RULE_239;
    static {
        try {
            RULE_239 = DocumentBuilderFactory.newInstance()
                                               .newDocumentBuilder()
                                               .newDocument();
        } catch (Exception e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    /* =============================================================
     *  This Part is Copied From XpathEngine.java as rules (1)-(21)
     * ============================================================= */

    private static List<Node> unique(List<Node> nodes) {
        List<Node> out = new ArrayList<>();
        for (Node n : nodes) {
            boolean seen = false;
            for (Node m : out) {
                if (m.isSameNode(n)) { seen = true; break; }
            }
            if (!seen) out.add(n);
        }
        return out;
    }

    private static boolean isKeeper(Node n) {
        if (n.getNodeType() == Node.ELEMENT_NODE) return true;
        if (n.getNodeType() == Node.TEXT_NODE)
            return !n.getTextContent().trim().isEmpty();
        return false;
    }

    // Return n and every descendant of n, in document order.
    private static List<Node> descendantOrSelf(Node n) {
        List<Node> out = new ArrayList<>();
        if (isKeeper(n)) out.add(n);
        NodeList children = n.getChildNodes();
        for (int i = 0; i < children.getLength(); i++)
            out.addAll(descendantOrSelf(children.item(i)));
        return out;
    }

    private static Document loadXml(String fileName) throws Exception {
        if (docPathOverride != null) {
            java.io.File f = new java.io.File(fileName);
            String baseName = f.getName();
            java.io.File overrideDir = new java.io.File(docPathOverride).getParentFile();
            fileName = new java.io.File(overrideDir, baseName).getPath();
        }
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setValidating(false);
        factory.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
        factory.setFeature("http://xml.org/sax/features/external-general-entities", false);
        factory.setFeature("http://xml.org/sax/features/external-parameter-entities", false);
        DocumentBuilder builder = factory.newDocumentBuilder();
        builder.setEntityResolver((publicId, systemId) ->
            new org.xml.sax.InputSource(new java.io.StringReader("")));
        Document doc = builder.parse(new File(fileName));
        doc.getDocumentElement().normalize();
        return doc;
    }

    private static String unquote(String quoted) {
        return quoted.substring(1, quoted.length() - 1);
    }

    private static List<Node> keepChildren(Node n) {
        NodeList cs = n.getChildNodes();
        List<Node> out = new ArrayList<>();
        for (int i = 0; i < cs.getLength(); i++)
            if (isKeeper(cs.item(i))) out.add(cs.item(i));
        return out;
    }

    private static boolean isValueEqual(Node a, Node b) {
        if (a == null || b == null) return a == b;
        if (a.getNodeType() != b.getNodeType()) return false;
        if (a.getNodeType() == Node.TEXT_NODE)
            return a.getNodeValue().equals(b.getNodeValue());
        if (a.getNodeType() == Node.ELEMENT_NODE) {
            if (!a.getNodeName().equals(b.getNodeName())) return false;
            List<Node> ak = keepChildren(a);
            List<Node> bk = keepChildren(b);
            if (ak.size() != bk.size()) return false;
            for (int i = 0; i < ak.size(); i++)
                if (!isValueEqual(ak.get(i), bk.get(i))) return false;
            return true;
        }
        return a.getNodeValue() != null && a.getNodeValue().equals(b.getNodeValue());
    }

    @Override
    public List<Node> visitApChildren(XQueryParser.ApChildrenContext ctx) {
        String fileName = unquote(ctx.STRING().getText());
        try {
        contextNodes = new ArrayList<>();
        Document doc = loadXml(fileName);
        contextNodes.add(doc);
        } catch (Exception e) {
        throw new RuntimeException(e);
        }

        return visit(ctx.rp());
    }

    @Override
    public List<Node> visitApDescendants(XQueryParser.ApDescendantsContext ctx) {
        String fileName = unquote(ctx.STRING().getText());
        try {
        contextNodes = new ArrayList<>();
        Document doc = loadXml(fileName);
        contextNodes.add(doc);
        contextNodes.addAll(descendantOrSelf(doc));
        } catch (Exception e) {
        throw new RuntimeException(e);
        }

        return visit(ctx.rp());
    }

    @Override
    public List<Node> visitApDocChildren(XQueryParser.ApDocChildrenContext ctx) {
        String fileName = unquote(ctx.STRING().getText());
        try {
            contextNodes = new ArrayList<>();
            Document doc = loadXml(fileName);
            contextNodes.add(doc);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return visit(ctx.rp());
    }

    @Override
    public List<Node> visitApDocDescendants(XQueryParser.ApDocDescendantsContext ctx) {
        String fileName = unquote(ctx.STRING().getText());
        try {
            contextNodes = new ArrayList<>();
            Document doc = loadXml(fileName);
            contextNodes.add(doc);
            contextNodes.addAll(descendantOrSelf(doc));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return visit(ctx.rp());
    }
    
    @Override
    public List<Node> visitRpTagName(XQueryParser.RpTagNameContext ctx) {
        List<Node> matchTagName = new ArrayList<>();
        String tag = ctx.tagName().getText();
        for (Node node : contextNodes) {
        NodeList children = node.getChildNodes();
        for (int i = 0; i < children.getLength(); i++) {
            Node child = children.item(i);
            if (child.getNodeType() == Node.ELEMENT_NODE && child.getNodeName().equals(tag))
            matchTagName.add(child);
        }

        }
        return unique(matchTagName);
    }

    @Override
    public List<Node> visitRpAllChildren(XQueryParser.RpAllChildrenContext ctx) {
        List<Node> out = new ArrayList<>();
        for (Node node : contextNodes) {
        NodeList children = node.getChildNodes();
        for (int i = 0; i < children.getLength(); i++) {
            Node child = children.item(i);
            if (child.getNodeType() == Node.ELEMENT_NODE)
            out.add(child);
        }

        }
        return unique(out);
    }

    @Override
    public List<Node> visitRpCurrent(XQueryParser.RpCurrentContext ctx) {
        return contextNodes;
    }

    @Override
    public List<Node> visitRpParent(XQueryParser.RpParentContext ctx) {
        List<Node> out = new ArrayList<>();
        for (Node node : contextNodes) {
        Node parent = node.getParentNode();
        if(parent!=null) out.add(parent);
        }
        return unique(out);
    }

    @Override
    public List<Node> visitRpText(XQueryParser.RpTextContext ctx) {
        List<Node> out = new ArrayList<>();
        for (Node node : contextNodes) {
        NodeList children = node.getChildNodes();
        for(int i=0;i<children.getLength();i++){
            Node n = children.item(i);
            if(isKeeper(n) && n.getNodeType()==Node.TEXT_NODE) out.add(n);
        }
        }
        return unique(out);
    }

    @Override
    public List<Node> visitRpAttName(XQueryParser.RpAttNameContext ctx) {
        String attName = ctx.NAME().getText();
        List<Node> out = new ArrayList<>();
        for (Node node : contextNodes) {
        if(node.getNodeType()==Node.ELEMENT_NODE){
            org.w3c.dom.Element e = (org.w3c.dom.Element) node;
            org.w3c.dom.Attr attr = e.getAttributeNode(attName);
            if (attr != null) out.add(attr);
        }
        }
        return unique(out);
    }

    @Override
    public List<Node> visitRpParens(XQueryParser.RpParensContext ctx) {
        return visit(ctx.rp());
    }

    @Override
    public List<Node> visitRpSlash(XQueryParser.RpSlashContext ctx) {
        List<Node> save = new ArrayList<>(contextNodes);
        List<Node> left = visit(ctx.rp(0));
        contextNodes = left;
        List<Node> right = visit(ctx.rp(1));
        contextNodes =  save;
        return unique(right);
    }

    @Override
    public List<Node> visitRpDoubleSlash(XQueryParser.RpDoubleSlashContext ctx) {
        List<Node> save = new ArrayList<>(contextNodes);
        List<Node> left = visit(ctx.rp(0));
        List<Node> expanded = new ArrayList<>();
        for (Node n : left) {
        expanded.addAll(descendantOrSelf(n));
        }
        contextNodes = unique(expanded);
        List<Node> right = visit(ctx.rp(1));
        contextNodes = save;
        return unique(right);
    }

    @Override
    public List<Node> visitRpFilter(XQueryParser.RpFilterContext ctx) {
        List<Node> save = new ArrayList<>(contextNodes);
        List<Node> candidates = visit(ctx.rp());
        List<Node> out = new ArrayList<>();
        for(Node candidate:candidates){
        contextNodes = new ArrayList<>();
        contextNodes.add(candidate);
        if(evaluateFilter(ctx.f())) out.add(candidate);
        }
        contextNodes = save;
        return unique(out);
    }

    @Override
    public List<Node> visitRpComma(XQueryParser.RpCommaContext ctx) {
        List<Node> save = new ArrayList<>(contextNodes);
        List<Node> left = visit(ctx.rp(0));
        contextNodes = save;
        List<Node> right = visit(ctx.rp(1));
        contextNodes = save;
        List<Node> out = new ArrayList<>(left);
        out.addAll(right);
        return out;
    }

    private boolean evaluateFilter(XQueryParser.FContext ctx) {
        if (ctx instanceof XQueryParser.FRpContext) {
        return !visit(((XQueryParser.FRpContext) ctx).rp()).isEmpty();
        }
        if (ctx instanceof XQueryParser.FEqContext) {
        XQueryParser.FEqContext c = (XQueryParser.FEqContext) ctx;
        List<Node> left = visit(c.rp(0));
        List<Node> right = visit(c.rp(1));
        for(Node l:left){
            for(Node r:right){
            if(isValueEqual(l,r)) return true;
            }
        }
        return false;
        }
        if (ctx instanceof XQueryParser.FIsContext) {
        XQueryParser.FIsContext c = (XQueryParser.FIsContext) ctx;
        List<Node> left = visit(c.rp(0));
        List<Node> right = visit(c.rp(1));
        for(Node l:left){
            for(Node r:right){
            if(l.isSameNode(r)) return true;
            }
        }
        return false;
        }
        if (ctx instanceof XQueryParser.FEqStringContext) {
        XQueryParser.FEqStringContext c = (XQueryParser.FEqStringContext) ctx;
        String s = unquote(c.STRING().getText());
        for (Node x : visit(c.rp())) {
            if (s.equals(x.getTextContent())) return true;
        }
        return false;
        }
        if (ctx instanceof XQueryParser.FParensContext) {
        return evaluateFilter(((XQueryParser.FParensContext) ctx).f());
        }
        if (ctx instanceof XQueryParser.FAndContext) {
        XQueryParser.FAndContext c = (XQueryParser.FAndContext) ctx;
        return evaluateFilter(c.f(0)) && evaluateFilter(c.f(1));
        }
        if (ctx instanceof XQueryParser.FOrContext) {
        XQueryParser.FOrContext c = (XQueryParser.FOrContext) ctx;
        return evaluateFilter(c.f(0)) || evaluateFilter(c.f(1));
        }
        if (ctx instanceof XQueryParser.FNotContext) {
        return !evaluateFilter(((XQueryParser.FNotContext) ctx).f());
        }
        throw new IllegalStateException("Unknown filter AST node: " + ctx.getClass());
    }
    /* ===========================================================
     *  XQuery expressions — rules (22) through (29), (38), (40).
     * =========================================================== */

    // Rule (22): [[Var]]_X(C) = <C(Var)>
    @Override
    public List<Node> visitXqVar(XQueryParser.XqVarContext ctx) {
        return context.lookup(ctx.var().getText());
    }

    // Rule (23): [[StringConstant]]_X(C) = <makeText(s)>
    @Override
    public List<Node> visitXqString(XQueryParser.XqStringContext ctx) {
        return Collections.singletonList(RULE_239.createTextNode(unquote(ctx.STRING().getText())));
    }

    // Rule (24): [[ap]]_X(C) = [[ap]]_A
    @Override
    public List<Node> visitXqAp(XQueryParser.XqApContext ctx) {
        return visit(ctx.ap());
    }

    // Rule (25): [[(XQ)]]_X(C) = [[XQ]]_X(C)
    @Override
    public List<Node> visitXqParens(XQueryParser.XqParensContext ctx) {
        return visit(ctx.xq());
    }

    // Rule (26): [[XQ1, XQ2]]_X(C) = [[XQ1]]_X(C), [[XQ2]]_X(C)
    @Override
    public List<Node> visitXqComma(XQueryParser.XqCommaContext ctx) {
        List<Node> comma = new ArrayList<>(visit(ctx.xq(0)));
        comma.addAll(visit(ctx.xq(1)));
        return comma;
    }

    // Rule (27): [[XQ/rp]]_X(C) = unique(<m | n <- [[XQ]]_X(C), m <- [[rp]]_R(n)>)
    @Override
    public List<Node> visitXqSlash(XQueryParser.XqSlashContext ctx) {
        List<Node> left = visit(ctx.xq());
        List<Node> save = new ArrayList<>(contextNodes);
        contextNodes = left;
        List<Node> result = unique(visit(ctx.rp()));
        contextNodes = save;
        return result;
    }

    // Rule (28): [[XQ//rp]]_X(C) = unique(<m | n <- [[XQ]]_X(C), m <- [[.//rp]]_R(n)>)
    @Override
    public List<Node> visitXqDoubleSlash(XQueryParser.XqDoubleSlashContext ctx) {
        List<Node> left = visit(ctx.xq());
        List<Node> expanded = new ArrayList<>();
        for (Node n : left) expanded.addAll(descendantOrSelf(n));
        List<Node> save = new ArrayList<>(contextNodes);
        contextNodes = unique(expanded);
        List<Node> result = visit(ctx.rp());
        contextNodes = save;
        return unique(result);
    }

    // Rule (29): [[<tag>{XQ}</tag>]]_X(C) = <makeElem(tag, [[XQ]]_X(C))>
    @Override
    public List<Node> visitXqElem(XQueryParser.XqElemContext ctx) {
        String tag = ctx.tagName(0).getText();
        List<Node> children = visit(ctx.xq());
        org.w3c.dom.Element elem = RULE_239.createElement(tag);
        for (Node child : children) {
            Node copy = (child.getOwnerDocument() == RULE_239)
                      ? child.cloneNode(true)
                      : RULE_239.importNode(child, true);
            elem.appendChild(copy);
        }
        return Collections.singletonList(elem);
    }

    // Rule (38): let, $vn := XQn return XQbody
    @Override
    public List<Node> visitXqLet(XQueryParser.XqLetContext ctx) {
        XQueryParser.LetClauseContext lc = ctx.letClause();
        Context c = context;
        List<XQueryParser.VarContext> vars = lc.var();
        List<XQueryParser.XqContext>  xqs = lc.xq();
        for (int i = 0; i < vars.size(); i++) {
            List<Node> val = evaluateXQ(xqs.get(i), c);
            c = c.extend(vars.get(i).getText(), val);
        }
        return evaluateXQ(ctx.retExpr, c);
    }

    // Rule (40): for ... let ... where ... return ...
    @Override
    public List<Node> visitXqFLWR(XQueryParser.XqFLWRContext ctx) {
        XQueryParser.ForClauseContext fc = ctx.forClause();
        XQueryParser.LetClauseContext lc = ctx.letClause(); 
        XQueryParser.CondContext      wc = ctx.cond();      
        XQueryParser.XqContext        rc = ctx.retExpr;   

        List<XQueryParser.VarContext> letVars = (lc != null) ? lc.var() : new ArrayList<>();
        List<XQueryParser.XqContext>  letXqs  = (lc != null) ? lc.xq()  : new ArrayList<>();

        return evalFLWR(fc.var(), fc.xq(), letVars, letXqs, wc, rc, 0, context);
    }

    // Recursive helper for Rule (40)
    private List<Node> evalFLWR(
            List<XQueryParser.VarContext> forVars,
            List<XQueryParser.XqContext>  forXqs,
            List<XQueryParser.VarContext> letVars,
            List<XQueryParser.XqContext>  letXqs,
            XQueryParser.CondContext      whereCond,
            XQueryParser.XqContext        returnExpr,
            int                           forIdx,
            Context                       ctx) {

        if (forIdx == forVars.size()) {
            Context c = ctx;
            for (int i = 0; i < letVars.size(); i++) {
                List<Node> val = evaluateXQ(letXqs.get(i), c);
                c = c.extend(letVars.get(i).getText(), val);
            }
            if (whereCond != null && !evaluateCond(whereCond, c))
                return new ArrayList<>();
            return evaluateXQ(returnExpr, c);
        }

        String     varName = forVars.get(forIdx).getText();
        List<Node> values  = evaluateXQ(forXqs.get(forIdx), ctx);
        List<Node> result  = new ArrayList<>();
        for (Node v : values) {
            Context newCtx = ctx.extend(varName, Collections.singletonList(v));
            result.addAll(evalFLWR(forVars, forXqs, letVars, letXqs,
                                   whereCond, returnExpr, forIdx + 1, newCtx));
        }
        return result;
    }

    /* ===========================================================
     *  Conditions — rules (30) through (37)
     * =========================================================== */

    private boolean evaluateCondition(XQueryParser.CondContext ctx) {
        // Rule (30): XQ1 = XQ2  OR  XQ1 eq XQ2
        if (ctx instanceof XQueryParser.CondEqContext) {
            XQueryParser.CondEqContext c = (XQueryParser.CondEqContext) ctx;
            for (Node l : visit(c.xq(0))) {
                for (Node r : visit(c.xq(1))) {
                    if (isValueEqual(l, r) || l.getTextContent().equals(r.getTextContent())) return true;
                }
            }
            return false;
        }

        // Rule (31): XQ1 == XQ2  OR  XQ1 is XQ2
        if (ctx instanceof XQueryParser.CondIsContext) {
            XQueryParser.CondIsContext c = (XQueryParser.CondIsContext) ctx;
            for (Node l : visit(c.xq(0)))
                for (Node r : visit(c.xq(1)))
                    if (l.isSameNode(r)) return true;
            return false;
        }

        // Rule (32): empty(XQ)
        if (ctx instanceof XQueryParser.CondEmptyContext)
            return visit(((XQueryParser.CondEmptyContext) ctx).xq()).isEmpty();

        // Rule (33): some $v1 in XQ1 ..., $vn in XQn satisfies Cond
        if (ctx instanceof XQueryParser.CondSomeContext) {
            XQueryParser.CondSomeContext c = (XQueryParser.CondSomeContext) ctx;
            return evalSome(c.forClause().var(), c.forClause().xq(), c.cond(), 0, context);
        }

        // Rule (34): Cond
        if (ctx instanceof XQueryParser.CondParensContext)
            return evaluateCondition(((XQueryParser.CondParensContext) ctx).cond());

        // Rule (35): Cond1 and Cond2
        if (ctx instanceof XQueryParser.CondAndContext) {
            XQueryParser.CondAndContext c = (XQueryParser.CondAndContext) ctx;
            return evaluateCondition(c.cond(0)) && evaluateCondition(c.cond(1));
        }

        // Rule (36): Cond1 or Cond2
        if (ctx instanceof XQueryParser.CondOrContext) {
            XQueryParser.CondOrContext c = (XQueryParser.CondOrContext) ctx;
            return evaluateCondition(c.cond(0)) || evaluateCondition(c.cond(1));
        }

        // Rule (37): not Cond
        if (ctx instanceof XQueryParser.CondNotContext)
            return !evaluateCondition(((XQueryParser.CondNotContext) ctx).cond());

        throw new IllegalStateException("Unknown condition AST node: " + ctx.getClass());
    }

    @Override
    public List<Node> visitXqJoin(XQueryParser.XqJoinContext ctx){
      List<Node> leftTuples = visit(ctx.xq(0));
      List<Node> rightTuples = visit(ctx.xq(1));

    List<String> leftAttrs = readAttrList(ctx.attrList(0));
    List<String> rightAttrs = readAttrList(ctx.attrList(1));

    if (leftAttrs.size() != rightAttrs.size()) {
        throw new RuntimeException("Join attribute lists must have the same length.");
    }

    Map<String,List<Node>> hashTable = new HashMap<>();

    for(Node leftTuple: leftTuples){
      String key = makeJoinKey(leftTuple, leftAttrs);
      if(key==null) continue;
      if(!hashTable.containsKey(key)){
        hashTable.put(key, new ArrayList<>());
      }
      hashTable.get(key).add(leftTuple);
    }
    List<Node> result = new ArrayList<>();
    for(Node rightTuple:rightTuples){
      String key = makeJoinKey(rightTuple, rightAttrs);
      if(key==null) continue;
      List<Node> matches = hashTable.get(key);
      if(matches == null) continue;
      for(Node leftTuple:matches){
        result.add(mergeTuples(leftTuple, rightTuple));
      }
    }
    return result;
    }

    // Recursive helper for rule (33)
    private boolean evalSome(
            List<XQueryParser.VarContext>  vars,
            List<XQueryParser.XqContext>   xqs,
            XQueryParser.CondContext       cond,
            int                            idx,
            Context                        ctx) {

        if (idx == vars.size())
            return evaluateCond(cond, ctx);

        String     varName = vars.get(idx).getText();
        List<Node> values  = evaluateXQ(xqs.get(idx), ctx);
        for (Node v : values) {
            Context newCtx = ctx.extend(varName, Collections.singletonList(v));
            if (evalSome(vars, xqs, cond, idx + 1, newCtx)) return true;
        }
        return false;
    }

    // Helper: evaluate an xq node
    private List<Node> evaluateXQ(XQueryParser.XqContext xqCtx, Context c) {
        Context saved = context;
        context = c;
        List<Node> result = visit(xqCtx);
        context = saved;
        return result;
    }

    // Helper: evaluate a cond node
    private boolean evaluateCond(XQueryParser.CondContext condCtx, Context c) {
        Context saved = context;
        context = c;
        boolean result = evaluateCondition(condCtx);
        context = saved;
        return result;
    }

    // Helper:
    private static Node getTupleField(Node tuple, String fieldName){
      NodeList children = tuple.getChildNodes();
        for(int i=0;i<children.getLength();i++){
          Node child = children.item(i);
          if (child.getNodeType() == Node.ELEMENT_NODE && child.getNodeName().equals(fieldName)){
            return child;
          }
        }
        return null;
    }

    private static String makeJoinKey(Node tuple, List<String> attrs){
      StringBuilder sb = new StringBuilder();
      for(String attr:attrs){
        Node field = getTupleField(tuple, attr);
        if(field == null) return null;
        sb.append(field.getTextContent());
        sb.append("\u0001"); // add a separator
      }
      return sb.toString();
    }

    private static Node mergeTuples(Node left, Node right){
      org.w3c.dom.Element result = RULE_239.createElement("tuple");
      NodeList childrenLeft = left.getChildNodes();
      for(int i=0;i<childrenLeft.getLength();i++){
        Node child = childrenLeft.item(i);
        if(child.getNodeType()==Node.ELEMENT_NODE){
          result.appendChild(RULE_239.importNode(child, true));
        }
      }
      NodeList childrenRight = right.getChildNodes();
      for(int i=0;i<childrenRight.getLength();i++){
        Node child = childrenRight.item(i);
        if(child.getNodeType()==Node.ELEMENT_NODE){
          result.appendChild(RULE_239.importNode(child, true));
        }
      }
      return result;

    }

    private static List<String> readAttrList(XQueryParser.AttrListContext ctx){
       List<String> attrs = new ArrayList<>();
       for(int i=0;i<ctx.NAME().size();i++){
        attrs.add(ctx.NAME(i).getText());
       }
       return attrs;
    }

    private static Node copyIntoResultDocument(Node node) {
    if (node.getOwnerDocument() == RULE_239) {
        return node.cloneNode(true);
    }

    return RULE_239.importNode(node, true);
}
}
