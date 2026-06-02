import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Milestone 1 — Naïve XPath evaluator.
 *
 * This class implements the recursive semantics of Section 1 in xpath-semantics.pdf. The note
 * defines three evaluation functions:
 *
 * [[ap]]_A        : returns list of Nodes       — absolute path evaluation [[rp]]_R(n)     :
 * returns list of Nodes       — relative path eval at node n [[f]]_F(n)      : returns boolean
 * — filter evaluation at node n
 *
 * ANTLR generated a visitor base class (XPathBaseVisitor<T>) where T is the return type of each
 * visit method. Because [[ap]]_A and [[rp]]_R both return lists of nodes, we parametrize the
 * visitor with List<Node>. Filters return boolean, so we handle them in a separate helper method
 * (evaluateFilter).
 *
 * The trick in the recursive definition is the "context node" n. ANTLR doesn't give us a natural
 * way to pass n as an argument to each visit, so we keep it as a private field `contextNodes` and
 * save/restore it around recursive calls.
 *
 * Semantic rule numbers below refer to equations in the note.
 */
public class XPathEngine extends XPathBaseVisitor<List<Node>> {

  /**
   * The current list of context nodes — i.e. the nodes at which we are currently evaluating a
   * relative path or filter. This mirrors the `n` argument of [[rp]]_R(n) and [[f]]_F(n) from the
   * note, but lifted to a list so we can evaluate at many nodes in parallel.
   *
   * Convention: it starts empty; visitApChildren / visitApDescendants initialize it; visitor
   * methods read it, compute a new list, and return the new list WITHOUT mutating
   * `contextNodes` in
   * place — that responsibility belongs to whoever called them (e.g. visitRpSlash).
   */
  public static String docPathOverride = null;
  private List<Node> contextNodes = new ArrayList<>();

  /* ===========================================================
   *  Helpers — free to use/extend as you see fit.
   * =========================================================== */

  /**
   * Remove duplicate nodes by identity (same Node reference). The note defines `unique(l)` as
   * first-occurrence deduplication.
   */
  private static List<Node> unique(List<Node> nodes) {
    List<Node> out = new ArrayList<>();
    for (Node n : nodes) {
      boolean seen = false;
      for (Node m : out) {
        if (m.isSameNode(n)) {
          seen = true;
          break;
        }
      }
      if (!seen)
        out.add(n);
    }
    return out;
  }

  private static boolean isKeeper(Node n) {
    if (n.getNodeType() == Node.ELEMENT_NODE)
      return true;
    if (n.getNodeType() == Node.TEXT_NODE)
      return !n.getTextContent().trim().isEmpty();
    return false;
  }

  /**
   * Return n and every descendant of n, in document order. Useful for `//` (double-slash) and
   * `descendant-or-self`.
   *
   */
  private static List<Node> descendantOrSelf(Node n) {
    List<Node> out = new ArrayList<>();
    NodeList children = n.getChildNodes();
    if (isKeeper(n))
      out.add(n);
    for (int i = 0; i < children.getLength(); i++) {
      Node child = children.item(i);
      out.addAll(descendantOrSelf(child));
    }
    return out;
  }

  /**
   * Parse an XML file into a DOM Document.
   */
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

  /**
   * Strip surrounding quotes from an ANTLR STRING token, e.g. "foo" → foo.
   */
  private static String unquote(String quoted) {
    return quoted.substring(1, quoted.length() - 1);
  }

  /* ===========================================================
   *  Absolute paths — rules (1), (2).
   * =========================================================== */

  // Rule (1): [[doc(fn)/rp]]_A  =  [[rp]]_R(root(fn))
  @Override
  public List<Node> visitApChildren(XPathParser.ApChildrenContext ctx) {
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

  // Rule (2): [[doc(fn)//rp]]_A  =  [[.//rp]]_R(root(fn))
  @Override
  public List<Node> visitApDescendants(XPathParser.ApDescendantsContext ctx) {
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

  /* ===========================================================
   *  Relative paths — rules (3) through (13).
   * =========================================================== */

  // Rule (3): [[tagName]]_R(n) = children of n whose tag equals tagName.
  @Override
  public List<Node> visitRpTagName(XPathParser.RpTagNameContext ctx) {
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

  // Rule (4): [[*]]_R(n) = children(n).
  @Override
  public List<Node> visitRpAllChildren(XPathParser.RpAllChildrenContext ctx) {
    // TODO: return all element children of every node in contextNodes.
    //       (The note's data model only includes element and text nodes;
    //       your call whether `*` selects only elements or also text.)
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

  // Rule (5): [[.]]_R(n) = <n>
  @Override
  public List<Node> visitRpCurrent(XPathParser.RpCurrentContext ctx) {
    return contextNodes;
  }

  // Rule (6): [[..]]_R(n) = parent(n) (empty list if n has no parent).
  @Override
  public List<Node> visitRpParent(XPathParser.RpParentContext ctx) {
    // TODO: collect parent of each context node, skip null parents,
    //       run through unique().
    List<Node> out = new ArrayList<>();
    for (Node node : contextNodes) {
      Node parent = node.getParentNode();
      if(parent!=null) out.add(parent);
    }
    return unique(out);
  }

  // Rule (7): [[text()]]_R(n) = txt(n), the text node(s) of n.
  @Override
  public List<Node> visitRpText(XPathParser.RpTextContext ctx) {
    // TODO: for each context node, collect its direct TEXT_NODE children.
    //       Consider whether to skip whitespace-only text nodes.
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

  // Rule (8): [[@attName]]_R(n) = attrib(n, attName).
  @Override
  public List<Node> visitRpAttName(XPathParser.RpAttNameContext ctx) {
    // TODO: for each context element, return its named Attr node if
    //       present. Attr nodes are nodes too but serialize specially —
    //       handle that in Main.serialize().
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

  // Rule (9): [[(rp)]]_R(n) = [[rp]]_R(n).
  @Override
  public List<Node> visitRpParens(XPathParser.RpParensContext ctx) {
    // TODO: just delegate to the inner rp.
    return visit(ctx.rp());
  }

  // Rule (10): [[rp1/rp2]]_R(n) = unique(<y | x ← [[rp1]]_R(n), y ← [[rp2]]_R(x)>)
  @Override
  public List<Node> visitRpSlash(XPathParser.RpSlashContext ctx) {
    // TODO:
    //   1. save the current contextNodes.
    //   2. visit ctx.rp(0) to get `leftNodes`.
    //   3. set contextNodes = leftNodes, then visit ctx.rp(1) to get `rightNodes`.
    //   4. restore the saved contextNodes (so sibling calls aren't polluted).
    //   5. return unique(rightNodes).
    List<Node> save = new ArrayList<>(contextNodes);
    List<Node> left = visit(ctx.rp(0));
    contextNodes = left;
    List<Node> right = visit(ctx.rp(1));
    contextNodes =  save;
    return unique(right);
  }

  // Rule (11): [[rp1//rp2]]_R(n) = unique([[rp1/rp2]]_R(n), [[rp1/*//rp2]]_R(n))
  //
  // Equivalent formulation that's easier to implement:
  //   evaluate rp1 at contextNodes, then for each result take
  //   descendant-or-self, then evaluate rp2 against that set.
  @Override
  public List<Node> visitRpDoubleSlash(XPathParser.RpDoubleSlashContext ctx) {
    // TODO: implement the descendant-or-self shortcut described above.
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

  // Rule (12): [[rp[f]]]_R(n) = <x | x ← [[rp]]_R(n), [[f]]_F(x)>
  @Override
  public List<Node> visitRpFilter(XPathParser.RpFilterContext ctx) {
    // TODO:
    //   1. save contextNodes.
    //   2. evaluate ctx.rp() to get candidate list.
    //   3. for each candidate x, temporarily set contextNodes = [x]
    //      and call evaluateFilter(ctx.f()). Keep x iff filter is true.
    //   4. restore contextNodes and return the kept list.
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

  // Rule (13): [[rp1, rp2]]_R(n) = [[rp1]]_R(n), [[rp2]]_R(n)   (concatenation; NOT unique'd)
  @Override
  public List<Node> visitRpComma(XPathParser.RpCommaContext ctx) {
    // TODO: evaluate both sides against the SAME contextNodes and
    //       concatenate. Note: the note does not unique() this —
    //       read rule (13) carefully.
    List<Node> save = new ArrayList<>(contextNodes);
    List<Node> left = visit(ctx.rp(0));
    contextNodes = save;
    List<Node> right = visit(ctx.rp(1));
    contextNodes = save;
    List<Node> out = new ArrayList<>(left);
    out.addAll(right);
    return out;
  }

  /* ===========================================================
   *  Filters — rules (14) through (21).
   *
   *  These return boolean, not List<Node>, so we don't use ANTLR's
   *  dispatch. We walk the filter AST manually via instanceof.
   * =========================================================== */

  /**
   * Evaluate filter `f` at the *current* contextNodes. The note defines filters as [[f]]_F(n)
   * for a
   * single node n — but at the call site (visitRpFilter) we already set contextNodes to [x]
   * for the
   * one node we're testing, so here we can assume contextNodes has one element.
   */
  private boolean evaluateFilter(XPathParser.FContext ctx) {
    // Rule (14): [[rp]]_F(n) = rp evaluated at n is non-empty.
    if (ctx instanceof XPathParser.FRpContext) {
      return !visit(((XPathParser.FRpContext) ctx).rp()).isEmpty();
    }
    // Rule (15): rp1 = rp2  OR  rp1 eq rp2  — value equality (any pair).
    if (ctx instanceof XPathParser.FEqContext) {
      XPathParser.FEqContext c = (XPathParser.FEqContext) ctx;
      List<Node> left = visit(c.rp(0));
      List<Node> right = visit(c.rp(1));
      for(Node l:left){
        for(Node r:right){
          if(isValueEqual(l,r)) return true;
        }
      }
      return false;
    }
    // Rule (16): rp1 == rp2  OR  rp1 is rp2 — identity equality (any pair).
    if (ctx instanceof XPathParser.FIsContext) {
      XPathParser.FIsContext c = (XPathParser.FIsContext) ctx;
      List<Node> left = visit(c.rp(0));
      List<Node> right = visit(c.rp(1));
      for(Node l:left){
        for(Node r:right){
          if(l.isSameNode(r)) return true;
        }
      }
      return false;
    }
    // Rule (17): rp = "stringConstant" — some x in rp has text == string.
    if (ctx instanceof XPathParser.FEqStringContext) {
      XPathParser.FEqStringContext c = (XPathParser.FEqStringContext) ctx;
      String s = unquote(c.STRING().getText());
      for (Node x : visit(c.rp())) {
        if (s.equals(x.getTextContent())) return true;
      }
      return false;
    }
    // Rule (18): (f)
    if (ctx instanceof XPathParser.FParensContext) {
      return evaluateFilter(((XPathParser.FParensContext) ctx).f());
    }
    // Rule (19): f1 and f2
    if (ctx instanceof XPathParser.FAndContext) {
      XPathParser.FAndContext c = (XPathParser.FAndContext) ctx;
      return evaluateFilter(c.f(0)) && evaluateFilter(c.f(1));
    }
    // Rule (20): f1 or f2
    if (ctx instanceof XPathParser.FOrContext) {
      XPathParser.FOrContext c = (XPathParser.FOrContext) ctx;
      return evaluateFilter(c.f(0)) || evaluateFilter(c.f(1));
    }
    // Rule (21): not f
    if (ctx instanceof XPathParser.FNotContext) {
      return !evaluateFilter(((XPathParser.FNotContext) ctx).f());
    }
    throw new IllegalStateException("Unknown filter AST node: " + ctx.getClass());
  }

  /**
   * Value equality on two XML nodes per the note: tag(n)  == tag(m) AND text(n) == text(m) AND
   * same
   * number of children AND k-th child of n is value-equal to k-th child of m, for each k.
   *
   * Note: Node.isEqualNode() is close to this but includes attributes and may differ in edge
   * cases
   * — read its Javadoc before relying on it.
   *
   */
  private static boolean isValueEqual(Node a, Node b) {
    if (a == null || b == null) return a == b;
    if (a.getNodeType() != b.getNodeType()) return false;
    if (a.getNodeType() == Node.TEXT_NODE) {
      return a.getNodeValue().equals(b.getNodeValue());
    }
    if (a.getNodeType() == Node.ELEMENT_NODE) {
      if (!a.getNodeName().equals(b.getNodeName()))
        return false;
      List<Node> ak = keepChildren(a);
      List<Node> bk = keepChildren(b);
      if (ak.size() != bk.size()) return false;
      for (int i = 0; i < ak.size(); i++) {
        if (!isValueEqual(ak.get(i), bk.get(i))) return false;
      }
      return true;
    }
    return a.getNodeValue() != null && a.getNodeValue().equals(b.getNodeValue());
  }
  private static List<Node> keepChildren(Node n) {
    NodeList cs = n.getChildNodes();
    List<Node> out = new ArrayList<>();
    for (int i = 0; i < cs.getLength(); i++) {
      Node c = cs.item(i);
      if (isKeeper(c)) out.add(c);
    }
    return out;
  }
}
