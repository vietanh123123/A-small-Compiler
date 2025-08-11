package tinycc.implementation.expression;

import tinycc.diagnostic.Locatable;
import tinycc.diagnostic.Diagnostic;
import tinycc.implementation.Scope;
import tinycc.implementation.type.Type;



/**
 * The main expression class (see project description)
 *
 * You can change this class but the given name of the class must not be
 * modified.
 */
public abstract class Expression implements Locatable {
    protected final Locatable loc; // location of this expression, it can be any object that implements Locatable

    public Expression(Locatable loc) {
        this.loc = loc;
    }

    public Locatable getLoc() {
        return loc;
    }

    /**
     * Creates a string representation of this expression.
     *
     * @remarks See project documentation.
     * @see StringBuilder
     */
    
    public abstract Type checkType(Diagnostic d, Scope s);


    @Override
    public abstract String toString();

    @Override 
    public String getInputName() { return loc.getInputName(); }
    @Override
    public int getLine() { return loc.getLine(); }
    @Override
    public int getColumn() { return loc.getColumn(); } 
}
