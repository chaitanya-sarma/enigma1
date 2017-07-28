package org.icrisat.genomicSelection.components.loginPanel;

import java.awt.Frame;
import java.awt.event.ActionEvent;

public class GerminateLoginPanel extends LoginPanel {

	private Frame parent;

	public GerminateLoginPanel(Frame parent) {
		super(parent, true, "Germinate Login");
		this.parent = parent;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String password = String.valueOf(passwordField.getPassword());
	}
}
