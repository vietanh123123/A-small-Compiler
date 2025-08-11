package tinycc.implementation.top_level_construct;

import tinycc.diagnostic.Locatable;
import tinycc.implementation.type.Type;
import tinycc.implementation.expression.primary.Identifier;


public class GlobalVariable  {
    private final Type type;
    private final Identifier identifier;
    
    public GlobalVariable(Type type, Identifier identifier) {
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
