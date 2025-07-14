package tinycc.implementation;

import tinycc.implementation.type.Type;
import tinycc.implementation.statement.Statement;
import tinycc.parser.Token;
import java.util.List;

public class FunctionDefinition {
    public final Type type;
    public final Token name;
    public final List<Token> parameterNames;
    public final Statement body;

    public FunctionDefinition(Type type, Token name, List<Token> parameterNames, Statement body) {
        this.type = type;
        this.name = name;
        this.parameterNames = parameterNames;
        this.body = body;
    }

    @Override
    public String toString() {
        return "FunctionDef[" + type.toString() + ", " + name.getText() + ", " + 
               parameterNames.size() + " params, " + body.toString() + "]";
    }
}