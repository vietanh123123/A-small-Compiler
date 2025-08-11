package tinycc.implementation.top_level_construct;

import java.util.List;

import tinycc.implementation.expression.primary.Identifier;
import tinycc.implementation.statement.Statement;
import tinycc.implementation.statement.block.Block;
import tinycc.implementation.type.Type;
import tinycc.parser.Token;
import tinycc.diagnostic.Locatable;

//Need to modify toString
public class Function extends ExternalDeclaration {
	public final Type type;
    public final Identifier identifier;
    public final List<NamedParameter> namedParameters;
    public final Block block; 
    
    
    public Function(Locatable loc, Type type, Identifier name, 
                              List<NamedParameter> parameterNames, Block body) {
        super(loc); 
        this.type = type;
        this.identifier = name;
        this.namedParameters = parameterNames;
        this.block = body;
    }
    @Override 
    public String toString() {
        return "Function{" +
                "type=" + type +
                ", identifier=" + identifier +
                ", namedParameters=" + namedParameters +
                ", block=" + block +
                '}';
    }

}
