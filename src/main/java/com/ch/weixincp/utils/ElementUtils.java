package com.ch.weixincp.utils;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.StringReader;
public class ElementUtils {
    private Element root = null;
    /**
     * 根据xml格式的字符串建document
     * @param sMsg
     * @throws ParserConfigurationException
     * @throws IOException
     * @throws SAXException
     */
    public ElementUtils(String sMsg) throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        StringReader sr = new StringReader(sMsg);
        InputSource is = new InputSource(sr);
        Document document = db.parse(is);
        root = document.getDocumentElement();
    }

    /**
     * 根据tagName获取内容
     * @param tagName
     * @return
     */
    public String get(String tagName){
        return root.getElementsByTagName(tagName).item(0).getTextContent();
    }
}