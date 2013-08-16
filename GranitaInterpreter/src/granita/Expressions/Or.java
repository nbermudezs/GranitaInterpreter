/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package granita.Expressions;

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
        return left.toString() + " || " + right.toString();
    }
}
