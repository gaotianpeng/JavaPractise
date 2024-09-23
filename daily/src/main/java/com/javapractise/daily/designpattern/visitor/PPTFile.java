package com.javapractise.daily.designpattern.visitor;

public class PPTFile extends ResourceFile{

    public PPTFile(String filePath) {
        super(filePath);
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
