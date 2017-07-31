package org.icrisat.genomicSelection.components.loginPanel;

import java.awt.Frame;
import java.awt.event.ActionEvent;

import org.icrisat.genomicSelection.components.gobiiPanels.GobiiMainPanel;

public class GobiiLoginPanel extends LoginPanel {

	private Frame parent;
	private GobiiMainPanel mainPanel;
	public GobiiLoginPanel(Frame parent) {
		super(parent, true, "Gobii Login");
		this.parent = parent;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		validateUserFields();
		this.setVisible(false);
		mainPanel = new GobiiMainPanel(parent, true);
		mainPanel.setVisible(true);
	}
}
