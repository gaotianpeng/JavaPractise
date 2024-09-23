package com.javapractise.daily.designpattern.visitor;

public class WordFile extends ResourceFile{

    public WordFile(String filePath) {
        super(filePath);
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
