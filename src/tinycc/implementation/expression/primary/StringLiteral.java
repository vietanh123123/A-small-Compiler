package tinycc.implementation.expression.primary;

import tinycc.implementation.expression.Expression;
public class StringLiteral extends Expression {
    private final String value; 

    public StringLiteral(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override 
    public String toString() {
        return "Const_\"" + value + "\"";
    }
}
