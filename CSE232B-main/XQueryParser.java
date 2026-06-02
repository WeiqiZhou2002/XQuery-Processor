// Generated from XQuery.g4 by ANTLR 4.13.2
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast", "CheckReturnValue", "this-escape"})
public class XQueryParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.13.2", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, T__7=8, T__8=9, 
		T__9=10, T__10=11, T__11=12, T__12=13, T__13=14, T__14=15, T__15=16, T__16=17, 
		T__17=18, T__18=19, T__19=20, T__20=21, T__21=22, T__22=23, T__23=24, 
		T__24=25, T__25=26, T__26=27, T__27=28, T__28=29, T__29=30, T__30=31, 
		T__31=32, T__32=33, T__33=34, T__34=35, VAR=36, NAME=37, STRING=38, WS=39;
	public static final int
		RULE_xq = 0, RULE_forClause = 1, RULE_letClause = 2, RULE_cond = 3, RULE_tagName = 4, 
		RULE_var = 5, RULE_ap = 6, RULE_rp = 7, RULE_f = 8;
	private static String[] makeRuleNames() {
		return new String[] {
			"xq", "forClause", "letClause", "cond", "tagName", "var", "ap", "rp", 
			"f"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'('", "')'", "','", "'/'", "'//'", "'<'", "'>'", "'{'", "'}'", 
			"'</'", "'let'", "'return'", "'for'", "'where'", "'in'", "':='", "'='", 
			"'eq'", "'=='", "'is'", "'empty'", "'some'", "'satisfies'", "'and'", 
			"'or'", "'not'", "'doc'", "'document'", "'*'", "'.'", "'..'", "'text'", 
			"'@'", "'['", "']'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, null, null, null, null, null, null, null, null, null, null, null, 
			"VAR", "NAME", "STRING", "WS"
		};
	}
	private static final String[] _SYMBOLIC_NAMES = makeSymbolicNames();
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}

	@Override
	public String getGrammarFileName() { return "XQuery.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public XQueryParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@SuppressWarnings("CheckReturnValue")
	public static class XqContext extends ParserRuleContext {
		public XqContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_xq; }
	 
		public XqContext() { }
		public void copyFrom(XqContext ctx) {
			super.copyFrom(ctx);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class XqApContext extends XqContext {
		public ApContext ap() {
			return getRuleContext(ApContext.class,0);
		}
		public XqApContext(XqContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof XQueryVisitor ) return ((XQueryVisitor<? extends T>)visitor).visitXqAp(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class XqVarContext extends XqContext {
		public VarContext var() {
			return getRuleContext(VarContext.class,0);
		}
		public XqVarContext(XqContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof XQueryVisitor ) return ((XQueryVisitor<? extends T>)visitor).visitXqVar(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class XqFLWRContext extends XqContext {
		public XqContext retExpr;
		public ForClauseContext forClause() {
			return getRuleContext(ForClauseContext.class,0);
		}
		public XqContext xq() {
			return getRuleContext(XqContext.class,0);
		}
		public LetClauseContext letClause() {
			return getRuleContext(LetClauseContext.class,0);
		}
		public CondContext cond() {
			return getRuleContext(CondContext.class,0);
		}
		public XqFLWRContext(XqContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof XQueryVisitor ) return ((XQueryVisitor<? extends T>)visitor).visitXqFLWR(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class XqElemContext extends XqContext {
		public List<TagNameContext> tagName() {
			return getRuleContexts(TagNameContext.class);
		}
		public TagNameContext tagName(int i) {
			return getRuleContext(TagNameContext.class,i);
		}
		public XqContext xq() {
			return getRuleContext(XqContext.class,0);
		}
		public XqElemContext(XqContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof XQueryVisitor ) return ((XQueryVisitor<? extends T>)visitor).visitXqElem(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class XqDoubleSlashContext extends XqContext {
		public XqContext xq() {
			return getRuleContext(XqContext.class,0);
		}
		public RpContext rp() {
			return getRuleContext(RpContext.class,0);
		}
		public XqDoubleSlashContext(XqContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof XQueryVisitor ) return ((XQueryVisitor<? extends T>)visitor).visitXqDoubleSlash(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class XqSlashContext extends XqContext {
		public XqContext xq() {
			return getRuleContext(XqContext.class,0);
		}
		public RpContext rp() {
			return getRuleContext(RpContext.class,0);
		}
		public XqSlashContext(XqContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof XQueryVisitor ) return ((XQueryVisitor<? extends T>)visitor).visitXqSlash(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class XqStringContext extends XqContext {
		public TerminalNode STRING() { return getToken(XQueryParser.STRING, 0); }
		public XqStringContext(XqContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof XQueryVisitor ) return ((XQueryVisitor<? extends T>)visitor).visitXqString(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class XqLetContext extends XqContext {
		public XqContext retExpr;
		public LetClauseContext letClause() {
			return getRuleContext(LetClauseContext.class,0);
		}
		public XqContext xq() {
			return getRuleContext(XqContext.class,0);
		}
		public XqLetContext(XqContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof XQueryVisitor ) return ((XQueryVisitor<? extends T>)visitor).visitXqLet(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class XqParensContext extends XqContext {
		public XqContext xq() {
			return getRuleContext(XqContext.class,0);
		}
		public XqParensContext(XqContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof XQueryVisitor ) return ((XQueryVisitor<? extends T>)visitor).visitXqParens(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class XqCommaContext extends XqContext {
		public List<XqContext> xq() {
			return getRuleContexts(XqContext.class);
		}
		public XqContext xq(int i) {
			return getRuleContext(XqContext.class,i);
		}
		public XqCommaContext(XqContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof XQueryVisitor ) return ((XQueryVisitor<? extends T>)visitor).visitXqComma(this);
			else return visitor.visitChildren(this);
		}
	}

	public final XqContext xq() throws RecognitionException {
		return xq(0);
	}

	private XqContext xq(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		XqContext _localctx = new XqContext(_ctx, _parentState);
		XqContext _prevctx = _localctx;
		int _startState = 0;
		enterRecursionRule(_localctx, 0, RULE_xq, _p);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(54);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case VAR:
				{
				_localctx = new XqVarContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;

				setState(19);
				var();
				}
				break;
			case STRING:
				{
				_localctx = new XqStringContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(20);
				match(STRING);
				}
				break;
			case T__26:
			case T__27:
				{
				_localctx = new XqApContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(21);
				ap();
				}
				break;
			case T__0:
				{
				_localctx = new XqParensContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(22);
				match(T__0);
				setState(23);
				xq(0);
				setState(24);
				match(T__1);
				}
				break;
			case T__5:
				{
				_localctx = new XqElemContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(26);
				match(T__5);
				setState(27);
				tagName();
				setState(28);
				match(T__6);
				setState(29);
				match(T__7);
				setState(30);
				xq(0);
				setState(31);
				match(T__8);
				setState(32);
				match(T__9);
				setState(33);
				tagName();
				setState(34);
				match(T__6);
				}
				break;
			case T__10:
				{
				_localctx = new XqLetContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(36);
				match(T__10);
				setState(37);
				letClause();
				setState(38);
				match(T__11);
				setState(39);
				((XqLetContext)_localctx).retExpr = xq(2);
				}
				break;
			case T__12:
				{
				_localctx = new XqFLWRContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(41);
				match(T__12);
				setState(42);
				forClause();
				setState(45);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==T__10) {
					{
					setState(43);
					match(T__10);
					setState(44);
					letClause();
					}
				}

				setState(49);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==T__13) {
					{
					setState(47);
					match(T__13);
					setState(48);
					cond(0);
					}
				}

				setState(51);
				match(T__11);
				setState(52);
				((XqFLWRContext)_localctx).retExpr = xq(1);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			_ctx.stop = _input.LT(-1);
			setState(67);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,4,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(65);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,3,_ctx) ) {
					case 1:
						{
						_localctx = new XqCommaContext(new XqContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_xq);
						setState(56);
						if (!(precpred(_ctx, 6))) throw new FailedPredicateException(this, "precpred(_ctx, 6)");
						setState(57);
						match(T__2);
						setState(58);
						xq(7);
						}
						break;
					case 2:
						{
						_localctx = new XqSlashContext(new XqContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_xq);
						setState(59);
						if (!(precpred(_ctx, 5))) throw new FailedPredicateException(this, "precpred(_ctx, 5)");
						setState(60);
						match(T__3);
						setState(61);
						rp(0);
						}
						break;
					case 3:
						{
						_localctx = new XqDoubleSlashContext(new XqContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_xq);
						setState(62);
						if (!(precpred(_ctx, 4))) throw new FailedPredicateException(this, "precpred(_ctx, 4)");
						setState(63);
						match(T__4);
						setState(64);
						rp(0);
						}
						break;
					}
					} 
				}
				setState(69);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,4,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ForClauseContext extends ParserRuleContext {
		public List<VarContext> var() {
			return getRuleContexts(VarContext.class);
		}
		public VarContext var(int i) {
			return getRuleContext(VarContext.class,i);
		}
		public List<XqContext> xq() {
			return getRuleContexts(XqContext.class);
		}
		public XqContext xq(int i) {
			return getRuleContext(XqContext.class,i);
		}
		public ForClauseContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_forClause; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof XQueryVisitor ) return ((XQueryVisitor<? extends T>)visitor).visitForClause(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ForClauseContext forClause() throws RecognitionException {
		ForClauseContext _localctx = new ForClauseContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_forClause);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(70);
			var();
			setState(71);
			match(T__14);
			setState(72);
			xq(0);
			setState(80);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__2) {
				{
				{
				setState(73);
				match(T__2);
				setState(74);
				var();
				setState(75);
				match(T__14);
				setState(76);
				xq(0);
				}
				}
				setState(82);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class LetClauseContext extends ParserRuleContext {
		public List<VarContext> var() {
			return getRuleContexts(VarContext.class);
		}
		public VarContext var(int i) {
			return getRuleContext(VarContext.class,i);
		}
		public List<XqContext> xq() {
			return getRuleContexts(XqContext.class);
		}
		public XqContext xq(int i) {
			return getRuleContext(XqContext.class,i);
		}
		public LetClauseContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_letClause; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof XQueryVisitor ) return ((XQueryVisitor<? extends T>)visitor).visitLetClause(this);
			else return visitor.visitChildren(this);
		}
	}

	public final LetClauseContext letClause() throws RecognitionException {
		LetClauseContext _localctx = new LetClauseContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_letClause);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(83);
			var();
			setState(84);
			match(T__15);
			setState(85);
			xq(0);
			setState(93);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__2) {
				{
				{
				setState(86);
				match(T__2);
				setState(87);
				var();
				setState(88);
				match(T__15);
				setState(89);
				xq(0);
				}
				}
				setState(95);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class CondContext extends ParserRuleContext {
		public CondContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_cond; }
	 
		public CondContext() { }
		public void copyFrom(CondContext ctx) {
			super.copyFrom(ctx);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class CondEmptyContext extends CondContext {
		public XqContext xq() {
			return getRuleContext(XqContext.class,0);
		}
		public CondEmptyContext(CondContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof XQueryVisitor ) return ((XQueryVisitor<? extends T>)visitor).visitCondEmpty(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class CondEqContext extends CondContext {
		public List<XqContext> xq() {
			return getRuleContexts(XqContext.class);
		}
		public XqContext xq(int i) {
			return getRuleContext(XqContext.class,i);
		}
		public CondEqContext(CondContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof XQueryVisitor ) return ((XQueryVisitor<? extends T>)visitor).visitCondEq(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class CondIsContext extends CondContext {
		public List<XqContext> xq() {
			return getRuleContexts(XqContext.class);
		}
		public XqContext xq(int i) {
			return getRuleContext(XqContext.class,i);
		}
		public CondIsContext(CondContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof XQueryVisitor ) return ((XQueryVisitor<? extends T>)visitor).visitCondIs(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class CondSomeContext extends CondContext {
		public ForClauseContext forClause() {
			return getRuleContext(ForClauseContext.class,0);
		}
		public CondContext cond() {
			return getRuleContext(CondContext.class,0);
		}
		public CondSomeContext(CondContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof XQueryVisitor ) return ((XQueryVisitor<? extends T>)visitor).visitCondSome(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class CondParensContext extends CondContext {
		public CondContext cond() {
			return getRuleContext(CondContext.class,0);
		}
		public CondParensContext(CondContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof XQueryVisitor ) return ((XQueryVisitor<? extends T>)visitor).visitCondParens(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class CondAndContext extends CondContext {
		public List<CondContext> cond() {
			return getRuleContexts(CondContext.class);
		}
		public CondContext cond(int i) {
			return getRuleContext(CondContext.class,i);
		}
		public CondAndContext(CondContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof XQueryVisitor ) return ((XQueryVisitor<? extends T>)visitor).visitCondAnd(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class CondOrContext extends CondContext {
		public List<CondContext> cond() {
			return getRuleContexts(CondContext.class);
		}
		public CondContext cond(int i) {
			return getRuleContext(CondContext.class,i);
		}
		public CondOrContext(CondContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof XQueryVisitor ) return ((XQueryVisitor<? extends T>)visitor).visitCondOr(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class CondNotContext extends CondContext {
		public CondContext cond() {
			return getRuleContext(CondContext.class,0);
		}
		public CondNotContext(CondContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof XQueryVisitor ) return ((XQueryVisitor<? extends T>)visitor).visitCondNot(this);
			else return visitor.visitChildren(this);
		}
	}

	public final CondContext cond() throws RecognitionException {
		return cond(0);
	}

	private CondContext cond(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		CondContext _localctx = new CondContext(_ctx, _parentState);
		CondContext _prevctx = _localctx;
		int _startState = 6;
		enterRecursionRule(_localctx, 6, RULE_cond, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(129);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,7,_ctx) ) {
			case 1:
				{
				_localctx = new CondEqContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;

				setState(97);
				xq(0);
				setState(98);
				match(T__16);
				setState(99);
				xq(0);
				}
				break;
			case 2:
				{
				_localctx = new CondEqContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(101);
				xq(0);
				setState(102);
				match(T__17);
				setState(103);
				xq(0);
				}
				break;
			case 3:
				{
				_localctx = new CondIsContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(105);
				xq(0);
				setState(106);
				match(T__18);
				setState(107);
				xq(0);
				}
				break;
			case 4:
				{
				_localctx = new CondIsContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(109);
				xq(0);
				setState(110);
				match(T__19);
				setState(111);
				xq(0);
				}
				break;
			case 5:
				{
				_localctx = new CondEmptyContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(113);
				match(T__20);
				setState(114);
				match(T__0);
				setState(115);
				xq(0);
				setState(116);
				match(T__1);
				}
				break;
			case 6:
				{
				_localctx = new CondSomeContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(118);
				match(T__21);
				setState(119);
				forClause();
				setState(120);
				match(T__22);
				setState(121);
				cond(5);
				}
				break;
			case 7:
				{
				_localctx = new CondParensContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(123);
				match(T__0);
				setState(124);
				cond(0);
				setState(125);
				match(T__1);
				}
				break;
			case 8:
				{
				_localctx = new CondNotContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(127);
				match(T__25);
				setState(128);
				cond(1);
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(139);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,9,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(137);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,8,_ctx) ) {
					case 1:
						{
						_localctx = new CondAndContext(new CondContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_cond);
						setState(131);
						if (!(precpred(_ctx, 3))) throw new FailedPredicateException(this, "precpred(_ctx, 3)");
						setState(132);
						match(T__23);
						setState(133);
						cond(4);
						}
						break;
					case 2:
						{
						_localctx = new CondOrContext(new CondContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_cond);
						setState(134);
						if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
						setState(135);
						match(T__24);
						setState(136);
						cond(3);
						}
						break;
					}
					} 
				}
				setState(141);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,9,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class TagNameContext extends ParserRuleContext {
		public TerminalNode NAME() { return getToken(XQueryParser.NAME, 0); }
		public TagNameContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_tagName; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof XQueryVisitor ) return ((XQueryVisitor<? extends T>)visitor).visitTagName(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TagNameContext tagName() throws RecognitionException {
		TagNameContext _localctx = new TagNameContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_tagName);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(142);
			match(NAME);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class VarContext extends ParserRuleContext {
		public TerminalNode VAR() { return getToken(XQueryParser.VAR, 0); }
		public VarContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_var; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof XQueryVisitor ) return ((XQueryVisitor<? extends T>)visitor).visitVar(this);
			else return visitor.visitChildren(this);
		}
	}

	public final VarContext var() throws RecognitionException {
		VarContext _localctx = new VarContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_var);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(144);
			match(VAR);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ApContext extends ParserRuleContext {
		public ApContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_ap; }
	 
		public ApContext() { }
		public void copyFrom(ApContext ctx) {
			super.copyFrom(ctx);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ApDescendantsContext extends ApContext {
		public TerminalNode STRING() { return getToken(XQueryParser.STRING, 0); }
		public RpContext rp() {
			return getRuleContext(RpContext.class,0);
		}
		public ApDescendantsContext(ApContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof XQueryVisitor ) return ((XQueryVisitor<? extends T>)visitor).visitApDescendants(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ApChildrenContext extends ApContext {
		public TerminalNode STRING() { return getToken(XQueryParser.STRING, 0); }
		public RpContext rp() {
			return getRuleContext(RpContext.class,0);
		}
		public ApChildrenContext(ApContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof XQueryVisitor ) return ((XQueryVisitor<? extends T>)visitor).visitApChildren(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ApDocChildrenContext extends ApContext {
		public TerminalNode STRING() { return getToken(XQueryParser.STRING, 0); }
		public RpContext rp() {
			return getRuleContext(RpContext.class,0);
		}
		public ApDocChildrenContext(ApContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof XQueryVisitor ) return ((XQueryVisitor<? extends T>)visitor).visitApDocChildren(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ApDocDescendantsContext extends ApContext {
		public TerminalNode STRING() { return getToken(XQueryParser.STRING, 0); }
		public RpContext rp() {
			return getRuleContext(RpContext.class,0);
		}
		public ApDocDescendantsContext(ApContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof XQueryVisitor ) return ((XQueryVisitor<? extends T>)visitor).visitApDocDescendants(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ApContext ap() throws RecognitionException {
		ApContext _localctx = new ApContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_ap);
		try {
			setState(170);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,10,_ctx) ) {
			case 1:
				_localctx = new ApChildrenContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(146);
				match(T__26);
				setState(147);
				match(T__0);
				setState(148);
				match(STRING);
				setState(149);
				match(T__1);
				setState(150);
				match(T__3);
				setState(151);
				rp(0);
				}
				break;
			case 2:
				_localctx = new ApDescendantsContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(152);
				match(T__26);
				setState(153);
				match(T__0);
				setState(154);
				match(STRING);
				setState(155);
				match(T__1);
				setState(156);
				match(T__4);
				setState(157);
				rp(0);
				}
				break;
			case 3:
				_localctx = new ApDocChildrenContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(158);
				match(T__27);
				setState(159);
				match(T__0);
				setState(160);
				match(STRING);
				setState(161);
				match(T__1);
				setState(162);
				match(T__3);
				setState(163);
				rp(0);
				}
				break;
			case 4:
				_localctx = new ApDocDescendantsContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(164);
				match(T__27);
				setState(165);
				match(T__0);
				setState(166);
				match(STRING);
				setState(167);
				match(T__1);
				setState(168);
				match(T__4);
				setState(169);
				rp(0);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class RpContext extends ParserRuleContext {
		public RpContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_rp; }
	 
		public RpContext() { }
		public void copyFrom(RpContext ctx) {
			super.copyFrom(ctx);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class RpTextContext extends RpContext {
		public RpTextContext(RpContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof XQueryVisitor ) return ((XQueryVisitor<? extends T>)visitor).visitRpText(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class RpAllChildrenContext extends RpContext {
		public RpAllChildrenContext(RpContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof XQueryVisitor ) return ((XQueryVisitor<? extends T>)visitor).visitRpAllChildren(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class RpAttNameContext extends RpContext {
		public TerminalNode NAME() { return getToken(XQueryParser.NAME, 0); }
		public RpAttNameContext(RpContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof XQueryVisitor ) return ((XQueryVisitor<? extends T>)visitor).visitRpAttName(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class RpSlashContext extends RpContext {
		public List<RpContext> rp() {
			return getRuleContexts(RpContext.class);
		}
		public RpContext rp(int i) {
			return getRuleContext(RpContext.class,i);
		}
		public RpSlashContext(RpContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof XQueryVisitor ) return ((XQueryVisitor<? extends T>)visitor).visitRpSlash(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class RpParentContext extends RpContext {
		public RpParentContext(RpContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof XQueryVisitor ) return ((XQueryVisitor<? extends T>)visitor).visitRpParent(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class RpParensContext extends RpContext {
		public RpContext rp() {
			return getRuleContext(RpContext.class,0);
		}
		public RpParensContext(RpContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof XQueryVisitor ) return ((XQueryVisitor<? extends T>)visitor).visitRpParens(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class RpTagNameContext extends RpContext {
		public TagNameContext tagName() {
			return getRuleContext(TagNameContext.class,0);
		}
		public RpTagNameContext(RpContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof XQueryVisitor ) return ((XQueryVisitor<? extends T>)visitor).visitRpTagName(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class RpCurrentContext extends RpContext {
		public RpCurrentContext(RpContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof XQueryVisitor ) return ((XQueryVisitor<? extends T>)visitor).visitRpCurrent(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class RpFilterContext extends RpContext {
		public RpContext rp() {
			return getRuleContext(RpContext.class,0);
		}
		public FContext f() {
			return getRuleContext(FContext.class,0);
		}
		public RpFilterContext(RpContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof XQueryVisitor ) return ((XQueryVisitor<? extends T>)visitor).visitRpFilter(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class RpCommaContext extends RpContext {
		public List<RpContext> rp() {
			return getRuleContexts(RpContext.class);
		}
		public RpContext rp(int i) {
			return getRuleContext(RpContext.class,i);
		}
		public RpCommaContext(RpContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof XQueryVisitor ) return ((XQueryVisitor<? extends T>)visitor).visitRpComma(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class RpDoubleSlashContext extends RpContext {
		public List<RpContext> rp() {
			return getRuleContexts(RpContext.class);
		}
		public RpContext rp(int i) {
			return getRuleContext(RpContext.class,i);
		}
		public RpDoubleSlashContext(RpContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof XQueryVisitor ) return ((XQueryVisitor<? extends T>)visitor).visitRpDoubleSlash(this);
			else return visitor.visitChildren(this);
		}
	}

	public final RpContext rp() throws RecognitionException {
		return rp(0);
	}

	private RpContext rp(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		RpContext _localctx = new RpContext(_ctx, _parentState);
		RpContext _prevctx = _localctx;
		int _startState = 14;
		enterRecursionRule(_localctx, 14, RULE_rp, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(186);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case NAME:
				{
				_localctx = new RpTagNameContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;

				setState(173);
				tagName();
				}
				break;
			case T__28:
				{
				_localctx = new RpAllChildrenContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(174);
				match(T__28);
				}
				break;
			case T__29:
				{
				_localctx = new RpCurrentContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(175);
				match(T__29);
				}
				break;
			case T__30:
				{
				_localctx = new RpParentContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(176);
				match(T__30);
				}
				break;
			case T__31:
				{
				_localctx = new RpTextContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(177);
				match(T__31);
				setState(178);
				match(T__0);
				setState(179);
				match(T__1);
				}
				break;
			case T__32:
				{
				_localctx = new RpAttNameContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(180);
				match(T__32);
				setState(181);
				match(NAME);
				}
				break;
			case T__0:
				{
				_localctx = new RpParensContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(182);
				match(T__0);
				setState(183);
				rp(0);
				setState(184);
				match(T__1);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			_ctx.stop = _input.LT(-1);
			setState(204);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,13,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(202);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,12,_ctx) ) {
					case 1:
						{
						_localctx = new RpSlashContext(new RpContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_rp);
						setState(188);
						if (!(precpred(_ctx, 4))) throw new FailedPredicateException(this, "precpred(_ctx, 4)");
						setState(189);
						match(T__3);
						setState(190);
						rp(5);
						}
						break;
					case 2:
						{
						_localctx = new RpDoubleSlashContext(new RpContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_rp);
						setState(191);
						if (!(precpred(_ctx, 3))) throw new FailedPredicateException(this, "precpred(_ctx, 3)");
						setState(192);
						match(T__4);
						setState(193);
						rp(4);
						}
						break;
					case 3:
						{
						_localctx = new RpCommaContext(new RpContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_rp);
						setState(194);
						if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
						setState(195);
						match(T__2);
						setState(196);
						rp(2);
						}
						break;
					case 4:
						{
						_localctx = new RpFilterContext(new RpContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_rp);
						setState(197);
						if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
						setState(198);
						match(T__33);
						setState(199);
						f(0);
						setState(200);
						match(T__34);
						}
						break;
					}
					} 
				}
				setState(206);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,13,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class FContext extends ParserRuleContext {
		public FContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_f; }
	 
		public FContext() { }
		public void copyFrom(FContext ctx) {
			super.copyFrom(ctx);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class FIsContext extends FContext {
		public List<RpContext> rp() {
			return getRuleContexts(RpContext.class);
		}
		public RpContext rp(int i) {
			return getRuleContext(RpContext.class,i);
		}
		public FIsContext(FContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof XQueryVisitor ) return ((XQueryVisitor<? extends T>)visitor).visitFIs(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class FNotContext extends FContext {
		public FContext f() {
			return getRuleContext(FContext.class,0);
		}
		public FNotContext(FContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof XQueryVisitor ) return ((XQueryVisitor<? extends T>)visitor).visitFNot(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class FEqContext extends FContext {
		public List<RpContext> rp() {
			return getRuleContexts(RpContext.class);
		}
		public RpContext rp(int i) {
			return getRuleContext(RpContext.class,i);
		}
		public FEqContext(FContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof XQueryVisitor ) return ((XQueryVisitor<? extends T>)visitor).visitFEq(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class FParensContext extends FContext {
		public FContext f() {
			return getRuleContext(FContext.class,0);
		}
		public FParensContext(FContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof XQueryVisitor ) return ((XQueryVisitor<? extends T>)visitor).visitFParens(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class FRpContext extends FContext {
		public RpContext rp() {
			return getRuleContext(RpContext.class,0);
		}
		public FRpContext(FContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof XQueryVisitor ) return ((XQueryVisitor<? extends T>)visitor).visitFRp(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class FOrContext extends FContext {
		public List<FContext> f() {
			return getRuleContexts(FContext.class);
		}
		public FContext f(int i) {
			return getRuleContext(FContext.class,i);
		}
		public FOrContext(FContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof XQueryVisitor ) return ((XQueryVisitor<? extends T>)visitor).visitFOr(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class FEqStringContext extends FContext {
		public RpContext rp() {
			return getRuleContext(RpContext.class,0);
		}
		public TerminalNode STRING() { return getToken(XQueryParser.STRING, 0); }
		public FEqStringContext(FContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof XQueryVisitor ) return ((XQueryVisitor<? extends T>)visitor).visitFEqString(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class FAndContext extends FContext {
		public List<FContext> f() {
			return getRuleContexts(FContext.class);
		}
		public FContext f(int i) {
			return getRuleContext(FContext.class,i);
		}
		public FAndContext(FContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof XQueryVisitor ) return ((XQueryVisitor<? extends T>)visitor).visitFAnd(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FContext f() throws RecognitionException {
		return f(0);
	}

	private FContext f(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		FContext _localctx = new FContext(_ctx, _parentState);
		FContext _prevctx = _localctx;
		int _startState = 16;
		enterRecursionRule(_localctx, 16, RULE_f, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(235);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,14,_ctx) ) {
			case 1:
				{
				_localctx = new FRpContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;

				setState(208);
				rp(0);
				}
				break;
			case 2:
				{
				_localctx = new FEqContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(209);
				rp(0);
				setState(210);
				match(T__16);
				setState(211);
				rp(0);
				}
				break;
			case 3:
				{
				_localctx = new FEqContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(213);
				rp(0);
				setState(214);
				match(T__17);
				setState(215);
				rp(0);
				}
				break;
			case 4:
				{
				_localctx = new FIsContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(217);
				rp(0);
				setState(218);
				match(T__18);
				setState(219);
				rp(0);
				}
				break;
			case 5:
				{
				_localctx = new FIsContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(221);
				rp(0);
				setState(222);
				match(T__19);
				setState(223);
				rp(0);
				}
				break;
			case 6:
				{
				_localctx = new FEqStringContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(225);
				rp(0);
				setState(226);
				match(T__16);
				setState(227);
				match(STRING);
				}
				break;
			case 7:
				{
				_localctx = new FParensContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(229);
				match(T__0);
				setState(230);
				f(0);
				setState(231);
				match(T__1);
				}
				break;
			case 8:
				{
				_localctx = new FNotContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(233);
				match(T__25);
				setState(234);
				f(1);
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(245);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,16,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(243);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,15,_ctx) ) {
					case 1:
						{
						_localctx = new FAndContext(new FContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_f);
						setState(237);
						if (!(precpred(_ctx, 3))) throw new FailedPredicateException(this, "precpred(_ctx, 3)");
						setState(238);
						match(T__23);
						setState(239);
						f(4);
						}
						break;
					case 2:
						{
						_localctx = new FOrContext(new FContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_f);
						setState(240);
						if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
						setState(241);
						match(T__24);
						setState(242);
						f(3);
						}
						break;
					}
					} 
				}
				setState(247);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,16,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public boolean sempred(RuleContext _localctx, int ruleIndex, int predIndex) {
		switch (ruleIndex) {
		case 0:
			return xq_sempred((XqContext)_localctx, predIndex);
		case 3:
			return cond_sempred((CondContext)_localctx, predIndex);
		case 7:
			return rp_sempred((RpContext)_localctx, predIndex);
		case 8:
			return f_sempred((FContext)_localctx, predIndex);
		}
		return true;
	}
	private boolean xq_sempred(XqContext _localctx, int predIndex) {
		switch (predIndex) {
		case 0:
			return precpred(_ctx, 6);
		case 1:
			return precpred(_ctx, 5);
		case 2:
			return precpred(_ctx, 4);
		}
		return true;
	}
	private boolean cond_sempred(CondContext _localctx, int predIndex) {
		switch (predIndex) {
		case 3:
			return precpred(_ctx, 3);
		case 4:
			return precpred(_ctx, 2);
		}
		return true;
	}
	private boolean rp_sempred(RpContext _localctx, int predIndex) {
		switch (predIndex) {
		case 5:
			return precpred(_ctx, 4);
		case 6:
			return precpred(_ctx, 3);
		case 7:
			return precpred(_ctx, 1);
		case 8:
			return precpred(_ctx, 2);
		}
		return true;
	}
	private boolean f_sempred(FContext _localctx, int predIndex) {
		switch (predIndex) {
		case 9:
			return precpred(_ctx, 3);
		case 10:
			return precpred(_ctx, 2);
		}
		return true;
	}

	public static final String _serializedATN =
		"\u0004\u0001\'\u00f9\u0002\u0000\u0007\u0000\u0002\u0001\u0007\u0001\u0002"+
		"\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0002\u0004\u0007\u0004\u0002"+
		"\u0005\u0007\u0005\u0002\u0006\u0007\u0006\u0002\u0007\u0007\u0007\u0002"+
		"\b\u0007\b\u0001\u0000\u0001\u0000\u0001\u0000\u0001\u0000\u0001\u0000"+
		"\u0001\u0000\u0001\u0000\u0001\u0000\u0001\u0000\u0001\u0000\u0001\u0000"+
		"\u0001\u0000\u0001\u0000\u0001\u0000\u0001\u0000\u0001\u0000\u0001\u0000"+
		"\u0001\u0000\u0001\u0000\u0001\u0000\u0001\u0000\u0001\u0000\u0001\u0000"+
		"\u0001\u0000\u0001\u0000\u0001\u0000\u0001\u0000\u0003\u0000.\b\u0000"+
		"\u0001\u0000\u0001\u0000\u0003\u00002\b\u0000\u0001\u0000\u0001\u0000"+
		"\u0001\u0000\u0003\u00007\b\u0000\u0001\u0000\u0001\u0000\u0001\u0000"+
		"\u0001\u0000\u0001\u0000\u0001\u0000\u0001\u0000\u0001\u0000\u0001\u0000"+
		"\u0005\u0000B\b\u0000\n\u0000\f\u0000E\t\u0000\u0001\u0001\u0001\u0001"+
		"\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001"+
		"\u0005\u0001O\b\u0001\n\u0001\f\u0001R\t\u0001\u0001\u0002\u0001\u0002"+
		"\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002"+
		"\u0005\u0002\\\b\u0002\n\u0002\f\u0002_\t\u0002\u0001\u0003\u0001\u0003"+
		"\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003"+
		"\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003"+
		"\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003"+
		"\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003"+
		"\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003"+
		"\u0001\u0003\u0003\u0003\u0082\b\u0003\u0001\u0003\u0001\u0003\u0001\u0003"+
		"\u0001\u0003\u0001\u0003\u0001\u0003\u0005\u0003\u008a\b\u0003\n\u0003"+
		"\f\u0003\u008d\t\u0003\u0001\u0004\u0001\u0004\u0001\u0005\u0001\u0005"+
		"\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006"+
		"\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006"+
		"\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006"+
		"\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006"+
		"\u0003\u0006\u00ab\b\u0006\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007"+
		"\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007"+
		"\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0003\u0007\u00bb\b\u0007"+
		"\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007"+
		"\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007"+
		"\u0001\u0007\u0001\u0007\u0005\u0007\u00cb\b\u0007\n\u0007\f\u0007\u00ce"+
		"\t\u0007\u0001\b\u0001\b\u0001\b\u0001\b\u0001\b\u0001\b\u0001\b\u0001"+
		"\b\u0001\b\u0001\b\u0001\b\u0001\b\u0001\b\u0001\b\u0001\b\u0001\b\u0001"+
		"\b\u0001\b\u0001\b\u0001\b\u0001\b\u0001\b\u0001\b\u0001\b\u0001\b\u0001"+
		"\b\u0001\b\u0001\b\u0003\b\u00ec\b\b\u0001\b\u0001\b\u0001\b\u0001\b\u0001"+
		"\b\u0001\b\u0005\b\u00f4\b\b\n\b\f\b\u00f7\t\b\u0001\b\u0000\u0004\u0000"+
		"\u0006\u000e\u0010\t\u0000\u0002\u0004\u0006\b\n\f\u000e\u0010\u0000\u0000"+
		"\u011b\u00006\u0001\u0000\u0000\u0000\u0002F\u0001\u0000\u0000\u0000\u0004"+
		"S\u0001\u0000\u0000\u0000\u0006\u0081\u0001\u0000\u0000\u0000\b\u008e"+
		"\u0001\u0000\u0000\u0000\n\u0090\u0001\u0000\u0000\u0000\f\u00aa\u0001"+
		"\u0000\u0000\u0000\u000e\u00ba\u0001\u0000\u0000\u0000\u0010\u00eb\u0001"+
		"\u0000\u0000\u0000\u0012\u0013\u0006\u0000\uffff\uffff\u0000\u00137\u0003"+
		"\n\u0005\u0000\u00147\u0005&\u0000\u0000\u00157\u0003\f\u0006\u0000\u0016"+
		"\u0017\u0005\u0001\u0000\u0000\u0017\u0018\u0003\u0000\u0000\u0000\u0018"+
		"\u0019\u0005\u0002\u0000\u0000\u00197\u0001\u0000\u0000\u0000\u001a\u001b"+
		"\u0005\u0006\u0000\u0000\u001b\u001c\u0003\b\u0004\u0000\u001c\u001d\u0005"+
		"\u0007\u0000\u0000\u001d\u001e\u0005\b\u0000\u0000\u001e\u001f\u0003\u0000"+
		"\u0000\u0000\u001f \u0005\t\u0000\u0000 !\u0005\n\u0000\u0000!\"\u0003"+
		"\b\u0004\u0000\"#\u0005\u0007\u0000\u0000#7\u0001\u0000\u0000\u0000$%"+
		"\u0005\u000b\u0000\u0000%&\u0003\u0004\u0002\u0000&\'\u0005\f\u0000\u0000"+
		"\'(\u0003\u0000\u0000\u0002(7\u0001\u0000\u0000\u0000)*\u0005\r\u0000"+
		"\u0000*-\u0003\u0002\u0001\u0000+,\u0005\u000b\u0000\u0000,.\u0003\u0004"+
		"\u0002\u0000-+\u0001\u0000\u0000\u0000-.\u0001\u0000\u0000\u0000.1\u0001"+
		"\u0000\u0000\u0000/0\u0005\u000e\u0000\u000002\u0003\u0006\u0003\u0000"+
		"1/\u0001\u0000\u0000\u000012\u0001\u0000\u0000\u000023\u0001\u0000\u0000"+
		"\u000034\u0005\f\u0000\u000045\u0003\u0000\u0000\u000157\u0001\u0000\u0000"+
		"\u00006\u0012\u0001\u0000\u0000\u00006\u0014\u0001\u0000\u0000\u00006"+
		"\u0015\u0001\u0000\u0000\u00006\u0016\u0001\u0000\u0000\u00006\u001a\u0001"+
		"\u0000\u0000\u00006$\u0001\u0000\u0000\u00006)\u0001\u0000\u0000\u0000"+
		"7C\u0001\u0000\u0000\u000089\n\u0006\u0000\u00009:\u0005\u0003\u0000\u0000"+
		":B\u0003\u0000\u0000\u0007;<\n\u0005\u0000\u0000<=\u0005\u0004\u0000\u0000"+
		"=B\u0003\u000e\u0007\u0000>?\n\u0004\u0000\u0000?@\u0005\u0005\u0000\u0000"+
		"@B\u0003\u000e\u0007\u0000A8\u0001\u0000\u0000\u0000A;\u0001\u0000\u0000"+
		"\u0000A>\u0001\u0000\u0000\u0000BE\u0001\u0000\u0000\u0000CA\u0001\u0000"+
		"\u0000\u0000CD\u0001\u0000\u0000\u0000D\u0001\u0001\u0000\u0000\u0000"+
		"EC\u0001\u0000\u0000\u0000FG\u0003\n\u0005\u0000GH\u0005\u000f\u0000\u0000"+
		"HP\u0003\u0000\u0000\u0000IJ\u0005\u0003\u0000\u0000JK\u0003\n\u0005\u0000"+
		"KL\u0005\u000f\u0000\u0000LM\u0003\u0000\u0000\u0000MO\u0001\u0000\u0000"+
		"\u0000NI\u0001\u0000\u0000\u0000OR\u0001\u0000\u0000\u0000PN\u0001\u0000"+
		"\u0000\u0000PQ\u0001\u0000\u0000\u0000Q\u0003\u0001\u0000\u0000\u0000"+
		"RP\u0001\u0000\u0000\u0000ST\u0003\n\u0005\u0000TU\u0005\u0010\u0000\u0000"+
		"U]\u0003\u0000\u0000\u0000VW\u0005\u0003\u0000\u0000WX\u0003\n\u0005\u0000"+
		"XY\u0005\u0010\u0000\u0000YZ\u0003\u0000\u0000\u0000Z\\\u0001\u0000\u0000"+
		"\u0000[V\u0001\u0000\u0000\u0000\\_\u0001\u0000\u0000\u0000][\u0001\u0000"+
		"\u0000\u0000]^\u0001\u0000\u0000\u0000^\u0005\u0001\u0000\u0000\u0000"+
		"_]\u0001\u0000\u0000\u0000`a\u0006\u0003\uffff\uffff\u0000ab\u0003\u0000"+
		"\u0000\u0000bc\u0005\u0011\u0000\u0000cd\u0003\u0000\u0000\u0000d\u0082"+
		"\u0001\u0000\u0000\u0000ef\u0003\u0000\u0000\u0000fg\u0005\u0012\u0000"+
		"\u0000gh\u0003\u0000\u0000\u0000h\u0082\u0001\u0000\u0000\u0000ij\u0003"+
		"\u0000\u0000\u0000jk\u0005\u0013\u0000\u0000kl\u0003\u0000\u0000\u0000"+
		"l\u0082\u0001\u0000\u0000\u0000mn\u0003\u0000\u0000\u0000no\u0005\u0014"+
		"\u0000\u0000op\u0003\u0000\u0000\u0000p\u0082\u0001\u0000\u0000\u0000"+
		"qr\u0005\u0015\u0000\u0000rs\u0005\u0001\u0000\u0000st\u0003\u0000\u0000"+
		"\u0000tu\u0005\u0002\u0000\u0000u\u0082\u0001\u0000\u0000\u0000vw\u0005"+
		"\u0016\u0000\u0000wx\u0003\u0002\u0001\u0000xy\u0005\u0017\u0000\u0000"+
		"yz\u0003\u0006\u0003\u0005z\u0082\u0001\u0000\u0000\u0000{|\u0005\u0001"+
		"\u0000\u0000|}\u0003\u0006\u0003\u0000}~\u0005\u0002\u0000\u0000~\u0082"+
		"\u0001\u0000\u0000\u0000\u007f\u0080\u0005\u001a\u0000\u0000\u0080\u0082"+
		"\u0003\u0006\u0003\u0001\u0081`\u0001\u0000\u0000\u0000\u0081e\u0001\u0000"+
		"\u0000\u0000\u0081i\u0001\u0000\u0000\u0000\u0081m\u0001\u0000\u0000\u0000"+
		"\u0081q\u0001\u0000\u0000\u0000\u0081v\u0001\u0000\u0000\u0000\u0081{"+
		"\u0001\u0000\u0000\u0000\u0081\u007f\u0001\u0000\u0000\u0000\u0082\u008b"+
		"\u0001\u0000\u0000\u0000\u0083\u0084\n\u0003\u0000\u0000\u0084\u0085\u0005"+
		"\u0018\u0000\u0000\u0085\u008a\u0003\u0006\u0003\u0004\u0086\u0087\n\u0002"+
		"\u0000\u0000\u0087\u0088\u0005\u0019\u0000\u0000\u0088\u008a\u0003\u0006"+
		"\u0003\u0003\u0089\u0083\u0001\u0000\u0000\u0000\u0089\u0086\u0001\u0000"+
		"\u0000\u0000\u008a\u008d\u0001\u0000\u0000\u0000\u008b\u0089\u0001\u0000"+
		"\u0000\u0000\u008b\u008c\u0001\u0000\u0000\u0000\u008c\u0007\u0001\u0000"+
		"\u0000\u0000\u008d\u008b\u0001\u0000\u0000\u0000\u008e\u008f\u0005%\u0000"+
		"\u0000\u008f\t\u0001\u0000\u0000\u0000\u0090\u0091\u0005$\u0000\u0000"+
		"\u0091\u000b\u0001\u0000\u0000\u0000\u0092\u0093\u0005\u001b\u0000\u0000"+
		"\u0093\u0094\u0005\u0001\u0000\u0000\u0094\u0095\u0005&\u0000\u0000\u0095"+
		"\u0096\u0005\u0002\u0000\u0000\u0096\u0097\u0005\u0004\u0000\u0000\u0097"+
		"\u00ab\u0003\u000e\u0007\u0000\u0098\u0099\u0005\u001b\u0000\u0000\u0099"+
		"\u009a\u0005\u0001\u0000\u0000\u009a\u009b\u0005&\u0000\u0000\u009b\u009c"+
		"\u0005\u0002\u0000\u0000\u009c\u009d\u0005\u0005\u0000\u0000\u009d\u00ab"+
		"\u0003\u000e\u0007\u0000\u009e\u009f\u0005\u001c\u0000\u0000\u009f\u00a0"+
		"\u0005\u0001\u0000\u0000\u00a0\u00a1\u0005&\u0000\u0000\u00a1\u00a2\u0005"+
		"\u0002\u0000\u0000\u00a2\u00a3\u0005\u0004\u0000\u0000\u00a3\u00ab\u0003"+
		"\u000e\u0007\u0000\u00a4\u00a5\u0005\u001c\u0000\u0000\u00a5\u00a6\u0005"+
		"\u0001\u0000\u0000\u00a6\u00a7\u0005&\u0000\u0000\u00a7\u00a8\u0005\u0002"+
		"\u0000\u0000\u00a8\u00a9\u0005\u0005\u0000\u0000\u00a9\u00ab\u0003\u000e"+
		"\u0007\u0000\u00aa\u0092\u0001\u0000\u0000\u0000\u00aa\u0098\u0001\u0000"+
		"\u0000\u0000\u00aa\u009e\u0001\u0000\u0000\u0000\u00aa\u00a4\u0001\u0000"+
		"\u0000\u0000\u00ab\r\u0001\u0000\u0000\u0000\u00ac\u00ad\u0006\u0007\uffff"+
		"\uffff\u0000\u00ad\u00bb\u0003\b\u0004\u0000\u00ae\u00bb\u0005\u001d\u0000"+
		"\u0000\u00af\u00bb\u0005\u001e\u0000\u0000\u00b0\u00bb\u0005\u001f\u0000"+
		"\u0000\u00b1\u00b2\u0005 \u0000\u0000\u00b2\u00b3\u0005\u0001\u0000\u0000"+
		"\u00b3\u00bb\u0005\u0002\u0000\u0000\u00b4\u00b5\u0005!\u0000\u0000\u00b5"+
		"\u00bb\u0005%\u0000\u0000\u00b6\u00b7\u0005\u0001\u0000\u0000\u00b7\u00b8"+
		"\u0003\u000e\u0007\u0000\u00b8\u00b9\u0005\u0002\u0000\u0000\u00b9\u00bb"+
		"\u0001\u0000\u0000\u0000\u00ba\u00ac\u0001\u0000\u0000\u0000\u00ba\u00ae"+
		"\u0001\u0000\u0000\u0000\u00ba\u00af\u0001\u0000\u0000\u0000\u00ba\u00b0"+
		"\u0001\u0000\u0000\u0000\u00ba\u00b1\u0001\u0000\u0000\u0000\u00ba\u00b4"+
		"\u0001\u0000\u0000\u0000\u00ba\u00b6\u0001\u0000\u0000\u0000\u00bb\u00cc"+
		"\u0001\u0000\u0000\u0000\u00bc\u00bd\n\u0004\u0000\u0000\u00bd\u00be\u0005"+
		"\u0004\u0000\u0000\u00be\u00cb\u0003\u000e\u0007\u0005\u00bf\u00c0\n\u0003"+
		"\u0000\u0000\u00c0\u00c1\u0005\u0005\u0000\u0000\u00c1\u00cb\u0003\u000e"+
		"\u0007\u0004\u00c2\u00c3\n\u0001\u0000\u0000\u00c3\u00c4\u0005\u0003\u0000"+
		"\u0000\u00c4\u00cb\u0003\u000e\u0007\u0002\u00c5\u00c6\n\u0002\u0000\u0000"+
		"\u00c6\u00c7\u0005\"\u0000\u0000\u00c7\u00c8\u0003\u0010\b\u0000\u00c8"+
		"\u00c9\u0005#\u0000\u0000\u00c9\u00cb\u0001\u0000\u0000\u0000\u00ca\u00bc"+
		"\u0001\u0000\u0000\u0000\u00ca\u00bf\u0001\u0000\u0000\u0000\u00ca\u00c2"+
		"\u0001\u0000\u0000\u0000\u00ca\u00c5\u0001\u0000\u0000\u0000\u00cb\u00ce"+
		"\u0001\u0000\u0000\u0000\u00cc\u00ca\u0001\u0000\u0000\u0000\u00cc\u00cd"+
		"\u0001\u0000\u0000\u0000\u00cd\u000f\u0001\u0000\u0000\u0000\u00ce\u00cc"+
		"\u0001\u0000\u0000\u0000\u00cf\u00d0\u0006\b\uffff\uffff\u0000\u00d0\u00ec"+
		"\u0003\u000e\u0007\u0000\u00d1\u00d2\u0003\u000e\u0007\u0000\u00d2\u00d3"+
		"\u0005\u0011\u0000\u0000\u00d3\u00d4\u0003\u000e\u0007\u0000\u00d4\u00ec"+
		"\u0001\u0000\u0000\u0000\u00d5\u00d6\u0003\u000e\u0007\u0000\u00d6\u00d7"+
		"\u0005\u0012\u0000\u0000\u00d7\u00d8\u0003\u000e\u0007\u0000\u00d8\u00ec"+
		"\u0001\u0000\u0000\u0000\u00d9\u00da\u0003\u000e\u0007\u0000\u00da\u00db"+
		"\u0005\u0013\u0000\u0000\u00db\u00dc\u0003\u000e\u0007\u0000\u00dc\u00ec"+
		"\u0001\u0000\u0000\u0000\u00dd\u00de\u0003\u000e\u0007\u0000\u00de\u00df"+
		"\u0005\u0014\u0000\u0000\u00df\u00e0\u0003\u000e\u0007\u0000\u00e0\u00ec"+
		"\u0001\u0000\u0000\u0000\u00e1\u00e2\u0003\u000e\u0007\u0000\u00e2\u00e3"+
		"\u0005\u0011\u0000\u0000\u00e3\u00e4\u0005&\u0000\u0000\u00e4\u00ec\u0001"+
		"\u0000\u0000\u0000\u00e5\u00e6\u0005\u0001\u0000\u0000\u00e6\u00e7\u0003"+
		"\u0010\b\u0000\u00e7\u00e8\u0005\u0002\u0000\u0000\u00e8\u00ec\u0001\u0000"+
		"\u0000\u0000\u00e9\u00ea\u0005\u001a\u0000\u0000\u00ea\u00ec\u0003\u0010"+
		"\b\u0001\u00eb\u00cf\u0001\u0000\u0000\u0000\u00eb\u00d1\u0001\u0000\u0000"+
		"\u0000\u00eb\u00d5\u0001\u0000\u0000\u0000\u00eb\u00d9\u0001\u0000\u0000"+
		"\u0000\u00eb\u00dd\u0001\u0000\u0000\u0000\u00eb\u00e1\u0001\u0000\u0000"+
		"\u0000\u00eb\u00e5\u0001\u0000\u0000\u0000\u00eb\u00e9\u0001\u0000\u0000"+
		"\u0000\u00ec\u00f5\u0001\u0000\u0000\u0000\u00ed\u00ee\n\u0003\u0000\u0000"+
		"\u00ee\u00ef\u0005\u0018\u0000\u0000\u00ef\u00f4\u0003\u0010\b\u0004\u00f0"+
		"\u00f1\n\u0002\u0000\u0000\u00f1\u00f2\u0005\u0019\u0000\u0000\u00f2\u00f4"+
		"\u0003\u0010\b\u0003\u00f3\u00ed\u0001\u0000\u0000\u0000\u00f3\u00f0\u0001"+
		"\u0000\u0000\u0000\u00f4\u00f7\u0001\u0000\u0000\u0000\u00f5\u00f3\u0001"+
		"\u0000\u0000\u0000\u00f5\u00f6\u0001\u0000\u0000\u0000\u00f6\u0011\u0001"+
		"\u0000\u0000\u0000\u00f7\u00f5\u0001\u0000\u0000\u0000\u0011-16ACP]\u0081"+
		"\u0089\u008b\u00aa\u00ba\u00ca\u00cc\u00eb\u00f3\u00f5";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}