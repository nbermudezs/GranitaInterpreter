/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package granita.Parser.Statements;

import granita.IR.Expressions.D_Expression;
import granita.IR.Statements.D_Block;
import granita.IR.Statements.D_Statement;
import granita.IR.Statements.D_While;
import granita.Parser.Expressions.Expression;
import granita.Types.BoolType;
import granita.Types.Type;
import granitainterpreter.ErrorHandler;
import granitainterpreter.GranitaException;

/**
 *
 * @author Néstor A. Bermúdez <nestor.bermudez@unitec.edu>
 */
public class WhileStatement extends Statement {

    Expression exp;
    BlockStatement block;

    public WhileStatement(int line) {
        super(line);
    }

    public WhileStatement(Expression exp, BlockStatement block, int line) {
        super(line);
        this.exp = exp;
        this.block = block;
    }

    public void setExpression(Expression exp) {
        this.exp = exp;
    }

    public void setBlock(BlockStatement block) {
        this.block = block;
    }

    @Override
    public String toString() {
        String w = "while (";
        w += exp.toString();
        w += ")";
        w += block.toString();

        return w;
    }

    @Override
    public void validateSemantics() throws GranitaException {
        /*super.validateSemantics();
        Type rtype = exp.validateSemantics();
        if (!(rtype instanceof BoolType)) {
            ErrorHandler.handle("while condition must evaluate to bool: line " + this.getLine());
        }
        block.validateSemantics();*/
    }

    @Override
    public Type hasReturn(Type methodType) throws GranitaException {
        return block.hasReturn(methodType);
    }

    @Override
    public D_Statement getIR() {
        checkForUnreachableStatement();
        D_Expression centinel = exp.getIR();
        if (centinel != null && !(centinel.getExpressionType() instanceof BoolType)) {
            ErrorHandler.handle("while condition must evaluate to bool: line " + exp.getLine());
        }
        D_Statement blk = block.getIR();
        return new D_While(centinel, (D_Block) blk);
    }
}
