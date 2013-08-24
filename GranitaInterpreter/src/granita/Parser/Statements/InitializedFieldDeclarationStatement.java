/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package granita.Parser.Statements;

import granita.Parser.Expressions.Expression;
import granita.Parser.FieldItems.Field;
import granita.Semantic.SymbolTable.SymbolTableNode;
import granita.Semantic.SymbolTable.SymbolTableTree;
import granita.Semantic.SymbolTable.Variable;
import granita.Semantic.Types.Type;
import granitainterpreter.GranitaException;

/**
 *
 * @author Néstor A. Bermúdez <nestor.bermudez@unitec.edu>
 */
public class InitializedFieldDeclarationStatement extends Statement {
    Expression initValue;
    String fieldName;
    Type type;

    public InitializedFieldDeclarationStatement(Type type, 
            String fieldName, Expression initValue, int line) {
        super(line);
        this.initValue = initValue;
        this.fieldName = fieldName;
        this.type = type;
    }

    @Override
    public String toString() {
        return type + " " + fieldName + " = " + initValue.toString();
    }

    @Override
    public void validateSemantics() throws GranitaException {
        SymbolTableNode node = SymbolTableTree.getInstance().getRoot();
        
        node.addEntry(fieldName, new Variable(type, initValue));
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
    
}