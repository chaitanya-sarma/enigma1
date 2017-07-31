package org.icrisat.genomicSelection.components.gobiiPanels;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import org.icrisat.genomicSelection.util.AlleleMatrices;

public class AlleleMatricesTableModel extends AbstractTableModel {

	private List<AlleleMatrices> alleleMatrices;
	private String[] columnNames = {"Name", "MatrixDbId", "Description", "LastUpdated", "StudyDbId"};
 	
	public void setData(List<AlleleMatrices> alleleMatrices) {
		this.alleleMatrices = alleleMatrices;
	}

	@Override
	public int getColumnCount() {
		return 5;
	}

	@Override
	public int getRowCount() {
		return alleleMatrices.size();
	}
	

	@Override
	public String getColumnName(int column) {
		return columnNames[column];
	}

	@Override
	public Object getValueAt(int row, int col) {
		AlleleMatrices alleleMatrix = alleleMatrices.get(row);
		switch (col) {
		case 0:
			return alleleMatrix.getName();
		case 1:
			return alleleMatrix.getMatrixDbId();
		case 2:
			return alleleMatrix.getDescription();
		case 3:
			return alleleMatrix.getLastUpdated();
		case 4:
			return alleleMatrix.getStudyDbId();
		default:
			return alleleMatrix.getMatrixDbId();
		}
	}
	public AlleleMatrices getAlleleMatrix(int rowNo){
		return alleleMatrices.get(rowNo);
	}
}
