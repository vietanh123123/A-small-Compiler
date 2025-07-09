package tinycc.implementation.expression.primary;

import tinycc.implementation.expression.Expression;

public class IntegerConstant extends Expression {
    private final int value; 

    public IntegerConstant(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    @Override 
    public String toString() {
        return "Const_" + value;
    }
}
