package com.nautilustudios.quilt_ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Polygon;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Random;

import javax.swing.SwingUtilities;
import javax.swing.JPanel;

import com.nautilustudios.quilt_main.App;

@SuppressWarnings("serial")
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
		
		initUnits(4);
		
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
					GridSquare gs = (GridSquare)e.getSource();
					gs.switchUnits();
					gs.repaint();
				}
			}
			public void mouseReleased(MouseEvent e) {}
			public void mouseEntered(MouseEvent e) {}
			public void mouseExited(MouseEvent e) {}
			
		});
	}
	
	private void initUnits(int count) {
		if(count == 4) {
			units[0] = new Unit(new int[]{0, size, size/2}, new int[]{0, 0, size/2}, 3);
			units[0].setColor(DEFAULT_COLOR, DEFAULT_BORDER_COLOR);
			units[1] = new Unit(new int[]{size, size, size/2}, new int[]{0, size, size/2}, 3);
			units[1].setColor(DEFAULT_COLOR, DEFAULT_BORDER_COLOR);
			units[2] = new Unit(new int[]{size, 0, size/2}, new int[]{size, size, size/2}, 3);
			units[2].setColor(DEFAULT_COLOR, DEFAULT_BORDER_COLOR);
			units[3] = new Unit(new int[]{0, 0, size/2}, new int[]{size, 0, size/2}, 3);
			units[3].setColor(DEFAULT_COLOR, DEFAULT_BORDER_COLOR);
		} else if(count == 8) {
			units[0] = new Unit(new int[]{0, size/2, size/2}, new int[]{0, 0, size/2}, 3);
			units[0].setColor(DEFAULT_COLOR, DEFAULT_BORDER_COLOR);
			units[1] = new Unit(new int[]{size/2, size, size/2}, new int[]{0, 0, size/2}, 3);
			units[1].setColor(DEFAULT_COLOR, DEFAULT_BORDER_COLOR);
			
			units[2] = new Unit(new int[]{size, size, size/2}, new int[]{0, size/2, size/2}, 3);
			units[2].setColor(DEFAULT_COLOR, DEFAULT_BORDER_COLOR);
			units[3] = new Unit(new int[]{size, size, size/2}, new int[]{size/2, size, size/2}, 3);
			units[3].setColor(DEFAULT_COLOR, DEFAULT_BORDER_COLOR);
			
			units[4] = new Unit(new int[]{size, size/2, size/2}, new int[]{size, size, size/2}, 3);
			units[4].setColor(DEFAULT_COLOR, DEFAULT_BORDER_COLOR);
			units[5] = new Unit(new int[]{size/2, 0, size/2}, new int[]{size, size, size/2}, 3);
			units[5].setColor(DEFAULT_COLOR, DEFAULT_BORDER_COLOR);
			
			units[6] = new Unit(new int[]{0, 0, size/2}, new int[]{size, size/2, size/2}, 3);
			units[6].setColor(DEFAULT_COLOR, DEFAULT_BORDER_COLOR);
			units[7] = new Unit(new int[]{0, 0, size/2}, new int[]{size/2, 0, size/2}, 3);
			units[7].setColor(DEFAULT_COLOR, DEFAULT_BORDER_COLOR);
		}
	}
	
	private void switchUnits() {
		Color[] prevCol = new Color[4];
		
		if(units.length == 4) {
			prevCol[0] = this.getUnit(0).bgColor;
			prevCol[1] = this.getUnit(1).bgColor;
			prevCol[2] = this.getUnit(2).bgColor;
			prevCol[3] = this.getUnit(3).bgColor;
			
			this.units = new Unit[8];
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
			
		} else if(units.length == 8) {
			prevCol[0] = this.getUnit(0).bgColor;
			prevCol[1] = this.getUnit(2).bgColor;
			prevCol[2] = this.getUnit(4).bgColor;
			prevCol[3] = this.getUnit(6).bgColor;
			
			this.units = new Unit[4];
			
			units[0] = new Unit(new int[]{0, size, size/2}, new int[]{0, 0, size/2}, 3);
			units[0].setColor(prevCol[0], DEFAULT_BORDER_COLOR);
			units[1] = new Unit(new int[]{size, size, size/2}, new int[]{0, size, size/2}, 3);
			units[1].setColor(prevCol[1], DEFAULT_BORDER_COLOR);
			units[2] = new Unit(new int[]{size, 0, size/2}, new int[]{size, size, size/2}, 3);
			units[2].setColor(prevCol[2], DEFAULT_BORDER_COLOR);
			units[3] = new Unit(new int[]{0, 0, size/2}, new int[]{size, 0, size/2}, 3);
			units[3].setColor(prevCol[3], DEFAULT_BORDER_COLOR);
			
		}
	}
	
	public Unit getUnit(int i) {
		if(i < units.length && i >= 0) {
			return units[i];
		} else {
			return null;
		}
	}
	
	public int getUnitCount() {
		return units.length;
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
