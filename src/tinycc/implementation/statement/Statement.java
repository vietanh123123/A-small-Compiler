package tinycc.implementation.statement;


import tinycc.diagnostic.Locatable;


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

    @Override
    public String getInputName() { return loc.getInputName(); }
    @Override
    public int getLine() { return loc.getLine(); }
    @Override
    public int getColumn() { return loc.getColumn(); }

}
