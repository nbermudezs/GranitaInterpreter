/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package granita.Parser.Expressions;

import granita.IR.Expressions.D_Expression;
import granita.IR.Expressions.D_Or;
import granita.Types.BoolType;
import granita.Types.ErrorType;
import granita.Types.Type;
import granitainterpreter.ErrorHandler;
import granitainterpreter.GranitaException;

/**
 *
 * @author Néstor A. Bermúdez <nestor.bermudez@unitec.edu>
 */
public class Or extends BinaryExpression {

    public Or(Expression left, Expression right, int line) {
        super(left, right, line);
    }

    @Override
    public String toString() {
        return "(" + left.toString() + " || " + right.toString() + ")";
    }

    @Override
    public Type validateSemantics() throws GranitaException {
        Type LHS = left.validateSemantics();
        if (LHS == null) {
            return ErrorHandler.handle("undefined variable " + left.toString()
                    + ": line " + line);
        }
        Type RHS = right.validateSemantics();
        if (RHS == null) {
            return ErrorHandler.handle("undefined variable " + right.toString()
                    + ": line " + line);
        }

        if (LHS instanceof BoolType && RHS instanceof BoolType) {
            return new BoolType();
        } else if (LHS instanceof ErrorType || RHS instanceof ErrorType) {
            return new ErrorType();
        } else {
            return ErrorHandler.handle("operator || cannot be applied to "
                    + LHS.toString() + " and " + RHS.toString()
                    + ": line " + line);
        }
    }

    @Override
    public D_Expression getIR() {
        D_Expression LHS = left.getIR();
        if (LHS == null) {
            ErrorHandler.handle("undefined variable " + left.toString()
                    + ": line " + line);
            return null;
        }
        D_Expression RHS = right.getIR();
        if (RHS == null) {
            ErrorHandler.handle("undefined variable " + right.toString()
                    + ": line " + line);
            return null;
        }
        Type rType = RHS.getExpressionType(), lType = LHS.getExpressionType();
        if (rType instanceof BoolType && lType instanceof BoolType) {
            return new D_Or(LHS, RHS);
        } else if (lType instanceof ErrorType || rType instanceof ErrorType) {
            return new D_Or(LHS, RHS);
        } else {
            ErrorHandler.handle("operator || cannot be applied to "
                    + LHS.toString() + " and " + RHS.toString()
                    + ": line " + line);
            return null;
        }
    }
}
