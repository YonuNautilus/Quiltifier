package com.nautilustudios.quilt_main;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JSpinner;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.nautilustudios.quilt_ui.GridToolBar;
import com.nautilustudios.quilt_ui.MainPanel;
import com.nautilustudios.quilt_ui.MenuBar;
import com.nautilustudios.quilt_ui.ToolBar;

public class App {
	
	public enum ToolState {
		BRUSH,
		CUT
	}
	
	public static ToolBar tb;
	public static MainPanel mp;
	public static MenuBar mb;
	public static GridToolBar gtb;
	
	private static String curFile = "";

	public static void main(String[] args) {
		JFrame window = new JFrame ("Quiltifier");
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(true);
		window.setSize(800, 600);
		
		mb = new MenuBar();
		window.setJMenuBar(mb);
		
		tb = new ToolBar();
		window.add(tb, BorderLayout.NORTH);

		mp = new MainPanel();
		window.add(mp, BorderLayout.CENTER);

		gtb = new GridToolBar();
		window.add(gtb, BorderLayout.EAST);
		
		window.pack();
		window.setVisible(true);
		
		mb.getMenu(0).getItem(2).addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				doSave();
			}
		});
		
		mb.getMenu(0).getItem(1).addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				doOpen();
			}
		});
		
		try {
			((JSpinner)tb.getComponent(0)).addChangeListener(new ChangeListener() {
				public void stateChanged(ChangeEvent e) {
					mp.mainGrid.setWidth((Integer)((JSpinner)e.getSource()).getValue());
					mp.refresh();
				}
			});
			
			((JSpinner)tb.getComponent(2)).addChangeListener(new ChangeListener() {
				public void stateChanged(ChangeEvent e) {
					mp.mainGrid.setHeight((Integer)((JSpinner)e.getSource()).getValue());
					mp.refresh();
				}
			});
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	/***
	 * Opens a dialog that asks the user if they want to save, discard unsaved changes, or cancel the operation
	 * @return The value returned from the JOptionPanel (yes, no, cancel)
	 */
	private static int unsavedChangesCheck() {
		return JOptionPane.showConfirmDialog(null, "There are unsaved changes -- would you like to save them now?"
				+ "\n\n Yes: Save current pattern first"
				+ "\n No: Discard unsaved changes of current pattern file"
				+ "\n Cancel: Go back");
	}
	
	
	/***
	 * Does a save of the current file, or does a save-as if there is no current file opened.
	 */
	private static void doSave() {
		if(curFile == "") {
			doSaveAs();
		} else {
			try {
				File saveFile = new File(curFile);
				if(saveFile.exists() && saveFile.canWrite()) {
					System.out.println("saving new file...");
					FileWriter sfw = new FileWriter(saveFile.getPath());
					sfw.write(getXMLString());
					sfw.close();
					mp.setUnsavedChanges(false);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/***
	 * Opens a file selected by the user.
	 */
	private static void doOpen() {
		if(mp.hasUnsavedChanges()) {
			int changeCheck = unsavedChangesCheck();
			if(changeCheck == JOptionPane.YES_OPTION) {
				doSave();
			} else if (changeCheck == JOptionPane.NO_OPTION) {
				//don't do stuff, just continue on to opening process.
				//Really we don't need this else if check, but keeping it for completeness
			} else if (changeCheck == JOptionPane.CANCEL_OPTION) {
				return;
			}
		}
		
		//If no unsaved changes, OR after unsaved changes have been handled: proceed with opening file
		
		JFileChooser jfc = new JFileChooser();
		jfc.setDialogTitle("Choose File To Open");
		jfc.setFileFilter(new FileNameExtensionFilter("Quiltifier File", ".qlt"));
		int retVal = jfc.showOpenDialog(null);
		
		if (retVal == JFileChooser.CANCEL_OPTION || retVal == JFileChooser.ERROR_OPTION) {
			JOptionPane.showMessageDialog(null, "Did not open a file, just to let you know...");
			return;
		}
		
		
	}
	
	/***
	 * Opens a file selection dialog to save a new file.
	 */
	private static void doSaveAs() {
		JFileChooser jfc = new JFileChooser();
		
		jfc.setDialogTitle("Choose File Save Location");
		jfc.setFileFilter(new FileNameExtensionFilter("Quiltifier File", ".qlt"));
		jfc.setApproveButtonText("Save File");
		int retVal = jfc.showOpenDialog(null);
		
		if (retVal == JFileChooser.CANCEL_OPTION || retVal == JFileChooser.ERROR_OPTION) {
			return;
		} else if (retVal == JFileChooser.APPROVE_OPTION) {
			jfc.setSelectedFile(new File(jfc.getSelectedFile().getPath() + ".qlt"));
			File saveFile = jfc.getSelectedFile();
			try {
				if(saveFile.createNewFile()) {
					System.out.println("saving new file...");
					FileWriter sfw = new FileWriter(saveFile.getPath());
					sfw.write(getXMLString());
					sfw.close();
					curFile = saveFile.getPath() + ".qlt";
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/***
	 * Gets the XML string from the main panel.
	 * @return A string that is the XML data of the pattern grid.
	 */
	private static String getXMLString() {
		return mp.getXMLString();
	}
	
	/***
	 * Gets the enumerator value of the currently selected tool.
	 * @return Enum value of currently selected tool.
	 */
	public static ToolState getToolState() {
		return tb.getToolState();
	}

}
