package tinycc.implementation.expression.binary;

public enum BinaryOperator {
    ADD("+"),
    SUBTRACT("-"),
    MUL("*"),
    DIV("/"),
    ASSIGN("="),
    EQUAL("=="),
    NOT_EQUAL("!="),
    LESS_THAN("<"),
    LESS_EQUAL("<="),
    GREATER_THAN(">"),
    GREATER_EQUAL(">="),
    AND("&&"),
    OR("||");


    private final String represent;
    
    BinaryOperator(String represent) {
        this.represent = represent;
    }

    @Override 
    public String toString() {
        return this.represent;
    }
}
