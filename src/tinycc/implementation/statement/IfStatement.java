package tinycc.implementation.statement;

import tinycc.implementation.expression.Expression;
import tinycc.diagnostic.Locatable;
import tinycc.implementation.type.Type;
import tinycc.implementation.type.TypeExpression;
import tinycc.implementation.type.BaseType;
import tinycc.diagnostic.Diagnostic;
import tinycc.implementation.Scope;
import tinycc.implementation.SemanticContext;

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

    public Expression getCondition() {
        return condition;
    }
    
    public Statement getThenBranch() {
        return thenBranch;
    }
    
    public Statement getElseBranch() {
        return elseBranch;
    }

    @Override
    public String toString() {
        if (elseBranch == null) {
            return "If[" + condition.toString() + ", " + thenBranch.toString() + "]";
        } else {
            return "If[" + condition.toString() + ", " + thenBranch.toString() + ", " + elseBranch.toString() + "]";
        }
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
        
        // Check both branches
        thenBranch.checkSemantics(d, s);
        if (elseBranch != null) {
            elseBranch.checkSemantics(d, s);
        }
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
        
        // Check both branches with context
        thenBranch.checkSemantics(d, s, ctx);
        if (elseBranch != null) {
            elseBranch.checkSemantics(d, s, ctx);
        }
    }
}
