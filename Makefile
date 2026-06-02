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
#   make test               run smoke tests for Milestones 1, 2, and 3
#   make test-m1            run the 5 XPath course example queries
#   make test-m2            run XQuery examples over j_caesar.xml
#   make test-m3            run join optimizer/hash-join smoke tests
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

test: test-m1 test-m2 test-m3

test-m1: classes
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
# Milestone 2: XQuery examples from the course milestone examples.
# ---------------------------------------------------------------------------
test-m2: classes
	@mkdir -p outputs
	@printf '%s\n' \
	  '<result>{' \
	  'for $$a in document("j_caesar.xml")//ACT,' \
	  '    $$sc in $$a//SCENE,' \
	  '    $$sp in $$sc/SPEECH' \
	  'where $$sp/LINE/text() = "Et tu, Brute! Then fall, Caesar."' \
	  'return <who>{$$sp/SPEAKER/text()}</who>,' \
	  '       <when>{<act>{$$a/TITLE/text()}</act>,' \
	  '             <scene>{$$sc/TITLE/text()}</scene>}' \
	  '       </when>' \
	  '}</result>' > outputs/m2_q1.txt
	@printf '%s\n' \
	  '<result>{' \
	  'for $$s in document("j_caesar.xml")//SPEAKER' \
	  'return <speaks>{<who>{$$s/text()}</who>,' \
	  '                for $$a in document("j_caesar.xml")//ACT' \
	  '                where some $$s1 in $$a//SPEAKER satisfies $$s1 eq $$s' \
	  '                return <when>{$$a/TITLE/text()}</when>}' \
	  '       </speaks>' \
	  '}</result>' > outputs/m2_q2.txt
	@echo "=== Milestone 2 example 1 ==="
	java -cp $(CP) main.Main $(TEST_XML) outputs/m2_q1.txt outputs/m2_example1.xml
	@grep -q '<who>CAESAR</who>' outputs/m2_example1.xml
	@grep -q '<act>ACT III</act>' outputs/m2_example1.xml
	@head -20 outputs/m2_example1.xml
	@echo ""
	@echo "=== Milestone 2 example 2 ==="
	java -cp $(CP) main.Main $(TEST_XML) outputs/m2_q2.txt outputs/m2_example2.xml
	@grep -q '<speaks>' outputs/m2_example2.xml
	@wc -l outputs/m2_example2.xml
	@echo ""

# ---------------------------------------------------------------------------
# Milestone 3: join operator + optimizer smoke tests.
# ---------------------------------------------------------------------------
test-m3: classes
	@mkdir -p outputs
	@echo "=== Milestone 3 manual join ==="
	java -cp $(CP) main.Main data/test.xml docs/join_test.xq outputs/m3_join_test.xml
	@grep -q '<joined>' outputs/m3_join_test.xml
	@echo "=== Milestone 3 local filter join ==="
	java -cp $(CP) main.Main data/test.xml docs/local_filter_join_test.xq outputs/m3_local_filter_join_test.xml
	@grep -q '<book id="2">' outputs/m3_local_filter_join_test.xml
	@echo "=== Milestone 3 multi-key join ==="
	java -cp $(CP) main.Main data/test.xml docs/multikey_join_test.xq outputs/m3_multikey_join_test.xml
	@grep -q '<book id="1">' outputs/m3_multikey_join_test.xml
	@grep -q '<book id="2">' outputs/m3_multikey_join_test.xml
	@echo "=== Milestone 3 nested three-group join ==="
	java -cp $(CP) main.Main data/test.xml docs/three_group_join_test.xq outputs/m3_three_group_join_test.xml
	@grep -q '<book id="1">' outputs/m3_three_group_join_test.xml
	@grep -q '<book id="2">' outputs/m3_three_group_join_test.xml
	@echo "=== Milestone 3 note-shaped query ==="
	java -cp $(CP) main.Main data/books_join.xml docs/m3_note_shape_test.xq outputs/m3_note_shape_test.xml
	@grep -q '<triplet>' outputs/m3_note_shape_test.xml
	@wc -l outputs/m3_*.xml
	@echo ""

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

.PHONY: all parser xpath-parser xquery-parser classes run test test-m1 test-m2 test-m3 submission clean
