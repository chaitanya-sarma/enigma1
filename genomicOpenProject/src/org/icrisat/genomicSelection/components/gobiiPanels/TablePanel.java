package org.icrisat.genomicSelection.components.gobiiPanels;

import java.awt.BorderLayout;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import org.icrisat.genomicSelection.util.AlleleMatrices;

public class TablePanel extends JPanel {

	private JTable table;
	private AlleleMatricesTableModel tableModel;

	public TablePanel() {
		tableModel = new AlleleMatricesTableModel();
		table = new JTable(tableModel);
		setLayout(new BorderLayout());
		add(new JScrollPane(table), BorderLayout.CENTER);
	}

	public void setData(List<AlleleMatrices> alleleMatrices) {
		tableModel.setData(alleleMatrices);
	}

	public void refresh() {
		tableModel.fireTableDataChanged();
	}
}
