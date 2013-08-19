/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package granita.Semantic.SymbolTable;

import granita.Parser.Expressions.Expression;
import granita.Semantic.Types.Type;
import java.util.HashMap;

/**
 *
 * @author Néstor A. Bermúdez <nestor.bermudez@unitec.edu>
 */
public class Function extends SymbolTableValue {
    private Type type;
    private HashMap<String, Variable> localSymbolTable;
    private HashMap<String, Variable> parameters;
    
    public Function(Type type){
        this.type = type;
        this.localSymbolTable = new HashMap<String, Variable>();
        this.parameters = new HashMap<String, Variable>();
    }

    public Function(Type type, HashMap<String, Variable> localSymbolTable, HashMap<String, Variable> parameters) {
        this.type = type;
        this.localSymbolTable = localSymbolTable;
        this.parameters = parameters;
    }
    
    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public HashMap<String, Variable> getLocalSymbolTable() {
        return localSymbolTable;
    }

    public void setLocalSymbolTable(HashMap<String, Variable> localSymbolTable) {
        this.localSymbolTable = localSymbolTable;
    }

    public HashMap<String, Variable> getParameters() {
        return parameters;
    }

    public void setParameters(HashMap<String, Variable> parameters) {
        this.parameters = parameters;
    }
    
    public void addSymbolTableEntry(String id, Variable value){
        this.localSymbolTable.put(id, value);
    }
    
    public Variable getSymbolTableEntry(String id){
        if (this.localSymbolTable.containsKey(id)){
            return this.localSymbolTable.get(id);
        }
        return null;
    }
    
    public void deleteSymbolTableEntry(String id){
        this.localSymbolTable.remove(id);
    }
    
}
