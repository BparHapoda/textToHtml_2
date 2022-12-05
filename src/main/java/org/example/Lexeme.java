package org.example;

public class Lexeme {
   private LexemeType type;
    private Tag tag;
    private String text;
    private boolean item=false;
private int number;
    private int spaces;
    private boolean root;

    public Lexeme(LexemeType type) {
        this.type = type;
    }

    public Lexeme(LexemeType type,String text,int spaces,int number) {
        this.type = type;
        this.text=text;
        this.spaces=spaces;
        this.number=number;
    }
    public Lexeme(LexemeType type,String text,int spaces) {
        this.type = type;
        this.text=text;
        this.spaces=spaces;
    }

    public Lexeme(LexemeType type,Tag tag,String text) {
        this.type = type;
        this.tag=tag;
        this.text=text;

    }public Lexeme(LexemeType type,Tag tag) {
        this.type = type;
        this.tag=tag;


    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public LexemeType getType() {
        return type;
    }

    public void setType(LexemeType type) {
        this.type = type;
    }

    public Tag getTag() {
        return tag;
    }

    public void setTag(Tag tag) {
        this.tag=tag;
    }

    @Override
    public String toString() {
        return spaces+
                text;
    }

    public boolean isItem() {
        return item;
    }

    public void setItem(boolean item) {
        this.item = item;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getSpaces() {
        return spaces;
    }

    public void setSpaces(int spaces) {
        this.spaces = spaces;
    }

}