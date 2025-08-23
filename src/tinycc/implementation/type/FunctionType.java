package tinycc.implementation.type;

import java.util.Collections;
import java.util.List;
import tinycc.implementation.type.Type;
import tinycc.diagnostic.Locatable;

public class FunctionType extends Type {
    private final Type returnType;
    private final List<Type> parameterTypes;

 
    public FunctionType(Locatable loc, Type returnType, List<Type> parameterTypes) {
        super(loc);
        this.returnType = returnType;
        this.parameterTypes = parameterTypes;
    }
    public Type getReturnType() {
        return returnType;
    }
    public List<Type> getParameterTypes() {
        return Collections.unmodifiableList(parameterTypes);
    }
    @Override
    public String toString() {
        String result = "FunctionType[" + returnType.toString();
        for (int i = 0; i < parameterTypes.size(); i++) {
            result += ", " + parameterTypes.get(i).toString();
        }
        result += "]";
        return result;
    }

    
    @Override 
    public boolean isInt() {
        return false;
    }
    @Override 
    public boolean isChar() {
        return false;
    }
    @Override
    public boolean isPointer() {
        return false;
    }
    @Override
    public boolean isVoid() {
        return false;
    }
    
    @Override
    public boolean isFunction() {
        return true;
    }
}   
