/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package granita.tree;

import granitainterpreter.GranitaException;

/**
 *
 * @author Néstor A. Bermúdez <nestor.bermudez@unitec.edu>
 */
public abstract class Statement
{
    abstract public void execute() throws GranitaException;
}