/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package granita.Parser.Statements;

import granita.IR.Expressions.D_Expression;
import granita.Interpreter.DataLayout.BoolVariable;
import granita.Interpreter.DataLayout.IntVariable;
import granita.Interpreter.Results.BoolResult;
import granita.Interpreter.Results.IntResult;
import granita.Misc.ErrorHandler;
import granita.Misc.GranitaException;
import granita.Parser.Expressions.Expression;
import granita.Semantic.DataLayout.SimpleVariable;
import granita.Semantic.SymbolTable.SymbolTableEntry;
import granita.Semantic.SymbolTable.SymbolTableNode;
import granita.Semantic.SymbolTable.SymbolTableTree;
import granita.Semantic.Types.BoolType;
import granita.Semantic.Types.Type;
import granita.Semantics.SemanticUtils;

/**
 *
 * @author Néstor A. Bermúdez < nestor.bermudez@unitec.edu >
 */
public class InitializedFieldDeclarationStatement extends DeclarationStatement {

    //<editor-fold defaultstate="collapsed" desc="Instance Attributes">
    Expression initValue;
    String fieldName;
    Type type;
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Constructors">
    public InitializedFieldDeclarationStatement(Type type,
            String fieldName, Expression initValue, int line) {
        super(line);
        this.initValue = initValue;
        this.fieldName = fieldName;
        this.type = type;
    }
    //</editor-fold>    

    @Override
    public String toString() {
        return type + " " + fieldName + " = " + initValue.toString();
    }

    public Expression getInitValue() {
        return initValue;
    }

    public void setInitValue(Expression initValue) {
        this.initValue = initValue;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    @Override
    public void register() throws GranitaException {
        SymbolTableNode node = SymbolTableTree.getInstance().getGlobal();
        SymbolTableEntry v = node.findInThisTable(fieldName);
        if (v != null) {
            ErrorHandler.handle("already defined variable '" + fieldName
                    + "': line " + this.getLine());
        } else {
            D_Expression d = initValue.getIR();
            node.addEntry(fieldName, new SimpleVariable(type, true));
            if (this.type instanceof BoolType) {
                Boolean init = ((BoolResult)d.eval()).getValue();
                SemanticUtils.getInstance().addVariableRE(new BoolVariable(fieldName, init));
            } else {
                Integer init = ((IntResult) d.eval()).getValue();
                SemanticUtils.getInstance().addVariableRE(new IntVariable(fieldName, init));
            }
        }
    }
}
