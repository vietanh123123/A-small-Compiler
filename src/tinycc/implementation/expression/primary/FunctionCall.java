package tinycc.implementation.expression.primary;

import tinycc.implementation.expression.Expression;
import java.util.List;
import java.util.stream.Collectors;

public class FunctionCall extends Expression {
    private final Expression function;
    private final List<Expression> arguments;

    public FunctionCall(Expression function, List<Expression> arguments) {
        this.function = function;
        this.arguments = arguments;
    }

    public Expression getFunction() {
        return function;
    }

    public List<Expression> getArguments() {
        return arguments;
    }

    @Override
    public String toString() {
        String args = arguments.stream()
            .map(Expression::toString)
            .collect(Collectors.joining(", "));
        return "Call_" + function.toString() + "[" + args + "]";
    }
}
