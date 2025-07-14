package tinycc.implementation.statement;

import tinycc.implementation.expression.Expression;
import tinycc.diagnostic.Locatable;

public class WhileStatement extends Statement {
    public final Expression condition;
    public final Statement body;

    public WhileStatement(Locatable loc, Expression condition, Statement body) {
        super(loc);
        this.condition = condition;
        this.body = body;
    }

    @Override 
    public String toString() {
        return "While[" + condition.toString() + ", " + body.toString() + "]";
    }
}
