package tinycc.implementation.expression.function_call;
    
import tinycc.implementation.expression.Expression;
import tinycc.diagnostic.Locatable;
import java.util.List;
import java.util.Collections;
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

public class FunctionCallExpression extends Expression {
    private final Expression function;
    private final List<Expression> arguments;

    public FunctionCallExpression(Locatable loc, Expression function, List<Expression> arguments) {
        super(loc);
        // Defensive copy to ensure immutability
        this.function = function;
        this.arguments = arguments == null ? Collections.emptyList() : List.copyOf(arguments);
    }

    public Expression getFunction() {
        return function;
    }

    public List<Expression> getArguments() {
        return arguments;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        //String builder provides a mutable sequence of characters for efficiently building and modifying strings.

        sb.append("Call[");
        sb.append(function.toString());
        for (Expression arg : arguments) {
            sb.append(", ").append(arg.toString());
        }
        sb.append("]");
        return sb.toString();
    }

    @Override 
    public abstract Type checkType(Diagnostic d, Scope s) {
       //For every type of function call, check if 
    }

}
