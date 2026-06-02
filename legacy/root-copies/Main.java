package main;

import main.antlr.XPathLexer;
import main.antlr.XPathParser;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.w3c.dom.Node;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Main {

    public static void main(String[] args) throws Exception {
        if (args.length < 3) {
            System.err.println("Usage: java main.Main <xml> <query> <output>");
            System.exit(1);
        }
        String xmlPath    = args[0];
        String queryPath  = args[1];
        String outputPath = args[2];

        String query = new String(Files.readAllBytes(Paths.get(queryPath))).trim();

        CharStream input = CharStreams.fromString(query);
        XPathLexer lexer = new XPathLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        XPathParser parser = new XPathParser(tokens);
        ParseTree tree = parser.ap();

        XPathEngine.docPathOverride = xmlPath;

        XPathEngine engine = new XPathEngine();
        List<Node> result = engine.visit(tree);

        serialize(result, outputPath);
    }

    private static void serialize(List<Node> nodes, String outPath) throws Exception {
        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer transformer = tf.newTransformer();
        transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(outPath))) {
            if (nodes == null || nodes.isEmpty()) return;

            for (Node n : nodes) {
                switch (n.getNodeType()) {
                    case Node.TEXT_NODE:
                        bw.write(n.getTextContent());
                        bw.newLine();
                        break;
                    case Node.ATTRIBUTE_NODE:
                        bw.write("@" + n.getNodeName() + "=\"" + n.getNodeValue() + "\"");
                        bw.newLine();
                        break;
                    case Node.DOCUMENT_NODE:
                        // Serialize the document element rather than a placeholder.
                        StringWriter dsw = new StringWriter();
                        transformer.transform(
                            new DOMSource(((org.w3c.dom.Document) n).getDocumentElement()),
                            new StreamResult(dsw));
                        bw.write(dsw.toString().trim());
                        bw.newLine();
                        break;
                    default:
                        StringWriter sw = new StringWriter();
                        transformer.transform(new DOMSource(n), new StreamResult(sw));
                        bw.write(sw.toString().trim());
                        bw.newLine();
                }
            }
        }
    }
}
