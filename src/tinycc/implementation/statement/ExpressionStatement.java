package tinycc.implementation.statement;

import tinycc.implementation.expression.Expression;

public class ExpressionStatement extends Statement {
    public final Expression exp;

    public ExpressionStatement(Expression exp) {
        this.exp = exp;
    }
    
    @Override
    public String toString() {
        return exp.toString() + ";";
    }

    
}
