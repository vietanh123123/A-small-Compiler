package tinycc.implementation.statement;

import tinycc.implementation.expression.Expression;
import tinycc.diagnostic.Locatable;
import tinycc.implementation.type.Type;
import tinycc.implementation.type.TypeExpression;
import tinycc.implementation.type.BaseType;
import tinycc.diagnostic.Diagnostic;
import tinycc.implementation.Scope;
import tinycc.implementation.SemanticContext;

public class WhileStatement extends Statement {
    public final Expression condition;
    public final Statement body;

    public WhileStatement(Locatable loc, Expression condition, Statement body) {
        super(loc);
        this.condition = condition;
        this.body = body;
    }

    public Expression getCondition() {
        return condition;
    }
    
    public Statement getBody() {
        return body;
    }

    @Override 
    public String toString() {
        return "While[" + condition.toString() + ", " + body.toString() + "]";
    }
    
    @Override
    public void checkSemantics(Diagnostic d, Scope s) {
        Type conditionType = condition.checkType(d, s);
        
        // If condition has error type, don't check further
        if (conditionType.isError()) {
            return;
        }
        
        // Apply automatic char to int conversion
        if (conditionType.isChar()) {
            conditionType = new TypeExpression(BaseType.INT);
        }
        
        // Condition must be scalar (int or pointer)
        if (!conditionType.isInt() && !conditionType.isPointer()) {
            d.printError(this, "Condition must be scalar type, got: " + conditionType);
            return;
        }
        
        // Check body
        body.checkSemantics(d, s);
    }
    
    @Override
    public void checkSemantics(Diagnostic d, Scope s, SemanticContext ctx) {
        Type conditionType = condition.checkType(d, s);
        
        // If condition has error type, don't check further
        if (conditionType.isError()) {
            return;
        }
        
        // Apply automatic char to int conversion
        if (conditionType.isChar()) {
            conditionType = new TypeExpression(BaseType.INT);
        }
        
        // Condition must be scalar (int or pointer)
        if (!conditionType.isInt() && !conditionType.isPointer()) {
            d.printError(this, "Condition must be scalar type, got: " + conditionType);
            return;
        }
        
        // Check body with context
        body.checkSemantics(d, s, ctx);
    }
}
