package datatypes;

import exceptions.InterpreterException;

import java.util.Stack;

public class MyStackImpl<T> implements MyStack<T> {
    private Stack<T> stack;

    public MyStackImpl() {

        this.stack = new Stack<>();
    }

    @Override
    public void push(T elem) {
        stack.push(elem);
    }

    @Override
    public T pop() {
        if (isEmpty())
            throw new InterpreterException("Empty stack!");
        return stack.pop();
    }

    @Override
    public T top() {
        if (isEmpty())
            throw new InterpreterException("Empty stack!");
        return stack.peek();
    }

    @Override
    public boolean isEmpty() {
        return stack.isEmpty();
    }

    @Override
    public Iterable<T> getAll() {
        return stack;
    }

    @Override
    public String toString() {
        String stack = "";
        Stack<T> copiedStack = (Stack<T>) this.stack.clone();
        while (!copiedStack.isEmpty()) {
            T t = copiedStack.pop();
            stack += t.toString() + '\n';
        }
        return stack;
    }



}
