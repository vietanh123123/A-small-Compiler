package tinycc.implementation;

import tinycc.parser.ASTFactory;
import tinycc.parser.Token;
import tinycc.parser.TokenKind;
import tinycc.diagnostic.Locatable;

import tinycc.implementation.expression.Expression;
import tinycc.implementation.expression.primary.Identifier;
import tinycc.implementation.expression.primary.IntegerConstant;
import tinycc.implementation.expression.primary.StringLiteral;
import tinycc.implementation.expression.primary.CharacterConstant;
import tinycc.implementation.expression.primary.FunctionCall;
import tinycc.implementation.expression.primary.ParenthesizedExpression;
import tinycc.implementation.expression.binary.BinaryExpression;
import tinycc.implementation.expression.binary.BinaryOperator;
import tinycc.implementation.expression.unary.UnaryExpression;
import tinycc.implementation.expression.unary.UnaryOperator;
import tinycc.implementation.expression.function_call.FunctionCallExpression;
import tinycc.implementation.expression.ConditionalExpression;

import tinycc.implementation.statement.Statement;
import tinycc.implementation.statement.ExpressionStatement;
import tinycc.implementation.statement.IfStatement;
import tinycc.implementation.statement.ReturnStatement;
import tinycc.implementation.statement.WhileStatement;
import tinycc.implementation.statement.block.Block;
import tinycc.implementation.statement.block.Declaration;


import tinycc.implementation.type.Type;
import tinycc.implementation.type.BaseType;
import tinycc.implementation.type.PointerType;
import tinycc.implementation.type.TypeExpression;
import tinycc.implementation.type.FunctionType;



import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

public class ASTFactoryImplement implements ASTFactory{
    
    private final List<ExternalDeclaration> externalDeclarations = new ArrayList<>();
    private final List<FunctionDefinition> functionDefinitions = new ArrayList<>();


    @Override
    public Statement createBlockStatement(Locatable loc, List<Statement> statements) {
        return new Block(loc, statements);
    }
     
    @Override 
    public Statement createDeclarationStatement(Type type, Token name, Expression init) {
        Locatable loc = type.getLoc();
        Identifier identifier = new Identifier(loc, name.getText());
        return new Declaration(name, type, identifier, init);
    }
    
    @Override
    public Statement createExpressionStatement(Locatable loc, Expression expression) {
        return new ExpressionStatement(loc, expression);

    }

    @Override
    public Statement createIfStatement(Locatable loc, Expression condition, Statement consequence, Statement alternative) {
        return new IfStatement(loc, condition, consequence, alternative);
    }

    @Override 
    public Statement createReturnStatement(Locatable loc, Expression expression) {
        return new ReturnStatement(loc, expression);
    }

    @Override
    public Statement createWhileStatement(Locatable loc, Expression condition, Statement body) {
        return new WhileStatement(loc, condition, body);
    }
    
    @Override 
    public Type createFunctionType(Type returnType, List<Type> parameterTypes) {
        return new FunctionType(returnType.getLoc(), returnType, parameterTypes);
    }

    @Override
    public Type createPointerType(Type baseType) {
        return new PointerType(baseType.getLoc(), baseType);
    }
    
    @Override
    public Type createBaseType(TokenKind kind) {
        switch (kind) {
            case INT:

                return new TypeExpression(BaseType.INT);
            case CHAR:
                return new TypeExpression(BaseType.CHAR);
            case VOID:
                return new TypeExpression(BaseType.VOID);
            default:
                throw new IllegalArgumentException("Unsupported base type: " + kind);
        }
    }
    
    @Override
    public Expression createBinaryExpression(Token operator, Expression left, Expression right) {
        switch (operator.getKind()) {
            case PLUS:
                return new BinaryExpression(left.getLoc(), left, right, BinaryOperator.ADD);
            case MINUS:
                return new BinaryExpression(left.getLoc(), left, right, BinaryOperator.SUBTRACT);
            case ASTERISK:
                return new BinaryExpression(left.getLoc(), left, right, BinaryOperator.MUL);
            case SLASH:
                return new BinaryExpression(left.getLoc(), left, right, BinaryOperator.DIV);
            case EQUAL:
                return new BinaryExpression(left.getLoc(), left, right, BinaryOperator.ASSIGN);
            case EQUAL_EQUAL:
                return new BinaryExpression(left.getLoc(), left, right, BinaryOperator.EQUAL);
            case BANG_EQUAL:
                return new BinaryExpression(left.getLoc(), left, right, BinaryOperator.NOT_EQUAL);
            case LESS:
                return new BinaryExpression(left.getLoc(), left, right, BinaryOperator.LESS_THAN);
            case LESS_EQUAL:
                return new BinaryExpression(left.getLoc(), left, right, BinaryOperator.LESS_EQUAL);
            case GREATER:
                return new BinaryExpression(left.getLoc(), left, right, BinaryOperator.GREATER_THAN);
            case GREATER_EQUAL:
                return new BinaryExpression(left.getLoc(), left, right, BinaryOperator.GREATER_EQUAL);
            case AND_AND:
                return new BinaryExpression(left.getLoc(), left, right, BinaryOperator.AND);
            case PIPE_PIPE:
                return new BinaryExpression(left.getLoc(), left, right, BinaryOperator.OR);
            default:
                throw new IllegalArgumentException("Unsupported binary operator: " + operator.getKind());
        }
            
    }
    
    @Override 
    public Expression createCallExpression(Token token, Expression callee, List<Expression> arguments) {
        return new FunctionCallExpression(token, callee, arguments);
    }
    
    @Override 
    public Expression createConditionalExpression(Token token, Expression condition, Expression consequence, Expression alternativ) {
        return new ConditionalExpression(token, condition, consequence, alternativ);
    }
    @Override 
    public Expression createUnaryExpression(Token operator, boolean postfix, Expression operand) {
    // Only handle prefix unary operators for now, unless you implement postfix support
        switch(operator.getKind()) {
            case BANG:
                return new UnaryExpression(operator, operand, UnaryOperator.NOT);
            case ASTERISK:
                return new UnaryExpression(operator, operand, UnaryOperator.DEREFERENCE);
            case AND:
                return new UnaryExpression(operator, operand, UnaryOperator.ADDRESS_OF);
            case SIZEOF:
                return new UnaryExpression(operator, operand, UnaryOperator.SIZEOF);
            // You can add more cases for other unary operators if needed
            default:
                throw new IllegalArgumentException("Unsupported unary operator: " + operator.getKind());
        } 
    }

    @Override 
    public Expression createPrimaryExpression(Token token) {
        switch (token.getKind()) {
            case IDENTIFIER:
                return new Identifier(token, token.getText());
            case NUMBER:
                return new IntegerConstant(token, Integer.parseInt(token.getText()));
            case STRING:
                return new StringLiteral(token, token.getText());
            case CHARACTER:
                return new CharacterConstant(token, token.getText().charAt(0));
            default:
                throw new IllegalArgumentException("Unsupported primary expression: " + token.getKind());
        }
    }

    @Override
    public void createExternalDeclaration(Type type, Token name) {
        externalDeclarations.add(new ExternalDeclaration(type, name));
    }

    @Override
    public void createFunctionDefinition(Type type, Token name, List<Token> parameterNames, Statement body) {
        functionDefinitions.add(new FunctionDefinition(type, name, parameterNames, body));
    }

    // Add getters to access the declarations
    public List<ExternalDeclaration> getExternalDeclarations() {
        return externalDeclarations;
    }

    public List<FunctionDefinition> getFunctionDefinitions() {
        return functionDefinitions;
    }
}


    

