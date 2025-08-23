package tinycc.implementation.top_level_construct;

import java.util.List;

import tinycc.implementation.expression.primary.Identifier;
import tinycc.implementation.statement.block.Block;
import tinycc.implementation.statement.block.Declaration;
import tinycc.implementation.type.Type;
import tinycc.implementation.type.FunctionType;
import tinycc.diagnostic.Locatable;
import tinycc.diagnostic.Diagnostic;
import tinycc.implementation.Scope;
import tinycc.implementation.SemanticContext;

//Need to modify toString
public class Function extends ExternalDeclaration {
	public final Type type;
    public final Identifier identifier;
    public final List<NamedParameter> namedParameters;
    public final Block block; 
    
    
    public Function(Locatable loc, Type type, Identifier name, 
                              List<NamedParameter> parameterNames, Block body) {
        super(loc); 
        this.type = type;
        this.identifier = name;
        this.namedParameters = parameterNames;
        this.block = body;
    }
    public Type getType() {
        return type;
    }
    
    public Identifier getIdentifier() {
        return identifier;
    }
    
    public List<NamedParameter> getParameters() {
        return namedParameters;
    }
    
    public Block getBody() {
        return block;
    }
    
    /**
     * Performs semantic analysis on this function
     */
    public void checkSemantics(Diagnostic d, Scope s) {
        // Check parameter types
        for (NamedParameter param : namedParameters) {
            Type paramType = param.getType();
            
            // Function parameters must not be void
            if (paramType.isVoid()) {
                d.printError(this, "Function parameter must not be of type void");
                return;
            }
        }
        
        // Create new scope for function body
        Scope functionScope = s.newNestedScope();
        
        // Add parameters to function scope
        for (NamedParameter param : namedParameters) {
            // Create a declaration for the parameter to add to scope
            Declaration paramDecl = new Declaration(param.getIdentifier(), param.getType(), param.getIdentifier(), null);
            functionScope.add(param.getIdentifier().getName(), paramDecl);
        }
        
        // Create semantic context with function return type
        Type functionReturnType = type.isFunction() ? ((FunctionType) type).getReturnType() : type;
        SemanticContext context = new SemanticContext(functionReturnType);
        
        // Check function body with context
        block.checkSemantics(d, functionScope, context);
    }
    
    @Override
    public void checkSemantics(Scope globalScope, Diagnostic diagnostic) {
        // Add function to global scope so it can be called (including recursively)
        Declaration functionDecl = new Declaration(identifier, type, identifier, null);
        globalScope.add(identifier.getName(), functionDecl);
        
        // Call the existing checkSemantics method with reordered parameters
        checkSemantics(diagnostic, globalScope);
    }
    
    @Override 
    public String toString() {
        return "Function{" +
                "type=" + type +
                ", identifier=" + identifier +
                ", namedParameters=" + namedParameters +
                ", block=" + block +
                '}';
    }

}
