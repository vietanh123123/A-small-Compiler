package tinycc.implementation;

import tinycc.implementation.type.Type;
import tinycc.parser.Token;

public class ExternalDeclaration {
    public final Type type;
    public final Token name;

    public ExternalDeclaration(Type type, Token name) {
        this.type = type;
        this.name = name;
    }

    @Override
    public String toString() {
        return "ExternalDecl[" + type.toString() + ", " + name.getText() + "]";
    }
}