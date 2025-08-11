package tinycc.implementation.expression.binary;


import tinycc.implementation.expression.Expression;
import tinycc.diagnostic.Locatable;
import tinycc.implementation.Scope;
import tinycc.diagnostic.Diagnostic;
import tinycc.implementation.type.Type;
import tinycc.implementation.type.BaseType;
import tinycc.implementation.type.PointerType;
import tinycc.implementation.type.TypeExpression;
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
    
    public Type checkTypePlus(Diagnostic d, Scope s) {
        Type l = getLeft().checkType(d,s);
        Type r = getRight().checkType(d,s);
    
        if (l.isInt() && r.isInt()) {
            return new TypeExpression(BaseType.INT);
        }
        else if (l.isPointer() && r.isInt()) {
            PointerType pt = (PointerType) l; // We know l is a pointer 
            if (pt.getType().isVoid()) {
                d.printError(this, "Cannot perform arithmetic on void pointer");
            }
            return new PointerType(pt.getType());
        }
        else if (l.isInt() && r.isPointer()) {
            PointerType pt = (PointerType) r; // We know r is a pointer
            if (pt.getType().isVoid()) {
                d.printError(this, "Cannot perform arithmetic on void pointer");
            }
            return new PointerType(pt.getType());
        }
        else {
            d.printError(this, "Invalid types for binary operation: " + l.toString() + " and " + r.toString());
            throw new IllegalArgumentException("Invalid types for binary operation: " + l.toString() + " and " + r.toString());
        }    
    }

    public Type checkTypeMinus(Diagnostic d, Scope s) {
        Type l = getLeft().checkType(d, s);
        Type r = getRight().checkType(d, s);

        // Case 1: int - int
        if (l.isInt() && r.isInt()) {
            return new TypeExpression(BaseType.INT);
        }
        // Case 2: pointer - int
        else if (l.isPointer() && r.isInt()) {
            PointerType pt = (PointerType) l;
            if (pt.getType().isVoid()) {
                d.printError(this, "Cannot perform arithmetic on void pointer");
            }
            return new PointerType(pt.getType());
        }
        // Case 3: pointer - pointer
        else if (l.isPointer() && r.isPointer()) {
            PointerType pl = (PointerType) l;
            PointerType pr = (PointerType) r;
            if (pl.getType().isVoid() || pr.getType().isVoid()) {
                d.printError(this, "Cannot subtract void pointers");
            } else if (!pl.getType().equals(pr.getType())) {
                d.printError(this, "Cannot subtract pointers to different types: " + pl.getType() + " and " + pr.getType());
            }
            return new TypeExpression(BaseType.INT);
        }
        // Case 4: all other cases
        else {
            d.printError(this, "Invalid types for pointer/integer subtraction: " + l.toString() + " and " + r.toString());
            throw new IllegalArgumentException("Invalid types for pointer/integer subtraction: " + l.toString() + " and " + r.toString());
        }
    }
    
    public Type checkTypeMulDiv (Diagnostic d, Scope s) {
        Type l = getLeft().checkType(d, s);
        Type r = getRight().checkType(d, s);

        if (!l.isInt() || !r.isInt()) {
            d.printError(this, "Invalid types for multiplication/division: " + l.toString() + " and " + r.toString());
            throw new IllegalArgumentException("Invalid types for multiplication/division: " + l.toString() + " and " + r.toString());
        }
        return new TypeExpression(BaseType.INT);
    }

    public Type checkTypeEqualUnequal (Diagnostic d, Scope s) {
        //if they are null pointer constant (integer constant with the value 0)
        if (getLeft() instanceof IntegerConstant && ((IntegerConstant) getLeft()).getValue() == 0 ||
            getRight() instanceof IntegerConstant && ((IntegerConstant) getRight()).getValue() == 0) {
            return new TypeExpression(BaseType.INT);
        }
       
        Type l = getLeft().checkType(d, s);
        Type r = getRight().checkType(d, s);

        if (l.isInt() && r.isInt()) {
            return new TypeExpression(BaseType.INT);
        } 
        else if (l.isPointer() && r.isPointer()) {
            PointerType pl = (PointerType) l;
            PointerType pr = (PointerType) r;
            //if they point to the same type or any pointer points to void  then resulted type is int
            if (pl.getType().isVoid() || pr.getType().isVoid() ) {
                return new TypeExpression(BaseType.INT);
            } 
            else if (pl.getType().isInt() && pr.getType().isInt()) {
                return new TypeExpression(BaseType.INT);
            }
            else if (pl.getType().isChar() && pr.getType().isChar()) {
                return new TypeExpression(BaseType.INT);
            }
            else if (pl.getType().isPointer() && pr.getType().isPointer()) {
                return new TypeExpression(BaseType.INT);
            }
            else {
                d.printError(this, "Cannot compare pointers to different types: " + pl.getType() + " and " + pr.getType());
                throw new IllegalArgumentException("Cannot compare pointers to different types: " + pl.getType() + " and " + pr.getType());
            }
        }
        else {
            d.printError(this, "Invalid types for equality/inequality operation: " + l.toString() + " and " + r.toString());
            throw new IllegalArgumentException("Invalid types for equality/inequality operation: " + l.toString() + " and " + r.toString());
        }
        
    }

    public Type checkTypeLessGreater (Diagnostic d, Scope s) {
        Type l = getLeft().checkType(d, s);
        Type r = getRight().checkType(d, s);

        if (l.isInt() && r.isInt()) {
            return new TypeExpression(BaseType.INT);
        } 
        else if (l.isPointer() && r.isPointer()) {
            PointerType pl = (PointerType) l;
            PointerType pr = (PointerType) r;
            //Identical type or void pointer
            if (pl.getType().isInt() && pr.getType().isInt() ||
                pl.getType().isChar() && pr.getType().isChar() ||
                pl.getType().isVoid() || pr.getType().isVoid() ||
                pl.getType().isPointer() && pr.getType().isPointer()) {
                return new TypeExpression(BaseType.INT);
           } 
            else {
            d.printError(this, "Invalid types for less/greater operation: " + l.toString() + " and " + r.toString());
            throw new IllegalArgumentException("Invalid types for less/greater operation: " + l.toString() + " and " + r.toString());
           }
        }
        d.printError(this, "Invalid types for less/greater operation: " + l.toString() + " and " + r.toString());
        throw new IllegalArgumentException("Invalid types for less/greater operation: " + l.toString() + " and " + r.toString());
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
        throw new IllegalArgumentException("Left side of assignment must be an identifier or a dereference expression");
    }

    Type leftType = getLeft().checkType(d, s);
    Type rightType = getRight().checkType(d, s);

    // Identical types
    if (leftType.equals(rightType)) {
        return leftType;
    }
    // Both integer types
    if (leftType.isInt() && rightType.isInt()) {
        return leftType;
    }
    // Both pointer types and at least one is void*
    if (leftType.isPointer() && rightType.isPointer()) {
        PointerType pl = (PointerType) leftType;
        PointerType pr = (PointerType) rightType;
        if (pl.getType().isVoid() || pr.getType().isVoid()) {
            return leftType;
        }
    }
    // Left is pointer, right is null pointer constant (int 0)
    if (leftType.isPointer() && getRight() instanceof IntegerConstant &&
        ((IntegerConstant) getRight()).getValue() == 0) {
        return leftType;
    }

    d.printError(this, "Invalid types for assignment: " + leftType + " and " + rightType);
    throw new IllegalArgumentException("Invalid types for assignment: " + leftType + " and " + rightType);
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
        // You can add more cases for AND, OR, etc., if you implement them
        default:
            d.printError(this, "Unknown binary operator: " + operator);
            throw new IllegalArgumentException("Unknown binary operator: " + operator);
    }
}
}