package tinycc.implementation.expression.unary;

import tinycc.implementation.expression.Expression;
import tinycc.diagnostic.Locatable;
import tinycc.implementation.type.Type;
import tinycc.implementation.type.TypeExpression;
import tinycc.implementation.type.PointerType;
import tinycc.implementation.type.BaseType;
import tinycc.implementation.type.ErrorType;
import tinycc.diagnostic.Diagnostic;
import tinycc.implementation.Scope;
import tinycc.implementation.expression.primary.Identifier;


public class UnaryExpression extends Expression {
    private final Expression expression;
    private final UnaryOperator operator;

    public UnaryExpression(Locatable loc, Expression expression, UnaryOperator operator) {
        super(loc);
        this.expression = expression;
        this.operator = operator;
    }

    public Expression getExpression() {
        return expression;
    }
    
    public UnaryOperator getOperator() {
        return operator;
    }
    @Override
    public String toString() {
        return "Unary_" + operator.toString() + "[" + expression.toString() + "]";
    }

    public Type checkTypeIndirection(Diagnostic d, Scope s) {
        // Operator is expected to be a dereference operator
        Type exprType = getExpression().checkType(d, s);
        
        // If expression has error type, propagate the error
        if (exprType.isError()) {
            return ErrorType.getInstance();
        }
        
        if (exprType.isPointer()) {
            PointerType ptrType = (PointerType) exprType;
            Type pointedType = ptrType.getType();
            
            // Check if pointed type is complete (not void)
            if (pointedType.isVoid()) {
                d.printError(this, "Cannot dereference void pointer");
                return ErrorType.getInstance();
            }
            
            return pointedType;
        } else {
            d.printError(this, "Dereference operator applied to non-pointer type: " + exprType);
            return ErrorType.getInstance();
        }
    }

    public Type checkTypeAddressOf(Diagnostic d, Scope s) {
        // Address-of operator requires an L-value
        // L-value is: identifier or dereference of pointer to complete type
        boolean isLValue = false;
        
        if (expression instanceof Identifier) {
            isLValue = true;
        } else if (expression instanceof UnaryExpression) {
            UnaryExpression unaryExpr = (UnaryExpression) expression;
            if (unaryExpr.getOperator() == UnaryOperator.DEREFERENCE) {
                Type operandType = unaryExpr.getExpression().checkType(d, s);
                if (operandType.isPointer()) {
                    PointerType ptrType = (PointerType) operandType;
                    if (!ptrType.getType().isVoid()) {
                        isLValue = true;
                    }
                }
            }
        }
        
        if (!isLValue) {
            d.printError(this, "Address-of operator requires an L-value");
            return ErrorType.getInstance();
        }
        
        Type exprType = expression.checkType(d, s);
        
        // If expression has error type, propagate the error
        if (exprType.isError()) {
            return ErrorType.getInstance();
        }
        
        // Return pointer to the expression's type
        return new PointerType(exprType);
    }
    
    public Type checkTypeSizeof(Diagnostic d, Scope s) {
        // sizeof returns int and doesn't evaluate the expression for type checking
        // but we still need to check if the expression is valid
        Type exprType = expression.checkType(d, s);
        
        // If expression has error type, propagate the error
        if (exprType.isError()) {
            return ErrorType.getInstance();
        }
        
        // sizeof always returns int
        return new TypeExpression(BaseType.INT);
    }
    
    public Type checkTypeNot(Diagnostic d, Scope s) {
        Type exprType = expression.checkType(d, s);
        
        // If expression has error type, propagate the error
        if (exprType.isError()) {
            return ErrorType.getInstance();
        }
        
        // Apply automatic char to int conversion (except for & and sizeof)
        if (exprType.isChar()) {
            exprType = new TypeExpression(BaseType.INT);
        }
        
        // Condition must be scalar (int or pointer)
        if (!exprType.isInt() && !exprType.isPointer()) {
            d.printError(this, "Logical NOT operator requires scalar type, got: " + exprType);
            return ErrorType.getInstance();
        }
        
        // Logical NOT always returns int
        return new TypeExpression(BaseType.INT);
    }
    
    @Override
    public Type checkType(Diagnostic d, Scope s) {
        switch (operator) {
            case DEREFERENCE:
                return checkTypeIndirection(d, s);
            case ADDRESS_OF:
                return checkTypeAddressOf(d, s);
            case SIZEOF:
                return checkTypeSizeof(d, s);
            case NOT:
                return checkTypeNot(d, s);
            default:
                d.printError(this, "Unknown unary operator: " + operator);
                return ErrorType.getInstance();
        }
    }
}
