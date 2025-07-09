package tinycc.implementation.statement.block;

import tinycc.implementation.statement.Statement;
import java.util.List;

public class Block extends Statement {
    public final List<BlockInside> insides;
    
    public Block(List<BlockInside> insides) {
        this.insides = insides;
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
}
