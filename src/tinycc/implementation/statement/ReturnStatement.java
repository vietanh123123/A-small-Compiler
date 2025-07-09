package tinycc.implementation.statement;

import tinycc.implementation.expression.Expression;


public class ReturnStatement extends Statement {
    public final Expression exp;  // Optional, can be null

    public ReturnStatement(Expression exp) {
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
