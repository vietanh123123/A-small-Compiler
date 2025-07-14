package tinycc.implementation.expression.primary;

import tinycc.implementation.expression.Expression;
import tinycc.diagnostic.Locatable;

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
}
