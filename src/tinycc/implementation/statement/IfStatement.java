package tinycc.implementation.statement;

import tinycc.implementation.expression.Expression;
import tinycc.diagnostic.Locatable;

public class IfStatement extends Statement {
    public final Expression condition;
    public final Statement thenBranch;
    public final Statement elseBranch;  // Optional, can be null

    public IfStatement(Locatable loc, Expression condition, Statement thenBranch, Statement elseBranch) {
        super(loc);
        this.condition = condition;
        this.thenBranch = thenBranch;
        this.elseBranch = elseBranch;
    }

    @Override
    public String toString() {
        if (elseBranch == null) {
            return "If[" + condition.toString() + ", " + thenBranch.toString() + "]";
        } else {
            return "If[" + condition.toString() + ", " + thenBranch.toString() + ", " + elseBranch.toString() + "]";
        }
    }
}
