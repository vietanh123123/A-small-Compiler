package tinycc.implementation.expression.primary;

import tinycc.implementation.expression.Expression;

public class ParenthesizedExpression extends Expression {
    private final Expression inner;

    public ParenthesizedExpression(Expression inner) {
        this.inner = inner;
    }

    public Expression getInner() {
        return inner;
    }

    @Override 
    public String toString() {
        return "(" + inner.toString() + ")";
    }
}
