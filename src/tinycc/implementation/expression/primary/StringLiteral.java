package tinycc.implementation.expression.primary;

import tinycc.implementation.expression.Expression;



import tinycc.diagnostic.Locatable;
import tinycc.implementation.type.BaseType;
import tinycc.implementation.type.Type;
import tinycc.implementation.type.TypeExpression;
import tinycc.implementation.type.PointerType;
import tinycc.diagnostic.Diagnostic;
import tinycc.implementation.Scope;


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

    @Override 
    public Type checkType(Diagnostic d, Scope s) {
        // Assuming a string literal is treated as a pointer to char type
        return new PointerType(new TypeExpression(BaseType.CHAR));
    }
}
