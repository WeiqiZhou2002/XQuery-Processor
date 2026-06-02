# CSE 232B - XQuery Processor

This project implements the CSE 232B XML query processor milestones:

- **Milestone 1:** naive XPath evaluation.
- **Milestone 2:** naive XQuery evaluation.
- **Milestone 3:** join-aware XQuery optimization plus hash-join execution.

The command-line driver accepts an XML input path, a query-file path, and an
output path:

```bash
java -cp build/classes:tools/antlr-4.13.2-complete.jar main.Main \
  data/j_caesar.xml \
  outputs/q1.txt \
  outputs/out1.xml
```

## Layout

```text
.
├── main/
│   ├── Main.java                 # driver, parser selection, serialization
│   ├── Context.java              # XQuery variable bindings
│   ├── XPathEngine.java          # Milestone 1 evaluator
│   ├── XQueryEngine.java         # Milestone 2 evaluator + join operator
│   ├── XQueryOptimizer.java      # Milestone 3 join rewriter
│   └── antlr/
│       ├── XPath.g4
│       ├── XQuery.g4
│       └── *Lexer/*Parser/*Visitor.java
├── data/                         # XML inputs used by local tests
├── docs/                         # notes and local test queries
├── outputs/                      # generated local test outputs
├── tools/                        # ANTLR jar
├── dist/                         # submission.zip
└── Makefile
```

## Build

```bash
make
```

This regenerates ANTLR files when needed and compiles all Java sources into
`build/classes`.

Useful parser-only targets:

```bash
make xpath-parser
make xquery-parser
make parser
```

## Run One Query

Use `make run` for quick ad-hoc tests:

```bash
make run XML=data/test.xml Q='document("test.xml")/library/book/title/text()' OUT=outputs/titles.xml
```

Or call the driver directly:

```bash
java -cp build/classes:tools/antlr-4.13.2-complete.jar main.Main \
  data/test.xml \
  docs/join_test.xq \
  outputs/join_test.xml
```

The first argument is the XML file supplied by the tester. Queries may use
`doc("...")` or `document("...")`; the engine resolves those against the XML
argument's directory.

## Tests

Run every milestone smoke test:

```bash
make test
```

Run one milestone at a time:

```bash
make test-m1
make test-m2
make test-m3
```

What they cover:

| Target | Coverage |
| --- | --- |
| `test-m1` | Five XPath queries over `data/j_caesar.xml`. |
| `test-m2` | Course XQuery examples over `data/j_caesar.xml`. |
| `test-m3` | Manual join, local-filter join, multi-key join, nested three-group join, and the note-shaped optimizer query. |

Milestone 3 join output order is not specified by the project note, so tests
check for successful execution and expected content rather than raw tuple order.

## Milestone 3 Notes

`XQueryOptimizer` handles the simplified FLWR shape from the join optimization
note:

- Builds variable dependency groups from the `for` clause.
- Splits `where` conditions into local filters and cross-group join predicates.
- Rewrites eligible queries into nested `join(...)` expressions.
- Falls back to the original query when it cannot safely optimize.

`XQueryEngine` implements `join(left, right, [leftAttrs], [rightAttrs])` with a
hash table over tuple-field text keys. Join inputs and outputs are lists of:

```xml
<tuple>
  <fieldName>...</fieldName>
</tuple>
```

## Submission

Build the grader archive:

```bash
make submission
```

Submit:

```text
dist/submission.zip
```

The archive contains `main/`, including handwritten sources, grammars, and
generated ANTLR Java files. Build artifacts, outputs, and local data files are
not required in the submission zip.

## Clean

```bash
make clean
```

This removes compiled classes, generated parser files, local outputs, and the
submission zip.
