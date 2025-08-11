package tinycc.implementation.top_level_construct;

import tinycc.implementation.expression.primary.Identifier;
import tinycc.implementation.type.Type;

public class Parameter {
    private final Type type;
    private final Identifier identifier;  // Maybe null

    public Parameter(Type type, Identifier identifier) {
        this.type = type;
        this.identifier = identifier;
    }
    public Parameter(Type type) {
        this(type, null);
    }


}
