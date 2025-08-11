package tinycc.implementation.expression.primary;

import tinycc.implementation.expression.Expression;
import tinycc.diagnostic.Locatable;
import tinycc.implementation.type.BaseType;
import tinycc.implementation.type.Type;
import tinycc.implementation.type.TypeExpression;
import tinycc.diagnostic.Diagnostic;
import tinycc.implementation.Scope;
import tinycc.implementation.statement.block.Declaration;
public class Identifier extends Expression {
    private final String name;

    public Identifier(Locatable loc, String name) {
        super(loc);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Var_" + name;
    }
    @Override
    public Type checkType(Diagnostic d, Scope s) {
        try {
            //Look up the declaration for this identifier 
            Declaration decl = s.lookup(name);

            //Return the type from the declaration 
            return decl.getType();
        } catch (IllegalArgumentException e) {
            d.printError(this,"Undeclared Identifier" + name);
            throw e;
        }
    }
}
