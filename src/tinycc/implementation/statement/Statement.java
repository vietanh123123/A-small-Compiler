package tinycc.implementation.statement;

import tinycc.implementation.statement.block.BlockInside;
/**
 * The main statement class (see project description)
 *
 * You can change this class but the given name of the class must not be
 * modified.
 */
public abstract class Statement implements BlockInside {

	/**
	 * Creates a string representation of this statement.
	 *
	 * @remarks See project documentation.
	 * @see StringBuilder
	 */
	@Override
	public abstract String toString();

}
