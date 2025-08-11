When visiting an AST node during type checking, it is advisable to store references to the significant declaration in the AST node that represents the using occurrence of an identifier. By this reference, the type can later on be looked up again. This may be important for successive passes like code generation which we discuss in the next section. In general, the AST node of the declaration stands for the variable itself and the compiler may want to associate other information with variables in later stages. The following code shows an example implementation of the AST node that represents the using occurrence of a variable.
public class Var implements Expression, Locatable {
  private String id;
  private Declaration decl = null;

  public Type checkType(Diagnostic d, Scope s) {
    try {
      this.decl = s.lookup(id);
      return this.decl.getType();
    }
    catch (IdUndeclared e) {
      d.printError(this, "Variable " + id + " not declared");
      return Types.getErrorType();
    }
  }
}