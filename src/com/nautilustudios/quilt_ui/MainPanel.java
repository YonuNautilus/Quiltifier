package com.nautilustudios.quilt_ui;

import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import com.nautilustudios.quilt_main.CommonMethods;

@SuppressWarnings("serial")
public class MainPanel extends JPanel {

	public Grid mainGrid;
	
	private ArrayList<Grid> gridList = new ArrayList<Grid>();
	
	private boolean hasUnsavedChanges = false;
	
	public MainPanel() {
		mainGrid = new Grid(10, 10, 50);
		this.add(mainGrid);
		this.setSize(mainGrid.getSize());
		gridList.add(mainGrid);
	}
	
	public boolean hasUnsavedChanges() {
		return hasUnsavedChanges;
	}
	
	public void setUnsavedChanges(boolean un) {
		hasUnsavedChanges = un;
	}
	
	public Document getXML() {
		Document rootNode = null;
		try {
			DocumentBuilderFactory docFac = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFac.newDocumentBuilder();
			
			//Root
			rootNode = docBuilder.newDocument();
			
			Element elm = rootNode.createElement("gridList");
			
			for(int i = 0; i < gridList.size(); i++) {
				Node importedNode = rootNode.importNode(gridList.get(i).getXML().getFirstChild(), true);
				elm.appendChild(importedNode);
			}
			rootNode.appendChild(elm);
			
		} catch(Exception e) {
			e.printStackTrace();
		}

		
		return rootNode;
	}
	
	public String getXMLString() {
		Document doc = getXML();
		
		if(doc != null) {
			return CommonMethods.writeXmlDocumentToXmlFile(doc);
		} else {
			return "";
		}
	}
	
	public boolean openFile(String data) {
		
		return true;
	}
	
	public void refresh() {
		mainGrid.refresh();
		this.setSize(mainGrid.getSize());
		try {
			Thread.sleep(1);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		this.repaint();
		this.validate();
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
	}
	
	
}
