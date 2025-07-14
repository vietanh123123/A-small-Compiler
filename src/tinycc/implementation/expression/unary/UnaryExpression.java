package tinycc.implementation.expression.unary;

import tinycc.implementation.expression.Expression;
import tinycc.diagnostic.Locatable;

public class UnaryExpression extends Expression {
    private final Expression expression;
    private final UnaryOperator operator;

    public UnaryExpression(Locatable loc, Expression expression, UnaryOperator operator) {
        super(loc);
        this.expression = expression;
        this.operator = operator;
    }

    public Expression getExpression() {
        return expression;
    }
    
    public UnaryOperator getOperator() {
        return operator;
    }
    @Override
    public String toString() {
        return "Unary_" + operator.toString() + "[" + expression.toString() + "]";
    }
}
