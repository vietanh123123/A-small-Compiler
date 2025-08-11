package tinycc.implementation.top_level_construct;

import tinycc.implementation.expression.primary.Identifier;
import tinycc.implementation.type.Type;

public class NamedParameter {
    private final Type type;
    private final Identifier identifier;

    public NamedParameter(Type type, Identifier identifier) {
        this.type = type;
        this.identifier = identifier;
    }
    public Type getType() {
        return type;
    }
    public Identifier getIdentifier() {
        return identifier;
    }
}
