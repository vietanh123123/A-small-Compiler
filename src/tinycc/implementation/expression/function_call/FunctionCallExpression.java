package tinycc.implementation.expression.function_call;
    
import tinycc.implementation.expression.Expression;
import java.util.List;
import java.util.Collections;

public class FunctionCallExpression extends Expression {
    private final Expression function;
    private final List<Expression> arguments;

    public FunctionCallExpression(Expression function, List<Expression> arguments) {
        this.function = function;
        // Defensive copy to ensure immutability
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
}
