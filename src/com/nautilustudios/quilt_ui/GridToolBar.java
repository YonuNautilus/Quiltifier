package com.nautilustudios.quilt_ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;

@SuppressWarnings("serial")
public class GridToolBar extends JToolBar {
	
	ArrayList<Grid> grids = new ArrayList<Grid>();
	
	
	static JTextField jtf = new JTextField();
	static JLabel jl = new JLabel("Name:");
	
	public GridToolBar() {
		super(JToolBar.VERTICAL);
		setLayout(new GridLayout(4, 0));
		this.setPreferredSize(new Dimension(100, 100));
		this.add(jl);
		this.add(jtf);
		jtf.setPreferredSize(new Dimension(100, 20));
		this.addSeparator(new Dimension(10, 10));
		grids.add(new Grid("D:\\jacob\\Documents\\Cat.qlt"));
		this.add(grids.get(0));
	}
	
	public Grid getData(int index) {
		return grids.get(index);
	}
	
	public void addData(Grid gd) {
		grids.add(gd);
		this.add(gd);
	}
	
	public void refresh() {
		this.repaint();
		this.validate();
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(Color.black);
	}
}
