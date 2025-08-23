package tinycc.implementation.type;

import tinycc.diagnostic.Location;

/**
 * A special type used to represent expressions that have already caused type errors.
 * This prevents cascading error messages and helps with error recovery during
 * semantic analysis.
 */
public final class ErrorType extends Type {
    
    // Singleton instance to avoid creating multiple error type objects
    private static final ErrorType INSTANCE = new ErrorType();
    
    /**
     * Private constructor to enforce singleton pattern
     */
    private ErrorType() {
        super(new Location("<error>", 0, 0)); // Dummy location for error type
    }
    
    /**
     * Gets the singleton instance of ErrorType
     * @return The singleton ErrorType instance
     */
    public static ErrorType getInstance() {
        return INSTANCE;
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
    public boolean isVoid() {
        return false;
    }
    
    @Override
    public boolean isPointer() {
        return false;
    }
    
    /**
     * Checks if this type is an error type
     * @return true, since this is an ErrorType
     */
    public boolean isError() {
        return true;
    }
    
    @Override
    public boolean equals(Object other) {
        return other instanceof ErrorType;
    }
    
    @Override
    public int hashCode() {
        return ErrorType.class.hashCode();
    }
    
    @Override
    public String toString() {
        return "<error-type>";
    }
}
