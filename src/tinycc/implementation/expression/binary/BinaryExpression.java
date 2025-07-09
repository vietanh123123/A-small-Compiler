package tinycc.implementation.expression.binary;


import tinycc.implementation.expression.Expression;

public class BinaryExpression extends Expression {

    private final Expression left;
    private final Expression right;
    private final BinaryOperator operator; 

    public BinaryExpression(Expression left, Expression right, BinaryOperator operator) {
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
