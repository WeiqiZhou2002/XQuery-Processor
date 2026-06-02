package main;

import org.w3c.dom.Node;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Context {
    private final Map<String, List<Node>> bindings;

    public Context() {
        this.bindings = new HashMap<>();
    }

    private Context(Map<String, List<Node>> bindings) {
        this.bindings = new HashMap<>(bindings);
    }

    public Context extend(String varName, List<Node> value) {
        Context next = new Context(this.bindings);
        next.bindings.put(varName, value);
        return next;
    }

    public List<Node> lookup(String varName) {
        if (!bindings.containsKey(varName)) {
            throw new RuntimeException("Variable Not Found: " + varName);
        }
        return bindings.get(varName);
    }
}
