package tinycc.implementation.top_level_construct;

import tinycc.diagnostic.Locatable;
import tinycc.diagnostic.Diagnostic;
import tinycc.implementation.Scope;



public abstract class ExternalDeclaration implements Locatable{

    protected final Locatable loc; // location of this expression, it can be any object that implements Locatable

    public ExternalDeclaration(Locatable loc) {
        this.loc = loc;
    }

    public Locatable getLoc() {
        return loc;
    }
    
    @Override 
    public abstract String toString();
    
    /**
     * Checks the semantics of this external declaration.
     */
    public abstract void checkSemantics(Scope globalScope, Diagnostic diagnostic);
    
    @Override
    public int getLine() { return loc.getLine(); }
    @Override
    public int getColumn() { return loc.getColumn(); }
    @Override 
    public String getInputName() { return loc.getInputName(); } 
}