package com.javapractise.daily.designpattern.visitor;

public class PdfFile extends ResourceFile{
    public PdfFile(String filePath) {
        super(filePath);
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
