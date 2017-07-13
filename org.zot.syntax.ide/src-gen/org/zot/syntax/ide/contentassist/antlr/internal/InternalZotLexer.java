package org.zot.syntax.ide.contentassist.antlr.internal;

// Hack: Use our own Lexer superclass by means of import. 
// Currently there is no other way to specify the superclass for the lexer.
import org.eclipse.xtext.ide.editor.contentassist.antlr.internal.Lexer;


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

@SuppressWarnings("all")
public class InternalZotLexer extends Lexer {
    public static final int RULE_STRING=11;
    public static final int RULE_SL_COMMENT=13;
    public static final int T__19=19;
    public static final int T__16=16;
    public static final int RULE_OP2=5;
    public static final int T__17=17;
    public static final int T__18=18;
    public static final int EOF=-1;
    public static final int RULE_ID=9;
    public static final int RULE_PROPOP=4;
    public static final int RULE_WS=14;
    public static final int RULE_OPFN=7;
    public static final int RULE_ANY_OTHER=15;
    public static final int RULE_INT=8;
    public static final int T__22=22;
    public static final int RULE_ML_COMMENT=12;
    public static final int T__23=23;
    public static final int RULE_OPF=6;
    public static final int RULE_FLOAT=10;
    public static final int T__24=24;
    public static final int T__25=25;
    public static final int T__20=20;
    public static final int T__21=21;

    // delegates
    // delegators

    public InternalZotLexer() {;} 
    public InternalZotLexer(CharStream input) {
        this(input, new RecognizerSharedState());
    }
    public InternalZotLexer(CharStream input, RecognizerSharedState state) {
        super(input,state);

    }
    public String getGrammarFileName() { return "InternalZot.g"; }

    // $ANTLR start "T__16"
    public final void mT__16() throws RecognitionException {
        try {
            int _type = T__16;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalZot.g:11:7: ( 'P' )
            // InternalZot.g:11:9: 'P'
            {
            match('P'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__16"

    // $ANTLR start "T__17"
    public final void mT__17() throws RecognitionException {
        try {
            int _type = T__17;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalZot.g:12:7: ( 'p' )
            // InternalZot.g:12:9: 'p'
            {
            match('p'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__17"

    // $ANTLR start "T__18"
    public final void mT__18() throws RecognitionException {
        try {
            int _type = T__18;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalZot.g:13:7: ( '(' )
            // InternalZot.g:13:9: '('
            {
            match('('); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__18"

    // $ANTLR start "T__19"
    public final void mT__19() throws RecognitionException {
        try {
            int _type = T__19;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalZot.g:14:7: ( ')' )
            // InternalZot.g:14:9: ')'
            {
            match(')'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__19"

    // $ANTLR start "T__20"
    public final void mT__20() throws RecognitionException {
        try {
            int _type = T__20;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalZot.g:15:7: ( '(zot-p' )
            // InternalZot.g:15:9: '(zot-p'
            {
            match("(zot-p"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__20"

    // $ANTLR start "T__21"
    public final void mT__21() throws RecognitionException {
        try {
            int _type = T__21;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalZot.g:16:7: ( '(zot-cp' )
            // InternalZot.g:16:9: '(zot-cp'
            {
            match("(zot-cp"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__21"

    // $ANTLR start "T__22"
    public final void mT__22() throws RecognitionException {
        try {
            int _type = T__22;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalZot.g:17:7: ( '&&' )
            // InternalZot.g:17:9: '&&'
            {
            match("&&"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__22"

    // $ANTLR start "T__23"
    public final void mT__23() throws RecognitionException {
        try {
            int _type = T__23;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalZot.g:18:7: ( '||' )
            // InternalZot.g:18:9: '||'
            {
            match("||"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__23"

    // $ANTLR start "T__24"
    public final void mT__24() throws RecognitionException {
        try {
            int _type = T__24;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalZot.g:19:7: ( '!!' )
            // InternalZot.g:19:9: '!!'
            {
            match("!!"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__24"

    // $ANTLR start "T__25"
    public final void mT__25() throws RecognitionException {
        try {
            int _type = T__25;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalZot.g:20:7: ( '-' )
            // InternalZot.g:20:9: '-'
            {
            match('-'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__25"

    // $ANTLR start "RULE_PROPOP"
    public final void mRULE_PROPOP() throws RecognitionException {
        try {
            int _type = RULE_PROPOP;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalZot.g:2202:13: ( ( '<' | '<=' | '=' | '>' | '>=' ) )
            // InternalZot.g:2202:15: ( '<' | '<=' | '=' | '>' | '>=' )
            {
            // InternalZot.g:2202:15: ( '<' | '<=' | '=' | '>' | '>=' )
            int alt1=5;
            switch ( input.LA(1) ) {
            case '<':
                {
                int LA1_1 = input.LA(2);

                if ( (LA1_1=='=') ) {
                    alt1=2;
                }
                else {
                    alt1=1;}
                }
                break;
            case '=':
                {
                alt1=3;
                }
                break;
            case '>':
                {
                int LA1_3 = input.LA(2);

                if ( (LA1_3=='=') ) {
                    alt1=5;
                }
                else {
                    alt1=4;}
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 1, 0, input);

                throw nvae;
            }

            switch (alt1) {
                case 1 :
                    // InternalZot.g:2202:16: '<'
                    {
                    match('<'); 

                    }
                    break;
                case 2 :
                    // InternalZot.g:2202:20: '<='
                    {
                    match("<="); 


                    }
                    break;
                case 3 :
                    // InternalZot.g:2202:25: '='
                    {
                    match('='); 

                    }
                    break;
                case 4 :
                    // InternalZot.g:2202:29: '>'
                    {
                    match('>'); 

                    }
                    break;
                case 5 :
                    // InternalZot.g:2202:33: '>='
                    {
                    match(">="); 


                    }
                    break;

            }


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "RULE_PROPOP"

    // $ANTLR start "RULE_OP2"
    public final void mRULE_OP2() throws RecognitionException {
        try {
            int _type = RULE_OP2;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalZot.g:2204:10: ( ( '->' | '<->' | 'until' | 'since' | 'release' | 'trigger' ) )
            // InternalZot.g:2204:12: ( '->' | '<->' | 'until' | 'since' | 'release' | 'trigger' )
            {
            // InternalZot.g:2204:12: ( '->' | '<->' | 'until' | 'since' | 'release' | 'trigger' )
            int alt2=6;
            switch ( input.LA(1) ) {
            case '-':
                {
                alt2=1;
                }
                break;
            case '<':
                {
                alt2=2;
                }
                break;
            case 'u':
                {
                alt2=3;
                }
                break;
            case 's':
                {
                alt2=4;
                }
                break;
            case 'r':
                {
                alt2=5;
                }
                break;
            case 't':
                {
                alt2=6;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 2, 0, input);

                throw nvae;
            }

            switch (alt2) {
                case 1 :
                    // InternalZot.g:2204:13: '->'
                    {
                    match("->"); 


                    }
                    break;
                case 2 :
                    // InternalZot.g:2204:18: '<->'
                    {
                    match("<->"); 


                    }
                    break;
                case 3 :
                    // InternalZot.g:2204:24: 'until'
                    {
                    match("until"); 


                    }
                    break;
                case 4 :
                    // InternalZot.g:2204:32: 'since'
                    {
                    match("since"); 


                    }
                    break;
                case 5 :
                    // InternalZot.g:2204:40: 'release'
                    {
                    match("release"); 


                    }
                    break;
                case 6 :
                    // InternalZot.g:2204:50: 'trigger'
                    {
                    match("trigger"); 


                    }
                    break;

            }


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "RULE_OP2"

    // $ANTLR start "RULE_OPF"
    public final void mRULE_OPF() throws RecognitionException {
        try {
            int _type = RULE_OPF;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalZot.g:2206:10: ( ( 'next' | 'yesterday' | 'alw' | 'som' | 'alwf' | 'somf' | 'alwp' | 'somp' ) )
            // InternalZot.g:2206:12: ( 'next' | 'yesterday' | 'alw' | 'som' | 'alwf' | 'somf' | 'alwp' | 'somp' )
            {
            // InternalZot.g:2206:12: ( 'next' | 'yesterday' | 'alw' | 'som' | 'alwf' | 'somf' | 'alwp' | 'somp' )
            int alt3=8;
            alt3 = dfa3.predict(input);
            switch (alt3) {
                case 1 :
                    // InternalZot.g:2206:13: 'next'
                    {
                    match("next"); 


                    }
                    break;
                case 2 :
                    // InternalZot.g:2206:20: 'yesterday'
                    {
                    match("yesterday"); 


                    }
                    break;
                case 3 :
                    // InternalZot.g:2206:32: 'alw'
                    {
                    match("alw"); 


                    }
                    break;
                case 4 :
                    // InternalZot.g:2206:38: 'som'
                    {
                    match("som"); 


                    }
                    break;
                case 5 :
                    // InternalZot.g:2206:44: 'alwf'
                    {
                    match("alwf"); 


                    }
                    break;
                case 6 :
                    // InternalZot.g:2206:51: 'somf'
                    {
                    match("somf"); 


                    }
                    break;
                case 7 :
                    // InternalZot.g:2206:58: 'alwp'
                    {
                    match("alwp"); 


                    }
                    break;
                case 8 :
                    // InternalZot.g:2206:65: 'somp'
                    {
                    match("somp"); 


                    }
                    break;

            }


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "RULE_OPF"

    // $ANTLR start "RULE_OPFN"
    public final void mRULE_OPFN() throws RecognitionException {
        try {
            int _type = RULE_OPFN;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalZot.g:2208:11: ( ( 'futr' | 'past' | 'withinf' | 'withinp' | 'lasts' | 'lasted' ) )
            // InternalZot.g:2208:13: ( 'futr' | 'past' | 'withinf' | 'withinp' | 'lasts' | 'lasted' )
            {
            // InternalZot.g:2208:13: ( 'futr' | 'past' | 'withinf' | 'withinp' | 'lasts' | 'lasted' )
            int alt4=6;
            alt4 = dfa4.predict(input);
            switch (alt4) {
                case 1 :
                    // InternalZot.g:2208:14: 'futr'
                    {
                    match("futr"); 


                    }
                    break;
                case 2 :
                    // InternalZot.g:2208:21: 'past'
                    {
                    match("past"); 


                    }
                    break;
                case 3 :
                    // InternalZot.g:2208:28: 'withinf'
                    {
                    match("withinf"); 


                    }
                    break;
                case 4 :
                    // InternalZot.g:2208:38: 'withinp'
                    {
                    match("withinp"); 


                    }
                    break;
                case 5 :
                    // InternalZot.g:2208:48: 'lasts'
                    {
                    match("lasts"); 


                    }
                    break;
                case 6 :
                    // InternalZot.g:2208:56: 'lasted'
                    {
                    match("lasted"); 


                    }
                    break;

            }


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "RULE_OPFN"

    // $ANTLR start "RULE_FLOAT"
    public final void mRULE_FLOAT() throws RecognitionException {
        try {
            int _type = RULE_FLOAT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalZot.g:2210:12: ( '.' ( '0' .. '9' )+ )
            // InternalZot.g:2210:14: '.' ( '0' .. '9' )+
            {
            match('.'); 
            // InternalZot.g:2210:18: ( '0' .. '9' )+
            int cnt5=0;
            loop5:
            do {
                int alt5=2;
                int LA5_0 = input.LA(1);

                if ( ((LA5_0>='0' && LA5_0<='9')) ) {
                    alt5=1;
                }


                switch (alt5) {
            	case 1 :
            	    // InternalZot.g:2210:19: '0' .. '9'
            	    {
            	    matchRange('0','9'); 

            	    }
            	    break;

            	default :
            	    if ( cnt5 >= 1 ) break loop5;
                        EarlyExitException eee =
                            new EarlyExitException(5, input);
                        throw eee;
                }
                cnt5++;
            } while (true);


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "RULE_FLOAT"

    // $ANTLR start "RULE_ID"
    public final void mRULE_ID() throws RecognitionException {
        try {
            int _type = RULE_ID;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalZot.g:2212:9: ( ( '^' )? ( 'a' .. 'z' | 'A' .. 'Z' | '_' ) ( 'a' .. 'z' | 'A' .. 'Z' | '_' | '0' .. '9' )* )
            // InternalZot.g:2212:11: ( '^' )? ( 'a' .. 'z' | 'A' .. 'Z' | '_' ) ( 'a' .. 'z' | 'A' .. 'Z' | '_' | '0' .. '9' )*
            {
            // InternalZot.g:2212:11: ( '^' )?
            int alt6=2;
            int LA6_0 = input.LA(1);

            if ( (LA6_0=='^') ) {
                alt6=1;
            }
            switch (alt6) {
                case 1 :
                    // InternalZot.g:2212:11: '^'
                    {
                    match('^'); 

                    }
                    break;

            }

            if ( (input.LA(1)>='A' && input.LA(1)<='Z')||input.LA(1)=='_'||(input.LA(1)>='a' && input.LA(1)<='z') ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            // InternalZot.g:2212:40: ( 'a' .. 'z' | 'A' .. 'Z' | '_' | '0' .. '9' )*
            loop7:
            do {
                int alt7=2;
                int LA7_0 = input.LA(1);

                if ( ((LA7_0>='0' && LA7_0<='9')||(LA7_0>='A' && LA7_0<='Z')||LA7_0=='_'||(LA7_0>='a' && LA7_0<='z')) ) {
                    alt7=1;
                }


                switch (alt7) {
            	case 1 :
            	    // InternalZot.g:
            	    {
            	    if ( (input.LA(1)>='0' && input.LA(1)<='9')||(input.LA(1)>='A' && input.LA(1)<='Z')||input.LA(1)=='_'||(input.LA(1)>='a' && input.LA(1)<='z') ) {
            	        input.consume();

            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;}


            	    }
            	    break;

            	default :
            	    break loop7;
                }
            } while (true);


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "RULE_ID"

    // $ANTLR start "RULE_INT"
    public final void mRULE_INT() throws RecognitionException {
        try {
            int _type = RULE_INT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalZot.g:2214:10: ( ( '0' .. '9' )+ )
            // InternalZot.g:2214:12: ( '0' .. '9' )+
            {
            // InternalZot.g:2214:12: ( '0' .. '9' )+
            int cnt8=0;
            loop8:
            do {
                int alt8=2;
                int LA8_0 = input.LA(1);

                if ( ((LA8_0>='0' && LA8_0<='9')) ) {
                    alt8=1;
                }


                switch (alt8) {
            	case 1 :
            	    // InternalZot.g:2214:13: '0' .. '9'
            	    {
            	    matchRange('0','9'); 

            	    }
            	    break;

            	default :
            	    if ( cnt8 >= 1 ) break loop8;
                        EarlyExitException eee =
                            new EarlyExitException(8, input);
                        throw eee;
                }
                cnt8++;
            } while (true);


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "RULE_INT"

    // $ANTLR start "RULE_STRING"
    public final void mRULE_STRING() throws RecognitionException {
        try {
            int _type = RULE_STRING;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalZot.g:2216:13: ( ( '\"' ( '\\\\' . | ~ ( ( '\\\\' | '\"' ) ) )* '\"' | '\\'' ( '\\\\' . | ~ ( ( '\\\\' | '\\'' ) ) )* '\\'' ) )
            // InternalZot.g:2216:15: ( '\"' ( '\\\\' . | ~ ( ( '\\\\' | '\"' ) ) )* '\"' | '\\'' ( '\\\\' . | ~ ( ( '\\\\' | '\\'' ) ) )* '\\'' )
            {
            // InternalZot.g:2216:15: ( '\"' ( '\\\\' . | ~ ( ( '\\\\' | '\"' ) ) )* '\"' | '\\'' ( '\\\\' . | ~ ( ( '\\\\' | '\\'' ) ) )* '\\'' )
            int alt11=2;
            int LA11_0 = input.LA(1);

            if ( (LA11_0=='\"') ) {
                alt11=1;
            }
            else if ( (LA11_0=='\'') ) {
                alt11=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 11, 0, input);

                throw nvae;
            }
            switch (alt11) {
                case 1 :
                    // InternalZot.g:2216:16: '\"' ( '\\\\' . | ~ ( ( '\\\\' | '\"' ) ) )* '\"'
                    {
                    match('\"'); 
                    // InternalZot.g:2216:20: ( '\\\\' . | ~ ( ( '\\\\' | '\"' ) ) )*
                    loop9:
                    do {
                        int alt9=3;
                        int LA9_0 = input.LA(1);

                        if ( (LA9_0=='\\') ) {
                            alt9=1;
                        }
                        else if ( ((LA9_0>='\u0000' && LA9_0<='!')||(LA9_0>='#' && LA9_0<='[')||(LA9_0>=']' && LA9_0<='\uFFFF')) ) {
                            alt9=2;
                        }


                        switch (alt9) {
                    	case 1 :
                    	    // InternalZot.g:2216:21: '\\\\' .
                    	    {
                    	    match('\\'); 
                    	    matchAny(); 

                    	    }
                    	    break;
                    	case 2 :
                    	    // InternalZot.g:2216:28: ~ ( ( '\\\\' | '\"' ) )
                    	    {
                    	    if ( (input.LA(1)>='\u0000' && input.LA(1)<='!')||(input.LA(1)>='#' && input.LA(1)<='[')||(input.LA(1)>=']' && input.LA(1)<='\uFFFF') ) {
                    	        input.consume();

                    	    }
                    	    else {
                    	        MismatchedSetException mse = new MismatchedSetException(null,input);
                    	        recover(mse);
                    	        throw mse;}


                    	    }
                    	    break;

                    	default :
                    	    break loop9;
                        }
                    } while (true);

                    match('\"'); 

                    }
                    break;
                case 2 :
                    // InternalZot.g:2216:48: '\\'' ( '\\\\' . | ~ ( ( '\\\\' | '\\'' ) ) )* '\\''
                    {
                    match('\''); 
                    // InternalZot.g:2216:53: ( '\\\\' . | ~ ( ( '\\\\' | '\\'' ) ) )*
                    loop10:
                    do {
                        int alt10=3;
                        int LA10_0 = input.LA(1);

                        if ( (LA10_0=='\\') ) {
                            alt10=1;
                        }
                        else if ( ((LA10_0>='\u0000' && LA10_0<='&')||(LA10_0>='(' && LA10_0<='[')||(LA10_0>=']' && LA10_0<='\uFFFF')) ) {
                            alt10=2;
                        }


                        switch (alt10) {
                    	case 1 :
                    	    // InternalZot.g:2216:54: '\\\\' .
                    	    {
                    	    match('\\'); 
                    	    matchAny(); 

                    	    }
                    	    break;
                    	case 2 :
                    	    // InternalZot.g:2216:61: ~ ( ( '\\\\' | '\\'' ) )
                    	    {
                    	    if ( (input.LA(1)>='\u0000' && input.LA(1)<='&')||(input.LA(1)>='(' && input.LA(1)<='[')||(input.LA(1)>=']' && input.LA(1)<='\uFFFF') ) {
                    	        input.consume();

                    	    }
                    	    else {
                    	        MismatchedSetException mse = new MismatchedSetException(null,input);
                    	        recover(mse);
                    	        throw mse;}


                    	    }
                    	    break;

                    	default :
                    	    break loop10;
                        }
                    } while (true);

                    match('\''); 

                    }
                    break;

            }


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "RULE_STRING"

    // $ANTLR start "RULE_ML_COMMENT"
    public final void mRULE_ML_COMMENT() throws RecognitionException {
        try {
            int _type = RULE_ML_COMMENT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalZot.g:2218:17: ( '/*' ( options {greedy=false; } : . )* '*/' )
            // InternalZot.g:2218:19: '/*' ( options {greedy=false; } : . )* '*/'
            {
            match("/*"); 

            // InternalZot.g:2218:24: ( options {greedy=false; } : . )*
            loop12:
            do {
                int alt12=2;
                int LA12_0 = input.LA(1);

                if ( (LA12_0=='*') ) {
                    int LA12_1 = input.LA(2);

                    if ( (LA12_1=='/') ) {
                        alt12=2;
                    }
                    else if ( ((LA12_1>='\u0000' && LA12_1<='.')||(LA12_1>='0' && LA12_1<='\uFFFF')) ) {
                        alt12=1;
                    }


                }
                else if ( ((LA12_0>='\u0000' && LA12_0<=')')||(LA12_0>='+' && LA12_0<='\uFFFF')) ) {
                    alt12=1;
                }


                switch (alt12) {
            	case 1 :
            	    // InternalZot.g:2218:52: .
            	    {
            	    matchAny(); 

            	    }
            	    break;

            	default :
            	    break loop12;
                }
            } while (true);

            match("*/"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "RULE_ML_COMMENT"

    // $ANTLR start "RULE_SL_COMMENT"
    public final void mRULE_SL_COMMENT() throws RecognitionException {
        try {
            int _type = RULE_SL_COMMENT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalZot.g:2220:17: ( '//' (~ ( ( '\\n' | '\\r' ) ) )* ( ( '\\r' )? '\\n' )? )
            // InternalZot.g:2220:19: '//' (~ ( ( '\\n' | '\\r' ) ) )* ( ( '\\r' )? '\\n' )?
            {
            match("//"); 

            // InternalZot.g:2220:24: (~ ( ( '\\n' | '\\r' ) ) )*
            loop13:
            do {
                int alt13=2;
                int LA13_0 = input.LA(1);

                if ( ((LA13_0>='\u0000' && LA13_0<='\t')||(LA13_0>='\u000B' && LA13_0<='\f')||(LA13_0>='\u000E' && LA13_0<='\uFFFF')) ) {
                    alt13=1;
                }


                switch (alt13) {
            	case 1 :
            	    // InternalZot.g:2220:24: ~ ( ( '\\n' | '\\r' ) )
            	    {
            	    if ( (input.LA(1)>='\u0000' && input.LA(1)<='\t')||(input.LA(1)>='\u000B' && input.LA(1)<='\f')||(input.LA(1)>='\u000E' && input.LA(1)<='\uFFFF') ) {
            	        input.consume();

            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;}


            	    }
            	    break;

            	default :
            	    break loop13;
                }
            } while (true);

            // InternalZot.g:2220:40: ( ( '\\r' )? '\\n' )?
            int alt15=2;
            int LA15_0 = input.LA(1);

            if ( (LA15_0=='\n'||LA15_0=='\r') ) {
                alt15=1;
            }
            switch (alt15) {
                case 1 :
                    // InternalZot.g:2220:41: ( '\\r' )? '\\n'
                    {
                    // InternalZot.g:2220:41: ( '\\r' )?
                    int alt14=2;
                    int LA14_0 = input.LA(1);

                    if ( (LA14_0=='\r') ) {
                        alt14=1;
                    }
                    switch (alt14) {
                        case 1 :
                            // InternalZot.g:2220:41: '\\r'
                            {
                            match('\r'); 

                            }
                            break;

                    }

                    match('\n'); 

                    }
                    break;

            }


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "RULE_SL_COMMENT"

    // $ANTLR start "RULE_WS"
    public final void mRULE_WS() throws RecognitionException {
        try {
            int _type = RULE_WS;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalZot.g:2222:9: ( ( ' ' | '\\t' | '\\r' | '\\n' )+ )
            // InternalZot.g:2222:11: ( ' ' | '\\t' | '\\r' | '\\n' )+
            {
            // InternalZot.g:2222:11: ( ' ' | '\\t' | '\\r' | '\\n' )+
            int cnt16=0;
            loop16:
            do {
                int alt16=2;
                int LA16_0 = input.LA(1);

                if ( ((LA16_0>='\t' && LA16_0<='\n')||LA16_0=='\r'||LA16_0==' ') ) {
                    alt16=1;
                }


                switch (alt16) {
            	case 1 :
            	    // InternalZot.g:
            	    {
            	    if ( (input.LA(1)>='\t' && input.LA(1)<='\n')||input.LA(1)=='\r'||input.LA(1)==' ' ) {
            	        input.consume();

            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;}


            	    }
            	    break;

            	default :
            	    if ( cnt16 >= 1 ) break loop16;
                        EarlyExitException eee =
                            new EarlyExitException(16, input);
                        throw eee;
                }
                cnt16++;
            } while (true);


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "RULE_WS"

    // $ANTLR start "RULE_ANY_OTHER"
    public final void mRULE_ANY_OTHER() throws RecognitionException {
        try {
            int _type = RULE_ANY_OTHER;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalZot.g:2224:16: ( . )
            // InternalZot.g:2224:18: .
            {
            matchAny(); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "RULE_ANY_OTHER"

    public void mTokens() throws RecognitionException {
        // InternalZot.g:1:8: ( T__16 | T__17 | T__18 | T__19 | T__20 | T__21 | T__22 | T__23 | T__24 | T__25 | RULE_PROPOP | RULE_OP2 | RULE_OPF | RULE_OPFN | RULE_FLOAT | RULE_ID | RULE_INT | RULE_STRING | RULE_ML_COMMENT | RULE_SL_COMMENT | RULE_WS | RULE_ANY_OTHER )
        int alt17=22;
        alt17 = dfa17.predict(input);
        switch (alt17) {
            case 1 :
                // InternalZot.g:1:10: T__16
                {
                mT__16(); 

                }
                break;
            case 2 :
                // InternalZot.g:1:16: T__17
                {
                mT__17(); 

                }
                break;
            case 3 :
                // InternalZot.g:1:22: T__18
                {
                mT__18(); 

                }
                break;
            case 4 :
                // InternalZot.g:1:28: T__19
                {
                mT__19(); 

                }
                break;
            case 5 :
                // InternalZot.g:1:34: T__20
                {
                mT__20(); 

                }
                break;
            case 6 :
                // InternalZot.g:1:40: T__21
                {
                mT__21(); 

                }
                break;
            case 7 :
                // InternalZot.g:1:46: T__22
                {
                mT__22(); 

                }
                break;
            case 8 :
                // InternalZot.g:1:52: T__23
                {
                mT__23(); 

                }
                break;
            case 9 :
                // InternalZot.g:1:58: T__24
                {
                mT__24(); 

                }
                break;
            case 10 :
                // InternalZot.g:1:64: T__25
                {
                mT__25(); 

                }
                break;
            case 11 :
                // InternalZot.g:1:70: RULE_PROPOP
                {
                mRULE_PROPOP(); 

                }
                break;
            case 12 :
                // InternalZot.g:1:82: RULE_OP2
                {
                mRULE_OP2(); 

                }
                break;
            case 13 :
                // InternalZot.g:1:91: RULE_OPF
                {
                mRULE_OPF(); 

                }
                break;
            case 14 :
                // InternalZot.g:1:100: RULE_OPFN
                {
                mRULE_OPFN(); 

                }
                break;
            case 15 :
                // InternalZot.g:1:110: RULE_FLOAT
                {
                mRULE_FLOAT(); 

                }
                break;
            case 16 :
                // InternalZot.g:1:121: RULE_ID
                {
                mRULE_ID(); 

                }
                break;
            case 17 :
                // InternalZot.g:1:129: RULE_INT
                {
                mRULE_INT(); 

                }
                break;
            case 18 :
                // InternalZot.g:1:138: RULE_STRING
                {
                mRULE_STRING(); 

                }
                break;
            case 19 :
                // InternalZot.g:1:150: RULE_ML_COMMENT
                {
                mRULE_ML_COMMENT(); 

                }
                break;
            case 20 :
                // InternalZot.g:1:166: RULE_SL_COMMENT
                {
                mRULE_SL_COMMENT(); 

                }
                break;
            case 21 :
                // InternalZot.g:1:182: RULE_WS
                {
                mRULE_WS(); 

                }
                break;
            case 22 :
                // InternalZot.g:1:190: RULE_ANY_OTHER
                {
                mRULE_ANY_OTHER(); 

                }
                break;

        }

    }


    protected DFA3 dfa3 = new DFA3(this);
    protected DFA4 dfa4 = new DFA4(this);
    protected DFA17 dfa17 = new DFA17(this);
    static final String DFA3_eotS =
        "\7\uffff\1\13\1\16\6\uffff";
    static final String DFA3_eofS =
        "\17\uffff";
    static final String DFA3_minS =
        "\1\141\2\uffff\1\154\1\157\1\167\1\155\2\146\6\uffff";
    static final String DFA3_maxS =
        "\1\171\2\uffff\1\154\1\157\1\167\1\155\2\160\6\uffff";
    static final String DFA3_acceptS =
        "\1\uffff\1\1\1\2\6\uffff\1\5\1\7\1\3\1\6\1\10\1\4";
    static final String DFA3_specialS =
        "\17\uffff}>";
    static final String[] DFA3_transitionS = {
            "\1\3\14\uffff\1\1\4\uffff\1\4\5\uffff\1\2",
            "",
            "",
            "\1\5",
            "\1\6",
            "\1\7",
            "\1\10",
            "\1\11\11\uffff\1\12",
            "\1\14\11\uffff\1\15",
            "",
            "",
            "",
            "",
            "",
            ""
    };

    static final short[] DFA3_eot = DFA.unpackEncodedString(DFA3_eotS);
    static final short[] DFA3_eof = DFA.unpackEncodedString(DFA3_eofS);
    static final char[] DFA3_min = DFA.unpackEncodedStringToUnsignedChars(DFA3_minS);
    static final char[] DFA3_max = DFA.unpackEncodedStringToUnsignedChars(DFA3_maxS);
    static final short[] DFA3_accept = DFA.unpackEncodedString(DFA3_acceptS);
    static final short[] DFA3_special = DFA.unpackEncodedString(DFA3_specialS);
    static final short[][] DFA3_transition;

    static {
        int numStates = DFA3_transitionS.length;
        DFA3_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA3_transition[i] = DFA.unpackEncodedString(DFA3_transitionS[i]);
        }
    }

    class DFA3 extends DFA {

        public DFA3(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 3;
            this.eot = DFA3_eot;
            this.eof = DFA3_eof;
            this.min = DFA3_min;
            this.max = DFA3_max;
            this.accept = DFA3_accept;
            this.special = DFA3_special;
            this.transition = DFA3_transition;
        }
        public String getDescription() {
            return "2206:12: ( 'next' | 'yesterday' | 'alw' | 'som' | 'alwf' | 'somf' | 'alwp' | 'somp' )";
        }
    }
    static final String DFA4_eotS =
        "\21\uffff";
    static final String DFA4_eofS =
        "\21\uffff";
    static final String DFA4_minS =
        "\1\146\2\uffff\1\151\1\141\1\164\1\163\1\150\1\164\1\151\1\145\1\156\2\uffff\1\146\2\uffff";
    static final String DFA4_maxS =
        "\1\167\2\uffff\1\151\1\141\1\164\1\163\1\150\1\164\1\151\1\163\1\156\2\uffff\1\160\2\uffff";
    static final String DFA4_acceptS =
        "\1\uffff\1\1\1\2\11\uffff\1\5\1\6\1\uffff\1\3\1\4";
    static final String DFA4_specialS =
        "\21\uffff}>";
    static final String[] DFA4_transitionS = {
            "\1\1\5\uffff\1\4\3\uffff\1\2\6\uffff\1\3",
            "",
            "",
            "\1\5",
            "\1\6",
            "\1\7",
            "\1\10",
            "\1\11",
            "\1\12",
            "\1\13",
            "\1\15\15\uffff\1\14",
            "\1\16",
            "",
            "",
            "\1\17\11\uffff\1\20",
            "",
            ""
    };

    static final short[] DFA4_eot = DFA.unpackEncodedString(DFA4_eotS);
    static final short[] DFA4_eof = DFA.unpackEncodedString(DFA4_eofS);
    static final char[] DFA4_min = DFA.unpackEncodedStringToUnsignedChars(DFA4_minS);
    static final char[] DFA4_max = DFA.unpackEncodedStringToUnsignedChars(DFA4_maxS);
    static final short[] DFA4_accept = DFA.unpackEncodedString(DFA4_acceptS);
    static final short[] DFA4_special = DFA.unpackEncodedString(DFA4_specialS);
    static final short[][] DFA4_transition;

    static {
        int numStates = DFA4_transitionS.length;
        DFA4_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA4_transition[i] = DFA.unpackEncodedString(DFA4_transitionS[i]);
        }
    }

    class DFA4 extends DFA {

        public DFA4(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 4;
            this.eot = DFA4_eot;
            this.eof = DFA4_eof;
            this.min = DFA4_min;
            this.max = DFA4_max;
            this.accept = DFA4_accept;
            this.special = DFA4_special;
            this.transition = DFA4_transition;
        }
        public String getDescription() {
            return "2208:13: ( 'futr' | 'past' | 'withinf' | 'withinp' | 'lasts' | 'lasted' )";
        }
    }
    static final String DFA17_eotS =
        "\1\uffff\1\37\1\42\1\44\1\uffff\3\36\1\52\1\53\2\uffff\12\40\2\36\2\uffff\3\36\4\uffff\1\40\12\uffff\13\40\6\uffff\1\40\1\uffff\2\40\1\120\4\40\1\120\3\40\1\132\1\uffff\2\40\2\120\1\uffff\2\40\1\120\1\40\2\120\1\132\2\40\2\uffff\2\51\4\40\1\132\1\40\2\uffff\4\40\1\132\2\51\1\40\2\132\1\40\1\120";
    static final String DFA17_eofS =
        "\162\uffff";
    static final String DFA17_minS =
        "\1\0\2\60\1\172\1\uffff\1\46\1\174\1\41\1\76\1\55\2\uffff\1\156\1\151\1\145\1\162\2\145\1\154\1\165\1\151\1\141\1\60\1\101\2\uffff\2\0\1\52\4\uffff\1\163\1\uffff\1\157\10\uffff\1\164\1\156\1\155\1\154\1\151\1\170\1\163\1\167\2\164\1\163\6\uffff\2\164\1\151\1\143\1\60\1\145\1\147\2\164\1\60\1\162\1\150\1\164\1\60\1\55\1\154\1\145\2\60\1\uffff\1\141\1\147\1\60\1\145\3\60\1\151\1\145\1\uffff\1\143\2\60\1\163\1\145\1\162\1\156\1\60\1\144\2\uffff\1\145\1\162\1\144\1\146\3\60\1\141\2\60\1\171\1\60";
    static final String DFA17_maxS =
        "\1\uffff\3\172\1\uffff\1\46\1\174\1\41\1\76\1\55\2\uffff\1\156\1\157\1\145\1\162\2\145\1\154\1\165\1\151\1\141\1\71\1\172\2\uffff\2\uffff\1\57\4\uffff\1\163\1\uffff\1\157\10\uffff\1\164\1\156\1\155\1\154\1\151\1\170\1\163\1\167\2\164\1\163\6\uffff\2\164\1\151\1\143\1\172\1\145\1\147\2\164\1\172\1\162\1\150\1\164\1\172\1\55\1\154\1\145\2\172\1\uffff\1\141\1\147\1\172\1\145\3\172\1\151\1\163\1\uffff\1\160\2\172\1\163\1\145\1\162\1\156\1\172\1\144\2\uffff\1\145\1\162\1\144\1\160\3\172\1\141\2\172\1\171\1\172";
    static final String DFA17_acceptS =
        "\4\uffff\1\4\5\uffff\2\13\14\uffff\1\20\1\21\3\uffff\1\25\1\26\1\1\1\20\1\uffff\1\2\1\uffff\1\3\1\4\1\7\1\10\1\11\1\14\1\12\1\13\13\uffff\1\17\1\21\1\22\1\23\1\24\1\25\23\uffff\1\15\11\uffff\1\16\11\uffff\1\5\1\6\14\uffff";
    static final String DFA17_specialS =
        "\1\1\31\uffff\1\0\1\2\126\uffff}>";
    static final String[] DFA17_transitionS = {
            "\11\36\2\35\2\36\1\35\22\36\1\35\1\7\1\32\3\36\1\5\1\33\1\3\1\4\3\36\1\10\1\26\1\34\12\31\2\36\1\11\1\12\1\13\2\36\17\30\1\1\12\30\3\36\1\27\1\30\1\36\1\22\4\30\1\23\5\30\1\25\1\30\1\20\1\30\1\2\1\30\1\16\1\15\1\17\1\14\1\30\1\24\1\30\1\21\1\30\1\36\1\6\uff83\36",
            "\12\40\7\uffff\32\40\4\uffff\1\40\1\uffff\32\40",
            "\12\40\7\uffff\32\40\4\uffff\1\40\1\uffff\1\41\31\40",
            "\1\43",
            "",
            "\1\46",
            "\1\47",
            "\1\50",
            "\1\51",
            "\1\51",
            "",
            "",
            "\1\54",
            "\1\55\5\uffff\1\56",
            "\1\57",
            "\1\60",
            "\1\61",
            "\1\62",
            "\1\63",
            "\1\64",
            "\1\65",
            "\1\66",
            "\12\67",
            "\32\40\4\uffff\1\40\1\uffff\32\40",
            "",
            "",
            "\0\71",
            "\0\71",
            "\1\72\4\uffff\1\73",
            "",
            "",
            "",
            "",
            "\1\75",
            "",
            "\1\76",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "\1\77",
            "\1\100",
            "\1\101",
            "\1\102",
            "\1\103",
            "\1\104",
            "\1\105",
            "\1\106",
            "\1\107",
            "\1\110",
            "\1\111",
            "",
            "",
            "",
            "",
            "",
            "",
            "\1\112",
            "\1\113",
            "\1\114",
            "\1\115",
            "\12\40\7\uffff\32\40\4\uffff\1\40\1\uffff\5\40\1\116\11\40\1\117\12\40",
            "\1\121",
            "\1\122",
            "\1\123",
            "\1\124",
            "\12\40\7\uffff\32\40\4\uffff\1\40\1\uffff\5\40\1\125\11\40\1\126\12\40",
            "\1\127",
            "\1\130",
            "\1\131",
            "\12\40\7\uffff\32\40\4\uffff\1\40\1\uffff\32\40",
            "\1\133",
            "\1\134",
            "\1\135",
            "\12\40\7\uffff\32\40\4\uffff\1\40\1\uffff\32\40",
            "\12\40\7\uffff\32\40\4\uffff\1\40\1\uffff\32\40",
            "",
            "\1\136",
            "\1\137",
            "\12\40\7\uffff\32\40\4\uffff\1\40\1\uffff\32\40",
            "\1\140",
            "\12\40\7\uffff\32\40\4\uffff\1\40\1\uffff\32\40",
            "\12\40\7\uffff\32\40\4\uffff\1\40\1\uffff\32\40",
            "\12\40\7\uffff\32\40\4\uffff\1\40\1\uffff\32\40",
            "\1\141",
            "\1\143\15\uffff\1\142",
            "",
            "\1\145\14\uffff\1\144",
            "\12\40\7\uffff\32\40\4\uffff\1\40\1\uffff\32\40",
            "\12\40\7\uffff\32\40\4\uffff\1\40\1\uffff\32\40",
            "\1\146",
            "\1\147",
            "\1\150",
            "\1\151",
            "\12\40\7\uffff\32\40\4\uffff\1\40\1\uffff\32\40",
            "\1\152",
            "",
            "",
            "\1\153",
            "\1\154",
            "\1\155",
            "\1\156\11\uffff\1\157",
            "\12\40\7\uffff\32\40\4\uffff\1\40\1\uffff\32\40",
            "\12\40\7\uffff\32\40\4\uffff\1\40\1\uffff\32\40",
            "\12\40\7\uffff\32\40\4\uffff\1\40\1\uffff\32\40",
            "\1\160",
            "\12\40\7\uffff\32\40\4\uffff\1\40\1\uffff\32\40",
            "\12\40\7\uffff\32\40\4\uffff\1\40\1\uffff\32\40",
            "\1\161",
            "\12\40\7\uffff\32\40\4\uffff\1\40\1\uffff\32\40"
    };

    static final short[] DFA17_eot = DFA.unpackEncodedString(DFA17_eotS);
    static final short[] DFA17_eof = DFA.unpackEncodedString(DFA17_eofS);
    static final char[] DFA17_min = DFA.unpackEncodedStringToUnsignedChars(DFA17_minS);
    static final char[] DFA17_max = DFA.unpackEncodedStringToUnsignedChars(DFA17_maxS);
    static final short[] DFA17_accept = DFA.unpackEncodedString(DFA17_acceptS);
    static final short[] DFA17_special = DFA.unpackEncodedString(DFA17_specialS);
    static final short[][] DFA17_transition;

    static {
        int numStates = DFA17_transitionS.length;
        DFA17_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA17_transition[i] = DFA.unpackEncodedString(DFA17_transitionS[i]);
        }
    }

    class DFA17 extends DFA {

        public DFA17(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 17;
            this.eot = DFA17_eot;
            this.eof = DFA17_eof;
            this.min = DFA17_min;
            this.max = DFA17_max;
            this.accept = DFA17_accept;
            this.special = DFA17_special;
            this.transition = DFA17_transition;
        }
        public String getDescription() {
            return "1:1: Tokens : ( T__16 | T__17 | T__18 | T__19 | T__20 | T__21 | T__22 | T__23 | T__24 | T__25 | RULE_PROPOP | RULE_OP2 | RULE_OPF | RULE_OPFN | RULE_FLOAT | RULE_ID | RULE_INT | RULE_STRING | RULE_ML_COMMENT | RULE_SL_COMMENT | RULE_WS | RULE_ANY_OTHER );";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            IntStream input = _input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA17_26 = input.LA(1);

                        s = -1;
                        if ( ((LA17_26>='\u0000' && LA17_26<='\uFFFF')) ) {s = 57;}

                        else s = 30;

                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA17_0 = input.LA(1);

                        s = -1;
                        if ( (LA17_0=='P') ) {s = 1;}

                        else if ( (LA17_0=='p') ) {s = 2;}

                        else if ( (LA17_0=='(') ) {s = 3;}

                        else if ( (LA17_0==')') ) {s = 4;}

                        else if ( (LA17_0=='&') ) {s = 5;}

                        else if ( (LA17_0=='|') ) {s = 6;}

                        else if ( (LA17_0=='!') ) {s = 7;}

                        else if ( (LA17_0=='-') ) {s = 8;}

                        else if ( (LA17_0=='<') ) {s = 9;}

                        else if ( (LA17_0=='=') ) {s = 10;}

                        else if ( (LA17_0=='>') ) {s = 11;}

                        else if ( (LA17_0=='u') ) {s = 12;}

                        else if ( (LA17_0=='s') ) {s = 13;}

                        else if ( (LA17_0=='r') ) {s = 14;}

                        else if ( (LA17_0=='t') ) {s = 15;}

                        else if ( (LA17_0=='n') ) {s = 16;}

                        else if ( (LA17_0=='y') ) {s = 17;}

                        else if ( (LA17_0=='a') ) {s = 18;}

                        else if ( (LA17_0=='f') ) {s = 19;}

                        else if ( (LA17_0=='w') ) {s = 20;}

                        else if ( (LA17_0=='l') ) {s = 21;}

                        else if ( (LA17_0=='.') ) {s = 22;}

                        else if ( (LA17_0=='^') ) {s = 23;}

                        else if ( ((LA17_0>='A' && LA17_0<='O')||(LA17_0>='Q' && LA17_0<='Z')||LA17_0=='_'||(LA17_0>='b' && LA17_0<='e')||(LA17_0>='g' && LA17_0<='k')||LA17_0=='m'||LA17_0=='o'||LA17_0=='q'||LA17_0=='v'||LA17_0=='x'||LA17_0=='z') ) {s = 24;}

                        else if ( ((LA17_0>='0' && LA17_0<='9')) ) {s = 25;}

                        else if ( (LA17_0=='\"') ) {s = 26;}

                        else if ( (LA17_0=='\'') ) {s = 27;}

                        else if ( (LA17_0=='/') ) {s = 28;}

                        else if ( ((LA17_0>='\t' && LA17_0<='\n')||LA17_0=='\r'||LA17_0==' ') ) {s = 29;}

                        else if ( ((LA17_0>='\u0000' && LA17_0<='\b')||(LA17_0>='\u000B' && LA17_0<='\f')||(LA17_0>='\u000E' && LA17_0<='\u001F')||(LA17_0>='#' && LA17_0<='%')||(LA17_0>='*' && LA17_0<=',')||(LA17_0>=':' && LA17_0<=';')||(LA17_0>='?' && LA17_0<='@')||(LA17_0>='[' && LA17_0<=']')||LA17_0=='`'||LA17_0=='{'||(LA17_0>='}' && LA17_0<='\uFFFF')) ) {s = 30;}

                        if ( s>=0 ) return s;
                        break;
                    case 2 : 
                        int LA17_27 = input.LA(1);

                        s = -1;
                        if ( ((LA17_27>='\u0000' && LA17_27<='\uFFFF')) ) {s = 57;}

                        else s = 30;

                        if ( s>=0 ) return s;
                        break;
            }
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 17, _s, input);
            error(nvae);
            throw nvae;
        }
    }
 

}