package tinycc.implementation.expression.primary;

import tinycc.implementation.expression.Expression;
import tinycc.diagnostic.Locatable;

public class IntegerConstant extends Expression {
    private final int value; 

    public IntegerConstant(Locatable loc, int value) {
        super(loc);
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
