package org.example;

public class Lexeme {
    private LexemeType type;
    private Tag tag;
    private String text;
    private boolean item = false;
    private int number;
    private int spaces;


    public Lexeme(LexemeType type, String text, int spaces, int number) {
        this.type = type;
        this.text = text;
        this.spaces = spaces;
        this.number = number;
    }

    public Lexeme(LexemeType type, String text, int spaces) {
        this.type = type;
        this.text = text;
        this.spaces = spaces;
    }


    public String getText() {
        return text;
    }


    public LexemeType getType() {
        return type;
    }


    public Tag getTag() {
        return tag;
    }


    @Override
    public String toString() {
        return spaces +
                text;
    }

    public int getSpaces() {
        return spaces;
    }
}