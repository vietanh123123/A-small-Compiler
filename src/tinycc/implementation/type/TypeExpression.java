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
    
    @Override 
    public boolean isInt() {
        return type == BaseType.INT;
    }
    @Override 
    public boolean isChar() {
        return type == BaseType.CHAR;
    }
    @Override 
    public boolean isPointer() {
        return false; 
    }
    @Override 
    public boolean isVoid() {
        return type == BaseType.VOID;
    }

}
