package com.nautilustudios.quilt_ui;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

public class MainPanel extends JPanel {

	public Grid mainGrid;
	
	private boolean hasUnsavedChanges = false;
	
	public MainPanel() {
		mainGrid = new Grid(10, 10, 50);
		this.add(mainGrid);
		this.setSize(mainGrid.getSize());
	}
	
	public boolean hasUnsavedChanges() {
		return hasUnsavedChanges;
	}
	
	public void setUnsavedChanges(boolean un) {
		hasUnsavedChanges = un;
	}
	
	public String getXML() {
		return mainGrid.getXML();
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
	}
	
	
}
