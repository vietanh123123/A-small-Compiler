package tinycc.implementation.type;

import tinycc.diagnostic.Locatable;


// import tinycc.implementation.type.basetype.BaseType; // Removed or update this line if BaseType is in a different package

public class TypeExpression extends Type {
    private final BaseType type;

    public TypeExpression(Locatable loc, BaseType type) {
        super(loc);
        this.type = type;
    }
    
    public TypeExpression(BaseType type) {
        super(null); // Assuming null is acceptable for Locatable
        this.type = type;
    }
    public BaseType getType() {
        return type;
    }

    @Override 
    public String toString() {
        return "Type_" + type.toString();
    }
    
 
}
