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
        List<LocalCondition> locals = extractLocalConditions(flwr.cond(), varToGroup);
        String joinExpr = buildJoinExpression(groups, joins,locals);
        if (joinExpr == null) {
            return originalQuery;
        }
        String rewrittenReturn = rewriteReturnExpr(flwr.retExpr.getText(), bindings);

        String optimized = "for $tuple in " + joinExpr + " return " + rewrittenReturn;
        // System.out.println("optimized query:");
        // System.out.println(optimized);

        // if (joinExpr != null) {
        //     System.out.println("join expression:");
        //     System.out.println(joinExpr);
        // }
        
        // for(int i=0;i<groups.size();i++){
        //     System.out.print("group" + i + ":");
        //     for (Binding b : groups.get(i).bindings) {
        //         System.out.print(b.var + " ");
        //      }
        //      System.out.println("subquery " + i + ":");
        //     System.out.println(buildTupleSubquery(groups.get(i)));
        //     System.out.println();
        // }

        
        return optimized;
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

        for (XQueryParser.CondContext part : flattenAndConditions(cond)) {
        if (!(part instanceof XQueryParser.CondEqContext)) {
            continue;
        }

        XQueryParser.CondEqContext c = (XQueryParser.CondEqContext) part;

        String left = c.xq(0).getText();
        String right = c.xq(1).getText();

        if (!left.startsWith("$") || !right.startsWith("$")) {
            continue;
        }

        Integer leftGroup = varToGroup.get(left);
        Integer rightGroup = varToGroup.get(right);

        if (leftGroup == null || rightGroup == null || leftGroup.equals(rightGroup)) {
            continue;
        }

        joins.add(new JoinCondition(
            leftGroup,
            rightGroup,
            left.substring(1),
            right.substring(1)
        ));
    }
        return joins;
    }

    // private static void collectJoinConditions(XQueryParser.CondContext cond, Map<String,Integer> varToGroup, List<JoinCondition> joins){
    //     if(cond instanceof XQueryParser.CondAndContext){
    //         XQueryParser.CondAndContext c = (XQueryParser.CondAndContext) cond;
    //         collectJoinConditions(c.cond(0), varToGroup, joins);
    //         collectJoinConditions(c.cond(1), varToGroup, joins);
    //         return;
    //     }
    //     if(cond instanceof XQueryParser.CondEqContext){
    //         XQueryParser.CondEqContext c = (XQueryParser.CondEqContext) cond;

    //         String left = c.xq(0).getText();
    //         String right = c.xq(1).getText();

    //         if(!left.startsWith("$") || !right.startsWith("$")) return;

    //         Integer leftGroup = varToGroup.get(left);
    //         Integer rightGroup = varToGroup.get(right);

    //         if(leftGroup == null || rightGroup == null) return;

    //         if(leftGroup.equals(rightGroup)) return;

    //         joins.add(new JoinCondition(leftGroup, rightGroup, left.substring(1), right.substring(1)));
    //     }
    // }

    // helper to preserve original order
    private static void sortGroupByOriginalOrder(
        VariableGroup group,
        List<Binding> allBindings) {

    group.bindings.sort((a, b) ->
        Integer.compare(allBindings.indexOf(a), allBindings.indexOf(b))
    );
}

    private static String buildTupleSubquery(VariableGroup group, List<LocalCondition> locals, int groupIndex){
        StringBuilder sb = new StringBuilder();
        sb.append("(for ");
        for(int i=0;i<group.bindings.size();i++){
            Binding b = group.bindings.get(i);

            if(i>0){
                sb.append(", ");
            }

            sb.append(b.var).append(" in ").append(b.expr);
        }
        List<String> conds = new ArrayList<>();
        for(LocalCondition local:locals){
            if(local.group == groupIndex){
                conds.add(local.condText);
            }
        }
        if(!conds.isEmpty()){
            sb.append(" where ");
            for(int i=0;i<conds.size();i++){
                if(i>0) sb.append(" and ");
                sb.append(conds.get(i));
            }
        }
        sb.append(" return <tuple>{");
        for(int i=0;i<group.bindings.size();i++){
            Binding b = group.bindings.get(i);
            String attr = b.var.substring(1);

            if(i>0){
                sb.append(", ");
            }

            sb.append("<").append(attr).append(">{").append(b.var).append("}</").append(attr).append(">");
        }
        sb.append("}</tuple>)");
        return sb.toString();
    }

    private static class JoinStep {
    int newGroup;
    List<String> currentAttrs = new ArrayList<>();
    List<String> newAttrs = new ArrayList<>();

    JoinStep(int newGroup) {
        this.newGroup = newGroup;
    }
    }

    private static JoinStep findNextJoinStep(Set<Integer> included, List<JoinCondition> joins){
        JoinStep step = null;
        for(JoinCondition j : joins){
            boolean leftIn = included.contains(j.leftGroup);
            boolean rightIn = included.contains(j.rightGroup);

            if(leftIn && !rightIn){
                if(step ==  null) step = new JoinStep(j.rightGroup);

                if(step.newGroup == j.rightGroup){
                    step.currentAttrs.add(j.leftAttr);
                    step.newAttrs.add(j.rightAttr);
                }
            } else if(rightIn && !leftIn){
                if(step ==  null) step = new JoinStep(j.leftGroup);

                if (step.newGroup == j.leftGroup) {
                step.currentAttrs.add(j.rightAttr);
                step.newAttrs.add(j.leftAttr);
                }
            }
        }
        return step;
    }

    private static String buildJoinExpression(
        List<VariableGroup> groups,
        List<JoinCondition> joins, List<LocalCondition> locals) {

    if (groups.size() <= 2 || joins.isEmpty()) {
        return null;
    }
    Set<Integer> included = new HashSet<>();
    int startGroup = joins.get(0).leftGroup;
    included.add(startGroup);

    String currentExpr = buildTupleSubquery(groups.get(startGroup), locals, startGroup);

    while(included.size()<groups.size()){
        JoinStep step = findNextJoinStep(included,joins);
        if(step == null) return null;
        String nextSubquery = buildTupleSubquery(groups.get(step.newGroup), locals, step.newGroup);
        currentExpr = "join(" + currentExpr + ", " 
        + nextSubquery +", " + buildAttrList(step.currentAttrs)+", "
        +buildAttrList(step.newAttrs)+ ")";
        included.add(step.newGroup);
    }
    return currentExpr;

    // JoinCondition first = joins.get(0);

    // int leftGroup = first.leftGroup;
    // int rightGroup = first.rightGroup;

    // List<String> leftAttrs = new ArrayList<>();
    // List<String> rightAttrs = new ArrayList<>();

    // for(JoinCondition j: joins){
    //     if(j.leftGroup == leftGroup && j.rightGroup == rightGroup){
    //         leftAttrs.add(j.leftAttr);
    //         rightAttrs.add(j.rightAttr);
    //     }else if(j.leftGroup==rightGroup && j.rightGroup==leftGroup){
    //         leftAttrs.add(j.rightAttr);
    //         rightAttrs.add(j.leftAttr);
    //     }
    // }

    // String leftSubquery = buildTupleSubquery(groups.get(leftGroup),locals,leftGroup);
    // String rightSubquery = buildTupleSubquery(groups.get(rightGroup),locals,rightGroup);

    // return "join(" +
    //     leftSubquery + ", " +
    //     rightSubquery + ", " +
    //      buildAttrList(leftAttrs) + ", " +
    //      buildAttrList(rightAttrs) +  
    //     ")";
    }   

    private static String buildAttrList(List<String> attrs) {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for(int i=0;i<attrs.size();i++){
            if(i>0){
                sb.append(", ");
            }
            sb.append(attrs.get(i));
        }
        sb.append("]");
        return sb.toString();
    }

    private static String rewriteReturnExpr(String returnExpr, List<Binding> bindings){
        // replace longer variable names first
        List<Binding> ordered = new ArrayList<>(bindings);

        ordered.sort((a, b) -> Integer.compare(b.var.length(), a.var.length()));

        String rewritten = returnExpr;

        for(Binding b:ordered){
            String var = b.var;             //"$b"
            String attr = var.substring(1); // "b";

            String pattern = "\\Q" + var + "\\E(?![a-zA-Z0-9_])";
            String replacement = "(\\$tuple/" + attr + "/*)";

            rewritten = rewritten.replaceAll(pattern, replacement);
        }
        return rewritten;
    }

    private static class LocalCondition {
    int group;
    String condText;

    LocalCondition(int group, String condText) {
        this.group = group;
        this.condText = condText;
    }
    }

    private static List<XQueryParser.CondContext> flattenAndConditions(XQueryParser.CondContext cond){
        List<XQueryParser.CondContext> parts = new ArrayList<>();
        if(cond == null){
            return parts;
        }

        if(cond instanceof XQueryParser.CondAndContext){
            XQueryParser.CondAndContext c = (XQueryParser.CondAndContext) cond;
            parts.addAll(flattenAndConditions(c.cond(0)));
            parts.addAll(flattenAndConditions(c.cond(1)));
        }else{
            parts.add(cond);
        }

        return parts;
    }

    private static String conditionToText(XQueryParser.CondContext part) {
    if (part instanceof XQueryParser.CondEqContext) {
        XQueryParser.CondEqContext c = (XQueryParser.CondEqContext) part;
        String left = c.xq(0).getText();
        String op = c.getChild(1).getText();
        String right = c.xq(1).getText();

        return left + " " + op + " " + right;
    }

    return part.getText();
    }

    private static List<LocalCondition> extractLocalConditions(XQueryParser.CondContext cond,Map<String,Integer> varToGroup){
        List<LocalCondition> locals = new ArrayList<>();
        for(XQueryParser.CondContext part : flattenAndConditions(cond)){
            if(part instanceof XQueryParser.CondEqContext){
                XQueryParser.CondEqContext c = (XQueryParser.CondEqContext) part;
                String left = c.xq(0).getText();
                String right = c.xq(1).getText();

                boolean leftIsVar = left.startsWith("$");
                boolean rightIsVar = right.startsWith("$");

                Integer leftGroup = leftIsVar ? varToGroup.get(left): null;
                Integer rightGroup = rightIsVar ? varToGroup.get(right): null;

                if(leftGroup!=null && rightGroup!=null && leftGroup.equals(rightGroup)){
                    locals.add(new LocalCondition(leftGroup, conditionToText(part)));
                    continue;
                }
                if (leftGroup != null && !rightIsVar) {
                locals.add(new LocalCondition(leftGroup, conditionToText(part)));
                continue;
                }
                if (!leftIsVar && rightGroup != null) {
                locals.add(new LocalCondition(rightGroup, conditionToText(part)));
                }
            }
        }
        return locals;
    }

}