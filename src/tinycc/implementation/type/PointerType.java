package tinycc.implementation.type;



public class PointerType extends Type {
    private final Type type;

    public PointerType(Type type) {
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
