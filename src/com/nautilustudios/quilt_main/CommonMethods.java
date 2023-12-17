package com.nautilustudios.quilt_main;

import java.io.StringWriter;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;

public class CommonMethods {
	
	public static String writeXmlDocumentToXmlFile(Document xmlDocument)
	{
	    TransformerFactory tf = TransformerFactory.newInstance();
	    Transformer transformer;
	    try {
	        transformer = tf.newTransformer();
	        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
	        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
	        transformer.setOutputProperty("omit-xml-declaration", "yes");
	         
	        // Uncomment if you do not require XML declaration
	        // transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
	         
	        //A character stream that collects its output in a string buffer, 
	        //which can then be used to construct a string.
	        StringWriter writer = new StringWriter();
	 
	        //transform document to string 
	        transformer.transform(new DOMSource(xmlDocument), new StreamResult(writer));
	 
	        String xmlString = writer.getBuffer().toString();   
	        return xmlString;
	    } 
	    catch (TransformerException e) 
	    {
	        e.printStackTrace();
	    }
	    catch (Exception e) 
	    {
	        e.printStackTrace();
	    }
	    return "";
	}

}
