package tinycc.implementation.top_level_construct;

import tinycc.diagnostic.Locatable;
import tinycc.diagnostic.Diagnostic;
import tinycc.implementation.Scope;
import tinycc.implementation.type.Type;
import tinycc.implementation.expression.primary.Identifier;
import tinycc.implementation.statement.block.Declaration;

public class GlobalVariable extends ExternalDeclaration {
    public final Type type;
    public final Identifier identifier;
    
    public GlobalVariable(Locatable loc, Type type, Identifier identifier) {
        super(loc);  // Call parent constructor
        this.type = type;
        this.identifier = identifier;
    }
    
    public Type getType() {
        return type;
    }
    
    public Identifier getIdentifier() {
        return identifier;
    }
    
    @Override
    public String toString() {
        return "GlobalVariable{" +
                "type=" + type +
                ", identifier=" + identifier +
                '}';
    }
    
    @Override
    public void checkSemantics(Scope globalScope, Diagnostic diagnostic) {
        // For global variables, check that the type is valid
        if (type == null) {
            diagnostic.printError(this, "Global variable must have a type");
            return;
        }
        
        // Check that the variable type is not void
        if (type.isVoid()) {
            diagnostic.printError(this, "Global variable cannot be of type void");
            return;
        }
        
        // Add the global variable to the global scope so it can be referenced
        // Create a Declaration object to store in the scope
        Declaration globalDecl = new Declaration(identifier, type, identifier, null);
        globalScope.add(identifier.getName(), globalDecl);
    }
}