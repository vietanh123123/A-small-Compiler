package tinycc.implementation.expression.binary;

import tinycc.implementation.expression.Expression;
import tinycc.diagnostic.Locatable;
import tinycc.implementation.Scope;
import tinycc.diagnostic.Diagnostic;
import tinycc.implementation.type.Type;
import tinycc.implementation.type.BaseType;
import tinycc.implementation.type.PointerType;
import tinycc.implementation.type.TypeExpression;
import tinycc.implementation.type.ErrorType;
import tinycc.implementation.expression.primary.IntegerConstant;
import tinycc.implementation.expression.primary.Identifier;
import tinycc.implementation.expression.unary.UnaryExpression;
import tinycc.implementation.expression.unary.UnaryOperator;

public class BinaryExpression extends Expression {

    private final Expression left;
    private final Expression right;
    private final BinaryOperator operator; 

    public BinaryExpression(Locatable loc, Expression left, Expression right, BinaryOperator operator) {
        super(loc);
        this.left = left;
        this.right = right;
        this.operator = operator;
    }

    public Expression getLeft() {
        return left;
    }

    public Expression getRight() {
        return right;
    }

    public BinaryOperator getOperator() {
        return operator;
    }

    @Override 
    public String toString() {
        return "Binary_" + operator.toString() + "[" + left.toString() + ", " + right.toString() + "]";
    }
    
    /**
     * Converts char types to int types automatically (except for & and sizeof operators)
     * @param type The type to potentially convert
     * @return int type if input was char, otherwise the original type
     */
    private Type convertCharToInt(Type type) {
        if (type.isChar()) {
            return new TypeExpression(BaseType.INT);
        }
        return type;
    }
    
    public Type checkTypePlus(Diagnostic d, Scope s) {
        Type l = getLeft().checkType(d, s);
        Type r = getRight().checkType(d, s);
        
        // If either operand is already an error type, propagate the error
        if (l.isError() || r.isError()) {
            return ErrorType.getInstance();
        }
        
        // Apply automatic char to int conversion
        l = convertCharToInt(l);
        r = convertCharToInt(r);
    
        if (l.isPointer() && r.isInt()) {
            PointerType pt = (PointerType) l;
            if (pt.getType().isVoid()) {
                d.printError(this, "Cannot perform arithmetic on void pointer");
                return ErrorType.getInstance();
            }
            return new PointerType(pt.getType());
        }
        else if (l.isInt() && r.isPointer()) {
            PointerType pt = (PointerType) r;
            if (pt.getType().isVoid()) {
                d.printError(this, "Cannot perform arithmetic on void pointer");
                return ErrorType.getInstance();
            }
            return new PointerType(pt.getType());
        }
        else if (l.isInt() && r.isInt()) {
            return new TypeExpression(BaseType.INT);
        }
        else {
            d.printError(this, "Invalid types for binary operation: " + l.toString() + " and " + r.toString());  
            return ErrorType.getInstance();
        }    
    }

    public Type checkTypeMinus(Diagnostic d, Scope s) {
        Type l = getLeft().checkType(d, s);
        Type r = getRight().checkType(d, s);

        // If either operand is already an error type, propagate the error
        if (l.isError() || r.isError()) {
            return ErrorType.getInstance();
        }

        // Apply automatic char to int conversion
        l = convertCharToInt(l);
        r = convertCharToInt(r);

        // Case 1: int - int
        if (l.isInt() && r.isInt()) {
            return new TypeExpression(BaseType.INT);
        }
        // Case 2: pointer - int
        else if (l.isPointer() && r.isInt()) {
            PointerType pt = (PointerType) l;
            if (pt.getType().isVoid()) {
                d.printError(this, "Cannot perform arithmetic on void pointer");
                return ErrorType.getInstance();
            }
            return new PointerType(pt.getType());
        }
        // Case 3: pointer - pointer
        else if (l.isPointer() && r.isPointer()) {
            PointerType pl = (PointerType) l;
            PointerType pr = (PointerType) r;
            if (pl.getType().isVoid() || pr.getType().isVoid()) {
                d.printError(this, "Cannot subtract void pointers");
                return ErrorType.getInstance();
            } else if (!pl.getType().equals(pr.getType())) {
                d.printError(this, "Cannot subtract pointers to different types: " + pl.getType() + " and " + pr.getType());
                return ErrorType.getInstance();
            }
            return new TypeExpression(BaseType.INT);
        }
        // Case 4: all other cases
        else {
            d.printError(this, "Invalid types for pointer/integer subtraction: " + l.toString() + " and " + r.toString());
            return ErrorType.getInstance();
        }
    }
    
    public Type checkTypeMulDiv(Diagnostic d, Scope s) {
        Type l = getLeft().checkType(d, s);
        Type r = getRight().checkType(d, s);

        // If either operand is already an error type, propagate the error
        if (l.isError() || r.isError()) {
            return ErrorType.getInstance();
        }

        // Apply automatic char to int conversion
        l = convertCharToInt(l);
        r = convertCharToInt(r);

        if (!l.isInt() || !r.isInt()) {
            d.printError(this, "Invalid types for multiplication/division: " + l.toString() + " and " + r.toString());
            return ErrorType.getInstance();
        }
        return new TypeExpression(BaseType.INT);
    }

    public Type checkTypeEqualUnequal(Diagnostic d, Scope s) {
        Type l = getLeft().checkType(d, s);
        Type r = getRight().checkType(d, s);

        // If either operand is already an error type, propagate the error
        if (l.isError() || r.isError()) {
            return ErrorType.getInstance();
        }

        // Check for null pointer constant first (integer constant with value 0)
        if ((getLeft() instanceof IntegerConstant && ((IntegerConstant) getLeft()).getValue() == 0) ||
            (getRight() instanceof IntegerConstant && ((IntegerConstant) getRight()).getValue() == 0)) {
            return new TypeExpression(BaseType.INT);
        }

        // Apply automatic char to int conversion
        l = convertCharToInt(l);
        r = convertCharToInt(r);

        if (l.isInt() && r.isInt()) {
            return new TypeExpression(BaseType.INT);
        } 
        else if (l.isPointer() && r.isPointer()) {
            PointerType pl = (PointerType) l;
            PointerType pr = (PointerType) r;
            
            // If they point to void or compatible types
            if (pl.getType().isVoid() || pr.getType().isVoid() ||
                (pl.getType().isInt() && pr.getType().isInt()) ||
                (pl.getType().isChar() && pr.getType().isChar()) ||
                (pl.getType().isPointer() && pr.getType().isPointer())) {
                return new TypeExpression(BaseType.INT);
            }
            else {
                d.printError(this, "Cannot compare pointers to different types: " + pl.getType() + " and " + pr.getType());
                return ErrorType.getInstance();
            }
        }
        else {
            d.printError(this, "Invalid types for equality/inequality operation: " + l.toString() + " and " + r.toString());
            return ErrorType.getInstance();
        }
    }

    public Type checkTypeLessGreater(Diagnostic d, Scope s) {
        Type l = getLeft().checkType(d, s);
        Type r = getRight().checkType(d, s);

        // If either operand is already an error type, propagate the error
        if (l.isError() || r.isError()) {
            return ErrorType.getInstance();
        }

        // Apply automatic char to int conversion
        l = convertCharToInt(l);
        r = convertCharToInt(r);

        if (l.isInt() && r.isInt()) {
            return new TypeExpression(BaseType.INT);
        } 
        else if (l.isPointer() && r.isPointer()) {
            PointerType pl = (PointerType) l;
            PointerType pr = (PointerType) r;
            
            // Identical type or void pointer
            if ((pl.getType().isInt() && pr.getType().isInt()) ||
                (pl.getType().isChar() && pr.getType().isChar()) ||
                pl.getType().isVoid() || pr.getType().isVoid() ||
                (pl.getType().isPointer() && pr.getType().isPointer())) {
                return new TypeExpression(BaseType.INT);
            } 
            else {
                d.printError(this, "Cannot compare pointers to different types: " + pl.getType() + " and " + pr.getType());
                return ErrorType.getInstance();
            }
        }
        else {
            d.printError(this, "Invalid types for less/greater operation: " + l.toString() + " and " + r.toString());
            return ErrorType.getInstance();
        }
    }

    public Type checkTypeAssign(Diagnostic d, Scope s) {
        // L-value check
        boolean isLValue = false;
        if (getLeft() instanceof Identifier) {
            isLValue = true;
        } else if (getLeft() instanceof UnaryExpression &&
                   ((UnaryExpression) getLeft()).getOperator() == UnaryOperator.DEREFERENCE) {
            isLValue = true;
        }
        
        if (!isLValue) {
            d.printError(this, "Left side of assignment must be an identifier or a dereference expression");
            return ErrorType.getInstance();
        }

        Type leftType = getLeft().checkType(d, s);
        Type rightType = getRight().checkType(d, s);

        // If either operand is already an error type, propagate the error
        if (leftType.isError() || rightType.isError()) {
            return ErrorType.getInstance();
        }

        // Assignment Rule 1: The types of the operands are identical
        if (leftType.equals(rightType)) {
            return leftType;
        }
        
        // Assignment Rule 2: Both operands have integer types (includes char to int conversion)
        if ((leftType.isInt() || leftType.isChar()) && (rightType.isInt() || rightType.isChar())) {
            return leftType;
        }
        
        // Assignment Rule 3: Both operands have pointer types and at least one has type void*
        if (leftType.isPointer() && rightType.isPointer()) {
            PointerType pl = (PointerType) leftType;
            PointerType pr = (PointerType) rightType;
            if (pl.getType().isVoid() || pr.getType().isVoid()) {
                return leftType;
            }
            // Check if both pointer types point to compatible types
            Type leftPointeeType = pl.getType();
            Type rightPointeeType = pr.getType();
            
            // Use type checking methods instead of equals for better compatibility
            if (leftPointeeType.isInt() && rightPointeeType.isInt()) {
                return leftType;
            }
            if (leftPointeeType.isChar() && rightPointeeType.isChar()) {
                return leftType;
            }
            if (leftPointeeType.isVoid() && rightPointeeType.isVoid()) {
                return leftType;
            }
        }
        
        // Assignment Rule 4: Left operand has pointer type and right operand is null pointer constant
        if (leftType.isPointer() && getRight() instanceof IntegerConstant &&
            ((IntegerConstant) getRight()).getValue() == 0) {
            return leftType;
        }

        d.printError(this, "Invalid types for assignment: " + leftType + " and " + rightType);
        return ErrorType.getInstance();
    }

    @Override 
    public Type checkType(Diagnostic d, Scope s) {
        switch (operator) {
            case ADD:
                return checkTypePlus(d, s);
            case SUBTRACT:
                return checkTypeMinus(d, s);
            case MUL:
            case DIV:
                return checkTypeMulDiv(d, s);
            case EQUAL:
            case NOT_EQUAL:
                return checkTypeEqualUnequal(d, s);
            case LESS_THAN:
            case GREATER_THAN:
            case LESS_EQUAL:
            case GREATER_EQUAL:
                return checkTypeLessGreater(d, s);
            case ASSIGN:
                return checkTypeAssign(d, s);
            default:
                d.printError(this, "Unknown binary operator: " + operator);
                return ErrorType.getInstance();
        }
    }
}