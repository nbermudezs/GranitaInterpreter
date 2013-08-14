/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package granita.Statements;

/**
 *
 * @author Néstor A. Bermúdez <nestor.bermudez@unitec.edu>
 */
public class ContinueStatement extends Statement {
    boolean isInsideLoop;

    public ContinueStatement(boolean isInsideLoop, int line) {
        super(line);
        this.isInsideLoop = isInsideLoop;
    }  

    public boolean isIsInsideLoop() {
        return isInsideLoop;
    }

    public void setIsInsideLoop(boolean isInsideLoop) {
        this.isInsideLoop = isInsideLoop;
    }    
}