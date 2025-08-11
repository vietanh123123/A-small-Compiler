package tinycc.implementation.top_level_construct;

import tinycc.diagnostic.Locatable;
import tinycc.implementation.type.Type;
import tinycc.implementation.statement.Statement;
import tinycc.implementation.expression.primary.Identifier;
import tinycc.implementation.top_level_construct.Parameter;
import java.util.List;


//Prototype may not have parameter list

public class FunctionDeclaration extends ExternalDeclaration {
    public final Type type;
    public final Identifier identifier;
    public final List<Parameter> parameters;
    
    

    public FunctionDeclaration(Locatable loc, Type type, Identifier identifier, 
                              List<Parameter> parameters) {
        super(loc);
        this.type = type;
        this.identifier = identifier;
        this.parameters = parameters;
    }

    @Override
    public String toString() {
        return "FunctionDeclaration{" +
                "type=" + type +
                ", identifier=" + identifier +
                ", parameters=" + parameters +
                '}';
    }
}