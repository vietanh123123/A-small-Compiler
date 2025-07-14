package tinycc.implementation.statement.block;

import tinycc.implementation.type.Type;
import tinycc.implementation.expression.Expression;
import tinycc.implementation.expression.primary.Identifier;
import tinycc.diagnostic.Locatable;
import tinycc.implementation.statement.Statement;

public class Declaration extends Statement {
    public final Type type;
    public final Identifier identifier;
    public final Expression exp;  // Optional, can be null
    

    public Declaration(Locatable loc, Type type, Identifier identifier, Expression exp) {
        super(loc);
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
