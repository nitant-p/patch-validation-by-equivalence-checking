package sg.edu.nus.se.its.validation.z3expression.nonterminal;

import com.microsoft.z3.Context;
import com.microsoft.z3.Expr;
import sg.edu.nus.se.its.validation.tokenizer.token.Token;
import sg.edu.nus.se.its.validation.translator.TranslationContext;
import sg.edu.nus.se.its.validation.z3expression.Z3Expression;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class SpreadZ3Expression implements Z3Expression {

    private final List<Z3Expression> spread;
    private Iterator<Z3Expression> it;

    public SpreadZ3Expression() {
        this.spread = new ArrayList<>();
    }

    public void add(Z3Expression expr) {
        spread.add(expr);
    }

    public void finishedAdding() {
        it = spread.iterator();
    }

    public int size() {
        return spread.size();
    }

    @Override
    public Expr interpret(TranslationContext translationContext, Context ctx) {
        // do nothing
        return null;
    }

    public Z3Expression get() {
        return it.next();
    }
}
