package tinycc.implementation.expression;

import tinycc.diagnostic.Locatable;
import tinycc.implementation.type.Type;
import tinycc.implementation.type.TypeExpression;
import tinycc.implementation.type.BaseType;
import tinycc.implementation.type.ErrorType;
import tinycc.diagnostic.Diagnostic;
import tinycc.implementation.Scope;

public class ConditionalExpression extends Expression{
    private final Expression condition;
    private final Expression consequenc;
    private final Expression alternative;
    
    public ConditionalExpression(Locatable loc, Expression condition, Expression consequence, Expression alternative) {
        super(loc);
        this.condition = condition;
        this.consequenc = consequence;
        this.alternative = alternative;
    }

    public Expression getCondition() {
        return condition;
    }
    
    public Expression getConsequence() {
        return consequenc;
    }
    
    public Expression getAlternative() {
        return alternative;
    }

    @Override 
    public String toString() {
        return "Conditional[" + condition + " ? " + consequenc + " : " + alternative + "]";
    }
    
    @Override
    public Type checkType(Diagnostic d, Scope s) {
        Type conditionType = condition.checkType(d, s);
        Type consequenceType = consequenc.checkType(d, s);
        Type alternativeType = alternative.checkType(d, s);
        
        // If any operand has error type, propagate the error
        if (conditionType.isError() || consequenceType.isError() || alternativeType.isError()) {
            return ErrorType.getInstance();
        }
        
        // Apply automatic char to int conversion for condition
        if (conditionType.isChar()) {
            conditionType = new TypeExpression(BaseType.INT);
        }
        
        // Condition must be scalar (int or pointer)
        if (!conditionType.isInt() && !conditionType.isPointer()) {
            d.printError(this, "Condition must be scalar type, got: " + conditionType);
            return ErrorType.getInstance();
        }
        
        // Both branches must have compatible types
        // For simplicity, require identical types
        if (consequenceType.equals(alternativeType)) {
            return consequenceType;
        }
        
        // Allow int/char compatibility
        if ((consequenceType.isInt() || consequenceType.isChar()) && 
            (alternativeType.isInt() || alternativeType.isChar())) {
            return new TypeExpression(BaseType.INT);
        }
        
        d.printError(this, "Incompatible types in conditional expression: " + 
                    consequenceType + " and " + alternativeType);
        return ErrorType.getInstance();
    }

}
