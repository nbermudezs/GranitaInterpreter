/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package granita.DataLayout;

import java.util.Stack;

/**
 *
 * @author Néstor A. Bermúdez <nestor.bermudez@unitec.edu>
 */
public class ContextStack {

    private Stack<Context> stack;
    private Stack<Context> savedContext;

    public ContextStack() {
        stack = new Stack<Context>();
        savedContext = new Stack<Context>();
    }

    public void push(Context context) {
        if (!stack.isEmpty() && !context.hasParent()) {
            context.setParent(stack.peek());
        }
        stack.push(context);
    }

    public Context pop() {
        if (!stack.isEmpty()) {
            return stack.pop();
        }
        return null;
    }

    public Context peek() {
        if (!stack.isEmpty()) {
            return stack.peek();
        }
        return null;
    }

    public void saveContext() {
        Context top = peek();
        if (top != null) {
            //System.out.println("Saving " + top);
            //top.print();
            Context copy = new Context(null);
            top.copyTo(copy);
            savedContext.push(copy);
        }
    }

    public void loadContext() {
        if (!savedContext.isEmpty()) {
            Context top = savedContext.pop();
            //System.out.println("Loading " + stack.peek());            
            if (top != null) {
                top.copyTo(stack.peek());
                //stack.peek().print();
            }
        }
    }
}
