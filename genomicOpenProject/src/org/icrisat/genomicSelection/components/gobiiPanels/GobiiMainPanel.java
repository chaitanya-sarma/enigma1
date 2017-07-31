package org.icrisat.genomicSelection.components.gobiiPanels;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;

import org.icrisat.genomicSelection.util.AlleleMatrices;
import org.icrisat.genomicSelection.util.RESTCallsGobii;

public class GobiiMainPanel extends JDialog implements ActionListener {

	private Frame parent;
	private TablePanel tablePanel;
	private JButton submit;

	public GobiiMainPanel(Frame parent, boolean modal) {
		super(parent, modal);
		this.parent = parent;
		Dimension dimension = new Dimension(400, 300);
		setTitle("Allele Matrices");
		setSize(dimension);
		setLocationRelativeTo(parent);
		setLayout(new BorderLayout());
		tablePanel = new TablePanel();
		tablePanel.setSize(dimension);
		add(tablePanel, BorderLayout.CENTER);

		submit = new JButton("Submit");
		submit.setFont(new Font("DejaVu Sans", Font.BOLD, 14));
		add(submit, BorderLayout.SOUTH);
		tablePanel.setData(RESTCallsGobii.getAlleleMatricesList());
		// addComponents();
		tablePanel.refresh();
		submit.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		//AlleleMatrices row = tablePanel.getSelectedRow();
	
		// Currently firing only for dataset Id. Delete this code once pipeline is working correctly.
		AlleleMatrices row = tablePanel.getFirstRow();
		RESTCallsGobii.postAlleleMatricesSearch(row.getMatrixDbId());
	}
}
