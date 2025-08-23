package tinycc.implementation.statement;

import tinycc.implementation.expression.Expression;
import tinycc.implementation.expression.primary.IntegerConstant;
import tinycc.diagnostic.Locatable;
import tinycc.implementation.type.Type;
import tinycc.implementation.type.PointerType;
import tinycc.diagnostic.Diagnostic;
import tinycc.implementation.Scope;
import tinycc.implementation.SemanticContext;


public class ReturnStatement extends Statement {
    public final Expression exp;  // Optional, can be null

    public ReturnStatement(Locatable loc, Expression exp) {
        super(loc);
        this.exp = exp;
    }

    public Expression getExpression() {
        return exp;
    }
    
    /**
     * Checks if a return expression type can be assigned to function return type using assignment rules
     */
    private boolean isValidReturnAssignment(Type functionReturnType, Type returnExprType, Expression returnExpr) {
        // Assignment Rule 1: The types are identical
        if (functionReturnType.equals(returnExprType)) {
            return true;
        }
        
        // Assignment Rule 2: Both have integer types (includes char to int conversion)
        if ((functionReturnType.isInt() || functionReturnType.isChar()) && 
            (returnExprType.isInt() || returnExprType.isChar())) {
            return true;
        }
        
        // Assignment Rule 3: Both have pointer types and at least one has type void*
        if (functionReturnType.isPointer() && returnExprType.isPointer()) {
            PointerType funcPtr = (PointerType) functionReturnType;
            PointerType retPtr = (PointerType) returnExprType;
            if (funcPtr.getType().isVoid() || retPtr.getType().isVoid()) {
                return true;
            }
        }
        
        // Assignment Rule 4: Function return type is pointer and return expression is null pointer constant
        if (functionReturnType.isPointer() && returnExpr instanceof IntegerConstant &&
            ((IntegerConstant) returnExpr).getValue() == 0) {
            return true;
        }
        
        return false;
    }

    @Override 
    public String toString() {
        if (exp == null) {
            return "Return[]";
        } else {
            return "Return[" + exp.toString() + "]";
        }
    }
    
    @Override
    public void checkSemantics(Diagnostic d, Scope s) {
        // This should not be called without context - needs function return type
        d.printError(this, "Internal error: Return statement checked without function context");
    }
    
    @Override
    public void checkSemantics(Diagnostic d, Scope s, SemanticContext ctx) {
        Type functionReturnType = ctx.getCurrentFunctionReturnType();
        
        if (functionReturnType.isVoid()) {
            // Function returns void - should not have expression
            if (exp != null) {
                d.printError(this, "Return statement should not have expression in void function");
                return;
            }
        } else {
            // Function returns non-void - must have expression
            if (exp == null) {
                d.printError(this, "Return statement must have expression in non-void function");
                return;
            }
            
            // Check return expression type
            Type returnExprType = exp.checkType(d, s);
            
            // If return expression has error type, don't continue
            if (returnExprType.isError()) {
                return;
            }
            
            // Check if return expression can be assigned to function return type
            if (!isValidReturnAssignment(functionReturnType, returnExprType, exp)) {
                d.printError(this, "Cannot return value of type " + returnExprType + 
                            " from function returning " + functionReturnType);
                return;
            }
        }
    }
}
