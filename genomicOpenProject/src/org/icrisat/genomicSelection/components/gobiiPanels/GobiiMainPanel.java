package org.icrisat.genomicSelection.components.gobiiPanels;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.icrisat.genomicSelection.util.RESTCallsGobii;

public class GobiiMainPanel extends JDialog implements ActionListener{

	private Frame parent;
	private TablePanel tablePanel;
	private JLabel label;
	public GobiiMainPanel(Frame parent, boolean modal) {
		super(parent, modal);
		this.parent = parent;
		Dimension dimension = new Dimension(400, 300);
		setTitle("Gobii Main Panel");
		setSize(dimension);
		setLocationRelativeTo(parent);
		
		tablePanel = new TablePanel();
		tablePanel.setSize(dimension);
		add(tablePanel);
		tablePanel.setData(RESTCallsGobii.getAlleleMatricesList());
		//addComponents();
		tablePanel.refresh();
	}

	private void addComponents() {
		label = new JLabel("Its main panel");
		tablePanel.add(label);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

}
