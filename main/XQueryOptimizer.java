package main;

import main.antlr.XQueryParser;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.ArrayList;
import java.util.List;

public class XQueryOptimizer {

    private static class Binding {
        String var;
        String expr;
        String dependsOn;

        Binding(String var, String expr, String dependsOn) {
            this.var = var;
            this.expr = expr;
            this.dependsOn = dependsOn;
        }
    }

    public static String optimize(String originalQuery, XQueryParser.XqContext tree) {
        if (!(tree instanceof XQueryParser.XqFLWRContext)) {
            return originalQuery;
        }

        XQueryParser.XqFLWRContext flwr = (XQueryParser.XqFLWRContext) tree;

        List<Binding> bindings = extractBindings(flwr.forClause());
        List<VariableGroup> groups = buildVariableGroups(bindings);
        Map<String, Integer> varToGroup = buildVarToGroup(groups);
        List<JoinCondition> joins = extractJoinConditions(flwr.cond(), varToGroup);

        for (JoinCondition j : joins) {
            System.out.println(
            "join condition: group " + j.leftGroup + " ." + j.leftAttr +
            " == group " + j.rightGroup + " ." + j.rightAttr
            );
        }

        for(int i=0;i<groups.size();i++){
            System.out.print("group" + i + ":");
            for (Binding b : groups.get(i).bindings) {
                System.out.print(b.var + " ");
             }
            System.out.println();
        }

        
        return originalQuery;
    }

    private static List<Binding> extractBindings(XQueryParser.ForClauseContext forClause) {
        List<Binding> bindings = new ArrayList<>();

        for (int i = 0; i < forClause.var().size(); i++) {
            String var = forClause.var(i).getText();
            String expr = forClause.xq(i).getText();
            String dependsOn = findDependency(expr);

            bindings.add(new Binding(var, expr, dependsOn));
        }

        return bindings;
    }

    private static String findDependency(String expr) {
        if (!expr.startsWith("$")) {
            return null;
        }

        int slash = expr.indexOf('/');
        if (slash == -1) {
            return expr;
        }

        return expr.substring(0, slash);
    }

    private static class VariableGroup{
        List<Binding> bindings = new ArrayList<>();
    }

    private static List<VariableGroup> buildVariableGroups(List<Binding> bindings){
        Map<String,Binding> byVar = new HashMap<>();

        for(Binding b:bindings){
            byVar.put(b.var,b);

        }
        Map<String,Set<String>> graph = new HashMap<>();
        for(Binding b : bindings){
            graph.putIfAbsent(b.var, new HashSet<>());
            if(b.dependsOn!=null){
                graph.putIfAbsent(b.dependsOn, new HashSet<>());
                graph.get(b.var).add(b.dependsOn);
                graph.get(b.dependsOn).add(b.var);
            }
        }
        List<VariableGroup> groups = new ArrayList<>();
        Set<String> visited = new HashSet<>();

        for(Binding b:bindings){
            if(visited.contains(b.var)) continue;
            VariableGroup group = new VariableGroup();
            collectGroup(b.var, graph, byVar, visited, group);
            groups.add(group);
        }

        return groups;
    }

    // dfs helper
    private static void collectGroup(
        String var,
        Map<String, Set<String>> graph,
        Map<String, Binding> byVar,
        Set<String> visited,
        VariableGroup group) {

    if (visited.contains(var)) {
        return;
    }

    visited.add(var);

    Binding binding = byVar.get(var);
    if (binding != null) {
        group.bindings.add(binding);
    }

    for (String neighbor : graph.getOrDefault(var, new HashSet<>())) {
        collectGroup(neighbor, graph, byVar, visited, group);
    }
    }

    private static class JoinCondition {
    int leftGroup;
    int rightGroup;
    String leftAttr;
    String rightAttr;

        JoinCondition(int leftGroup, int rightGroup, String leftAttr, String rightAttr) {
            this.leftGroup = leftGroup;
            this.rightGroup = rightGroup;
            this.leftAttr = leftAttr;
            this.rightAttr = rightAttr;
        }
    }

    private static Map<String, Integer> buildVarToGroup(List<VariableGroup> groups){
        Map<String, Integer> varToGroup = new HashMap<>();
        for(int i=0;i<groups.size();i++){
            for(Binding b:groups.get(i).bindings){
                varToGroup.put(b.var, i);
            }
        }
        return varToGroup;
    }

    private static List<JoinCondition> extractJoinConditions(XQueryParser.CondContext cond,Map<String,Integer> varToGroup){
        List<JoinCondition> joins = new ArrayList<>();
        if(cond == null){
            return joins;
        }

        collectJoinConditions(cond,varToGroup,joins);
        return joins;
    }

    private static void collectJoinConditions(XQueryParser.CondContext cond, Map<String,Integer> varToGroup, List<JoinCondition> joins){
        if(cond instanceof XQueryParser.CondAndContext){
            XQueryParser.CondAndContext c = (XQueryParser.CondAndContext) cond;
            collectJoinConditions(c.cond(0), varToGroup, joins);
            collectJoinConditions(c.cond(1), varToGroup, joins);
            return;
        }
        if(cond instanceof XQueryParser.CondEqContext){
            XQueryParser.CondEqContext c = (XQueryParser.CondEqContext) cond;

            String left = c.xq(0).getText();
            String right = c.xq(1).getText();

            if(!left.startsWith("$") || !right.startsWith("$")) return;

            Integer leftGroup = varToGroup.get(left);
            Integer rightGroup = varToGroup.get(right);

            if(leftGroup == null || rightGroup == null) return;

            if(leftGroup.equals(rightGroup)) return;

            joins.add(new JoinCondition(leftGroup, rightGroup, left.substring(1), right.substring(1)));
        }
    }
}