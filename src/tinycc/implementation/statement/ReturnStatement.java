package tinycc.implementation.statement;

import tinycc.implementation.expression.Expression;
import tinycc.diagnostic.Locatable;


public class ReturnStatement extends Statement {
    public final Expression exp;  // Optional, can be null

    public ReturnStatement(Locatable loc, Expression exp) {
        super(loc);
        this.exp = exp;
    }

    @Override 
    public String toString() {
        if (exp == null) {
            return "Return[]";
        } else {
            return "Return[" + exp.toString() + "]";
        }
    }
}
