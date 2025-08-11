package tinycc.implementation;

import tinycc.implementation.statement.block.Declaration;
import java.util.Map;
import java.util.HashMap;

public class Scope {
    private final Map<String, Declaration> table; 
    private final Scope parent;

    public Scope() {
        this(null);
    }
    public Scope(Scope parent) {
        this.parent = parent;
        this.table = new HashMap<String, Declaration>();
    }

    public Scope newNestedScope() {
        return new Scope(this); // create a new Scope whose parent is the current scope 
    }

    public void add(String id, Declaration d) {
    // Shadowing is possible, since we have parent scopes.
    table.put(id, d);
    }

    public Declaration lookup(String id)  {
        if (id == null) {
            throw new IllegalArgumentException("Identifier cannot be null");
        }
        
        // Single lookup instead of containsKey + get
        Declaration result = table.get(id);
        if (result != null) {
            return result;
        } else if (parent != null) {
            return parent.lookup(id);
        } else {
            throw new IllegalArgumentException("Identifier undeclared: " + id);
        }
    }

}
