# CSE 232B — XQuery Processor
#
# Targets:
#   make                    generate ANTLR parser + compile everything
#   make parser             regenerate XPath and XQuery parsers
#   make xpath-parser       only regenerate parser after editing XPath.g4
#   make xquery-parser      only regenerate parser after editing XQuery.g4
#   make run Q='...' [XML=...] [OUT=...]
#                           run one ad-hoc query against XML (default test.xml),
#                           writing the result to OUT (default out.xml), and print it.
#   make test               run all 5 course example queries against j_caesar.xml
#                           and dump outputs to outputs/out[1-5].xml
#   make submission         build submission.zip in the correct grader layout
#   make clean              delete generated + compiled files + outputs

ANTLR_JAR = tools/antlr-4.13.2-complete.jar
BUILD_DIR = build/classes
CP        = $(BUILD_DIR):$(ANTLR_JAR)
JFLAGS    = -g

# Hand-written sources.
HAND_SRCS = main/Main.java main/Context.java main/XPathEngine.java main/XQueryEngine.java main/XQueryOptimizer.java

# ANTLR-generated sources (live next to the grammars in main/antlr/).
XPATH_GEN_SRCS  = main/antlr/XPathLexer.java \
                  main/antlr/XPathParser.java \
                  main/antlr/XPathVisitor.java \
                  main/antlr/XPathBaseVisitor.java

XQUERY_GEN_SRCS = main/antlr/XQueryLexer.java \
                  main/antlr/XQueryParser.java \
                  main/antlr/XQueryVisitor.java \
                  main/antlr/XQueryBaseVisitor.java

GEN_SRCS = $(XPATH_GEN_SRCS) $(XQUERY_GEN_SRCS)

all: classes

# Regenerate the lexer/parser/visitor from the grammar, emitting
# into package main.antlr so the compiled classes sit at
# main/antlr/*.class alongside their sources.
parser: xpath-parser xquery-parser

xpath-parser: $(XPATH_GEN_SRCS)

xquery-parser: $(XQUERY_GEN_SRCS)

$(XPATH_GEN_SRCS): main/antlr/XPath.g4
	cd main/antlr && java -jar ../../$(ANTLR_JAR) -visitor -no-listener -package main.antlr XPath.g4
	rm -f main/antlr/*.interp main/antlr/*.tokens

$(XQUERY_GEN_SRCS): main/antlr/XQuery.g4 main/antlr/XPath.g4
	cd main/antlr && java -jar ../../$(ANTLR_JAR) -visitor -no-listener -package main.antlr XQuery.g4
	rm -f main/antlr/*.interp main/antlr/*.tokens

# Compile everything into build/classes so generated artifacts stay out of source.
classes: parser
	@mkdir -p $(BUILD_DIR)
	javac $(JFLAGS) -d $(BUILD_DIR) -cp $(ANTLR_JAR) $(HAND_SRCS) $(GEN_SRCS)

# ---------------------------------------------------------------------------
# Ad-hoc single-query runner.
#   make run Q='doc("test.xml")/library/book'
#   make run XML=j_caesar.xml Q='doc("j_caesar.xml")//PERSONA' OUT=out.xml
# ---------------------------------------------------------------------------
XML ?= data/test.xml
Q   ?= doc("data/test.xml")/library/book
OUT ?= outputs/out.xml

run: classes
	@mkdir -p $(dir $(OUT))
	@printf '%s' '$(Q)' > .query.tmp
	java -cp $(CP) main.Main $(XML) .query.tmp $(OUT)
	@rm -f .query.tmp
	@echo "=== $(OUT) ==="
	@cat $(OUT)
	@echo ""

# ---------------------------------------------------------------------------
# Run the 5 example queries from the course page. Expects j_caesar.xml in
# this directory. Outputs land in outputs/out1.xml .. outputs/out5.xml.
# ---------------------------------------------------------------------------
TEST_XML = data/j_caesar.xml

test: classes
	@if [ ! -f $(TEST_XML) ]; then \
	  echo "ERROR: $(TEST_XML) not found in project root."; \
	  echo "Download it from the course page and place it here."; exit 1; \
	fi
	@mkdir -p outputs
	@printf '%s' 'doc("data/j_caesar.xml")//PERSONA'                                                                                           > outputs/q1.txt
	@printf '%s' 'doc("data/j_caesar.xml")//SCENE[SPEECH/SPEAKER/text() = "CAESAR"]'                                                           > outputs/q2.txt
	@printf '%s' 'doc("data/j_caesar.xml")//ACT[SCENE[SPEECH/SPEAKER/text() = "CAESAR" and SPEECH/SPEAKER/text() = "BRUTUS"]]'                 > outputs/q3.txt
	@printf '%s' 'doc("data/j_caesar.xml")//ACT[SCENE[SPEECH/SPEAKER/text() = "CAESAR"][SPEECH/SPEAKER/text() = "BRUTUS"]]'                    > outputs/q4.txt
	@printf '%s' 'doc("data/j_caesar.xml")//ACT[not .//SPEAKER/text() = "CAESAR"]'                                                             > outputs/q5.txt
	@for i in 1 2 3 4 5; do \
	  echo "=== Query $$i: $$(cat outputs/q$$i.txt) ==="; \
	  java -cp $(CP) main.Main $(TEST_XML) outputs/q$$i.txt outputs/out$$i.xml; \
	  echo "--- outputs/out$$i.xml (first 40 lines) ---"; \
	  head -40 outputs/out$$i.xml; \
	  echo ""; \
	done

# ---------------------------------------------------------------------------
# Build submission.zip in the exact layout the grader expects.
#   submission.zip
#   └── main/
#       ├── Main.java
#       ├── XPathEngine.java
#       └── antlr/
#           ├── XPath.g4
#           └── XPath*.java (generated)
# ---------------------------------------------------------------------------
submission: parser
	@mkdir -p dist
	@rm -f dist/submission.zip
	zip -r dist/submission.zip main/ -x "*.interp" "*.tokens" "*.class"
	@echo ""
	@echo "=== submission.zip contents ==="
	@unzip -l dist/submission.zip

clean:
	find . -name "*.class" -delete
	rm -rf build
	rm -f main/antlr/*.interp main/antlr/*.tokens
	rm -f $(GEN_SRCS)
	rm -rf outputs
	rm -f dist/submission.zip .query.tmp

.PHONY: all parser xpath-parser xquery-parser classes run test submission clean
