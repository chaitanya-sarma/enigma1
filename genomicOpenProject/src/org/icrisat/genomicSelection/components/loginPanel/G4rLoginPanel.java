package org.icrisat.genomicSelection.components.loginPanel;

import java.awt.Frame;
import java.awt.event.ActionEvent;

public class G4rLoginPanel extends LoginPanel {

	private Frame parent;

	public G4rLoginPanel(Frame parent) {
		super(parent, true, "G4R Login");
		this.parent = parent;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String password = String.valueOf(passwordField.getPassword());

	}

}
