package tinycc.implementation.expression.primary;

import tinycc.implementation.expression.Expression;
import tinycc.diagnostic.Locatable;

public class StringLiteral extends Expression {
    private final String value; 

    public StringLiteral(Locatable loc, String value) {
        super(loc);
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
