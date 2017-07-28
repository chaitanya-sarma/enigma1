package org.icrisat.genomicSelection.components.gobiiPanels;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class GobiiMainPanel extends JDialog implements ActionListener{

	private Frame parent;
	private JPanel mainPanel;
	private JLabel label;
	public GobiiMainPanel(Frame parent, boolean modal) {
		super(parent, modal);
		this.parent = parent;
		System.out.println("yeehaw");
		Dimension d = new Dimension(400, 300);
		setTitle("Gobii Main Panel");
		setSize(d);
		mainPanel = new JPanel();
		mainPanel.setSize(d);
		add(mainPanel);
		label = new JLabel("Its main panel");
		mainPanel.add(label);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
	}
}
