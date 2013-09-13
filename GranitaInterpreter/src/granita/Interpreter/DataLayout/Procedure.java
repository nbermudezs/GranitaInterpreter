/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package granita.Interpreter.DataLayout;

/**
 *
 * @author Néstor A. Bermúdez <nestor.bermudez@unitec.edu>
 */
public class Procedure {
    private int variablesCount;
    private boolean isFunction;

    public Procedure(int variablesCount, boolean isFunction) {
        this.isFunction = isFunction;
        this.variablesCount = variablesCount;
    }    
    
    public RE_Variable[] createEnvironment() {
        int size = variablesCount;
        if (isFunction) {
            size += 1;
        }
        return new RE_Variable[size];
    }
}