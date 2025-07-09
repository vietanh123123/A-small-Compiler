package tinycc.implementation.expression.unary;

import tinycc.implementation.expression.Expression;

public class UnaryExpression extends Expression {
    private final Expression expression;
    private final UnaryOperator operator;

    public UnaryExpression(Expression expression, UnaryOperator operator) {
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
