package tinycc.implementation.statement.block;

import tinycc.implementation.type.Type;
import tinycc.implementation.expression.Expression;
import tinycc.implementation.expression.primary.Identifier;

public class Declaration implements BlockInside{
    public final Type type;
    public final Identifier identifier;
    public final Expression exp;  // Optional, can be null

    public Declaration(Type type, Identifier identifier, Expression exp) {
        this.type = type;
        this.identifier = identifier;
        this.exp = exp;
    }

    
    @Override
    public String toString() {
        if (exp == null) {
            return "Declaration_" + identifier.getName() + "[" + type.toString() + "]";
        } else {
            return "Declaration_" + identifier.getName() + "[" + type.toString() + ", " + exp.toString() + "]";
        }
    }
}
