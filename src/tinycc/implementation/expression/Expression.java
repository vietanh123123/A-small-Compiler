package tinycc.implementation.expression;

import tinycc.diagnostic.Locatable;

/**
 * The main expression class (see project description)
 *
 * You can change this class but the given name of the class must not be
 * modified.
 */
public abstract class Expression {
    protected final Locatable loc;

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
    public abstract String toString();
}
