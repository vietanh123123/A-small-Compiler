package tinycc.implementation.type;

import tinycc.diagnostic.Locatable;



public class PointerType extends Type {
    private final Type type;

    public PointerType(Locatable loc, Type type) {
        super(loc);
        this.type = type;
    }

    public Type getType() {
        return type;
    }
    
    @Override
    public String toString() {
        return "Pointer[" + "Type_" + type.toString() + "]";
    }
}
