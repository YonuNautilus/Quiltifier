package com.nautilustudios.quilt_ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Polygon;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Random;

import javax.swing.SwingUtilities;
import javax.swing.BorderFactory;
import javax.swing.JPanel;

import com.nautilustudios.quilt_main.App;

public class GridSquare extends JPanel {	
	
	private Unit[] units;
	private int size;
	
	private static Color DEFAULT_COLOR = Color.WHITE;
	private static Color DEFAULT_BORDER_COLOR = Color.BLACK;
	
	public class Unit implements MouseListener{
		public Color bgColor;
		public Color bordColor;
		
		private Polygon poly;
		
		public Unit(int[] xPoints, int[] yPoints, int numPoints) {
			poly = new Polygon(xPoints, yPoints, numPoints);
			
			addMouseListener(this);
		}
		
		public void setColor(Color c) {
			bgColor = c;
		}
		
		public void setColor(Color c, Color borC) {
			bgColor = c;
			bordColor = borC;
		}

		public void mouseClicked(MouseEvent e) {}
		public void mousePressed(MouseEvent e) {
			if(poly.contains(e.getPoint())) {
				if(App.getToolState() == App.ToolState.BRUSH) {
					if(SwingUtilities.isLeftMouseButton(e)) {
						this.setColor(App.tb.getPrimColor());
						repaint();
						App.mp.setUnsavedChanges(true);
					}
					if(SwingUtilities.isRightMouseButton(e)) {
						this.setColor(App.tb.getSecColor());
						repaint();
						App.mp.setUnsavedChanges(true);
					}
				} else {
					getParent().dispatchEvent(e);
				}
			}
		}
		public void mouseReleased(MouseEvent e) {}
		public void mouseEntered(MouseEvent e) {}
		public void mouseExited(MouseEvent e) {}
		
	}
	
	public GridSquare(int xLoc, int yLoc, int s) {
		units = new Unit[4];
		//x = xLoc;
		//y = yLoc;
		size = s;
		
		units[0] = new Unit(new int[]{0, size, size/2}, new int[]{0, 0, size/2}, 3);
		units[0].setColor(DEFAULT_COLOR, DEFAULT_BORDER_COLOR);
		units[1] = new Unit(new int[]{size, size, size/2}, new int[]{0, size, size/2}, 3);
		units[1].setColor(DEFAULT_COLOR, DEFAULT_BORDER_COLOR);
		units[2] = new Unit(new int[]{size, 0, size/2}, new int[]{size, size, size/2}, 3);
		units[2].setColor(DEFAULT_COLOR, DEFAULT_BORDER_COLOR);
		units[3] = new Unit(new int[]{0, 0, size/2}, new int[]{size, 0, size/2}, 3);
		units[3].setColor(DEFAULT_COLOR, DEFAULT_BORDER_COLOR);
		
		this.setSize(size, size);
		this.setPreferredSize(new Dimension(size, size));
		this.setLocation(xLoc, yLoc);
		
		Random rand = new Random();
		
		//this.setBorder(BorderFactory.createLineBorder(new Color(rand.nextFloat(), rand.nextFloat(), rand.nextFloat())));
		
		this.addMouseListener(new MouseListener() {

			public void mouseClicked(MouseEvent e) {}
			public void mousePressed(MouseEvent e) {
				if(App.getToolState() == App.ToolState.CUT && SwingUtilities.isLeftMouseButton(e)) {
					//Switch unit states (4 to 8, or 8 to 4)
					Color[] prevCol = new Color[4];
					//System.out.println(this.getClass().getName());
					GridSquare gs = (GridSquare)e.getSource();
					if(gs.units.length == 4) {
						prevCol[0] = gs.getUnit(0).bgColor;
						prevCol[1] = gs.getUnit(1).bgColor;
						prevCol[2] = gs.getUnit(2).bgColor;
						prevCol[3] = gs.getUnit(3).bgColor;
						
						gs.units = new Unit[8];
						units[0] = new Unit(new int[]{0, size/2, size/2}, new int[]{0, 0, size/2}, 3);
						units[0].setColor(prevCol[0], DEFAULT_BORDER_COLOR);
						units[1] = new Unit(new int[]{size/2, size, size/2}, new int[]{0, 0, size/2}, 3);
						units[1].setColor(prevCol[0], DEFAULT_BORDER_COLOR);
						
						units[2] = new Unit(new int[]{size, size, size/2}, new int[]{0, size/2, size/2}, 3);
						units[2].setColor(prevCol[1], DEFAULT_BORDER_COLOR);
						units[3] = new Unit(new int[]{size, size, size/2}, new int[]{size/2, size, size/2}, 3);
						units[3].setColor(prevCol[1], DEFAULT_BORDER_COLOR);
						
						units[4] = new Unit(new int[]{size, size/2, size/2}, new int[]{size, size, size/2}, 3);
						units[4].setColor(prevCol[2], DEFAULT_BORDER_COLOR);
						units[5] = new Unit(new int[]{size/2, 0, size/2}, new int[]{size, size, size/2}, 3);
						units[5].setColor(prevCol[2], DEFAULT_BORDER_COLOR);
						
						units[6] = new Unit(new int[]{0, 0, size/2}, new int[]{size, size/2, size/2}, 3);
						units[6].setColor(prevCol[3], DEFAULT_BORDER_COLOR);
						units[7] = new Unit(new int[]{0, 0, size/2}, new int[]{size/2, 0, size/2}, 3);
						units[7].setColor(prevCol[3], DEFAULT_BORDER_COLOR);
						
						gs.repaint();
						
					} else {
						prevCol[0] = gs.getUnit(0).bgColor;
						prevCol[1] = gs.getUnit(2).bgColor;
						prevCol[2] = gs.getUnit(4).bgColor;
						prevCol[3] = gs.getUnit(6).bgColor;
						
						gs.units = new Unit[4];
						
						units[0] = new Unit(new int[]{0, size, size/2}, new int[]{0, 0, size/2}, 3);
						units[0].setColor(prevCol[0], DEFAULT_BORDER_COLOR);
						units[1] = new Unit(new int[]{size, size, size/2}, new int[]{0, size, size/2}, 3);
						units[1].setColor(prevCol[1], DEFAULT_BORDER_COLOR);
						units[2] = new Unit(new int[]{size, 0, size/2}, new int[]{size, size, size/2}, 3);
						units[2].setColor(prevCol[2], DEFAULT_BORDER_COLOR);
						units[3] = new Unit(new int[]{0, 0, size/2}, new int[]{size, 0, size/2}, 3);
						units[3].setColor(prevCol[3], DEFAULT_BORDER_COLOR);
						
						gs.repaint();
					}
				}
			}
			public void mouseReleased(MouseEvent e) {}
			public void mouseEntered(MouseEvent e) {}
			public void mouseExited(MouseEvent e) {}
			
		});
	}
	
	public Unit getUnit(int i) {
		if(i < units.length && i >= 0) {
			return units[i];
		} else {
			return null;
		}
	}

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		for (Unit u : units) {
			g.setColor(u.bgColor);
			g.fillPolygon(u.poly);
			g.setColor(u.bordColor);
			g.drawPolygon(u.poly);
		}
	}
	
}
