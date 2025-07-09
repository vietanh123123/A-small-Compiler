package tinycc.implementation.expression.primary;

import tinycc.implementation.expression.Expression;

public class Identifier extends Expression {
    private final String name;

    public Identifier(String name) {
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
