package tinycc.implementation.expression.primary;

import tinycc.implementation.expression.Expression;
import tinycc.diagnostic.Locatable;
import tinycc.implementation.type.BaseType;
import tinycc.implementation.type.Type;
import tinycc.implementation.type.TypeExpression;
import tinycc.diagnostic.Diagnostic;
import tinycc.implementation.Scope;


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
    @Override
    public Type checkType(Diagnostic d, Scope s) {
        return new TypeExpression(BaseType.CHAR);
    }
}