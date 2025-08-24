package tinycc.implementation.type;

import tinycc.diagnostic.Locatable;

/**
 * The main type class (see project description)
 *
 * You can change this class but the given name of the class must not be
 * modified.
 */
public abstract class Type implements Locatable {
    protected final Locatable loc;

    public Type(Locatable loc) {
        this.loc = loc;
    }

    public Locatable getLoc() {
        return loc;
    }
    
    @Override
    public int getLine() {
        return loc != null ? loc.getLine() : 0;
    }
    
    @Override
    public int getColumn() {
        return loc != null ? loc.getColumn() : 0;
    }
    
    @Override
    public String getInputName() {
        return loc != null ? loc.getInputName() : "";
    }
    
    public abstract boolean isInt();
    public abstract boolean isChar();
    public abstract boolean isVoid();
    public abstract boolean isPointer();
    
    /**
     * Checks if this type is a function type
     * @return true if this is a FunctionType, false otherwise
     */
    public boolean isFunction() {
        return false; // Default implementation - only FunctionType returns true
    }
    
    /**
     * Checks if this type is an error type
     * @return true if this is an ErrorType, false otherwise
     */
    public boolean isError() {
        return false; // Default implementation - only ErrorType returns true
    }
    
    /**
     * Checks if this type is compatible with another type for assignment.
     * Two types are compatible if:
     * 1. They are identical types
     * 2. Both are integer types (int or char)
     * 3. Both are pointer types and either:
     *    - At least one is void*
     *    - Their pointee types are compatible
     * @param other The other type to check compatibility with
     * @return true if the types are compatible, false otherwise
     */
    public boolean isCompatibleWith(Type other) {
        if (this == other) return true;
        
        // Both are integer types
        if ((this.isInt() || this.isChar()) && (other.isInt() || other.isChar())) {
            return true;
        }
        
        // Both are void
        if (this.isVoid() && other.isVoid()) {
            return true;
        }
        
        // Both are pointer types
        if (this.isPointer() && other.isPointer()) {
            PointerType thisPtr = (PointerType) this;
            PointerType otherPtr = (PointerType) other;
            
            // If either is void*, they're compatible
            if (thisPtr.getType().isVoid() || otherPtr.getType().isVoid()) {
                return true;
            }
            
            // Recursively check pointee types
            return thisPtr.getType().isCompatibleWith(otherPtr.getType());
        }
        
        return false;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Type)) return false;
        Type other = (Type) obj;
        return isCompatibleWith(other) && other.isCompatibleWith(this);
    }
    
    @Override
    public int hashCode() {
        return loc != null ? loc.hashCode() : 0;
    }
}
