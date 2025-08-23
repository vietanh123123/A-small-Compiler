package tinycc.implementation;

import tinycc.implementation.type.Type;

/**
 * Context information for semantic analysis, including current function's return type
 */
public class SemanticContext {
    private final Type currentFunctionReturnType;
    
    public SemanticContext(Type currentFunctionReturnType) {
        this.currentFunctionReturnType = currentFunctionReturnType;
    }
    
    public Type getCurrentFunctionReturnType() {
        return currentFunctionReturnType;
    }
    
    /**
     * Creates a new context with the same function return type
     */
    public SemanticContext copy() {
        return new SemanticContext(currentFunctionReturnType);
    }
}
