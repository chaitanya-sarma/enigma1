package org.icrisat.genomicSelection.util;
/**
 * "data" : [ {
      "name" : "vcc_dominant-vcc_dominant",
      "matrixDbId" : "1",
      "description" : "Dummy description 1",
      "lastUpdated" : "2017-07-26",
      "studyDbId" : "1"
    } ]
 *
 */
public class AlleleMatrices {
	private String name;
	private String matrixDbId;
	private String description;
	private String lastUpdated;
	private String studyDbId;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getMatrixDbId() {
		return matrixDbId;
	}
	public void setMatrixDbId(String matrixDbId) {
		this.matrixDbId = matrixDbId;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getLastUpdated() {
		return lastUpdated;
	}
	public void setLastUpdated(String lastUpdated) {
		this.lastUpdated = lastUpdated;
	}
	public String getStudyDbId() {
		return studyDbId;
	}
	public void setStudyDbId(String studyDbId) {
		this.studyDbId = studyDbId;
	}
}
