package tinycc.implementation.statement;

import tinycc.diagnostic.Locatable;
import tinycc.diagnostic.Diagnostic;
import tinycc.implementation.Scope;
import tinycc.implementation.SemanticContext;


/**
 * The main statement class (see project description)
 *
 * You can change this class but the given name of the class must not be
 * modified.
 */
public abstract class Statement implements Locatable {
    protected final Locatable loc;

    public Statement(Locatable loc) {
        this.loc = loc;
    }

    public Locatable getLoc() {
        return loc;
    }
    
    @Override
    public abstract String toString();
    
    /**
     * Performs semantic analysis on this statement
     * @param d Diagnostic for error reporting
     * @param s Scope for variable lookups
     */
    public abstract void checkSemantics(Diagnostic d, Scope s);
    
    /**
     * Performs semantic analysis on this statement with context
     * @param d Diagnostic for error reporting
     * @param s Scope for variable lookups
     * @param ctx Semantic context (e.g., current function return type)
     */
    public void checkSemantics(Diagnostic d, Scope s, SemanticContext ctx) {
        // Default implementation delegates to the original method
        checkSemantics(d, s);
    }

    @Override
    public String getInputName() { return loc.getInputName(); }
    @Override
    public int getLine() { return loc.getLine(); }
    @Override
    public int getColumn() { return loc.getColumn(); }

}
