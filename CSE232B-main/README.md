# CSE 232B — XQuery Processor

Course project: build an XQuery processor from scratch.
This repo currently targets **Milestone 1 (XPath naïve evaluator)**.

## Architecture

```
  XQuery string ──▶ [ANTLR parser] ──▶ AST ─┐
                                            ├─▶ [Engine] ──▶ DOM nodes ──▶ [serializer] ──▶ Output XML
  Input XML doc ──▶ [javax→DOM parser] ─▶ DOM
```

| Piece                     | File(s)                                  | Who writes it                    |
|---------------------------|------------------------------------------|----------------------------------|
| XPath grammar             | `XPath.g4`                               | you (already scaffolded)         |
| XPath parser / lexer / visitor base | `XPathLexer.java`, `XPathParser.java`, `XPathVisitor.java`, `XPathBaseVisitor.java` | ANTLR generates from `XPath.g4` |
| XML → DOM parser          | `javax.xml.parsers`                      | comes with the JDK               |
| **Engine** (the core work)| `XPathEngine.java`                       | **you**                          |
| Driver + serializer       | `Main.java`                              | **you**                          |

## Files in this folder

```
XPath.g4                   — ANTLR grammar (labels each alt with the semantic rule #)
Main.java                  — entry point; wires parser → engine → serializer
XPathEngine.java           — the recursive evaluator; every method is a TODO
test.xml                   — tiny sample XML to sanity-check against
Makefile                   — build targets
antlr-4.13.2-complete.jar  — ANTLR runtime + tool
xpath-semantics.pdf        — course note defining the semantics (rules 1–21)
```

## Build & run

```bash
make                                       # generate parser, compile everything
make run Q='doc("test.xml")/library/book'  # run a query
make clean                                 # wipe generated + compiled files
```

After `make parser`, ANTLR will create `XPath*.java` in this directory.
Those are regenerated every time you edit `XPath.g4`, so don't hand-edit them.

## How to work through Milestone 1

The note `xpath-semantics.pdf` defines three evaluation functions:

| In the note  | In the code                                      |
|--------------|--------------------------------------------------|
| `[[ap]]_A`   | `XPathEngine.visitApChildren` / `visitApDescendants` |
| `[[rp]]_R(n)`| `XPathEngine.visitRp*` methods                   |
| `[[f]]_F(n)` | `XPathEngine.evaluateFilter(...)`                |

Each TODO in `XPathEngine.java` is tagged with the rule number it implements
(e.g. `// Rule (10): [[rp1/rp2]]_R(n) = ...`). Read the rule first, then fill
the stub.

Suggested implementation order:

1. `descendantOrSelf(Node)` helper and `visitApChildren` — get something running end-to-end.
2. `visitRpTagName`, `visitRpAllChildren`, `visitRpCurrent`, `visitRpParent` — the atom paths.
3. `visitRpSlash` — the one-step composition; easy to get subtly wrong, so test early.
4. `visitRpDoubleSlash` and `visitApDescendants`.
5. `visitRpText`, `visitRpAttName`.
6. `visitRpFilter` + all of `evaluateFilter`. Filters are the trickiest part because
   they need a *per-candidate* context node, not the whole list.
7. `visitRpComma`, `visitRpParens`.
8. `Main.serialize`.

## Gotchas the note will punish you for if you skip

- **Context management.** Every `visitRp*` that recurses into sub-rp's must
  save/restore `contextNodes`, or you'll leak state across siblings.
- **`unique()` vs. not.** Rule (10) and (11) explicitly dedupe; rule (13)
  (the comma) does not. Don't paper over this.
- **Value equality is recursive**, not textContent equality. See rule near the
  end of §1 ("two XML nodes n and m are value-equal iff…").
- **Attribute nodes** can't be serialized with a generic `Transformer`. Handle
  them specially in `Main.serialize`.
- **Whitespace text nodes.** Without a DTD/XSD, `DocumentBuilder` keeps every
  bit of whitespace as a `TEXT_NODE`. Decide explicitly whether your `text()`
  filter and descendant walk should keep them.

## Milestone 2 (not yet started)

XQuery proper adds: variables, `let`/`for`/`where`/`return`, element constructors
(`<tag>{...}</tag>`), `some ... satisfies`, `empty()`, and string constants.
When you're ready, we'll add a new grammar (or extend this one) and a second
engine class that maintains a `Context` mapping variable names → node lists.
