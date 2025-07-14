package tinycc.implementation.statement;

import tinycc.implementation.expression.Expression;
import tinycc.diagnostic.Locatable;

public class ExpressionStatement extends Statement {
    public final Expression exp;

    public ExpressionStatement(Locatable loc, Expression exp) {
        super(loc);
        this.exp = exp;
    }
    
    @Override
    public String toString() {
        return exp.toString() + ";";
    }
}
