package com.nautilustudios.quilt_ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.nautilustudios.quilt_main.App;
import com.nautilustudios.quilt_main.App.ToolState;

import javax.swing.JToolBar;
import javax.swing.JToggleButton;
import javax.swing.JSpinner;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JRadioButton;

@SuppressWarnings("serial")
public class ToolBar extends JToolBar {
	
	private String lockEmoji = new String(Character.toChars(Integer.parseInt("1F512", 16)));
	private String unlockEmoji = new String(Character.toChars(Integer.parseInt("1F513", 16)));
	
	private String scissorsEmoji = new String(Character.toChars(Integer.parseInt("2702", 16)));
	private String brushEmoji = new String(Character.toChars(Integer.parseInt("1F58C", 16)));
	
	private String bucket = (new String(Character.toChars(Integer.parseInt("2294", 16)))) + (new String(Character.toChars(Integer.parseInt("0311", 16))));
	

	private boolean dimsLocked = false;
	public boolean getLocked() { return dimsLocked; }
	
	private Color primColor = Color.red;
	public Color getPrimColor() { return primColor; }
	private Color secColor = Color.blue;
	public Color getSecColor() { return secColor; }
	
	
	 JRadioButton brushButton;
	 JRadioButton cutButton;
	 ButtonGroup toolButtonGroup;

	public ToolBar() {
		 JSpinner wSpinner = new JSpinner();
		 JToggleButton lockButton = new JToggleButton(unlockEmoji);
		 JSpinner hSpinner = new JSpinner();
		 
		 JButton primColorButton = new JButton("     ");
		 JColorChooser jccPrim = new JColorChooser(primColor);
		 //primColorButton.add(jccPrim);
		 primColorButton.setBackground(primColor);
		 
		 JButton secColorButton = new JButton("     ");
		 JColorChooser jccSec = new JColorChooser(secColor);
		 //secColorButton.add(jccSec);
		 secColorButton.setBackground(secColor);
		 
		 toolButtonGroup = new ButtonGroup();
		 brushButton = new JRadioButton(brushEmoji);
		 cutButton = new JRadioButton(scissorsEmoji);
		 toolButtonGroup.add(brushButton);
		 toolButtonGroup.add(cutButton);
		 brushButton.setSelected(true);
		 
		 wSpinner.setValue(10);
		 hSpinner.setValue(10);
		 
		 /*
		  * Actions/Events
		  */
		 ActionListener lockButtonAction = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JToggleButton tBtn = (JToggleButton)e.getSource();
				if(tBtn.isSelected()) {
					tBtn.setText(lockEmoji);
					dimsLocked = true;
				} else {
					tBtn.setText(unlockEmoji);
					dimsLocked = false;
				}
			}
		 };
		 lockButton.addActionListener(lockButtonAction);
		 
		 
		 ChangeListener wSpinnerAction = new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				 if(dimsLocked) {
					 hSpinner.setValue(wSpinner.getValue());
				 }
			}
		 };
		 wSpinner.addChangeListener(wSpinnerAction);
		 
		 
		 ChangeListener hSpinnerAction = new ChangeListener() {
			 public void stateChanged(ChangeEvent e) {
				 if(dimsLocked) {
					 wSpinner.setValue(hSpinner.getValue());
				 }
			 }
		 };
		 hSpinner.addChangeListener(hSpinnerAction);
		 
		 
		 ActionListener colorButtonAction = new ActionListener() {
			 public void actionPerformed(ActionEvent e) {
				 
				 JButton btn = (JButton) e.getSource();
				 Color initCol;
				 
				 JColorChooser jcc;
				 
				 if(btn.equals(primColorButton)) {
					 initCol = primColor;
					 jcc = jccPrim;
				 } else {
					 initCol = secColor;
					 jcc = jccSec;
				 }
				 
				 ActionListener okListener = new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						Color newColor = jcc.getColor();
						btn.setBackground(newColor);
						if(btn.equals(primColorButton)) {
							primColor = newColor;
						} else {
							secColor = newColor;
						}
					}
				 };
				 JColorChooser.createDialog(null, "Choose Color", true, jcc, okListener, null).setVisible(true);
			 }
		 };
		 primColorButton.addActionListener(colorButtonAction);
		 secColorButton.addActionListener(colorButtonAction);
		 
		 
		 this.add(wSpinner);
		 this.add(lockButton);
		 this.add(hSpinner);
		 this.addSeparator(new Dimension(10, 10));
		 this.add(primColorButton);
		 this.add(secColorButton);
		 this.addSeparator(new Dimension(10, 10));
		 this.add(brushButton);
		 this.add(cutButton);
	}
	
	
	public App.ToolState getToolState() {
		if(brushButton.isSelected()) {
			return ToolState.BRUSH;
		} else {
			return ToolState.CUT;
		}
	}
	
}








