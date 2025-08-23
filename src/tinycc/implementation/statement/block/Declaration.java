package tinycc.implementation.statement.block;

import tinycc.implementation.type.Type;
import tinycc.implementation.type.PointerType;
import tinycc.implementation.expression.Expression;
import tinycc.implementation.expression.primary.Identifier;
import tinycc.implementation.expression.primary.IntegerConstant;
import tinycc.diagnostic.Locatable;
import tinycc.diagnostic.Diagnostic;
import tinycc.implementation.Scope;
import tinycc.implementation.statement.Statement;

public class Declaration extends Statement {
    public final Type type;
    public final Identifier identifier;
    public final Expression exp;  // Optional, can be null
    

    public Declaration(Locatable loc, Type type, Identifier identifier, Expression exp) {
        super(loc);
        this.type = type;
        this.identifier = identifier;
        this.exp = exp;
        
    }

    public Type getType() {
        return type;
    }
    
    public Identifier getIdentifier() {
        return identifier;
    }
    
    public Expression getInitializer() {
        return exp;
    }
    
    /**
     * Checks if an initializer type can be assigned to a variable type using assignment rules
     */
    private boolean isValidInitializerAssignment(Type variableType, Type initializerType, Expression initializer) {
        // Assignment Rule 1: The types are identical
        if (variableType.equals(initializerType)) {
            return true;
        }
        
        // Assignment Rule 2: Both have integer types (includes char to int conversion)
        if ((variableType.isInt() || variableType.isChar()) && (initializerType.isInt() || initializerType.isChar())) {
            return true;
        }
        
        // Assignment Rule 3: Both have pointer types and at least one has type void*
        if (variableType.isPointer() && initializerType.isPointer()) {
            PointerType varPtr = (PointerType) variableType;
            PointerType initPtr = (PointerType) initializerType;
            if (varPtr.getType().isVoid() || initPtr.getType().isVoid()) {
                return true;
            }
        }
        
        // Assignment Rule 4: Variable has pointer type and initializer is null pointer constant
        if (variableType.isPointer() && initializer instanceof IntegerConstant &&
            ((IntegerConstant) initializer).getValue() == 0) {
            return true;
        }
        
        return false;
    }

    @Override
    public String toString() {
        if (exp == null) {
            return "Declaration_" + identifier.getName() + "[" + type.toString() + "]";
        } else {
            return "Declaration_" + identifier.getName() + "[" + type.toString() + ", " + exp.toString() + "]";
        }
    }
    
    @Override
    public void checkSemantics(Diagnostic d, Scope s) {
        // Check if variable type is void (not allowed)
        if (type.isVoid()) {
            d.printError(this, "Variables must not be defined with type void");
            return;
        }
        
        // Check if already declared in current scope (not parent scopes)
        try {
            s.lookup(identifier.getName());
            // If we get here without exception, variable is already declared
            // We need to check if it's in the same scope - for now, report error
            d.printError(this, "Variable already declared: " + identifier.getName());
            return;
        } catch (IllegalArgumentException e) {
            // Good - variable not declared yet
        }
        
        // Check initializer if present
        if (exp != null) {
            Type initializerType = exp.checkType(d, s);
            
            // If initializer has error type, don't continue
            if (initializerType.isError()) {
                return;
            }
            
            // Check if initializer can be assigned to variable
            if (!isValidInitializerAssignment(type, initializerType, exp)) {
                d.printError(this, "Cannot initialize variable of type " + type + 
                            " with value of type " + initializerType);
                return;
            }
        }
        
        // Add variable to scope
        s.add(identifier.getName(), this);
    }
}
