package tinycc.implementation.top_level_construct;


import java.util.List;

public class TranslationUnit {
    public final List<ExternalDeclaration> externalDeclarations;

    public TranslationUnit(List<ExternalDeclaration> externalDeclarations) {
        this.externalDeclarations = externalDeclarations;
    }

}
