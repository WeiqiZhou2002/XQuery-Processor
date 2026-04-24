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

public class XPathEngine extends XPathBaseVisitor<List<Node>> {

  private List<Node> contextNodes = new ArrayList<>();

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

  private static Document loadXml(String fileName) throws Exception {
    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
    DocumentBuilder builder = factory.newDocumentBuilder();
    Document doc = builder.parse(new File(fileName));
    doc.getDocumentElement().normalize();
    return doc;
  }

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
    return visit(ctx.rp());
  }

  // Rule (10): [[rp1/rp2]]_R(n) = unique(<y | x ← [[rp1]]_R(n), y ← [[rp2]]_R(x)>)
  @Override
  public List<Node> visitRpSlash(XPathParser.RpSlashContext ctx) {
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
    List<Node> save = new ArrayList<>(contextNodes);
    List<Node> left = visit(ctx.rp(0));
    contextNodes = save;
    List<Node> right = visit(ctx.rp(1));
    contextNodes = save;
    List<Node> out = new ArrayList<>(left);
    out.addAll(right);
    return out;
  }

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
