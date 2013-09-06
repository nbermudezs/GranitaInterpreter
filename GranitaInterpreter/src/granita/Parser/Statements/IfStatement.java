/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package granita.Parser.Statements;

import granita.IR.Expressions.D_Expression;
import granita.IR.Statements.D_Block;
import granita.IR.Statements.D_If;
import granita.IR.Statements.D_Statement;
import granita.Parser.Expressions.Expression;
import granita.Semantic.Types.BoolType;
import granita.Semantic.Types.Type;
import granitainterpreter.ErrorHandler;
import granitainterpreter.GranitaException;
import granitainterpreter.SemanticUtils;

/**
 *
 * @author Néstor A. Bermúdez <nestor.bermudez@unitec.edu>
 */
public class IfStatement extends Statement {

    Expression conditional;
    BlockStatement trueBlock, falseBlock;

    //<editor-fold defaultstate="collapsed" desc="Constructors">
    public IfStatement(int line) {
        super(line);
    }

    public IfStatement(Expression conditional, BlockStatement trueBlock,
            BlockStatement falseBlock, int line) {
        super(line);
        this.conditional = conditional;
        this.trueBlock = trueBlock;
        this.falseBlock = falseBlock;
    }
    //</editor-fold>    

    //<editor-fold defaultstate="collapsed" desc="Getters & Setters">
    public void setConditional(Expression conditional) {
        this.conditional = conditional;
    }

    public void setTrueBlock(BlockStatement trueBlock) {
        this.trueBlock = trueBlock;
    }

    public void setFalseBlock(BlockStatement falseBlock) {
        this.falseBlock = falseBlock;
    }
    //</editor-fold>    

    @Override
    public String toString() {
        String i = "if (";
        i += conditional.toString();
        i += ")\n";
        i += trueBlock.toString();
        if (falseBlock != null) {
            i += "else";
            i += falseBlock.toString();
        }
        return i;
    }

    @Override
    public void validateSemantics() throws GranitaException {
        super.validateSemantics();
        Type c = conditional.validateSemantics();
        if (!(c instanceof BoolType)) {
            ErrorHandler.handle("if condition must evaluate to bool: line "
                    + conditional.getLine());
        }
        trueBlock.parentBlock = SemanticUtils.getInstance().getCurrentBlock();
        trueBlock.validateSemantics();
        if (falseBlock != null) {
            falseBlock.parentBlock = SemanticUtils.getInstance().getCurrentBlock();
            falseBlock.validateSemantics();
        }
    }

    @Override
    public Type hasReturn(Type methodType) throws GranitaException {
        Type t1, t2 = null;
        t1 = trueBlock.hasReturn(methodType);
        if (falseBlock != null) {
            t2 = falseBlock.hasReturn(methodType);
        }
        if (t2 == null) {
            return null;
        }
        if (methodType.equivalent(t2)) {
            return t1;
        }
        return t2;
    }

    @Override
    public void execute() throws GranitaException {
        Boolean ret = (Boolean) conditional.evaluate();
        if (ret) {
            trueBlock.execute();
        } else if (falseBlock != null) {
            falseBlock.execute();
        }
    }

    @Override
    public D_Statement getIR() {
        D_Expression cond = conditional.getIR();
        D_Statement tBlock = trueBlock.getIR();
        if (tBlock != null && tBlock instanceof D_Block) {
            if (falseBlock != null) {
                return new D_If(cond, (D_Block) tBlock, (D_Block) falseBlock.getIR());
            }
            return new D_If(cond, (D_Block) tBlock);
        }
        return null;
    }
}
