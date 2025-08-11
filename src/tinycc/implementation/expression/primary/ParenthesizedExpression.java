package tinycc.implementation.expression.primary;

import tinycc.implementation.expression.Expression;
import tinycc.diagnostic.Locatable;
import tinycc.implementation.type.Type;
import tinycc.diagnostic.Diagnostic;
import tinycc.implementation.Scope;

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
    @Override 
    public Type checkType(Diagnostic d, Scope s) {
        // Assuming the inner expression's type is the type of the parenthesized expression
        return inner.checkType(d, s);
    }
}
