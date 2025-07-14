package tinycc.implementation.expression;


import tinycc.diagnostic.Locatable;

public class ConditionalExpression extends Expression{
    private final Expression condition;
    private final Expression consequenc;
    private final Expression alternative;
    
    public ConditionalExpression(Locatable loc, Expression condition, Expression consequence, Expression alternative) {
        super(loc);
        this.condition = condition;
        this.consequenc = consequence;
        this.alternative = alternative;
    }

    @Override 
    public String toString() {
        return "Conditional[" + condition + " ? " + consequenc + " : " + alternative + "]";
    }

}
