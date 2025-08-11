package tinycc.implementation.expression.unary;

import tinycc.implementation.expression.Expression;
import tinycc.diagnostic.Locatable;

import tinycc.diagnostic.Locatable;
import tinycc.implementation.type.BaseType;
import tinycc.implementation.type.Type;
import tinycc.implementation.type.TypeExpression;
import tinycc.implementation.type.PointerType;
import tinycc.diagnostic.Diagnostic;
import tinycc.implementation.Scope;


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
        //Operator is expected to be a dereference operator
        Type exprType = getExpression().checkType(d, s);
        if (exprType instanceof PointerType
            && !((PointerType) exprType).isVoid()) {
            return  ((PointerType) exprType).getType();
        } else {
            d.printError(this, "Dereference operator applied to non-pointer type");
            throw new IllegalArgumentException("Dereference operator applied to non-pointer type");
        }
        
    }

    public Type checkTypeAdressOf (Diagnostic d, Scope s) {
        //
    }
}
