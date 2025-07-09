package tinycc.implementation.statement;

import tinycc.implementation.expression.Expression;

public class IfStatement extends Statement {
    public final Expression condition;
    public final Statement thenBranch;
    public final Statement elseBranch;  // Optional, can be null

    public IfStatement(Expression condition, Statement thenBranch, Statement elseBranch) {
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
