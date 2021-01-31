package com.nautilustudios.quilt_ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.util.ArrayList;
import javax.swing.JPanel;

import java.io.File;
import java.io.StringWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.nautilustudios.quilt_main.App;

public class Grid extends JPanel{
	
	private String name = "NewGrid";

	private ArrayList<ArrayList<GridSquare>> gridList;
	
	private int sqSize = 50;
	
	private int rows, cols;
	
	public Grid(int rCount, int cCount, int sSize) {
		rows = rCount;
		cols = cCount;
		
		sqSize = sSize;
		
		this.setLayout(new GridLayout(rows, cols));
		
		//this.setBorder(BorderFactory.createLineBorder(Color.green, 2));
		
		this.setSize(sqSize * cols, sqSize * rows);
		
		gridList = new ArrayList<ArrayList<GridSquare>>();
		
		//first dimension of gridList is x (column, or the horizontal)
		for(int c = 0; c < cols; c++) {
			gridList.add(new ArrayList<GridSquare>());
			for(int r = 0; r < rows; r++) {
				gridList.get(c).add(new GridSquare(sqSize * c, sqSize * r, sqSize));
				this.add(gridList.get(c).get(r));
			}
		}
	}
	
	public String getXML() {
		String saveData = "";
		
		try {
			DocumentBuilderFactory docFac = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFac.newDocumentBuilder();
			
			//Root
			Document doc = docBuilder.newDocument();
			
			Element gridElement = doc.createElement("grid");
			gridElement.setAttribute("name", this.name);
			gridElement.setAttribute("width", String.valueOf(cols));
			gridElement.setAttribute("height", String.valueOf(rows));
			gridElement.setAttribute("primColor", String.valueOf(App.tb.getPrimColor().getRGB()));
			gridElement.setAttribute("secColor", String.valueOf(App.tb.getSecColor().getRGB()));
			
			//read across each row. (Writing data left to right, then top to bottom)
			for(int r = 0; r < rows; r++){
				for(int c = 0; c < cols; c++) {
					Element gsElm = doc.createElement("gridSquare");
					GridSquare gs = gridList.get(c).get(r);
					for(int i = 0; i < 4; i++){
						Element e = doc.createElement("unit_" + String.valueOf(i));
						e.setAttribute("color", String.valueOf(gs.getUnit(i).bgColor.getRGB()));
						gsElm.appendChild(e);
					}
					gridElement.appendChild(gsElm);
				}
			}
			
			doc.appendChild(gridElement);
			
			saveData = writeXmlDocumentToXmlFile(doc);
			
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
		
		return saveData;
	}
	
	
	private static String writeXmlDocumentToXmlFile(Document xmlDocument)
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
	
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(Color.black);
	}


}
