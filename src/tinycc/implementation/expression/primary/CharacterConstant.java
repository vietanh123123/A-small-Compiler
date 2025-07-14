package tinycc.implementation.expression.primary;

import tinycc.implementation.expression.Expression;
import tinycc.diagnostic.Locatable;

public class CharacterConstant extends Expression {
    private final char value; 

    public CharacterConstant(Locatable loc, char value) {
        super(loc);
        this.value = value;
    }

    public char getValue() {
        return value;
    }

    @Override 
    public String toString() {
        return "Const_'" + value + "'";
    }
}