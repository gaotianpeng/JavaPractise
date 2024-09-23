package com.javapractise.daily.designpattern.visitor;

public class Compressor implements Visitor {
    public void visit(PPTFile pptFile) {
        System.out.println("Compress ppt" );
    }

    public void visit(PdfFile pdfFile) {
        System.out.println("Compress pdf");
    }

    public void visit(WordFile wordFile) {
        System.out.println("Compress word");
    }
}
