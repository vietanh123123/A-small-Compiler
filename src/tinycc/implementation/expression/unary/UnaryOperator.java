package tinycc.implementation.expression.unary;

public enum UnaryOperator {
    DEREFERENCE("*"),
    ADDRESS_OF("&"),
    SIZEOF("sizeof"),
    NOT("!");

    private final String represent;

    UnaryOperator(String represent) {
        this.represent = represent;
    }

    @Override 
    public String toString() {
        return this.represent;
    }
}
