package com.javapractise.daily.designpattern.visitor;

public class Extractor implements Visitor {
    @Override
    public void visit(PdfFile pdfFile) {
        System.out.println("Extract PDF.");
    }

    public void visit(PPTFile pptFile) {
        System.out.println("Extract PPT.");
    }

    @Override
    public void visit(WordFile wordFile) {
        System.out.println("Extract WORD.");
    }
}
