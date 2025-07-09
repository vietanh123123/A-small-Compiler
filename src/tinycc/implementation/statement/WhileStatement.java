package tinycc.implementation.statement;

import tinycc.implementation.expression.Expression;

public class WhileStatement extends Statement {
    public final Expression condition;
    public final Statement body;

    public WhileStatement(Expression condition, Statement body) {
        this.condition = condition;
        this.body = body;
    }

    @Override 
    public String toString() {
        return "While[" + condition.toString() + ", " + body.toString() + "]";
    }
    
}
