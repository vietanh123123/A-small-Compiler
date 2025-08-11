package tinycc.implementation.type;

import tinycc.diagnostic.Locatable;



public class PointerType extends Type {
    private final Type type;

    public PointerType(Locatable loc, Type type) {
        super(loc);
        this.type = type;
    }
    public PointerType(Type type) {
        super(null);  // Assuming loc is not needed for pointer types if not specified
        this.type = type;
    }

    public Type getType() {
        return type;
    }
    
    @Override
    public String toString() {
        return "Pointer[" + type.toString() + "]";
    }
    @Override 
    public boolean isInt() {
        return false;
    }
    @Override 
    public boolean isChar() {
        return false;
    }
    @Override
    public boolean isPointer() {
        return true;
    }
    @Override
    public boolean isVoid() {
        return false;
    }
}
