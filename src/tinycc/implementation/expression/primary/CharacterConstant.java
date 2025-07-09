package tinycc.implementation.expression.primary;

import tinycc.implementation.expression.Expression;

public class CharacterConstant extends Expression {
    private final char value; 

    public CharacterConstant(char value) {
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