package tinycc.implementation.statement;


import tinycc.diagnostic.Locatable;


/**
 * The main statement class (see project description)
 *
 * You can change this class but the given name of the class must not be
 * modified.
 */
public abstract class Statement {
    protected final Locatable loc;

    public Statement(Locatable loc) {
        this.loc = loc;
    }

    public Locatable getLoc() {
        return loc;
    }
    
    @Override
    public abstract String toString();
}
