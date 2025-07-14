package tinycc.implementation.expression.binary;


import tinycc.implementation.expression.Expression;
import tinycc.diagnostic.Locatable;

public class BinaryExpression extends Expression {

    private final Expression left;
    private final Expression right;
    private final BinaryOperator operator; 

    public BinaryExpression(Locatable loc, Expression left, Expression right, BinaryOperator operator) {
        super(loc);
        this.left = left;
        this.right = right;
        this.operator = operator;
    }


    public Expression getLeft() {
        return left;
    }

    public Expression getRight() {
        return right;
    }

    public BinaryOperator getOperator() {
        return operator;
    }

    @Override 
    public String toString() {
        return "Binary_" + operator.toString() + "[" + left.toString() + ", " + right.toString() + "]";
    }

    
}
