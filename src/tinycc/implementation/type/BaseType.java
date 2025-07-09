package tinycc.implementation.type;

public enum BaseType {
    CHAR("char"),
    INT("int"),
    VOID("void");

    private final String name;
    // The name of the base type, used for string representation.
    BaseType(String name) {
        this.name = name;
    }
    @Override
    public String toString() {
        // Returns the string representation of the base type.
        return name;
    }
}
