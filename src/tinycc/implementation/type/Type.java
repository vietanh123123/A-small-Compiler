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
    
    public abstract boolean isInt() ;
    public abstract boolean isChar() ;
    public abstract boolean isVoid();
    public abstract boolean isPointer();
    
	/**
	 * Creates a string representation of this type.
	 *
	 * @remarks See project documentation.
	 * @see StringBuilder
	 */
	@Override
	public abstract String toString();

	@Override
    public String getInputName() { return loc.getInputName(); }
    @Override
    public int getLine() { return loc.getLine(); }
    @Override
    public int getColumn() { return loc.getColumn(); }

}
