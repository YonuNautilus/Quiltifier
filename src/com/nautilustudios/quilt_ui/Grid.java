package com.nautilustudios.quilt_ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.util.ArrayList;
import javax.swing.JPanel;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Node;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.nautilustudios.quilt_main.App;
import com.nautilustudios.quilt_main.CommonMethods;

@SuppressWarnings("serial")
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
		this.setSize(sqSize * cols, sqSize * rows);
		
		gridList = new ArrayList<ArrayList<GridSquare>>();
		
		//first dimension of gridList is y (row, or the vertical!!!)
		//this is because the GridLayout puts new components left to right, then top to bottom (like writing)
		//So the second dimension needs to be on the inner for loop (so that things are added in the correct order)
		for(int r = 0; r < cols; r++) {
			gridList.add(new ArrayList<GridSquare>());
			for(int c = 0; c < rows; c++) {
				gridList.get(r).add(new GridSquare(sqSize * c, sqSize * r, sqSize));
				this.add(gridList.get(r).get(c));
			}
		}
	}
	
	
	public Grid(String xmlFilePath) {
		try {
			File f = new File(xmlFilePath);
			
			DocumentBuilderFactory docFac = DocumentBuilderFactory.newInstance();
			docFac.setIgnoringElementContentWhitespace(true);
			DocumentBuilder docBuilder = docFac.newDocumentBuilder();
			
			//Root
			Document doc = docBuilder.parse(f);
			
			doc.getDocumentElement().normalize();
			
			Element root = doc.getDocumentElement();
			
			System.out.println("Root element: " + root.getNodeName());
			System.out.println("Child Count : " + root.getChildNodes().getLength());
			
			NodeList nl = doc.getDocumentElement().getChildNodes();
			System.out.println(nl.getLength());
			for(int i = 0; i < nl.getLength(); i++) {
				 Node n = nl.item(i);
				if(n.getNodeType() == Node.ELEMENT_NODE) {
					Element e = (Element)n;
					
					this.setHeight(Integer.parseInt(e.getAttribute("height")));
					this.setWidth(Integer.parseInt(e.getAttribute("width")));
					
					NodeList clList = e.getChildNodes();
				}
			}
			
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}
		
	}
	
	public Document getXML() {
		Document retNode = null;
		
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
					GridSquare gs = gridList.get(r).get(c);
					gsElm.setAttribute("unit_count", String.valueOf(gs.getUnitCount()));
					for(int i = 0; i < gs.getUnitCount(); i++){
						Element e = doc.createElement("unit_" + String.valueOf(i));
						e.setAttribute("color", String.valueOf(gs.getUnit(i).bgColor.getRGB()));
						gsElm.appendChild(e);
					}
					gridElement.appendChild(gsElm);
				}
			}
			
			doc.appendChild(gridElement);
			
			retNode = doc;
			
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
		
		return retNode;
	}
	
	
	public String getXMLString() {
		String saveData = "";
		
		Document d = getXML();
		
		if(d != null) {
			saveData = CommonMethods.writeXmlDocumentToXmlFile(d);
		}
		
		return saveData;
	}
	
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(Color.black);
	}
	
	public void setHeight(int h) {
		rows = h;
		
		int deltaH = h - gridList.size();
		int staticSize = gridList.size();
		
		if(deltaH < 0) {
			for(int i = rows - 1; i > staticSize + deltaH; i--) {
				ArrayList<GridSquare> temp = gridList.remove(i);
				for(GridSquare gs : temp) {
					this.remove(gs);
				}
			}
		} else {
			ArrayList<GridSquare> temp = new ArrayList<GridSquare>();
			for(int i = 0; i < cols; i++) {
				temp.add(new GridSquare(sqSize * rows, sqSize * i, sqSize));
			}
			gridList.add(temp);
		}
		this.repaint();
	}

	public void setWidth(int w) {
		cols = w;
		
		int deltaW = w - gridList.get(0).size();
		int staticSize = gridList.get(0).size();
		
		//remove the last item from every row (from every list in the first dimension) -- this removes the last column
		if(deltaW < 0) {
			for(int i = 0; i < rows; i++) {
				this.remove(gridList.get(i).remove(gridList.get(i).size() - 1));
			}
		} else if(deltaW > 0) {
			for(int i = 0; i < rows; i++) {
				gridList.get(i).add(new GridSquare(sqSize * gridList.get(i).size(), sqSize * i, sqSize));
			}
		}
		this.repaint();
	}

	public void refresh() {
		this.removeAll();
		this.setLayout(new GridLayout(rows, cols));
		for(int r = 0; r < rows; r++) {
			for(int c = 0; c < cols; c++) {
				this.add(gridList.get(r).get(c));
			}
		}
		setPreferredSize(new Dimension(sqSize * cols, sqSize * rows));
		this.repaint();
		this.validate();
	}
	
	
	public String getName() {
		return name + "\n" + "(" + String.valueOf(cols) + "x" + String.valueOf(rows) + ")";
	}
	
	public void changeSize(int size) {
		sqSize = size;
		this.setSize(sqSize * cols, sqSize * rows);
		for(ArrayList<GridSquare> gsl: gridList) {
			for(GridSquare gs: gsl) {
				gs.setPreferredSize(new Dimension(sqSize, sqSize));
			}
		}
	}

}
