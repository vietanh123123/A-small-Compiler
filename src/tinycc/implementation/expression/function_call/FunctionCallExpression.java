package tinycc.implementation.expression.function_call;
    
import tinycc.implementation.expression.Expression;
import tinycc.diagnostic.Locatable;
import java.util.List;
import java.util.Collections;
import tinycc.implementation.Scope;
import tinycc.diagnostic.Diagnostic;
import tinycc.implementation.type.Type;
import tinycc.implementation.type.PointerType;
import tinycc.implementation.type.FunctionType;
import tinycc.implementation.type.ErrorType;
import tinycc.implementation.expression.primary.IntegerConstant;

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

    /**
     * Checks if an argument type can be assigned to a parameter type using assignment rules
     * @param parameterType The formal parameter type (left side of assignment)
     * @param argumentType The actual argument type (right side of assignment)  
     * @param argument The argument expression (needed to check for null pointer constants)
     * @return true if assignment is valid, false otherwise
     */
    private boolean isValidParameterAssignment(Type parameterType, Type argumentType, Expression argument) {
        // Assignment Rule 1: The types are identical
        if (parameterType.equals(argumentType)) {
            return true;
        }
        
        // Assignment Rule 2: Both have integer types (includes char to int conversion)
        if ((parameterType.isInt() || parameterType.isChar()) && (argumentType.isInt() || argumentType.isChar())) {
            return true;
        }
        
        // Assignment Rule 3: Both have pointer types and at least one has type void*
        if (parameterType.isPointer() && argumentType.isPointer()) {
            PointerType paramPtr = (PointerType) parameterType;
            PointerType argPtr = (PointerType) argumentType;
            if (paramPtr.getType().isVoid() || argPtr.getType().isVoid()) {
                return true;
            }
            // Check if both pointer types point to compatible types
            Type paramPointeeType = paramPtr.getType();
            Type argPointeeType = argPtr.getType();
            
            // Use type checking methods instead of equals for better compatibility
            if (paramPointeeType.isInt() && argPointeeType.isInt()) {
                return true;
            }
            if (paramPointeeType.isChar() && argPointeeType.isChar()) {
                return true;
            }
            if (paramPointeeType.isVoid() && argPointeeType.isVoid()) {
                return true;
            }
            if (paramPointeeType.isPointer() && argPointeeType.isPointer()) {
                // For nested pointers, recursively check
                return isValidParameterAssignment(paramPointeeType, argPointeeType, argument);
            }
        }
        
        // Assignment Rule 4: Parameter has pointer type and argument is null pointer constant
        return parameterType.isPointer() && argument instanceof IntegerConstant &&
               ((IntegerConstant) argument).getValue() == 0;
    }

    @Override 
    public Type checkType(Diagnostic d, Scope s) {
        // First, check the function expression type
        Type functionType = function.checkType(d, s);
        
        // If function expression has error type, propagate the error
        if (functionType.isError()) {
            return ErrorType.getInstance();
        }
        
        // Function must have function type
        if (!functionType.isFunction()) {
            d.printError(this, "Expression is not a function");
            return ErrorType.getInstance();
        }
        
        FunctionType funcType = (FunctionType) functionType;
        List<Type> parameterTypes = funcType.getParameterTypes();
        
        // Check if number of arguments matches number of parameters
        if (arguments.size() != parameterTypes.size()) {
            d.printError(this, "Function expects " + parameterTypes.size() + 
                        " arguments, but " + arguments.size() + " provided");
            return ErrorType.getInstance();
        }
        
        // Check each argument against its corresponding parameter using assignment rules
        for (int i = 0; i < arguments.size(); i++) {
            Expression argument = arguments.get(i);
            Type argumentType = argument.checkType(d, s);
            Type parameterType = parameterTypes.get(i);
            
            // If argument has error type, propagate the error
            if (argumentType.isError()) {
                return ErrorType.getInstance();
            }
            
            // Check if argument can be assigned to parameter
            if (!isValidParameterAssignment(parameterType, argumentType, argument)) {
                d.printError(this, "Argument " + (i + 1) + " of type " + argumentType + 
                            " cannot be assigned to parameter of type " + parameterType);
                return ErrorType.getInstance();
            }
        }
        
        // If all checks pass, return the function's return type
        return funcType.getReturnType();
    }

}
