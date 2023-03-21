package com.example.practicaguiada17;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.jar.Attributes;

public class ManejadorXML extends DefaultHandler {
    private final StringBuilder resultado = new StringBuilder();
    private final StringBuilder nodoTexto = new StringBuilder();
    private boolean entryTag;
    private String total;

    public String getResultado() {
        return resultado.toString();
    }

    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        nodoTexto.setLength(0);
        if (localName.equals("entry"))
            entryTag = true;
    }

    @Override
    public void characters(char[] ch, int start, int length)
            throws SAXException {
        nodoTexto.append(ch, start, length);
    }

    @Override
    public void endElement(String uri, String localName, String qName)
            throws SAXException {
        if (localName.equals("totalResults")) {
            total = nodoTexto.toString();
        } else if (localName.equals("itemsPerPage")) {
            resultado.append("Mostrando ");
            resultado.append(nodoTexto);
            resultado.append(" resultados de ");
            resultado.append(total);
            resultado.append("\n\n");
        } else if (entryTag && qName.equals("title")) {
            resultado.append(nodoTexto);
            resultado.append("\n");
        } else if (localName.equals("entry")) {
            entryTag = false;
            resultado.append("\n");
        }
    }
}

