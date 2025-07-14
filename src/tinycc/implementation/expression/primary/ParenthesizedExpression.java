package tinycc.implementation.expression.primary;

import tinycc.implementation.expression.Expression;
import tinycc.diagnostic.Locatable;

public class ParenthesizedExpression extends Expression {
    private final Expression inner;

    public ParenthesizedExpression(Locatable loc, Expression inner) {
        super(loc);
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
