/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package granita.Parser.Statements;

import granita.Parser.Expressions.Expression;
import granita.Semantic.Types.ErrorType;
import granita.Semantic.Types.Type;
import granita.Semantic.Types.VoidType;
import granitainterpreter.ErrorHandler;
import granitainterpreter.GranitaException;
import granitainterpreter.Utils;

/**
 *
 * @author Néstor A. Bermúdez <nestor.bermudez@unitec.edu>
 */
public class ReturnStatement extends Statement {

    private boolean isInsideFunction;
    private Expression returnExpression;
    private Type returnType = null;

    public ReturnStatement(boolean isInsideFunction, Expression returnExpression, int line) {
        super(line);
        this.isInsideFunction = isInsideFunction;
        this.returnExpression = returnExpression;
    }

    public boolean isIsInsideFunction() {
        return isInsideFunction;
    }

    public void setIsInsideFunction(boolean isInsideFunction) {
        this.isInsideFunction = isInsideFunction;
    }

    @Override
    public String toString() {
        String ret = "return";
        if (returnExpression != null) {
            ret += " " + returnExpression.toString();
        }
        return ret;
    }

    @Override
    public void validateSemantics() throws GranitaException {
        super.validateSemantics();
        if (!Utils.getInstance().mustReturnExpression()
                && returnExpression != null) {
            ErrorHandler.handle("cannot return a value from method whose result"
                    + " type is void: line " + returnExpression.getLine());
            Utils.getInstance().setErrored();
        }
        if (returnExpression != null) {
            if (returnType == null) {
                returnType = returnExpression.validateSemantics();
            }
            if (returnType instanceof VoidType) {
                ErrorHandler.handle("return value cannot be void: line " + line);
                Utils.getInstance().setErrored();
            }

        }
        Utils.getInstance().setUnreachableStatement();
    }

    @Override
    public Type hasReturn(Type methodType) throws GranitaException {
        if (returnExpression == null) {
            return null;
        }
        if (returnType == null) {
            returnType = returnExpression.validateSemantics();
        }
        Type expectedType = Utils.getInstance().getExpectedReturnType();
        if (expectedType != null 
                && !returnType.equivalent(new ErrorType()) 
                && !expectedType.equivalent(returnType)) {
            ErrorHandler.handle("return expression type must be "
                    + expectedType + " but found " + returnType + ": line "
                    + this.getLine());
        }
        return returnType;
    }
}
