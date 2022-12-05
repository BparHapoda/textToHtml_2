package org.example;

import java.util.ArrayList;

public class Stack {
    private ArrayList<Lexeme> stack;

    public Stack() {
        this.stack = new ArrayList<>();
    }

    public void push(Lexeme lexeme) {
        stack.add(lexeme);
    }

    public ArrayList<Lexeme> getStack() {
        return stack;
    }

    public Lexeme pop() {
        Lexeme tempLexeme = stack.get(stack.size() - 1);
        stack.remove(stack.size() - 1);
        return tempLexeme;

    }

    @Override
    public String toString() {
        return "Stack{" +
                "stack=" + stack +
                '}';
    }
}
