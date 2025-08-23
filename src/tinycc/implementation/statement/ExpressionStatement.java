package tinycc.implementation.statement;

import tinycc.implementation.expression.Expression;
import tinycc.diagnostic.Locatable;
import tinycc.diagnostic.Diagnostic;
import tinycc.implementation.Scope;

public class ExpressionStatement extends Statement {
    public final Expression exp;

    public ExpressionStatement(Locatable loc, Expression exp) {
        super(loc);
        this.exp = exp;
    }
    
    public Expression getExpression() {
        return exp;
    }
    
    @Override
    public String toString() {
        return exp.toString();
    }
    
    @Override
    public void checkSemantics(Diagnostic d, Scope s) {
        // Just check the expression type
        exp.checkType(d, s);
    }
}
