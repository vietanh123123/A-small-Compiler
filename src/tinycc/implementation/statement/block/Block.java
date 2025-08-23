package tinycc.implementation.statement.block;

import tinycc.implementation.statement.Statement;
import tinycc.diagnostic.Locatable;
import tinycc.diagnostic.Diagnostic;
import tinycc.implementation.Scope;
import tinycc.implementation.SemanticContext;
import java.util.List;

public class Block extends Statement {
    public final List<Statement> insides;
    
    public Block(Locatable loc, List<Statement> insides) {
        super(loc);
        this.insides = insides;
    }

    public List<Statement> getStatements() {
        return insides;
    }
    
    @Override 
    public String toString() {
        String result = "Block[";
        for (int i = 0; i < insides.size(); i++) {
            result += insides.get(i).toString();
            if (i < insides.size() - 1) {
                result += ", ";
            }
        }
        result += "]";
        return result;
    }
    
    @Override
    public void checkSemantics(Diagnostic d, Scope s) {
        // Create new nested scope for this block
        Scope blockScope = s.newNestedScope();
        
        // Check each statement in the block scope
        for (Statement stmt : insides) {
            stmt.checkSemantics(d, blockScope);
        }
    }
    
    @Override
    public void checkSemantics(Diagnostic d, Scope s, SemanticContext ctx) {
        // Create new nested scope for this block
        Scope blockScope = s.newNestedScope();
        
        // Check each statement in the block scope with context
        for (Statement stmt : insides) {
            stmt.checkSemantics(d, blockScope, ctx);
        }
    }
}
